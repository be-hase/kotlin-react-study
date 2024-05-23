plugins {
    id("conventions.preset.base")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // BOMs
    jsMainImplementation(enforcedPlatform(libs.kotlin.wrappers.bom))
    jsMainImplementation(enforcedPlatform(libs.kotlin.coroutines.bom))

    // React, React DOM
    jsMainImplementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    jsMainImplementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")

    // Kotlin React Emotion (CSS)
    jsMainImplementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")

    // Video Player
//    jsMainImplementation(npm("react-player", "2.16.0"))

    // Share Buttons
//    jsMainImplementation(npm("react-share", "5.1.0"))

    // Coroutines
    jsMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
}
