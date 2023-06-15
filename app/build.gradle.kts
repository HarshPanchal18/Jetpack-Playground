val compose_version = "1.4.0" // Replace with the appropriate version number
val nav_compose_version = "2.4.0-alpha10"
//def nav_compose_version = "2.5.0"//-alpha02" // Use the appropriate version that matches your Jetpack Compose version
val nav_version = "2.5.3"
val accompanist_version = "0.28.0"

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    lint {
        //baseline = file("lint-baseline.xml")
        checkReleaseBuilds = false
        abortOnError = false
    }
    //compileSdkVersion = 32
    compileSdk = 33 // Use String literal for compileSdkVersion

    defaultConfig {
        applicationId = "com.example.first_jetcompose"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures.compose = true

    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime:2.6.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    //implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.compose.runtime:runtime-livedata:$compose_version") // for observableAsState()
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")
    implementation("androidx.compose.material:material-icons-extended:$compose_version")

    //implementation("com.android.support:appcompat-v7:28.0.0-rc01")
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")
    //implementation("com.google.accompanist:accompanist-coil:0.22.0-rc")
    //implementation("com.google.accompanist:accompanist-pager:0.23.1")
    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("com.google.accompanist:accompanist-pager:$accompanist_version") // Pager
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanist_version") // Pager Indicators
}
