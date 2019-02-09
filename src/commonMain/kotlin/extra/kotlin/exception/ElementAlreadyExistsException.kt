package extra.kotlin.exception

/**
 * Used to indicate that that element already exists, and replacing it is refused.
 */
class ElementAlreadyExistsException(message: String): Exception(message)
