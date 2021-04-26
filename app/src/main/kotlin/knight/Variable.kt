package knight

import knight.NonIdempotent

class Variable private constructor(final val name: String): NonIdempotent() {
	companion object {
		private var VARIABLES: MutableMap<String, Variable> = HashMap()

		fun fetch(name: String) = VARIABLES.getOrPut(name) { Variable(name) }
	}

	var value: Value? = null

	override fun dump() = print("Identifier($name)")
	override fun run() = value ?: throw RunException("undefined variable: $name")
}