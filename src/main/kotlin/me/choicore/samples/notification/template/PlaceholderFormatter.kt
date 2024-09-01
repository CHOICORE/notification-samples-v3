package me.choicore.samples.notification.template

import me.choicore.samples.notification.template.Placeholder.Companion.WELL_KNOWN_PLACEHOLDER_BRACKETS

data class PlaceholderFormatter(
    private val startsWith: String,
    private val endsWith: String,
) {
    init {
        require(startsWith.isNotBlank()) { "startsWith must not be blank" }
        require(endsWith.isNotBlank()) { "endsWith must not be blank" }

        val matched: Boolean =
            WELL_KNOWN_PLACEHOLDER_BRACKETS.any { (s: String, e: String) ->
                startsWith == s && endsWith == e
            }
        require(matched) {
            "startWith and endWith must not be the same as known brackets. Supported brackets: ${
                WELL_KNOWN_PLACEHOLDER_BRACKETS.entries.map {
                    "${it.key}placeholder${it.value}"
                }
            }"
        }
    }

    fun format(value: String): String = "$startsWith$value$endsWith"

    fun parse(value: String): String {
        require(value = value.startsWith(startsWith) && value.endsWith(endsWith)) {
            "value must start with $startsWith and end with $endsWith"
        }
        return value.removePrefix(startsWith).removeSuffix(endsWith)
    }

    fun hasPlaceholders(value: String): Boolean {
        val start: Int = value.indexOf(startsWith)
        val end: Int = value.indexOf(endsWith)
        return start != -1 && end != -1 && start < end
    }

    companion object {
        private val defaultBrackets: Map.Entry<String, String> = WELL_KNOWN_PLACEHOLDER_BRACKETS.entries.first()
        val DEFAULT = PlaceholderFormatter(startsWith = defaultBrackets.key, endsWith = defaultBrackets.value)

        fun ofPattern(brackets: Pair<String, String>): PlaceholderFormatter =
            PlaceholderFormatter(startsWith = brackets.first, endsWith = brackets.second)

        fun ofPattern(
            startsWith: String,
            endsWith: String,
        ): PlaceholderFormatter = PlaceholderFormatter(startsWith, endsWith)
    }
}
