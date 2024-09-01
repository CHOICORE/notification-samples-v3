package me.choicore.samples.notification.template

class PlaceholderRegistry {
    private val placeholders = mutableMapOf<String, Placeholder>()

    fun getPlaceholders(): List<Placeholder> = this.placeholders.values.toList()

    fun getPlaceholder(target: String): Placeholder = this.placeholders[target] ?: throw NoSuchElementException("Placeholder not exists")

    fun addPlaceholder(placeholder: Placeholder) {
        this.placeholders[placeholder.target] = placeholder
    }

    fun addPlaceholder(
        target: String,
        replacement: Any,
    ) {
        addPlaceholder(Placeholder(target, replacement.toString()))
    }

    fun addPlaceholders(placeholders: List<Placeholder>) {
        placeholders.forEach { addPlaceholder(it) }
    }

    fun addPlaceholders(vararg placeholders: Placeholder) {
        if (placeholders.isEmpty()) throw IllegalArgumentException("Placeholders must not be empty")
        addPlaceholders(placeholders.toList())
    }
}
