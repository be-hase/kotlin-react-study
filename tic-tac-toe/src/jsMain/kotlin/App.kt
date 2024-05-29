import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.useState
import web.cssom.ClassName

external interface SquareProps : Props {
    var value: String?
    var onSquareClick: () -> Unit
}

val Square = FC<SquareProps> { props ->
    button {
        className = ClassName("square")
        onClick = { props.onSquareClick() }
        +props.value.orEmpty()
    }
}

external interface BoardProps : Props {
    var xIsNext: Boolean
    var squares: MutableList<String?>
    var onPlay: (MutableList<String?>) -> Unit
}

val Board = FC<BoardProps> { props ->
    fun handleClick(i: Int) {
        if (calculateWinner(props.squares) != null || props.squares[i] != null) {
            return
        }
        val nextSquares = props.squares.toMutableList()
        nextSquares[i] = if (props.xIsNext) "X" else "◯"
        props.onPlay(nextSquares)
    }

    val winner = calculateWinner(props.squares)
    val status = if (winner == null) {
        "Next player: ${if (props.xIsNext) "X" else "◯"}"
    } else {
        "Winner: $winner"
    }

    div {
        className = ClassName("status")
        +status
    }
    div {
        className = ClassName("board-row")
        Square {
            value = props.squares[0]
            onSquareClick = { handleClick(0) }
        }
        Square {
            value = props.squares[1]
            onSquareClick = { handleClick(1) }
        }
        Square {
            value = props.squares[2]
            onSquareClick = { handleClick(2) }
        }
    }
    div {
        className = ClassName("board-row")
        Square {
            value = props.squares[3]
            onSquareClick = { handleClick(3) }
        }
        Square {
            value = props.squares[4]
            onSquareClick = { handleClick(4) }
        }
        Square {
            value = props.squares[5]
            onSquareClick = { handleClick(5) }
        }
    }
    div {
        className = ClassName("board-row")
        Square {
            value = props.squares[6]
            onSquareClick = { handleClick(6) }
        }
        Square {
            value = props.squares[7]
            onSquareClick = { handleClick(7) }
        }
        Square {
            value = props.squares[8]
            onSquareClick = { handleClick(8) }
        }
    }
}

val Game = FC {
    var history: List<MutableList<String?>> by useState(listOf(MutableList(9) { null }))
    var currentMove: Int by useState(0)
    val xIsNext = currentMove % 2 == 0
    val currentSquares = history[currentMove]

    fun handlePlay(nextSquares: MutableList<String?>) {
        val nextHistory = history.slice(0..currentMove) + listOf(nextSquares)
        history = nextHistory
        currentMove = nextHistory.size - 1
    }

    fun jumpTo(nextMove: Int) {
        currentMove = nextMove
    }

    div {
        className = ClassName("game")
        div {
            className = ClassName("game-board")
            Board {
                this.xIsNext = xIsNext
                squares = currentSquares
                onPlay = { handlePlay(it) }
            }
        }
        div {
            className = ClassName("game-info")
            ol {
                history.forEachIndexed { move, _ ->
                    li {
                        key = move.toString()
                        button {
                            onClick = { jumpTo(move) }
                            if (move > 0) {
                                +"Go to move #$move"
                            } else {
                                +"Go to game start"
                            }
                        }
                    }
                }
            }
        }
    }
}

fun calculateWinner(squares: List<String?>): String? {
    val lines = listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6),
    )
    lines.forEach { line ->
        val (a, b, c) = line
        if (squares[a] != null && squares[a] == squares[b] && squares[a] == squares[c]) {
            return squares[a]
        }
    }
    return null
}
