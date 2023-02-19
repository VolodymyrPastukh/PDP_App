package com.example.pdpapp.services.notification

import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.example.pdpapp.services.base.CoroutinesService
import com.example.pdpapp.services.base.dependency
import org.koin.android.ext.android.getKoin

class NotificationService : CoroutinesService() {
    companion object{
        private const val TAG = "NotificationService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "$TAG - created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "$TAG - destroyed")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}