package knight

import knight.Value
import knight.NonIdempotent
import knight.Number

class Ast(val func: Function, val args: Array<Value>): NonIdempotent() {
	override fun run() = (func.func)(args)
	override fun dump() = print("Function(${func.name}, $args)")
}

data class Function private constructor(val name: Char, val arity: Int, val func: (Array<Value>) -> Value) {
	override fun toString() = name.toString()

	companion object {
		private var FUNCTIONS: MutableMap<Char, Function> = HashMap()

		fun register(name: Char, arity: Int, func: (Array<Value>) -> Value) {
			FUNCTIONS[name] = Function(name, arity, func)
		}

		fun fetch(name: Char) = FUNCTIONS.get(name)

		init {
			register('R', 0, { Number(kotlin.math.abs(kotlin.random.Random.nextLong())) })
			register('P', 0) { Text(readLine() ?: "") }

			register('E', 1) { (code,) -> Stream(code.toString()).parse()?.run() ?: throw RunException("nothing parsed") }
			register('B', 1) { (block,) -> block }
			register('C', 1) { (block,) -> block.run().run() }
			register('`', 1) { (cmd,) -> 
				var builder = ProcessBuilder("/bin/sh", "-c", cmd.toString())
				val output: String

				try {
					var proc = builder.start()
					output = proc.inputStream.use { return@use String(it.readAllBytes()) }
					proc.waitFor()
				} catch (err: java.io.IOException) {
					throw RunException("Cannot run command: $err")
				}

				return@register Text(output)
			}

			register('Q', 1) { (status,) -> kotlin.system.exitProcess(status.toLong().toInt()) }
			register('!', 1) { (bool,) -> Bool(!bool) }
			register('L', 1) { (string,) -> Number(string.toString().length.toLong()) }
			register('D', 1) { it[0].run().also { it.dump(); println() } }
			register('O', 1) {
				val string = it[0].toString()

				if (string.lastOrNull() == '\\') {
					print(string.dropLast(1))
					System.out.flush()
				} else {
					println(string)
				}

				return@register Null()
			}

			register('+', 2) { (lhs, rhs) -> lhs + rhs }
			register('-', 2) { (lhs, rhs) -> lhs - rhs }
			register('*', 2) { (lhs, rhs) -> lhs * rhs }
			register('/', 2) { (lhs, rhs) -> lhs / rhs }
			register('%', 2) { (lhs, rhs) -> lhs % rhs }
			register('^', 2) { (lhs, rhs) -> lhs pow rhs }
			register('<', 2) { (lhs, rhs) -> Bool(lhs < rhs) }
			register('>', 2) { (lhs, rhs) -> Bool(lhs > rhs) }
			register('?', 2) { (lhs, rhs) -> Bool(lhs.run().equals(rhs.run())) }
			register('&', 2) { (lhs, rhs) -> lhs.run().let { if(it.toBoolean()) rhs.run() else it  } }
			register('|', 2) { (lhs, rhs) -> lhs.run().let { if(it.toBoolean()) it else rhs.run()  } }
			register(';', 2) { (lhs, rhs) -> lhs.run().let { rhs.run() } }
			register('=', 2) { (var_, val_) -> val_.run().also { (var_ as Variable).value = it }  }
			register('W', 2) { (cond, body) ->
				while (cond.toBoolean())
					body.run()
				return@register Null()
			}

			register('I', 3) { (cnd, ift, iff) -> if (cnd.toBoolean()) ift.run() else iff.run() }
			register('G', 3) {
				val string = it[0].toString()
				val start = it[1].toLong().toInt()
				val length = it[2].toLong().toInt()

				return@register Text(if (string.isEmpty()) "" else string.substring(start, start + length))
			}

			register('S', 4) { 
				val string = it[0].toString()
				val start = it[1].toLong().toInt()
				val length = it[2].toLong().toInt()
				val repl = it[3].toString()

				val prefix = string.substring(0, start)
				val suffix = if (start + length > string.length) "" else string.substring(start + length)

				return@register Text(prefix + repl + suffix)
			}
		}
	}
}
