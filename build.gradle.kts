buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2") // Ensure this version matches your needs
        // Add other dependencies for your buildscript if needed
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}