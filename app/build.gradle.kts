import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
}

val keystorePropertiesFile = rootProject.file("keys/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = Config.packageName
    compileSdk = Config.compileVersion

    defaultConfig {
        applicationId = Config.packageName
        minSdk = Config.minVersion
        targetSdk = Config.targetVersion
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.addAll(listOf("version"))
    productFlavors {
        create("prod") {
            dimension = "version"
            buildConfigField("String", "SERVER_URL", "\"${keystoreProperties.getProperty("pdp_server_endpoint") as String}\"")
        }
        create("dev") {
            dimension = "version"
            buildConfigField("String", "SERVER_URL", "\"${keystoreProperties.getProperty("pdp_server_endpoint") as String}\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":modules:data"))
    implementation(project(":modules:components"))
    implementation(project(":modules:utils"))
    implementation(project(":modules:baseUi"))

    implementation(Dependencies.Core.coreKtx)
    implementation(Dependencies.Core.material)
    implementation(Dependencies.Core.appCompat)
    implementation(Dependencies.Core.constraintLayout)

    implementation(Dependencies.Ktx.viewModel)
    implementation(Dependencies.Ktx.liveData)
    implementation(Dependencies.Ktx.runtime)
    implementation(Dependencies.Ktx.navigationFragment)
    implementation(Dependencies.Ktx.navigationUi)

    implementation(Dependencies.DI.koin)
    implementation(Dependencies.DI.koinExt)

    implementation(Dependencies.Picasso.picasso)
}