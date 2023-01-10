package ru.gozerov.lyceum8.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.gozerov.lyceum8.utils.sharedPrefs

class PushService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.data.forEach { entity ->
            applicationContext.sharedPrefs
                .edit()
                .putString(entity.key, entity.value)
                .apply()
        }
    }

}