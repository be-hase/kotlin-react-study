plugins {
    kotlin("multiplatform") version libs.versions.kotlin.core.get() apply false
}

// yarn lock周りが煩わしいので、緩い設定にしておく
// ref: https://kotlinlang.org/docs/js-project-setup.html#reporting-that-yarn-lock-has-been-updated
//rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
//    rootProject.the<YarnRootExtension>().yarnLockMismatchReport = YarnLockMismatchReport.WARNING
//    rootProject.the<YarnRootExtension>().reportNewYarnLock = false
//    rootProject.the<YarnRootExtension>().yarnLockAutoReplace = true
//}
