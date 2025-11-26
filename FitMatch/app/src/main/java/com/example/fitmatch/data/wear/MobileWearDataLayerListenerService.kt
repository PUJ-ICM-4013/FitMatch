package com.example.fitmatch.data.wear

import android.util.Log
import com.google.android.gms.wearable.*
import com.example.fitmatch.data.wear.MobileWearDataLayerManager.Companion.ACTION_LIKE_PATH
import com.example.fitmatch.data.wear.MobileWearDataLayerManager.Companion.ACTION_PASS_PATH
import com.example.fitmatch.data.wear.MobileWearDataLayerManager.Companion.REQUEST_NEXT_PATH

class MobileWearDataLayerListenerService : WearableListenerService() {

    companion object {
        private const val TAG = "MobileWearListener"

        // EventBus o LiveData para comunicar con el ViewModel
        var onWearActionListener: ((String, String) -> Unit)? = null
        var onWearRequestNextListener: (() -> Unit)? = null
    }

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        dataEvents.forEach { dataEvent ->
            val path = dataEvent.dataItem.uri.path

            Log.d(TAG, "Datos recibidos del reloj: $path")

            when (path) {
                REQUEST_NEXT_PATH -> {
                    // 🟩 Paso 2: Reloj solicita siguiente prenda
                    Log.d(TAG, "📱 Reloj solicita siguiente prenda")
                    onWearRequestNextListener?.invoke()
                }

                ACTION_LIKE_PATH -> {
                    // 🟩 Paso 6a: Reloj envía LIKE
                    val dataMap = DataMapItem.fromDataItem(dataEvent.dataItem).dataMap
                    val productId = dataMap.getString("productId", "")
                    Log.d(TAG, "❤️ Reloj envió LIKE: $productId")
                    onWearActionListener?.invoke("LIKE", productId)
                }

                ACTION_PASS_PATH -> {
                    // 🟩 Paso 6b: Reloj envía PASS
                    val dataMap = DataMapItem.fromDataItem(dataEvent.dataItem).dataMap
                    val productId = dataMap.getString("productId", "")
                    Log.d(TAG, "👋 Reloj envió PASS: $productId")
                    onWearActionListener?.invoke("PASS", productId)
                }
            }
        }
    }
}