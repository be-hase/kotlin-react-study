import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.useState

val MyApp = FC {
    var countState: Int by useState(0)

    fun handleClick() {
        countState += 1
    }

    div {
        h1 { +"Counters that update separately" }
        MyButton {
            count = countState
            handleClick = { handleClick() }
        }
        MyButton {
            count = countState
            handleClick = { handleClick() }
        }
    }
}

external interface MyButtonProps : Props {
    var count: Int
    var handleClick: () -> Unit
}

val MyButton = FC<MyButtonProps> { props ->
    button {
        onClick = { props.handleClick() }
        +"Clicked ${props.count} times"
    }
}
