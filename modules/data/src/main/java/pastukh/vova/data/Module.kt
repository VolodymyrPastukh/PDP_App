package pastukh.vova.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pastukh.vova.data.dataSource.PreferencesDataSource
import pastukh.vova.data.server.ServerApi
import pastukh.vova.data.server.repository.RecipesRepository
import pastukh.vova.data.server.repository.RecipesRepositoryImpl
import pastukh.vova.utils.DispatcherToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val PREFERENCES_NAME = "recipes_preferences"

val moduleData = module {
    factory { Gson() }

    single { serverApi(get(qualifier = named("server_url"))) }
    single { sharedPreferences(get()) }

    single { PreferencesDataSource(get(), get()) }

    single<RecipesRepository> {
        RecipesRepositoryImpl(get(), get(), get(named(DispatcherToken.IO)))
    }
}

private fun sharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
}

private fun serverApi(serverUrl: String): ServerApi {
    val okHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }.build()

    val retrofit = Retrofit.Builder().client(okHttpClient)
        .baseUrl(serverUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ServerApi::class.java)
}
