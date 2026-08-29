plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.vtol.zaka"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.vtol.zaka"
        minSdk = 24
        targetSdk = 36
        versionCode = 2
        versionName = "1.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String","INTERSTITIAL_AD_UNIT", "\"${project.findProperty("INTERSTITIAL_AD_UNIT") ?: "ca-app-pub-3940256099942544/5224354917"}\"")
        buildConfigField("String","REWARDED_AD_UNIT", "\"${project.findProperty("REWARDED_AD_UNIT") ?: "ca-app-pub-3940256099942544/1033173712"}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.firebase.ai)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    //
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    // 1. Core Hilt Dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // 2. Jetpack Compose Integration (CRITICAL)
    // This allows you to call hiltViewModel() inside your Composable screens
    implementation(libs.androidx.hilt.navigation.compose)


    // Room DB
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Coil
    implementation(libs.coil.compose)

    // AdMob
    implementation(libs.play.services.ads)

    // data store
    implementation(libs.datastore.preferences)

    //
    implementation("androidx.media3:media3-exoplayer:1.10.1")
    implementation("androidx.media3:media3-ui:1.10.1")

}