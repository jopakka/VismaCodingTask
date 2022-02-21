// https://stackoverflow.com/a/11733697
fun getQueryMap(query: String): Map<String, String> {
    val params = query.split("&").toTypedArray()
    val map: MutableMap<String, String> = HashMap()
    for (param in params) {
        val name = param.split("=").toTypedArray()[0]
        val value = param.split("=").toTypedArray()[1]
        map[name] = value
    }
    return map
}