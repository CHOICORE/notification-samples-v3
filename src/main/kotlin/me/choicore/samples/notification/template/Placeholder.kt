package me.choicore.samples.notification.template

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class Placeholder(
    val target: String,
    val replacement: String,
) {
    init {
        validateTarget()
        validateReplacement()
    }

    private fun validateTarget() {
        log.debug("Validating placeholder target: {}", target)
        require(target.isNotBlank()) { "target must not be blank" }
        validatePlaceholderFormat()
        log.debug("Validation passed for target: {}", target)
    }

    private fun validateReplacement() {
        require(replacement.isNotBlank()) { "replacement must not be blank" }
    }

    private fun validatePlaceholderFormat() {
        require(
            WELL_KNOWN_PLACEHOLDER_BRACKETS.any { (start, end) ->
                target.startsWith(start) &&
                    target.endsWith(end) &&
                    !target.substringAfter(start).substringBeforeLast(end).any { it in charArrayOf('{', '}') }
            },
        ) {
            "Target must be enclosed in known brackets. Supported brackets: ${getSupportedFormats()}"
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(Placeholder::class.java)
        val WELL_KNOWN_PLACEHOLDER_BRACKETS =
            mapOf(
                "#{" to "}",
                "\${" to "}",
                "{" to "}",
            )

        fun getSupportedFormats(): String =
            WELL_KNOWN_PLACEHOLDER_BRACKETS.entries.joinToString(", ") { (startsWith: String, endsWith: String) ->
                "${startsWith}placeholder$endsWith"
            }
    }
}
