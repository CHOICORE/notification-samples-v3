package me.choicore.samples.notification.template

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PlaceholderBinder {
    fun bind(
        message: Message,
        placeholderRegistry: PlaceholderRegistry,
    ) {
        when (message) {
            is Message.Template -> {
                log.trace("Binding placeholders for template: '{}'", message.label)
                message.setPlaceholders(placeholders = placeholderRegistry.getPlaceholders())
                log.info("Placeholders bound successfully for template with label: '{}'", message.label)
            }

            else ->
                log.info(
                    "Skipping binding: message is not a template. label: '{}', type: '{}'",
                    message.label,
                    message::class.simpleName,
                )
        }
    }

    private fun inspect(
        text: String,
        registry: PlaceholderRegistry,
    ): List<Placeholder> =
        registry.getPlaceholders().filter { placeholder ->
            val matched: Boolean = text.contains(placeholder.target)
            if (matched) {
                log.debug("'{}' found in text", placeholder.target)
            } else {
                log.trace("'{}' not found in text", placeholder.target)
            }

            matched
        }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(PlaceholderBinder::class.java)
    }
}
