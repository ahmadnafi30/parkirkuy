// build.gradle (Module: app)

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.parkirkuy"
    compileSdk = 34  // Pastikan menggunakan compileSdk terbaru yang stabil

    defaultConfig {
        applicationId = "com.example.parkirkuy"
        minSdk = 29  // Tentukan minimal SDK yang dibutuhkan
        targetSdk = 34  // Sesuaikan dengan target SDK terbaru
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        jvmTarget = "1.8"  // Set JVM Target to 1.8
    }

    buildFeatures {
        viewBinding = true  // Aktifkan ViewBinding
    }
}

dependencies {
    // Core libraries (AndroidX)
    implementation(libs.androidx.core.ktx)  // KTX Extensions for AndroidX Core
    implementation(libs.androidx.appcompat)  // AndroidX AppCompat
    implementation(libs.material)  // Material Components for AndroidX
    implementation(libs.androidx.activity)  // AndroidX Activity
    implementation(libs.androidx.constraintlayout)  // ConstraintLayout

    // Google Maps & Services (AndroidX)
    implementation(libs.play.services.maps)  // Google Play Services Maps

    // Testing dependencies (AndroidX)
    testImplementation(libs.junit)  // JUnit testing framework
    androidTestImplementation(libs.androidx.junit)  // AndroidX JUnit
    androidTestImplementation(libs.androidx.espresso.core)  // Espresso for UI testing

    // OSM Droid for OpenStreetMap (pastikan versi ini mendukung AndroidX)
    implementation(libs.osmdroid.android)  // OpenStreetMap Android
    implementation(libs.graphview)  // GraphView library (pastikan kompatibilitas AndroidX)


}
