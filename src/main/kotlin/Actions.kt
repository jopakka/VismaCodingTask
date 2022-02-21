import java.util.UUID

open class Action(open val source: String, open val type: String)

class LoginAction(override val source: String) : Action(source, "login")
class ConfirmAction(override val source: String, val paymentNumber: String) : Action(source, "confirm")
class SignAction(override val source: String, val documentId: UUID) : Action(source, "sign")