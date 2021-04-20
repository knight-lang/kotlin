package knight

import knight.Stream

fun main(args: Array<String>) {
	Stream(args[1]).parse()!!.run()
}
