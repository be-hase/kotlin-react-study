import react.FC
import react.PropsWithChildren
import react.createContext
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.h4
import react.dom.html.ReactHTML.h5
import react.dom.html.ReactHTML.section
import react.useContext

val LevelContext = createContext(0)

val Heading = FC<PropsWithChildren> { props ->
    when (val level = useContext(LevelContext)) {
        0 -> error("Heading must be inside a Section!")
        1 -> h1 { +props.children }
        2 -> h2 { +props.children }
        3 -> h3 { +props.children }
        4 -> h4 { +props.children }
        5 -> h5 { +props.children }
        else -> error("Unknown level: $level")
    }
}

val Section = FC<PropsWithChildren> { props ->
    val level = useContext(LevelContext)
    section {
        LevelContext.Provider {
            value = level + 1
            +props.children
        }
    }
}

val Page = FC {
    Section {
        Heading { +"Title" }
        Section {
            Heading { +"Heading" }
            Heading { +"Heading" }
            Heading { +"Heading" }
            Section {
                Heading { +"Sub-heading" }
                Heading { +"Sub-heading" }
                Heading { +"Sub-heading" }
                Section {
                    Heading { +"Sub-sub-heading" }
                    Heading { +"Sub-sub-heading" }
                    Heading { +"Sub-sub-heading" }
                }
            }
        }
    }
}
