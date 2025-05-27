import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.allvideo.status.free.downloade"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.allvideo.status.free.downloade"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
         val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        debug {
            buildConfigField("String", "API_KEY", properties.getProperty("api_key"))
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY",  properties.getProperty("api_key"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.lifecycle.runtime.ktx)
    //implementation(libs.androidx.activity.compose)
    //implementation(platform(libs.androidx.compose.bom))
   // implementation(libs.androidx.ui)
    //implementation(libs.androidx.ui.graphics)
    //implementation(libs.androidx.ui.tooling.preview)
   // implementation(libs.androidx.material3)
    //implementation(libs.androidx.lifecycle.runtime.compose.android)
    /*testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)*/

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.graphics:graphics-shapes:1.0.1")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended:1.7.6")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.6")
    implementation("androidx.lifecycle:lifecycle-runtime-compose-android:2.8.7")
    implementation("androidx.appcompat:appcompat:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation("androidx.compose.material:material-icons-extended:1.6.8")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")

    // Glide
    implementation("io.coil-kt:coil-compose:1.3.2")

    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("com.google.accompanist:accompanist-flowlayout:0.17.0")

    //gson
    implementation("com.google.code.gson:gson:2.8.7")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9")

    // Glide
    implementation("io.coil-kt:coil-compose:1.3.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:1.0.0-alpha01")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    testImplementation("android.arch.core:core-testing:1.1.1")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:2.13.0")
}