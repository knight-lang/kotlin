package knight

import knight.Stream
typealias int = Int

fun main(args: Array<String>) {
	Stream(args[1]).parse()!!.run()
}
