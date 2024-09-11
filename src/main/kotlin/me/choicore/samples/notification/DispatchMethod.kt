package me.choicore.samples.notification

enum class DispatchMethod(
    private val description: String,
) {
    IMMEDIATE("즉시 발송"),
    SCHEDULED("예약 발송"),
}
