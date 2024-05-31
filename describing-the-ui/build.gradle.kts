plugins {
    id("conventions.preset.base")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // BOMs
    jsMainImplementation(enforcedPlatform(libs.kotlin.wrappers.bom))

    // React, React DOM
    jsMainImplementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    jsMainImplementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")

    // Kotlin React Emotion (CSS)
    jsMainImplementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")
}
