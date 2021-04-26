package knight

/**
 * A class representing a type which may change between runs.
 *
 * Note: Implementors of this class should (a) guarantee that [run] returns a different value or (b) implement all of
 * xthe overridden functions themselves. If this contract isn't honored, infinite loops will occur.
 */
abstract class NonIdempotent : Value {
	/**
	 * Converts the result of [run]ning `this` to a `Long`.
	 */
	override fun toLong() = run().toLong()

	/**
	 * Converts the result of [run]ning `this` to a `String`.
	 */
	override fun toString() = run().toString()

	/**
	 * Converts the result of [run]ning `this` to a `Boolean`.
	 */
	override fun toBoolean() = run().toBoolean()

	/**
	 * Compares the result of [run]ning `this` to that of [run]ning [other].
	 */
	override operator fun compareTo(other: Value) = run().compareTo(other)

	/**
	 * Adds to the result of [run]ning `this` the result of [run]ning [other].
	 */
	override operator fun plus(other: Value) = run() + other.run()

	/**
	 * Subtracts from the result of [run]ning `this` the result of [run]ning [other].
	 */
	override operator fun minus(other: Value) = run() - other.run()

	/**
	 * Multiplies the result of [run]ning `this` by the result of [run]ning [other].
	 */
	override operator fun times(other: Value) = run() * other.run()

	/**
	 * Divides from the result of [run]ning `this` the result of [run]ning [other].
	 */
	override operator fun div(other: Value) = run() / other.run()

	/**
	 * Modulos from the result of [run]ning `this` the result of [run]ning [other].
	 */
	override operator fun rem(other: Value) = run() % other.run()

	/**
	 * Exponentiates the result of [run]ning `this` by the result of [run]ning [other].
	 */
	override infix fun pow(other: Value) = run() pow other.run()
}
