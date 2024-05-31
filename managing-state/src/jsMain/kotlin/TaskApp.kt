import react.Dispatch
import react.FC
import react.Props
import react.PropsWithChildren
import react.Reducer
import react.createContext
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.useContext
import react.useReducer
import react.useState
import web.html.InputType

val TasksContext = createContext<List<Task>?>(null)

val TasksDispatchContext = createContext<Dispatch<TaskAction>?>(null)

fun useTasks() = checkNotNull(useContext(TasksContext))

fun useTasksDispatch() = checkNotNull(useContext(TasksDispatchContext))

val TasksProvider = FC<PropsWithChildren> { props ->
    val (tasks, dispatch) = useReducer(tasksReducer, initialTasks)

    TasksContext.Provider {
        value = tasks
        TasksDispatchContext.Provider {
            value = dispatch
            +props.children
        }
    }
}

val tasksReducer: Reducer<List<Task>, TaskAction> = { tasks, action ->
    when (action) {
        is TaskAction.Added -> {
            buildList {
                addAll(tasks)
                add(Task(id = action.id, text = action.text, done = false))
            }
        }
        is TaskAction.Changed -> {
            tasks.map {
                if (it.id == action.task.id) {
                    action.task
                } else {
                    it
                }
            }
        }
        is TaskAction.Deleted -> tasks.filterNot { it.id == action.id }
    }
}

var nextId = 3

data class Task(
    val id: Int,
    val text: String,
    val done: Boolean,
)

val initialTasks = listOf(
    Task(id = 0, text = "Visit Kafka Museum", done = true),
    Task(id = 1, text = "Watch a puppet show", done = false),
    Task(id = 2, text = "Lennon Wall pic", done = false),
)

val TaskApp = FC {
    TasksProvider {
        h1 { +"Prague itinerary" }
        AddTask {}
        TaskList {}
    }
}

val AddTask = FC {
    var text: String by useState("")
    val dispatch = useTasksDispatch()

    input {
        placeholder = "Add task"
        value = text
        onChange = { text = it.target.value }
    }
    button {
        onClick = {
            text = ""
            dispatch(TaskAction.Added(id = nextId++, text = text))
        }
        +"Add"
    }
}

val TaskList = FC {
    val tasks = useTasks()

    ul {
        tasks.forEach { task ->
            li {
                key = task.id.toString()
                TaskComponent {
                    this.task = task
                }
            }
        }
    }
}

external interface TaskComponentProps : Props {
    var task: Task
}

val TaskComponent = FC<TaskComponentProps> { props ->
    var isEditing: Boolean by useState(false)
    val dispatch = useTasksDispatch()

    label {
        input {
            type = InputType.checkbox
            checked = props.task.done
            onChange = {
                dispatch(TaskAction.Changed(props.task.copy(done = it.target.checked)))
            }
        }
        if (isEditing) {
            input {
                value = props.task.text
                onChange = {
                    dispatch(TaskAction.Changed(props.task.copy(text = it.target.value)))
                }
            }
            button {
                onClick = { isEditing = false }
                +"Save"
            }
        } else {
            +props.task.text
            button {
                onClick = { isEditing = true }
                +"Edit"
            }
        }
        button {
            onClick = { dispatch(TaskAction.Deleted(props.task.id)) }
            +"Delete"
        }
    }
}

sealed interface TaskAction {
    data class Added(val id: Int, val text: String) : TaskAction
    data class Changed(val task: Task) : TaskAction
    data class Deleted(val id: Int) : TaskAction
}
