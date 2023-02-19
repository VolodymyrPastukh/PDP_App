package com.example.pdpapp.data.server

import com.example.pdpapp.data.server.entity.AddRecipeDTO
import com.example.pdpapp.data.server.entity.RecipeDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServerApi {
    @GET(ServerConstants.EVENTS_ENDPOINT)
    suspend fun getEvents(): List<RecipeDTO>

    @POST(ServerConstants.EVENTS_ENDPOINT)
    suspend fun postEvent(@Body body: RecipeDTO): AddRecipeDTO
}