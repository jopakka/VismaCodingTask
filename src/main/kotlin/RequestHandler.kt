import java.net.URI
import java.util.*

class RequestHandler(url: String) {
    val action: Action

    init {
        val (uri, query) = parseUrl(url)
        checkScheme(uri)
        action = checkAction(uri.host, query)
    }

    /**
     * Parses URL and returns URI and map of query parameters
     */
    private fun parseUrl(url: String) : Pair<URI, Map<String, String>> {
        val uri = URI(url)
        val queryMap = getQueryMap(uri.query)
        return uri to queryMap
    }

    /**
     * Checks if scheme is "visma-identity".
     * Throws an error if it is not
     */
    private fun checkScheme(uri: URI) {
        val scheme = uri.scheme
        if(scheme != "visma-identity") throw IllegalArgumentException("Unknown scheme")
    }

    /**
     * Checks which action is selected and returns corresponding action class.
     * Throws an error if source is missing
     * or if action is confirm, and it is missing payment number
     * or if action is sign, and it is missing document ID
     * or if action is invalid
     */
    private fun checkAction(host: String, query: Map<String, String>): Action {
        val source = query.getValue("source")
        if(source.isBlank()) throw IllegalArgumentException("Missing source")

        return when (host) {
            "login" -> LoginAction(source)
            "confirm" -> {
                val paymentNumber = query.getValue("paymentnumber")
                if(paymentNumber.isBlank()) throw IllegalArgumentException("Missing payment number")
                ConfirmAction(source, paymentNumber)
            }
            "sign" -> {
                val documentId = query.getValue("documentid")
                if(documentId.isBlank()) throw IllegalArgumentException("Missing document ID")
                val uuid = UUID.fromString(documentId)
                SignAction(source, uuid)
            }
            else -> throw IllegalArgumentException("Unknown action")
        }
    }
}