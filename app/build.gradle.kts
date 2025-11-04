plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.usertestapplication"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.usertestapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions{
        resources{
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //network
    implementation(libs.retrofit2)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.interceptor)

    //hilt
    implementation(libs.hilt.android)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)
    ksp(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.test)
    kaptAndroidTest(libs.hilt.compiler)

    //image
    implementation(libs.coil.compose)

    //test
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk.test)
    testImplementation(libs.turbine.test)
    testImplementation(libs.androidx.compose)

    androidTestImplementation(libs.mockk.test)
    androidTestImplementation(libs.androidx.compose)
    debugImplementation(libs.androidx.ui.compose)
    androidTestImplementation(libs.android.test)
    androidTestImplementation(libs.androidx.test.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.android.test)
}