package com.example.pdpapp.data.server.repository

import com.example.pdpapp.data.datastore.dataSource.PreferencesDataSource
import com.example.pdpapp.data.server.ServerApi
import com.example.pdpapp.data.server.entity.RecipeDTO
import com.example.pdpapp.data.server.entity.base.ResponseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class RecipesRepositoryImpl(
    private val api: ServerApi,
    private val database: PreferencesDataSource,
    private val dispatcher: CoroutineDispatcher
) : RecipesRepository {

    override val storeRecipesFlow = MutableSharedFlow<String>()
    private val stored: List<RecipeDTO>?
        get() = database.getData(PreferencesDataSource.SELECTED_EVENTS)

    override suspend fun getRecipes(): ResponseResult<List<RecipeDTO>> =
        withContext(dispatcher) {
            try {
                ResponseResult.Success(api.getEvents())
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

    override suspend fun getRecipe(id: String): ResponseResult<RecipeDTO> =
        withContext(dispatcher) {
            try {
                val recipe = stored?.firstOrNull { it.id == id } ?: api.getEvents()
                    .firstOrNull { it.id == id }
                ResponseResult.Success(recipe!!)
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

    override suspend fun deployRecipe(event: RecipeDTO): ResponseResult<String> =
        withContext(dispatcher) {
            try {
                ResponseResult.Success(api.postEvent(event).status)
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

    override suspend fun getStoredRecipes(): ResponseResult<List<RecipeDTO>> =
        withContext(dispatcher) {
            try {
                ResponseResult.Success(stored!!)
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

    override suspend fun storeRecipe(id: String): ResponseResult<String> =
        withContext(dispatcher) {
            try {
                val selected = api.getEvents().firstOrNull { it.id == id }!!
                val recipes = (stored?.toMutableList() ?: mutableListOf()).apply {
                    if (!contains(selected)) add(selected)
                }
                database.putData(PreferencesDataSource.SELECTED_EVENTS, recipes)
                storeRecipesFlow.emit(id)
                ResponseResult.Success(id)
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

}