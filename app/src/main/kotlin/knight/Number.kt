package knight

import knight.Value
import kotlin.math.pow
import kotlin.math.roundToLong

class Number(val num: Long) : Value {
	override fun run() = this
	override fun dump() = print("Number($num)")

	override fun toLong()    = num
	override fun toString()  = "" + num
	override fun toBoolean() = num != 0L

	override operator fun compareTo(other: Value) = num.compareTo(other.toLong())
	override          fun equals(other: Any?) = other is Number && num == other.num

	override operator fun plus(other: Value)  = Number(num + other.toLong())
	override operator fun minus(other: Value) = Number(num - other.toLong())
	override operator fun times(other: Value) = Number(num * other.toLong())
	override operator fun div(other: Value)   = Number(num / other.toLong())
	override operator fun rem(other: Value)   = Number(num % other.toLong())
	override    infix fun pow(other: Value)   = Number(num.toDouble().pow(other.toLong().toDouble()).roundToLong())
}
