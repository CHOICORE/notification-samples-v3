package me.choicore.samples.notification.template

import org.slf4j.Logger
import org.slf4j.LoggerFactory

sealed interface Message {
    val key: String
    val value: String
    val label: Label

    fun render(): String

    data class Static(
        override val key: String,
        override val label: Label,
        override val value: String,
    ) : Message {
        override fun render(): String = value
    }

    data class Template(
        override val key: String,
        override val label: Label,
        override val value: String,
        val placeholderStartsWith: String,
        val placeholderEndsWith: String,
    ) : Message {
        constructor(key: String, label: Label, value: String) : this(
            key = key,
            label = label,
            value = value,
            placeholderStartsWith = "#{",
            placeholderEndsWith = "}",
        )

        init {
            require(value.isNotBlank()) { "Message value is required, must not be empty" }
        }

        private var _placeholders: List<Placeholder> = mutableListOf()

        private var _placeholderNames: Set<String>? = null

        val placeholders: List<Placeholder>
            get() = _placeholders.toList()

        val placeholderNames: Set<String>
            get() = if (_placeholderNames == null) extractPlaceholderNames() else _placeholderNames!!

        fun setPlaceholders(placeholders: List<Placeholder>) {
            require(_placeholders.isEmpty()) { "Placeholders are already set" }

            val extracted: List<Placeholder> = placeholders.filter { it.target in placeholderNames }
            if (placeholderNames.size != extracted.size) {
                val unresolvedPlaceholders: Set<String> = placeholderNames - extracted.map { it.target }.toSet()
                require(unresolvedPlaceholders.isEmpty()) {
                    "Unresolved in placeholders: ${unresolvedPlaceholders.joinToString(", ")}"
                }
            }

            log.info("Total placeholders expected: {}, bound: {}", placeholderNames.size, extracted.size)
            _placeholders = extracted
        }

        private fun extractPlaceholderNames(): Set<String> {
            val startWith: String = placeholderStartsWith
            val endWith: String = placeholderEndsWith
            val placeholderNames: MutableSet<String> = mutableSetOf()
            var startIndex = 0

            while (true) {
                val start: Int = value.indexOf(startWith, startIndex)
                if (start == -1) {
                    break
                }
                val end: Int = value.indexOf(endWith, start + startWith.length)
                if (end == -1) {
                    break
                }
                placeholderNames.add(startWith + value.substring(start + startWith.length, end) + endWith)
                startIndex = end + endWith.length
            }

            _placeholderNames = placeholderNames
            return placeholderNames
        }

        override fun render(): String {
            if (placeholders.isEmpty()) {
                throw IllegalStateException("Placeholders are not set. please set placeholders before rendering")
            }

            var resolvedValue: String = value
            placeholders.forEach { resolvedValue = resolvedValue.replace(it.target, it.replacement) }
            return resolvedValue
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Template

            if (key != other.key) return false
            if (label != other.label) return false
            if (value != other.value) return false
            if (placeholders != other.placeholders) return false

            return true
        }

        override fun toString(): String = "Template(key='$key', label=$label, value='$value', placeholders=$placeholders)"

        override fun hashCode(): Int {
            var result = key.hashCode()
            result = 31 * result + label.hashCode()
            result = 31 * result + value.hashCode()
            result = 31 * result + placeholders.hashCode()
            return result
        }

        companion object {
            private val log: Logger = LoggerFactory.getLogger(Template::class.java)
        }
    }

    enum class Label {
        TITLE,
        CONTENT,
        BUTTON,
        LINK,
    }
}
