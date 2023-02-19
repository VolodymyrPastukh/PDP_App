package com.example.pdpapp.services.loading

import android.content.Intent
import android.util.Log
import com.example.pdpapp.data.server.entity.base.getOrNull
import com.example.pdpapp.data.server.repository.RecipesRepository
import com.example.pdpapp.receivers.DownloadingInfoReceiver
import com.example.pdpapp.services.ServiceConstants
import com.example.pdpapp.services.base.CoroutinesService
import com.example.pdpapp.services.base.dependency
import com.example.pdpapp.ui.utils.tickerFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class LoadingService : CoroutinesService() {
    companion object {
        private const val TAG = "LoadingService"
    }

    private val _recipesRepository: RecipesRepository = dependency()


    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "$TAG created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "$TAG destroyed")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            val id = intent?.getStringExtra(ServiceConstants.EXTRA_RECIPE_URL)
            Log.i(TAG, "Intent $intent")
            Log.i(TAG, "Flags $flags")
            Log.i(TAG, "StartId $startId")
            Log.i(TAG, "intent[id] $id")
            start(id, startId)
        }
        return START_REDELIVER_INTENT
    }

    private suspend fun start(id: String?, startId: Int) {
        if (id == null) {
            stopSelf(startId)
            return
        }

        delay(Random.nextInt(2..5).seconds)
        tickerFlow(5.minutes)
            .onEach {
                _recipesRepository.storeRecipe(id).getOrNull()?.let {
                    sendBroadcastNotification(id)
                    stopSelf(startId)
                }
            }.launchIn(scope)
    }

    private suspend fun sendBroadcastNotification(id: String) {
        val stored = _recipesRepository.getRecipe(id).getOrNull()?.title
        sendBroadcast(
            Intent(DownloadingInfoReceiver.Constants.ACTION).apply {
                putExtra(DownloadingInfoReceiver.Constants.Key.ID, id)
                stored?.let { putExtra(DownloadingInfoReceiver.Constants.Key.TITLE, it) }
            }
        )
    }
}