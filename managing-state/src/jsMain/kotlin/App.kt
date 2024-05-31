import react.FC
import react.Props
import react.Reducer
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.useReducer
import react.useState
import web.html.InputType

val TaskApp = FC {
    val (tasks, dispatch) = useReducer(tasksReducer, initialTasks)

    val handleAddTask: (String) -> Unit = { text ->
        dispatch(TaskAction.Added(id = nextId++, text = text))
    }

    val handleChangedTask: (Task) -> Unit = { task ->
        dispatch(TaskAction.Changed(task))
    }

    val handleDeleteTask: (Int) -> Unit = { taskId ->
        dispatch(TaskAction.Deleted(taskId))
    }

    h1 { +"Prague itinerary" }
    AddTask {
        onAddTask = handleAddTask
    }
    TaskList {
        this.tasks = tasks
        onChangeTask = handleChangedTask
        onDeleteTask = handleDeleteTask
    }
}

external interface AddTaskProps : Props {
    var onAddTask: (String) -> Unit
}

val AddTask = FC<AddTaskProps> { props ->
    var text: String by useState("")

    input {
        placeholder = "Add task"
        value = text
        onChange = { text = it.target.value }
    }
    button {
        onClick = {
            text = ""
            props.onAddTask(text)
        }
        +"Add"
    }
}

sealed interface TaskAction {
    data class Added(val id: Int, val text: String) : TaskAction
    data class Changed(val task: Task) : TaskAction
    data class Deleted(val id: Int) : TaskAction
}

external interface TaskListProps : Props {
    var tasks: List<Task>
    var onChangeTask: (Task) -> Unit
    var onDeleteTask: (Int) -> Unit
}

val TaskList = FC<TaskListProps> { props ->
    ul {
        props.tasks.forEach { task ->
            li {
                key = task.id.toString()
                TaskComponent {
                    this.task = task
                    onChange = props.onChangeTask
                    onDelete = props.onDeleteTask
                }
            }
        }
    }
}

external interface TaskProps : Props {
    var task: Task
    var onChange: (Task) -> Unit
    var onDelete: (Int) -> Unit
}

val TaskComponent = FC<TaskProps> { props ->
    var isEditing: Boolean by useState(false)

    label {
        input {
            type = InputType.checkbox
            checked = props.task.done
            onChange = {
                props.onChange(props.task.copy(done = it.target.checked))
            }
        }
        if (isEditing) {
            input {
                value = props.task.text
                onChange = {
                    props.onChange(props.task.copy(text = it.target.value))
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
            onClick = { props.onDelete(props.task.id) }
            +"Delete"
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
