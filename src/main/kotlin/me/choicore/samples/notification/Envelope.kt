package me.choicore.samples.notification

import me.choicore.samples.notification.template.PlaceholderRegistry
import me.choicore.samples.notification.template.Template
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class Envelope(
    val template: Template,
    val placeholderRegistry: PlaceholderRegistry,
) {
    init {
        bind()
    }

    var errorMessage: String? = null
        private set

    var crashed = false
        private set

    private fun bind() {
        try {
            template.bind(registry = placeholderRegistry)
        } catch (e: Exception) {
            errorMessage = "${e.message}"
            crashed = true
            log.info("binding errors: $errorMessage")
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(Envelope::class.java)
    }
}
