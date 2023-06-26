package pastukh.vova.appcompose.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import pastukh.vova.appcompose.navigation.MainNavigation
import pastukh.vova.appcompose.ui.components.theme.AppTheme
import pastukh.vova.components.receivers.DownloadingInfoReceiver

class MainActivity : ComponentActivity() {
    private val receivers = mutableListOf<BroadcastReceiver>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceivers()
        setContent {
            AppTheme {
                MainNavigation()
            }
        }
    }

    override fun onDestroy() {
        receivers.forEach { unregisterReceiver(it) }
        super.onDestroy()
    }

    private fun registerReceivers() {
        registerReceiver(
            DownloadingInfoReceiver().also { receivers.add(it) },
            IntentFilter().apply { addAction(DownloadingInfoReceiver.Constants.ACTION) }
        )
    }
}