package knight

/**
 * A class representing a type which does not change between [run]s.
 */
abstract class Idempotent<T>(val data: T) : Value {
	/**
	 * Simply returns `this`.
	 *
	 * Idempotent values do not change whenrun.
	 */
	final override fun run() = this

	/**
	 * Returns whether [other] is the exact same class as `this` and has the same [data].
	 */
	override fun equals(other: Any?) =
		other != null
			&& other is Idempotent<*>
			&& this::class == other::class
			&& data == other.data
}
