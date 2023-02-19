package com.example.pdpapp.services.media

import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.pdpapp.data.server.entity.RecipeDTO
import com.example.pdpapp.data.server.entity.base.getOrNull
import com.example.pdpapp.data.server.repository.RecipesRepository
import com.example.pdpapp.services.ServiceConstants.EXTRA_RECIPE_URL
import com.example.pdpapp.services.base.CoroutinesService
import com.example.pdpapp.services.base.dependency
import com.example.pdpapp.ui.utils.read
import com.example.pdpapp.ui.utils.waitUntilSpeaking
import com.example.pdpapp.ui.utils.withDelay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
            val id = intent?.getStringExtra(EXTRA_RECIPE_URL)
            Log.i(TAG, "Intent $intent")
            Log.i(TAG, "Flags $flags")
            Log.i(TAG, "StartId $startId")
            Log.i(TAG, "intent[id] $id")
            start(id)
        }
        return START_STICKY
    }

    private suspend fun start(id: String?) {
        delay(1000)
        when {
            id == null -> recipeNotFound()
            _recipesRepository.getRecipe(id).getOrNull() == null -> recipeNotFound()
            else -> startLiveRecipe(_recipesRepository.getRecipe(id).getOrNull()!!)
        }
    }

    private suspend fun startLiveRecipe(recipe: RecipeDTO) = with(tss) {
        withDelay(5000) { read("Starting live recipe ${recipe.title}", recipe.id) }
        recipe.steps.forEach { step ->
            withDelay(step.durationInMillis) {
                read("${step.title} - ${step.instructions}", step.title)
                waitUntilSpeaking()
            }
        }
        withDelay(3000) { read("Congratulations!", recipe.id) }
        stopSelf()
    }

    private suspend fun recipeNotFound() = with(tss) {
        withDelay(3000) { read("Recipe is not found", "no_recipe") }
        stopSelf()
    }
}