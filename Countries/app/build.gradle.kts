plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dhanush.countries"
    compileSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation ("com.squareup.retrofit2:retrofit: 2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")

    implementation("com.google.dagger:dagger:2.38.1") // Dagger core library
    implementation("com.google.dagger:dagger-android:2.38.1") // Dagger Android support
    implementation("com.google.dagger:dagger-android-support:2.38.1") // Dagger Android support for fragments and activities
    annotationProcessor("com.google.dagger:dagger-compiler:2.38.1") // Dagger annotation processor
    annotationProcessor("com.google.dagger:dagger-android-processor:2.38.1") // Dagger Android processor

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    implementation("com.github.bumptech.glide:glide:4.12.0") // Glide dependency
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0") // Glide annotation processor

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0") // Lifecycle Extensions dependency


    testImplementation("junit:junit:4.13.2")

    testImplementation("org.mockito:mockito-inline:3.12.4") // Mockito Inline testing dependency

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}