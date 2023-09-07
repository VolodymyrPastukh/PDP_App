package pastukh.vova.components.services.loading

import android.content.Intent
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pastukh.vova.components.receivers.DownloadingInfoReceiver
import pastukh.vova.components.services.ServiceConstants
import pastukh.vova.components.services.base.CoroutinesService
import pastukh.vova.components.services.base.dependency
import pastukh.vova.data.server.entity.base.getOrNull
import pastukh.vova.data.server.repository.RecipesRepository
import pastukh.vova.utils.tickerFlow
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
            val id = intent?.getIntExtra(ServiceConstants.EXTRA_RECIPE_URL, -1)
            if (id != null && id < 0) throw IllegalArgumentException()

            Log.i(TAG, "Intent $intent")
            Log.i(TAG, "Flags $flags")
            Log.i(TAG, "StartId $startId")
            Log.i(TAG, "intent[id] $id")
            start(id, startId)
        }
        return START_REDELIVER_INTENT
    }

    private suspend fun start(id: Int?, startId: Int) {
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

    private suspend fun sendBroadcastNotification(id: Int) {
        val stored = _recipesRepository.getRecipe(id).getOrNull()?.recipe_title
        sendBroadcast(
            Intent(DownloadingInfoReceiver.Constants.ACTION).apply {
                putExtra(DownloadingInfoReceiver.Constants.Key.ID, id)
                stored?.let { putExtra(DownloadingInfoReceiver.Constants.Key.TITLE, it) }
            }
        )
    }
}