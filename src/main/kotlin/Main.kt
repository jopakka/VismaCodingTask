import kotlin.system.exitProcess

fun main() {
    println("Choose action:")
    println("[1] Login")
    println("[2] Confirm")
    println("[3] Sign")
    println("[4] Exit")

    var actionInput: String?
    do {
        print("Enter your choice: ")
        actionInput = readlnOrNull()
    } while (actionInput?.toInt() !in 1..4)
    val action = actionInput?.toInt()
    if(action == 4) exitProcess(-1)

    print("Enter source: ")
    val source = readlnOrNull()
    if(source.isNullOrBlank()) exitProcess(-1)

    var url = "visma-identity://"
    when(action) {
        1 -> url += "login?source=$source"
        2 -> {
            var paymentNumber: String?
            do {
                print("Enter payment number: ")
                paymentNumber = readlnOrNull()
            } while (paymentNumber.isNullOrBlank())
            url += "confirm?source=$source&paymentnumber=$paymentNumber"
        }
        3 -> {
            var documentId: String?
            do {
                print("Enter document id: ")
                documentId = readlnOrNull()
            } while (documentId.isNullOrBlank())
            url += "sign?source=$source&documentid=$documentId"
        }
    }

    val request = RequestHandler(url)
    print("Action: ")
    when(request.action) {
        is LoginAction -> println(request.action.type)
        is ConfirmAction -> {
            println(request.action.type)
            println("Payment number: ${request.action.paymentNumber}")
        }
        is SignAction -> {
            println(request.action.type)
            println("Document ID: ${request.action.documentId}")
        }
    }
}