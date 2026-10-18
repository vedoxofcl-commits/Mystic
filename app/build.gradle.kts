plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    // Baaki existing android settings ko rehne dena

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
