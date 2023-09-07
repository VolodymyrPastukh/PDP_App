package pastukh.vova.data.server.repository

import pastukh.vova.data.server.entity.RecipeDTO
import pastukh.vova.data.server.entity.base.ResponseResult
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    val storeRecipesFlow: Flow<Int>

    suspend fun getRecipes(): ResponseResult<List<RecipeDTO>>

    suspend fun getStoredRecipes(): ResponseResult<List<RecipeDTO>>

    suspend fun getRecipe(id: Int): ResponseResult<RecipeDTO>

    suspend fun storeRecipe(id: Int): ResponseResult<Int>

    suspend fun deployRecipe(recipe: RecipeDTO): ResponseResult<RecipeDTO>
}