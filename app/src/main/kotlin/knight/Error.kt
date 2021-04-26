package knight

/**
 * The parent class for all exceptions within Knight
 */
open class KnightException(message: String) : RuntimeException(message)

/**
 * An issue occurred whilst parsing Knight code.
 */
class ParseException(message: String) : KnightException(message)

/**
 * An issue occured whilst running Knight code.
 */
class RunException(message: String) : KnightException(message)
