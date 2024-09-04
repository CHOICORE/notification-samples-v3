package me.choicore.samples.notification.template

import me.choicore.samples.notification.template.TemplateType.CONTENT
import me.choicore.samples.notification.template.TemplateType.TITLE_AND_CONTENT

data class TemplateDefinition(
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
                requireNotNull(title) { "Title is required for $this" }
                require(title.value.isNotBlank()) { "Title is required for $this" }
            }

            CONTENT -> {
                require(content.value.isNotBlank()) { "Content is required for $this" }
            }
        }
    }
}
