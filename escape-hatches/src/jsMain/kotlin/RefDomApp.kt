import react.FC
import react.ForwardRef
import react.PropsWithRef
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.useRef
import web.html.HTMLInputElement

external interface MyInputProps : PropsWithRef<HTMLInputElement>

val MyInput = ForwardRef<MyInputProps> { props ->
    input {
        ref = props.ref
    }
}

val Form = FC {
    val inputRef = useRef<HTMLInputElement>(null)

    fun handleClick() {
        inputRef.current!!.focus()
    }

    MyInput {
        ref = inputRef
    }
    button {
        onClick = { handleClick() }
        +"Focus the input"
    }
}
