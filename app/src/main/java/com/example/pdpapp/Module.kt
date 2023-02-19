package com.example.pdpapp

import android.content.Context
import android.content.SharedPreferences
import com.example.pdpapp.data.datastore.dataSource.PreferencesDataSource
import com.example.pdpapp.data.server.ServerApi
import com.example.pdpapp.data.server.ServerConstants
import com.example.pdpapp.data.server.repository.RecipesRepository
import com.example.pdpapp.data.server.repository.RecipesRepositoryImpl
import com.example.pdpapp.ui.screen.recipedetails.RecipeDetailsViewModel
import com.example.pdpapp.ui.screen.recipes.RecipesViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single(named(DispatcherToken.IO)) { Dispatchers.IO }
    single(named(DispatcherToken.DEFAULT)) { Dispatchers.Default }
    single(named(DispatcherToken.MAIN)) { Dispatchers.Main }

    factory { Gson() }

    single { serverApi() }
    single { sharedPreferences(get()) }

    single { PreferencesDataSource(get(), get()) }

    single<RecipesRepository> { RecipesRepositoryImpl(get(), get(), get(named(DispatcherToken.IO))) }

    viewModel { RecipesViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
}

private const val PREFERENCES_NAME = "recipes_preferences"
private fun sharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
}

private fun serverApi(): ServerApi {
    val okHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()

    val retrofit = Retrofit.Builder().client(okHttpClient)
        .baseUrl(ServerConstants.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ServerApi::class.java)
}

private enum class DispatcherToken {
    IO,
    DEFAULT,
    MAIN,
}