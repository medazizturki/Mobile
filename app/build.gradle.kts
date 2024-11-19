import java.util.Properties

// build.gradle.kts (app module)

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.user_module"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.user_module"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Load environment variables from local.properties
        val localProperties = File(rootDir, "local.properties")
        val properties = Properties()

        if (localProperties.exists()) {
            properties.load(localProperties.inputStream())
        }

        val smtpHost: String = properties.getProperty("smtpHost", "")
        val smtpPort: String = properties.getProperty("smtpPort", "")
        val emailAddress: String = properties.getProperty("emailAddress", "")
        val emailPassword: String = properties.getProperty("emailPassword", "")

        // Pass them as buildConfigFields
        buildConfigField("String", "SMTP_HOST", "\"$smtpHost\"")
        buildConfigField("String", "SMTP_PORT", "\"$smtpPort\"")
        buildConfigField("String", "EMAIL_ADDRESS", "\"$emailAddress\"")
        buildConfigField("String", "EMAIL_PASSWORD", "\"$emailPassword\"")
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
    // Enable BuildConfig generation
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.javamail)
    implementation(libs.activation)
    implementation(libs.bcrypt)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.volley)
    implementation(libs.room.common)
    implementation(libs.room.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor(libs.room.compiler)

    implementation(libs.appcompat) // AppCompat via alias
    implementation(libs.material) // Material components
    implementation(libs.activity) // AndroidX Activity
    implementation(libs.constraintlayout)
    testImplementation(libs.junit) // JUnit for testing
    androidTestImplementation(libs.ext.junit) // AndroidX JUnit extension
    androidTestImplementation(libs.espresso.core) // Espresso for UI tests

    // Ajoutez ces deux lignes pour Room (en Java)
    implementation("androidx.room:room-runtime:2.4.3")  // Room runtime
    annotationProcessor("androidx.room:room-compiler:2.4.3")  // Room compiler pour Java

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0") // For annotation processing
    implementation ("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation ("androidx.navigation:navigation-ui-ktx:2.6.0")
    implementation ("com.google.android.gms:play-services-auth:20.2.0")

}
