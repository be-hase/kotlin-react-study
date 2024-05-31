import react.FC
import react.Props
import react.PropsWithChildren
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import web.cssom.ClassName

external interface CardProps : PropsWithChildren

val Card = FC<CardProps> { props ->
    div {
        className = ClassName("card")
        +props.children
    }
}

val Profile = FC {
    Card {
        Avatar {
            person = Person(name = "Katsuko Saruhashi", imageId = "YfeOqp2")
            size = 100.0
        }
    }
}

data class Person(
    val name: String,
    val imageId: String,
)

external interface AvatarProps : Props {
    var person: Person
    var size: Double
}

val Avatar = FC<AvatarProps> { props ->
    img {
        className = ClassName("avatar")
        src = getImageUrl(props.person)
        alt = props.person.name
        width = props.size
        height = props.size
    }
}

fun getImageUrl(
    person: Person,
    size: String = "s",
): String {
    return "https://i.imgur.com/${person.imageId}$size.jpg"
}
