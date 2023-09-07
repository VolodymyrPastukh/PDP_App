package pastukh.vova.components.services.media

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pastukh.vova.components.services.ServiceConstants.EXTRA_RECIPE_URL
import pastukh.vova.components.services.base.CoroutinesService
import pastukh.vova.components.services.base.dependency
import pastukh.vova.data.server.entity.base.getOrNull
import pastukh.vova.data.server.repository.RecipesRepository
import pastukh.vova.utils.read
import pastukh.vova.utils.waitUntilSpeaking
import pastukh.vova.utils.withDelay


class LiveRecipeService : CoroutinesService() {
    companion object {
        private const val TAG = "LiveRecipeService"
    }

    private var tss: TextToSpeech? = null
    private val _recipesRepository: RecipesRepository = dependency()


    override fun onCreate() {
        super.onCreate()
        tss = TextToSpeech(applicationContext) {
            Log.i(TAG, "init status - tss[$it]")
        }
        Log.i(TAG, "$TAG created")
    }

    override fun onDestroy() {
        tss?.stop()
        tss?.shutdown()
        super.onDestroy()
        Log.i(TAG, "$TAG destroyed")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            val id = intent?.getIntExtra(EXTRA_RECIPE_URL, -1) ?: -1
            Log.i(TAG, "Intent $intent")
            Log.i(TAG, "Flags $flags")
            Log.i(TAG, "StartId $startId")
            Log.i(TAG, "intent[id] $id")
            start(id)
        }
        return START_STICKY
    }

    private suspend fun start(id: Int) {
        delay(1000)
        when {
            id < 0 -> recipeNotFound()
            _recipesRepository.getRecipe(id).getOrNull() == null -> recipeNotFound()
            else -> startLiveRecipe(_recipesRepository.getRecipe(id).getOrNull()!!)
        }
    }

    private suspend fun startLiveRecipe(recipe: pastukh.vova.data.server.entity.RecipeDTO) =
        with(tss) {
            withDelay(5000) { read("Starting live recipe ${recipe.recipe_title}", recipe.id.toString()) }
            recipe.steps.forEach { step ->
                withDelay(step.recipe_step_duration_millis) {
                    read("${step.recipe_step_title} - ${step.recipe_step_instruction}", step.recipe_step_title)
                    waitUntilSpeaking()
                }
            }
            withDelay(3000) { read("Congratulations!", recipe.id.toString()) }
            stopSelf()
        }

    private suspend fun recipeNotFound() = with(tss) {
        withDelay(3000) { read("Recipe is not found", "no_recipe") }
        stopSelf()
    }
}