import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(Dependencies.Compose.material1)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.activity)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.constraint)
    implementation(Dependencies.Compose.uitool)
    implementation(Dependencies.Compose.viewModel)

    implementation(Dependencies.DI.koin)
    implementation(Dependencies.DI.compose)

    implementation(Dependencies.Utils.gson)
    implementation(Dependencies.Utils.lottie)
    implementation(Dependencies.Utils.picasso)
}