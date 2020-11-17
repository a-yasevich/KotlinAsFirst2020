@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E = get(Cell(row, column))

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E) = set(Cell(row, column), value)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    else return MatrixImpl(height, width, e)
}

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    private val map = mutableMapOf<Cell, E>()
    private val isntInsideException = IndexOutOfBoundsException("Cell is not inside the matrix")

    init {
        for (i in 1 until height)
            for (j in 1 until width)
                map[Cell(i, j)] = e
    }

    override fun get(cell: Cell): E {
        if (cell.isInside())
            return map[cell] ?: throw NullPointerException("Cell is empty")
        else throw isntInsideException
    }

    override fun set(cell: Cell, value: E) {
        if (cell.isInside())
            map[cell] = value
        else throw isntInsideException
    }

    private fun Cell.isInside() = this.row in 0 until height && this.column in 0 until width
    fun getElements() = map

    override fun equals(other: Any?) =
        other is MatrixImpl<*> &&
                height == other.height &&
                width == other.width &&
                map == other.getElements()

    override fun toString(): String = map.toString()

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + map.hashCode()
        return result
    }
}

