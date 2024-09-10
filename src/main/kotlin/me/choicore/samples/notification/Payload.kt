package me.choicore.samples.notification

import me.choicore.samples.notification.template.Message

data class Payload(
    val title: Message?,
    val content: Message,
    val options: List<Message> = emptyList(),
) {
    val all = listOfNotNull(title, content) + options

    fun get(key: String): Message? = all.find { it.key == key }
}
