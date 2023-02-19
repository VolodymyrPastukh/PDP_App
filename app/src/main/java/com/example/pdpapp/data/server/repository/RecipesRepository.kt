package com.example.pdpapp.data.server.repository

import com.example.pdpapp.data.server.entity.RecipeDTO
import com.example.pdpapp.data.server.entity.base.ResponseResult
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    val storeRecipesFlow: Flow<String>

    suspend fun getRecipes(): ResponseResult<List<RecipeDTO>>

    suspend fun getStoredRecipes(): ResponseResult<List<RecipeDTO>>

    suspend fun getRecipe(id: String): ResponseResult<RecipeDTO>

    suspend fun storeRecipe(id: String): ResponseResult<String>

    suspend fun deployRecipe(event: RecipeDTO): ResponseResult<String>
}