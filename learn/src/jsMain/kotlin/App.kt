import emotion.react.css
import react.FC
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import web.cssom.ClassName
import web.cssom.px

val MyButton = FC {
    button {
        +"I'm a button"
    }
}

val MyApp = FC {
    div {
        h1 { +"MyApp" }
        MyButton {}
    }
}

val AboutPage = FC {
    h1 { +"About" }
    p {
        className = ClassName("about-page")
        +"Hello there."
        br {}
        +"How do you do?"
    }
}

val user = mapOf<String, Any>(
    "name" to "Hedy Lamarr",
    "imageUrl" to "https://i.imgur.com/yXOvdOSs.jpg",
    "imageSize" to 90,
)

val Profile = FC {
    h1 { +user["name"].toString() }
    img {
        className = ClassName("avatar")
        src = user["imageUrl"].toString()
        alt = "Photo of ${user["name"]}"
        css {
            width = (user["imageSize"] as Int).px
            height = (user["imageSize"] as Int).px
        }
    }
}
