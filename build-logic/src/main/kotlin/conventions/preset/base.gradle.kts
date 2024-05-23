package conventions.preset

plugins {
    id("conventions.kotlin-js-browser")
    id("conventions.ktlint")
    id("conventions.detekt")
}

kotlin {
    js {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
        }
        binaries.executable()
    }
}
