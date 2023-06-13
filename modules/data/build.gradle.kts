plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = Config.Data.packageName
    compileSdk = Config.compileVersion

    defaultConfig {
        minSdk = Config.minVersion
        targetSdk = Config.targetVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":modules:utils"))

    implementation(Dependencies.Core.coreKtx)
    implementation(Dependencies.Core.appCompat)

    implementation(Dependencies.Server.retrofit)
    implementation(Dependencies.Server.retrofitJsonConverter)
    implementation(Dependencies.Server.okkHttp)
    implementation(Dependencies.Server.okkHttpLoggingInterceptor)
    implementation(Dependencies.Server.gson)

    implementation(Dependencies.DI.koin)
    implementation(Dependencies.DI.koinExt)
}