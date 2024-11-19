plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.blogappll"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.blogappll"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {

    implementation(libs.appcompat) // AppCompat via alias
    implementation(libs.material) // Material components
    implementation(libs.activity) // AndroidX Activity
    implementation(libs.constraintlayout) // Constraint Layout
    testImplementation(libs.junit) // JUnit for testing
    androidTestImplementation(libs.ext.junit) // AndroidX JUnit extension
    androidTestImplementation(libs.espresso.core) // Espresso for UI tests

    // Ajoutez ces deux lignes pour Room (en Java)
    implementation("androidx.room:room-runtime:2.4.3")  // Room runtime
    annotationProcessor("androidx.room:room-compiler:2.4.3")  // Room compiler pour Java

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0") // For annotation processing
    implementation(kotlin("script-runtime"))

}
