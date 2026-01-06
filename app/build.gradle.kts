plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mindease"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mindease"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // --- Core Android Libraries ---
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.7.2")
    implementation("com.google.android.material:material:1.9.0")

    // --- Room Database (Corrected for Kotlin DSL) ---
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // --- Onboarding & UI ---
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta01")
    implementation("com.tbuonomo:dotsindicator:4.3")

    // --- Analytics & Graphs ---
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // --- Testing ---
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}