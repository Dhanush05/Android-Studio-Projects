plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
//    kotlin("kapt")
//    kotlin("android")
}

android {
    namespace = "com.dhanush.countries"
    compileSdk = 33
    buildFeatures{
        dataBinding = true
    }

    defaultConfig {
        applicationId = "com.dhanush.countries"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation ("com.squareup.retrofit2:retrofit: 2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")

    implementation("com.google.dagger:dagger:2.47") // Dagger core library
//    annotationProcessor("com.google.dagger:dagger-compiler:2.38.1") // Dagger annotation processor
    kapt("com.google.dagger:dagger-compiler:2.47")

    implementation("com.google.dagger:dagger-android:2.47") // Dagger Android support
    implementation("com.google.dagger:dagger-android-support:2.47") // Dagger Android support for fragments and activities
    kapt("com.google.dagger:dagger-android-processor:2.47") // Dagger Android processor

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")


    implementation("androidx.recyclerview:recyclerview:1.3.1")

    implementation("com.github.bumptech.glide:glide:4.16.0") // Glide dependency
    kapt("com.github.bumptech.glide:compiler:4.16.0") // Glide annotation processor

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0") // Lifecycle Extensions dependency


    testImplementation("junit:junit:4.13.2")

    testImplementation("org.mockito:mockito-inline:5.2.0") // Mockito Inline testing dependency

    testImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}