package me.choicore.samples.notification.template

import javax.print.attribute.UnmodifiableSetException

sealed interface Component {
    val key: String
    val label: String?
    val type: Type
    val value: String

    fun render(): String

    data class Static(
        override val key: String,
        override val label: String? = null,
        override val type: Type,
        override val value: String,
    ) : Component {
        constructor(key: String, type: Type, value: String) : this(key = key, label = null, type = type, value = value)

        override fun render(): String = value
    }

    data class Template(
        override val key: String,
        override val label: String? = null,
        override val type: Type,
        override val value: String,
    ) : Component {
        constructor(key: String, type: Type, value: String) : this(
            key = key,
            label = null,
            type = type,
            value = value,
        )

        private var _placeholders: List<Placeholder>? = null

        val placeholders: List<Placeholder>
            get() = _placeholders ?: throw IllegalStateException("Placeholders are not set")

        fun setPlaceholders(placeholders: List<Placeholder>) {
            if (_placeholders != null) {
                throw UnmodifiableSetException("Placeholders are already set")
            }
            _placeholders = placeholders
        }

        override fun render(): String {
            if (_placeholders == null) {
                throw IllegalStateException("Placeholders are not set")
            }
            var resolvedValue: String = value
            placeholders.forEach { resolvedValue = resolvedValue.replace(it.target, it.replacement) }
            return resolvedValue
        }
    }

    enum class Type {
        TITLE,
        CONTENT,
    }
}
