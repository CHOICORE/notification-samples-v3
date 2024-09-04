package me.choicore.samples.notification.template

import java.util.concurrent.atomic.AtomicLong

class TestTemplateRepository : TemplateRepository {
    private val database = mutableMapOf<Long, Template>()
    private var sequence = AtomicLong(0)

    override fun register(definition: TemplateDefinition): Long {
        val id: Long = sequence.incrementAndGet()
        database[id] =
            Template(
                id,
                definition.key,
                definition.type,
                definition.name,
                definition.description,
                definition.title,
                definition.content,
                definition.options,
            )
        return id
    }

    override fun existsByName(name: String): Boolean = database.values.any { it.name == name }

    override fun getTemplate(name: String): Template =
        database.values.find { it.name == name }
            ?: throw IllegalArgumentException("Template not found: $name")
}
