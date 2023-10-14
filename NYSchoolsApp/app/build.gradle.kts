plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

}

android {
    namespace = "com.dhanush.nyschoolsapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dhanush.nyschoolsapp"
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
    buildFeatures {
        viewBinding = true
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

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//dagger dependencies
    implementation("com.google.dagger:dagger:2.47")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.0-alpha01") // Dagger core library
    kapt("com.google.dagger:dagger-compiler:2.47")
    implementation("com.google.dagger:dagger-android:2.47") // Dagger Android support
    implementation("com.google.dagger:dagger-android-support:2.47") // Dagger Android support for fragments and activities
    kapt("com.google.dagger:dagger-android-processor:2.47") // Dagger Android processor
//retrofit dependencies
    implementation ("com.squareup.retrofit2:retrofit: 2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
//rxjava dependencies
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")

    implementation("androidx.recyclerview:recyclerview:1.3.1")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0") // Lifecycle Extensions dependency

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha01")
}