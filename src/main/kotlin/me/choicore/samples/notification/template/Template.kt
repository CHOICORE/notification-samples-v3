package me.choicore.samples.notification.template

import me.choicore.samples.notification.template
    .TemplateType.CONTENT
import me.choicore.samples.notification.template
    .TemplateType.TITLE_AND_CONTENT
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class Template(
    val id: Long,
    val key: String,
    val type: TemplateType,
    val name: String,
    val description: String,
    val title: Message?,
    val content: Message,
    val options: List<Message> = emptyList(),
) {
    init {
        require(name.isNotBlank()) { "template name is required, must not be empty" }

        when (type) {
            TITLE_AND_CONTENT -> {
                requireNotNull(title) { "Subject is required for $this" }
                require(title.value.isNotBlank()) { "Subject is required for $this" }
            }

            CONTENT -> {
                require(content.value.isNotBlank()) { "Content is required for $this" }
            }
        }
    }

    fun bind(registry: PlaceholderRegistry) {
        log.info("Binding placeholders for template '{}' with key '{}'", name, key)
        val all: List<Message> = listOfNotNull(title, content) + options
        if (all.isEmpty()) {
            log.warn("Template '{}' (key: '{}') contains no component to bind.", name, key)
        } else {
            all.forEachIndexed { index, component ->
                log.debug(
                    "Binding placeholders for component {} of template '{}': {}",
                    index + 1,
                    name,
                    component::class.simpleName,
                )
                placeholderBinder.bind(component, registry)
            }
        }
    }

    companion object {
        private val placeholderBinder: PlaceholderBinder = PlaceholderBinder()
        private val log: Logger = LoggerFactory.getLogger(Template::class.java)
    }
}
