package me.choicore.samples.notification

data class Retryable(
    val retries: Int = 0,
    val threshold: Int = 5,
) {
    fun attempt(): Retryable = copy(retries = retries + 1)

    fun reset(): Retryable = copy(retries = 0)

    fun retryable(): Boolean = retries < threshold

    companion object {
        val DEFAULT = Retryable()
        val ONCE = Retryable(threshold = 1)
    }
}
