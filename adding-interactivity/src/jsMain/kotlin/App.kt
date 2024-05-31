import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h1
import react.useState
import web.prompts.alert

val Counter = FC {
    var number: Int by useState(0)
    h1 { +number.toString() }
    button {
        onClick = {
            number += 5
            alert(number.toString())
        }
        +"+5"
    }
}
