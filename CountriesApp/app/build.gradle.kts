plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dhanush.countriesapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dhanush.countriesapp"
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
val lifeCycleExtensionsVersion = "1.1.1"
val supportVersion = "28.0.0"
val retrofitVersion = "2.3.0"
val daggerVersion = "2.13"
val glideVersion = "4.8.0"
val mockitoVersion = "2.11.0"
val rxJavaVersion = "2.0.1"

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")

    implementation("io.reactivex.rxjava2:rxjava:$rxJavaVersion")
    implementation("io.reactivex.rxjava2:rxandroid:$rxJavaVersion")

    implementation("com.google.dagger:dagger:$daggerVersion")
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-compiler:$daggerVersion")
    annotationProcessor("com.google.dagger:dagger-android-processor:$daggerVersion")

//    implementation("com.android.support:recyclerview-v7:28.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.0")

    implementation("com.github.bumptech.glide:glide:$glideVersion")

//    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("androidx.appcompat:appcompat:1.4.0")

    implementation("android.arch.lifecycle:extensions:$lifeCycleExtensionsVersion")

    testImplementation("junit:junit:4.13.2")

    testImplementation("org.mockito:mockito-inline:$mockitoVersion")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}