package pastukh.vova.data.server.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import pastukh.vova.data.dataSource.PreferencesDataSource
import pastukh.vova.data.server.ServerApi
import pastukh.vova.data.server.entity.RecipeDTO
import pastukh.vova.data.server.entity.base.ResponseResult

class RecipesRepositoryImpl(
    private val api: ServerApi,
    private val database: PreferencesDataSource,
    private val dispatcher: CoroutineDispatcher,
) : RecipesRepository {

    override val storeRecipesFlow = MutableSharedFlow<Int>()
    private val stored: List<RecipeDTO>?
        get() = database.getData(PreferencesDataSource.SELECTED_EVENTS)

    override suspend fun getRecipes(): ResponseResult<List<RecipeDTO>> =
        withContext(dispatcher) {
            try {
                ResponseResult.fromServerResult(api.recipes())
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

    override suspend fun getRecipe(id: Int): ResponseResult<RecipeDTO> =
        withContext(dispatcher) {
            try {
                when {
                    stored?.map { it.id }?.contains(id) == true ->
                        ResponseResult.Success(stored?.firstOrNull { it.id == id }!!)

                    else -> ResponseResult.fromServerResult(api.recipeById(id))
                }
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

    override suspend fun deployRecipe(recipeDTO: RecipeDTO): ResponseResult<RecipeDTO> =
        withContext(dispatcher) {
            try {
                ResponseResult.fromServerResult(api.postRecipe(recipeDTO))
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

    override suspend fun storeRecipe(id: Int): ResponseResult<Int> =
        withContext(dispatcher) {
            try {
                val sr = api.recipeById(id)
                when (val selected = sr.result) {
                    null -> ResponseResult.ServerError(null)

                    else -> {
                        val recipes = (stored?.toMutableList() ?: mutableListOf()).apply {
                            if (!contains(selected)) add(selected)
                        }
                        database.putData(PreferencesDataSource.SELECTED_EVENTS, recipes)
                        storeRecipesFlow.emit(id)
                        ResponseResult.Success(id)
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(e)
            }
        }

}