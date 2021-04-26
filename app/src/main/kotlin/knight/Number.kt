package knight

import knight.Value
import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * The numeric value within Knight.
 *
 * As per the Knight specs, the only numbers in Knight are integers.
 */
class Number(val num: Long) : Idempotent<Long>(num) {
	override fun dump() = print("Number($data)")

	/**
	 * Simply returns the number associated with this class.
	 */
	override fun toLong() = data

	/**
	 * Simply returns the string representation [data].
	 */
	override fun toString() = "" + data

	/**
	 * Returns whether [data] is nonzero.
	 */
	override fun toBoolean() = data != 0L

	/**
	 * Converts [other] to a `Long` and compares it against [data].
	 */
	override operator fun compareTo(other: Value) = data.compareTo(other.toLong())

	/**
	 * Converts [other] to a `Long` and adds it to [data].
	 */
	override operator fun plus(other: Value) = Number(data + other.toLong())

	/**
	 * Converts [other] to a `Long` and subtracts it from [data].
	 */
	override operator fun minus(other: Value) = Number(data - other.toLong())

	/**
	 * Converts [other] to a `Long` and multiplies [data] by it.
	 */
	override operator fun times(other: Value) = Number(data * other.toLong())

	/**
	 * Converts [other] to a `Long` and divides [data] by it.
	 *
	 * @throws RunException if [other]s' `Long` representation is zero.
	 */
	override operator fun div(other: Value) =
		other
			.toLong()
			.also { it == 0 && throw RunException("cannot divide by zero") }
			.let { Number(data / it) }

	/**
	 * Converts [other] to a `Long` and modulos [data] by it.
	 *
	 * @throws RunException if [other]s' `Long` representation is zero.
	 */
	override operator fun rem(other: Value) =
		other
			.toLong()
			.also { it == 0 && throw RunException("cannot modulo by zero") }
			.let { Number(data / it) }

	/**
	 * Converts [other] to a `Long` and exponentiates [data] by it.
	 *
	 * @throws RunException if [other]s' `Long` representation is negative and [data] is zero.
	 */	
	override infix fun pow(other: Value) =
		other
			.toLong()
			.also { it < 0 && data == 0 && throw RunException("cannot raise zero to a negative power") }
			.let { Number(data.toDouble().pow(it.toDouble()).roundToLong())} 

}
