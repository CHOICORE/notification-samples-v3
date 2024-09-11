package me.choicore.samples.notification

import org.springframework.stereotype.Repository

@Repository
class TestOutboxRepository : OutboxRepository {
    override fun register(
        dispatchStrategy: DispatchStrategy,
        envelope: Envelope,
        sender: Sender,
        recipient: Recipient,
    ): Long = 0
}
