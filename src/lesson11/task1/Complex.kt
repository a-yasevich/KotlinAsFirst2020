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
class Complex(var re: Double, var im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Конструктор из строки вида x+yi
     */
    constructor(s: String) : this(0.0, 0.0) {
        fun String.convertIm() =
            if (this == "-i") -1.0
            else if (this == "i" || this == "+i") 1.0
            else this.dropLast(1).toDouble()

        val delimiter = max(s.indexOf('+'), s.indexOf('-', 1))

        if (delimiter == -1) {
            if (s.contains('i')) {
                im = s.convertIm()
                re = 0.0
            } else {
                re = s.toDouble()
                im = 0.0
            }
        } else {
            im = s.substring(delimiter until s.length).convertIm()
            re = s.substring(0 until delimiter).toDouble()
        }
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
