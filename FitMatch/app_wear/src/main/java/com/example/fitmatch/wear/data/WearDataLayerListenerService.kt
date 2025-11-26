package com.example.fitmatch.wear.data

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WearDataLayerListenerService : WearableListenerService() {

    companion object {
        private const val TAG = "WearDataLayer"
        const val PRODUCT_DATA_PATH = "/fitmatch/product"
        const val ACTION_PATH = "/fitmatch/action"
        const val SYNC_PATH = "/fitmatch/sync"
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d(TAG, "Mensaje recibido: ${messageEvent.path}")

        when (messageEvent.path) {
            ACTION_PATH -> {
                // Móvil recibe acción (like/pass) del reloj
                val action = String(messageEvent.data)
                handleAction(action)
            }
        }
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { dataEvent ->
            Log.d(TAG, "Datos cambiados: ${dataEvent.dataItem.uri.path}")
        }
    }

    private fun handleAction(action: String) {
        Log.d(TAG, "Acción desde reloj: $action")
        // Notificar al ViewModel en el móvil
    }
}