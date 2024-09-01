package me.choicore.samples.notification.template

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PlaceholderBinder {
    fun bind(
        component: Component,
        placeholderRegistry: PlaceholderRegistry,
    ) {
        log.debug("Binding placeholders for component of type: {}", component::class.simpleName)
        when (component) {
            is Component.Template -> {
                log.trace("inspection of placeholders in text: '{}'", component.value)

                val placeholders: List<Placeholder> = inspect(component.value, placeholderRegistry)

                if (placeholders.isNotEmpty()) {
                    component.setPlaceholders(placeholders)
                    log.debug("Placeholders bound successfully")
                } else {
                    log.info("No placeholders found to bind")
                }
            }

            else -> log.info("Component is not a template, skipping binding. Component type: {}", component::class.simpleName)
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
