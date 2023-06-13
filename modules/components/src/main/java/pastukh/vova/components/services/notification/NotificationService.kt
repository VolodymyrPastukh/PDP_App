package pastukh.vova.components.services.notification

import android.content.Intent
import android.os.IBinder
import android.util.Log
import pastukh.vova.components.services.base.CoroutinesService

class NotificationService : CoroutinesService() {
    companion object {
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