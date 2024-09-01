package me.choicore.samples.notification.template

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class Placeholder(
    val target: String,
    val replacement: String,
) {
    init {
        validate(target)
        require(replacement.isNotBlank()) { "replacement must not be blank" }
    }

    private fun validate(target: String) {
        log.trace("placeholder target: $target")
        require(target.isNotBlank()) { "target must not be blank" }

        val matched: Boolean =
            WELL_KNOWN_PLACEHOLDER_BRACKETS.any { (s: String, e: String) ->
                target.startsWith(s) && target.endsWith(e)
            }
        require(matched) {
            "Target must be enclosed in known brackets. Supported brackets: ${
                WELL_KNOWN_PLACEHOLDER_BRACKETS.entries.map {
                    "${it.key}placeholder${it.value}"
                }
            }"
        }
    }

    companion object {
        val WELL_KNOWN_PLACEHOLDER_BRACKETS =
            mapOf(
                "#{" to "}",
                "\${" to "}",
                "{" to "}",
            )

        private val log: Logger = LoggerFactory.getLogger(Placeholder::class.java)
    }
}
