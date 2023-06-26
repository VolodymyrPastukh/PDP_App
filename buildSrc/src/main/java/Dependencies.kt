object Dependencies {

    object Ktx {
        private const val lifecycleKtxVersion = "2.5.1"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleKtxVersion"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleKtxVersion"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleKtxVersion"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$lifecycleKtxVersion"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$lifecycleKtxVersion"
    }

    object Core {
        const val coreKtx = "androidx.core:core-ktx:1.10.1"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val material = "com.google.android.material:material:1.7.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    }

    object Compose {
        const val material1 = "androidx.compose.material:material:1.4.3"
        const val material3 = "androidx.compose.material3:material3:1.2.0-alpha02"
        const val navigation = "androidx.navigation:navigation-compose:2.6.0"
        const val activity = "androidx.activity:activity-compose:1.7.2"
        const val constraint = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        const val uitool = "androidx.compose.ui:ui-tooling:1.4.3"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"
    }

    object Server {
        private const val retrofitVersion = "2.9.0"
        private const val okkHttpVersion = "5.0.0-alpha.8"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitJsonConverter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val okkHttp = "com.squareup.okhttp3:okhttp:$okkHttpVersion"
        const val okkHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okkHttpVersion"
        const val gson = "com.google.code.gson:gson:2.9.1"
    }

    object DI {
        private const val koinVersion = "3.4.0"
        const val koin = "io.insert-koin:koin-android:$koinVersion"
        const val koinExt = "io.insert-koin:koin-android-ext:3.0.1"
        const val compose = "io.insert-koin:koin-androidx-compose:3.4.4"
    }

    object Picasso {
        const val picasso = "com.squareup.picasso:picasso:2.8"
    }

    object Utils{
        const val gson = "com.google.code.gson:gson:2.9.1"
        const val lottie = "com.airbnb.android:lottie-compose:6.0.1"
        const val picasso = "com.squareup.picasso:picasso:2.8"
    }
}