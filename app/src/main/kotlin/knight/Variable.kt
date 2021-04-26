 package knight

/**
 * The type within Knight that is used to represent variables.
 *
 * As per the Knight specs, all variables are global.
 *
 * Note that the constructor for this class is intentionally private---to get a new [Variable], you must go through the
 * [fetch] function.
 */
class Variable private constructor(final val name: String): NonIdempotent() {
	companion object {
		private var VARIABLES: MutableMap<String, Variable> = HashMap()

		/**
		 * Retrieves a variable for the given [name].
		 *
		 * If a variable with the given name has not been used yet, a new one is created for it. This variable starts off
		 * unassigned, and may be initialized by assigning to [value].
		 */
		fun fetch(name: String) = VARIABLES.getOrPut(name) { Variable(name) }
	}

	/**
	 * The value associated with this Variable.
	 *
	 * A value of `null` indicates it has yet to be assigned.
	 */
	var value: Value? = null

	override fun dump() = print("Identifier($name)")

	/**
	 * Returns the last assigned value for [this].
	 *
	 * @throws RunException Thrown if this class hasn't been assigned a value yet.
	 */
	override fun run() = value ?: throw RunException("undefined variable: $name")
}
