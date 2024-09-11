package me.choicore.samples.notification

import me.choicore.samples.notification.template.PlaceholderRegistry
import me.choicore.samples.notification.template.Template
import me.choicore.samples.notification.template.TemplateRegistry

class OutboxRegistrar(
    private val outboxRepository: OutboxRepository,
    private val templateRegistry: TemplateRegistry,
) {
    fun register(
        dispatchStrategy: DispatchStrategy,
        templateName: String,
        placeholderRegistry: PlaceholderRegistry,
        sender: Sender,
        recipient: Recipient,
    ) {
        val template: Template = templateRegistry.getTemplate(templateName)
        val envelope = Envelope(template = template, placeholderRegistry = placeholderRegistry)

        outboxRepository.register(
            dispatchStrategy = dispatchStrategy,
            envelope = envelope,
            sender = sender,
            recipient = recipient,
        )
    }

    fun register(
        dispatchStrategy: DispatchStrategy,
        templateName: String,
        placeholderRegistry: PlaceholderRegistry,
        recipient: Recipient,
    ) = this.register(
        dispatchStrategy = dispatchStrategy,
        templateName = templateName,
        placeholderRegistry = placeholderRegistry,
        sender = Sender.ADMINISTRATOR,
        recipient = recipient,
    )
}
