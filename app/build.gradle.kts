@file:Suppress("UnstableApiUsage")

val compose_version = "1.4.0" // Replace with the appropriate version number
val nav_compose_version = "2.4.0-alpha10"
//def nav_compose_version = "2.5.0"//-alpha02" // Use the appropriate version that matches your Jetpack Compose version
val nav_version = "2.5.3"
val accompanist_version = "0.28.0"
val dialogVersion = "1.1.1"

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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")
    implementation("androidx.compose.material:material-icons-extended:$compose_version")

    //implementation("com.android.support:appcompat-v7:28.0.0-rc01")
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")
    //implementation("com.google.accompanist:accompanist-coil:0.22.0-rc")
    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("com.google.accompanist:accompanist-pager:$accompanist_version") // Pager
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanist_version") // Pager Indicators
    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc") // Swipe to refresh
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1") // system bars customization

    implementation("com.github.madrapps:plot:0.1.1") // Line Charts

    // Compose Dialog - https://github.com/maxkeppeler/sheets-compose-dialogs
    //implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.1.1") // CORE
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:$dialogVersion") // CALENDAR
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:$dialogVersion") // CLOCK
    implementation("com.maxkeppeler.sheets-compose-dialogs:date-time:$dialogVersion") // DATE-TIME
    implementation("com.maxkeppeler.sheets-compose-dialogs:color:$dialogVersion") // COLOR
    implementation("com.maxkeppeler.sheets-compose-dialogs:duration:$dialogVersion") // DURATION
    implementation("com.maxkeppeler.sheets-compose-dialogs:input:$dialogVersion") // INPUT

    implementation("com.github.jeziellago:compose-markdown:0.3.4") // Markdown Text
    implementation("io.github.dokar3:expandabletext:0.3.1") // Expandable Text: https://github.com/dokar3/ExpandableText
    implementation("com.github.vsnappy1:ComposeDatePicker:2.2.0")

    /*implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.google.android.gms:play-services-maps:18.1.0")*/

    /*// For Glance support
    implementation("androidx.glance:glance:1.0.0-beta01")

    // For AppWidgets support
    implementation("androidx.glance:glance-appwidget:1.0.0-beta01")*/

    // Compose only
    //implementation("com.leinardi.android:speed-dial.compose:1.0.0-alpha04") // https://github.com/leinardi/FloatingActionButtonSpeedDial
    //implementation("com.github.leinardi:FloatingActionButtonSpeedDial:master-SNAPSHOT")

    implementation("io.github.jisungbin:timelineview:1.0.2")
}
