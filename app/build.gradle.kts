plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.myrecipesdoffemontdjegherif"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myrecipesdoffemontdjegherif"
        minSdk = 26
        targetSdk = 35
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

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // HTTP Logger
    implementation(libs.logging.interceptor)

    // Picasso
    implementation(libs.picasso)

    // WOrk Manager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.legacy.support.v4)

    // Fragment + LifeCycle
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.core.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.testing)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
