package com.example.fitmatch.data.wear

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import com.example.fitmatch.wear.model.WearProduct
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MobileWearDataLayerManager(private val context: Context) {

    private val dataClient: DataClient = Wearable.getDataClient(context)
    private val messageClient: MessageClient = Wearable.getMessageClient(context)

    companion object {
        private const val TAG = "MobileWearManager"
        const val PRODUCT_DATA_PATH = "/fitmatch/product"
        const val ACTION_LIKE_PATH = "/fitmatch/action/like"
        const val ACTION_PASS_PATH = "/fitmatch/action/pass"
        const val REQUEST_NEXT_PATH = "/fitmatch/request/next"
    }

    /**
     * Paso 3: Móvil envía prenda al reloj
     * Llamar desde ClienteDashboardViewModel cuando se detecte solicitud del reloj
     */
    suspend fun sendProductToWear(product: WearProduct): Boolean = suspendCancellableCoroutine { cont ->
        try {
            val productJson = Json.encodeToString(product)

            val putDataMapRequest = PutDataMapRequest.create(PRODUCT_DATA_PATH)
            putDataMapRequest.dataMap.apply {
                putString("product", productJson)
                putLong("timestamp", System.currentTimeMillis())
            }

            val task = dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
            Tasks.await(task)

            Log.d(TAG, "✅ Prenda enviada al reloj: ${product.title}")
            cont.resume(true)
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error enviando prenda: ${e.message}")
            cont.resumeWithException(e)
        }
    }

    /**
     * Obtiene los nodos del reloj (para enviar mensajes)
     */
    suspend fun getConnectedNodes(): List<Node> = suspendCancellableCoroutine { cont ->
        try {
            val task = Wearable.getNodeClient(context).connectedNodes
            val nodes = Tasks.await(task)
            Log.d(TAG, "Nodos conectados: ${nodes.size}")
            cont.resume(nodes)
        } catch (e: Exception) {
            Log.e(TAG, "Error obteniendo nodos: ${e.message}")
            cont.resume(emptyList())
        }
    }
}