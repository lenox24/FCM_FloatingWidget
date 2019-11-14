package com.example.shinhantest

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.R.attr.data
import android.R.attr.keySet
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




open class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val Tag: String = "ReceivedMessage"

    override fun onNewToken(token: String) {
        Log.d(Tag, "Refresh Token $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(Tag, "From : ${remoteMessage.from}")

        remoteMessage.data.isNotEmpty().let {
            Log.d(Tag, "Message data payload : ${remoteMessage.data}")

            handleNow(remoteMessage.data.toString())
        }

        remoteMessage.notification?.let {
            Log.d(Tag, "Message Notification Body : ${it.body}")
        }
    }

    private fun handleNow(str: String) {
        Log.d(Tag, "HandleNow $str")
    }

    private fun sendNotification(data: Map<String, String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


    }
}