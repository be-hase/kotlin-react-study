import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1

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
