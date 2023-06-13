package pastukh.vova.pdpapp.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import pastukh.vova.components.receivers.DownloadingInfoReceiver
import pastukh.vova.pdpapp.R
import pastukh.vova.pdpapp.ui.utils.helper.DeepLinkHelper
import pastukh.vova.pdpapp.ui.utils.isNetworkAvailable


class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null
    private val receivers = mutableListOf<BroadcastReceiver>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceivers()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val startNavLink = DeepLinkHelper.navDeepLinkRequest(uri = intent?.data)
        startNavLink?.let {
            navController?.clearBackStack(R.id.splashFragment)
            navController?.navigate(it)
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
        registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    Log.e("MainActivity.BroadcastReceiver", "networking changed")
                    if (context.isNetworkAvailable()) {
                        navController?.popBackStack(R.id.splashFragment, false)
                    }
                }
            },
            IntentFilter().apply { addAction("android.net.conn.CONNECTIVITY_CHANGE") }
        )
    }
}