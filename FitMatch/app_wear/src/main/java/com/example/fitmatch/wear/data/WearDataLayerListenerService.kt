package com.example.fitmatch.wear.data

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService

class WearDataLayerListenerService : WearableListenerService() {

    companion object {
        private const val TAG = "WearDataListener"
        const val ACTION_PRODUCT_RECEIVED = "com.example.fitmatch.wear.PRODUCT_RECEIVED"
        const val EXTRA_PRODUCT_JSON = "product_json"
        const val ACTION_DATA_UPDATED = "com.example.fitmatch.wear.DATA_UPDATED"
        const val EXTRA_MESSAGE_DATA = "message_data"
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { dataEvent ->
            val path = dataEvent.dataItem.uri.path
            Log.d(TAG, "Datos recibidos: $path")

            when (path) {
                "/fitmatch/product" -> {
                    // El móvil envió un producto
                    val dataMap = DataMapItem.fromDataItem(dataEvent.dataItem).dataMap
                    val productJson = dataMap.getString("product", "")

                    if (productJson.isNotEmpty()) {
                        Log.d(TAG, "Producto recibido: $productJson")

                        // Broadcast al ViewModel
                        val intent = Intent(ACTION_PRODUCT_RECEIVED).apply {
                            putExtra(EXTRA_PRODUCT_JSON, productJson)
                        }
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                    } else {
                        Log.e(TAG, "JSON vacío")
                    }
                }
                else -> {
                    Log.d(TAG, "⚠Path no manejado: $path")
                }
            }
        }
    }
}