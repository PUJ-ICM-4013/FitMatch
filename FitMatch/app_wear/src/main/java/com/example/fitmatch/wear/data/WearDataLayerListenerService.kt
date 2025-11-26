package com.example.fitmatch.wear.data

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import org.json.JSONObject

class WearDataLayerListenerService : WearableListenerService() {

    companion object {
        const val ACTION_DATA_UPDATED = "com.example.fitmatch.wear.DATA_UPDATED"
        const val EXTRA_MESSAGE_DATA = "message_data"
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        when (messageEvent.path) {
            "/match_update", "/profile_update" -> {
                val data = String(messageEvent.data, Charsets.UTF_8)

                // Broadcast para actualizar el UI
                val intent = Intent(ACTION_DATA_UPDATED).apply {
                    putExtra(EXTRA_MESSAGE_DATA, data)
                }
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            }
        }
    }
}