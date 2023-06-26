package pastukh.vova.appcompose

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import pastukh.vova.baseui.moduleBaseUi
import pastukh.vova.data.moduleData
import pastukh.vova.pdpapp.BuildConfig
import pastukh.vova.utils.moduleUtils

class App: Application() {
    companion object {
        private const val TAG = "App"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(
            TAG,
            "${BuildConfig.BUILD_TYPE}-${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
        )

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                moduleBaseUi,
                moduleData,
                moduleUtils
            )
        }
    }
}