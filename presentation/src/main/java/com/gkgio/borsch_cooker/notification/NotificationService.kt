package com.gkgio.borsch_cooker.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.main.LaunchActivity
import com.gkgio.domain.auth.AuthUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import javax.inject.Inject

class NotificationService : FirebaseMessagingService() {
    private val TAG = NotificationService::class.java.simpleName

    @Inject
    lateinit var authUseCase: AuthUseCase

    @SuppressLint("CheckResult")
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        authUseCase.sendPushToken(token)
            .applySchedulers()
            .subscribe({

            }, {
                Timber.e(it)
            })

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
    }

    private fun showNotification(title: String?, message: String?) {
        val notificationId = NotificationID.id
        val intent = Intent(this, LaunchActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId /* Request code */,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationUtils = NotificationUtils(this)
        notificationUtils.buildNotification(title, message, pendingIntent, notificationId)
    }
}