package me.choicore.samples.notification.template

interface TemplateRepository {
    fun register(definition: TemplateDefinition): Long

    fun existsByName(name: String): Boolean

    fun getTemplate(name: String): Template
}
