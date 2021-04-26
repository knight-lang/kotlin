package knight

/**
 * An exception indicating that a class does not support an operation.
 */
class UndefinedOperation(message: String) : KnightException(message)

/**
 * The interface which all Knight values implement.
 */
interface Value : Comparable<Value> {
	/**
	 * Returns the result of executing this value.
	 */
	abstract fun run(): Value

	/**
	 * Writes the debug representation of this class to stdout.
	 *
	 * Note that a trailing newline will not be added.
	 */
	abstract fun dump()

	/**
	 * Converts this class to its `Long` representation.
	 */
	abstract fun toLong(): Long

	/**
	 * Converts this class to its `String` representation.
	 */
	override abstract fun toString(): String

	/**
	 * Converts this class to its `Boolean` representation.
	 */
	abstract fun toBoolean(): Boolean

	/**
	 * Compares [this] to [other]
	 *
	 * The default implementation throws [UndefinedOperation].
	 */
	override operator fun compareTo(other: Value): Int = throw UndefinedOperation("cannot compare $this")

	/**
	 * Returns the logical negation of [this].
	 *
	 * This method probably shouldn't be overridden---it simply returns the logical negation of [toBoolean].
	 */
	operator fun not(): Boolean = !toBoolean()

	/**
	 * Adds [this] and [other] together.
	 *
	 * The default implementation throws [UndefinedOperation].
	 */
	operator fun plus(other: Value): Value = throw UndefinedOperation("cannot add $this")

	/**
	 * Subtracts [other] from [this].
	 *
	 * The default implementation throws [UndefinedOperation].
	 */
	operator fun minus(other: Value): Value = throw UndefinedOperation("cannot subtract $this")

	/**
	 * Multiplies [this] and [other] together.
	 *
	 * The default implementation throws [UndefinedOperation].
	 */
	operator fun times(other: Value): Value = throw UndefinedOperation("cannot multiply $this")

	/**
	 * Divides [this] by [other] together.
	 *
	 * The default implementation throws [UndefinedOperation].
	 */
	operator fun div(other: Value): Value = throw UndefinedOperation("cannot divide $this")

	/**
	 * Modulos [this] by [other] together.
	 *
	 * The default implementation throws [UndefinedOperation].
	 */
	operator fun rem(other: Value): Value = throw UndefinedOperation("cannot modulo $this")

	/**
	 * Exponentiates [this] by [other] together.
	 *
	 * The default implementation throws [UndefinedOperation].
	 */
	infix fun pow(other: Value): Value = throw UndefinedOperation("cannot exponentiate $this")
}
