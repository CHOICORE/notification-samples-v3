package me.choicore.samples.notification

interface OutboxRepository {
    fun register(
        dispatchStrategy: DispatchStrategy,
        envelope: Envelope,
        sender: Sender,
        recipient: Recipient,
    ): Long
}
