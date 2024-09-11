package me.choicore.samples.notification

import java.time.LocalDateTime

sealed interface DispatchStrategy {
    val channel: DispatchChannel
    val method: DispatchMethod
    val retryable: Retryable
    val scheduledAt: LocalDateTime

    fun isSatisfied(): Boolean

    data class Immediate(
        override val channel: DispatchChannel,
        override val retryable: Retryable,
    ) : DispatchStrategy {
        override val method = DispatchMethod.IMMEDIATE
        override val scheduledAt: LocalDateTime = LocalDateTime.now()

        override fun isSatisfied(): Boolean = true
    }

    data class Scheduled(
        override val channel: DispatchChannel,
        override val scheduledAt: LocalDateTime,
        override val retryable: Retryable,
    ) : DispatchStrategy {
        override val method = DispatchMethod.SCHEDULED

        override fun isSatisfied(): Boolean = LocalDateTime.now().isAfter(scheduledAt)
    }
}
