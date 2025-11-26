package com.example.fitmatch.wear.data

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataItem
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WearDataLayerManager(private val context: Context) {

    private val dataClient: DataClient = Wearable.getDataClient(context)

    companion object {
        private const val TAG = "WearDataLayerManager"
        private const val PRODUCT_DATA_PATH = "/fitmatch/product"
        private const val ACTION_LIKE_PATH = "/fitmatch/action/like"
        private const val ACTION_PASS_PATH = "/fitmatch/action/pass"
        private const val REQUEST_NEXT_PATH = "/fitmatch/request/next"
    }

    /**
     * Reloj solicita siguiente prenda al móvil
     */
    suspend fun requestNextProduct(): Boolean = withContext(Dispatchers.IO) {
        try {
            val putDataMapRequest = PutDataMapRequest.create(REQUEST_NEXT_PATH)
            putDataMapRequest.dataMap.putLong("timestamp", System.currentTimeMillis())

            val task = dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
            Tasks.await(task)

            Log.d(TAG, "Solicitud enviada al móvil")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error solicitando producto: ${e.message}")
            throw e
        }
    }

    /**
     * Reloj envía acción LIKE al móvil
     */
    suspend fun sendLike(productId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val putDataMapRequest = PutDataMapRequest.create(ACTION_LIKE_PATH)
            putDataMapRequest.dataMap.apply {
                putString("productId", productId)
                putLong("timestamp", System.currentTimeMillis())
            }

            val task = dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
            Tasks.await(task)

            Log.d(TAG, "LIKE enviado: $productId")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando LIKE: ${e.message}")
            throw e
        }
    }

    /**
     * Reloj envía acción PASS al móvil
     */
    suspend fun sendPass(productId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val putDataMapRequest = PutDataMapRequest.create(ACTION_PASS_PATH)
            putDataMapRequest.dataMap.apply {
                putString("productId", productId)
                putLong("timestamp", System.currentTimeMillis())
            }

            val task = dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
            Tasks.await(task)

            Log.d(TAG, "PASS enviado: $productId")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando PASS: ${e.message}")
            throw e
        }
    }

    /**
     * Móvil envía prenda al reloj
     */
    suspend fun sendProductToWear(productJson: String): Boolean = suspendCancellableCoroutine { cont ->
        try {
            val putDataMapRequest = PutDataMapRequest.create(PRODUCT_DATA_PATH)
            putDataMapRequest.dataMap.apply {
                putString("product", productJson)
                putLong("timestamp", System.currentTimeMillis())
            }

            val task = dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
            Tasks.await(task)

            Log.d(TAG, "Prenda enviada al reloj")
            cont.resume(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error enviando prenda: ${e.message}")
            cont.resumeWithException(e)
        }
    }
}