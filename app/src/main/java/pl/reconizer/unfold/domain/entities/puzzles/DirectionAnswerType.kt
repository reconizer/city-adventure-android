package pl.reconizer.unfold.domain.entities.puzzles

enum class DirectionAnswerType(val code: String) {
    UP("u"),
    RIGHT("r"),
    DOWN("d"),
    LEFT("l");

    fun isSameAxisAs(direction: DirectionAnswerType): Boolean {
        return if (direction == this) {
            true
        } else {
            when (this) {
                UP -> direction == DOWN
                RIGHT -> direction == LEFT
                DOWN -> direction == UP
                LEFT -> direction == RIGHT
            }
        }
    }
}