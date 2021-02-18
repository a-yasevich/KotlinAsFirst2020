@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import lesson1.task1.sqr
import kotlin.math.max

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex {
    val re: Double
    val im: Double


    constructor(re: Double, im: Double) {
        this.re = re
        this.im = im
    }

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) {
        re = x
        im = 0.0
    }

    /**
     * Конструктор из строки вида x+yi
     */
    constructor(s: String) {
        fun String.convertImPart() =
            if (this == "-i") -1.0
            else if (this == "i" || this == "+i") 1.0
            else this.dropLast(1).toDouble()

        val rePart = Regex("""^((-|\+|^)[0-9.]+)(?![i])""").find(s)?.value
        val imPart = Regex("""((-|\+|^)[0-9.]*i)""").find(s)?.value
        re = rePart?.toDouble() ?: 0.0
        im = imPart?.convertImPart() ?: 0.0
    }

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-re, -im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = this + -other

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex = Complex(re * other.re - im * other.im, re * other.im + other.re * im)

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {
        val calculatedRe = (re * other.re + im * other.im) / (sqr(other.re) + sqr(other.im))
        val calculatedIm = (other.re * im + re * other.im) / (sqr(other.re) + sqr(other.im))
        return Complex(calculatedRe, calculatedIm)
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        other is Complex &&
                re == other.re &&
                im == other.im

    /**
     * Преобразование в строку
     */
    override fun toString(): String {
        return when {
            im == 0.0 -> re.toString()
            re == 0.0 -> {
                when (im) {
                    1.0 -> "i"
                    -1.0 -> "-i"
                    else -> "${im}i"
                }
            }
            else -> {
                if (im > 0)
                    "$re+${im}i"
                else "$re${im}i"
            }
        }

    }

    override fun hashCode(): Int {
        var result = re.hashCode()
        result = 31 * result + im.hashCode()
        return result
    }
}
