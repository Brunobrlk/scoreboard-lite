import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.googleDaggerHilt)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    id("com.github.triplet.play") version "3.12.1"
}

android {
    namespace = "com.bgbrlk.scoreboardbrlk"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bgbrlk.scoreboardbrlk"
        minSdk = 26
        targetSdk = 35
        versionName = getTag()
        versionCode = getVersionCode(versionName)
        archivesName = getCustomVersionName(versionName, versionCode)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file(project.property("RELEASE_STORE_FILE") as String)
            storePassword = project.property("RELEASE_STORE_PASSWORD") as String
            keyAlias = project.property("RELEASE_KEY_ALIAS") as String
            keyPassword = project.property("RELEASE_KEY_PASSWORD") as String
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            ndk.debugSymbolLevel = "FULL"
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        animationsDisabled = true

        managedDevices {
            localDevices {
                create("pixel2api30") {
                    device = "Pixel 2"
                    apiLevel = 30
                    systemImageSource = "aosp-atd"
                }
                create("pixel2api31") {
                    device = "Pixel 2"
                    apiLevel = 31
                    systemImageSource = "aosp-atd"
                }
            }
            groups {
                create("basicDevices") {
                    targetDevices.add(devices["pixel2api30"])
                    targetDevices.add(devices["pixel2api31"])
                }
            }
        }
    }

    ndkVersion = "27.0.11718014 rc1"

    kotlinOptions {
        jvmTarget = "17"
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Ads
    implementation(libs.play.services.ads)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.config)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.fragment.testing)
    kapt(libs.hilt.android.compiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)

    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.screengrab)
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"
}

play {
    serviceAccountCredentials.set(file("google-play-service-account.json"))
    defaultToAppBundles.set(true)

    // Circleci workspace integration
    artifactDir.set(file("build/outputs/bundle/release"))

    // internal/alpha/beta/production
    track.set("internal")

    val versionName = android.defaultConfig.versionName
    val versionCode = android.defaultConfig.versionCode
    releaseName.set("$versionName($versionCode) - Alpha")
}

tasks.register<Zip>("zipSymbols") {
    group = "build"
    description = "Zips the merged native libs into symbols.zip."

    val outDir = layout.buildDirectory.dir("intermediates/merged_native_libs/release/mergeReleaseNativeLibs/out/lib")

    destinationDirectory.set(layout.buildDirectory.dir("outputs/symbols"))
    archiveFileName.set("symbols.zip")

    from(outDir)
}

afterEvaluate {
    tasks.named("bundleRelease").configure {
        finalizedBy("zipSymbols")
    }
}
