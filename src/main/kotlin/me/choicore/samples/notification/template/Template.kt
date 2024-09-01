package me.choicore.samples.notification.template

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class Template(
    val key: String,
    val name: String,
    val description: String,
    val components: List<Component>,
) {
    fun bind(registry: PlaceholderRegistry) {
        log.info("Binding placeholders for template '{}' with key '{}'", name, key)

        if (components.isEmpty()) {
            log.warn("Template '{}' (key: '{}') contains no components to bind.", name, key)
        } else {
            components.forEachIndexed { index, component ->
                log.debug("Binding placeholders for component {} of template '{}': {}", index + 1, name, component::class.simpleName)
                placeholderBinder.bind(component, registry)
            }
        }
    }

    companion object {
        private val placeholderBinder: PlaceholderBinder = PlaceholderBinder()
        private val log: Logger = LoggerFactory.getLogger(Template::class.java)
    }
}
