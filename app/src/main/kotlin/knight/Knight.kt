package knight

import kotlin.system.exitProcess
import java.io.File

/**
 * Parses and runs [input] as Knight code, returning the result of execution.
 *
 * @throws ParseException Thrown when no value could be parsed from [input].
 * @throws ParseException Thrown when [input] contains invalid Knight syntax.
 */
fun run(input: String): Value = Stream(input).parse()?.run() ?: throw ParseException("nothing to parse")

private fun usage() : Nothing {
	println("usage: knight (-e 'expr' | -f filename)")
	exitProcess(1)
}

fun main(args: Array<String>) {
	if (args.size != 2)
		usage()

	val stream =
		when (args[0]) {
			"-e" -> args[1]
			"-f" ->
				try {
					File(args[1]).readText()
				} catch (e: Exception) {
					println("unable to read file ${args[1]}: $e")
					exitProcess(1)
				}
			else -> usage()
		}

	try {
		run(stream)
	} catch (e: KnightException) {
		println(e)
		exitProcess(1)
	} catch (e: Exception) {
		println("[bug] uncaught error:")
		e.printStackTrace()
		exitProcess(1)
	}
}
