package me.choicore.samples.notification.template

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PlaceholderRegistryTests {
    @Test
    fun t1() {
        val placeholderRegistry = PlaceholderRegistry()
        val target = "#{name}"
        val placeholder = Placeholder(target, "John")
        placeholderRegistry.addPlaceholder(placeholder)
        assertThat(placeholderRegistry.getPlaceholder(target)).isEqualTo(placeholder)
    }

    @Test
    fun t2() {
        val placeholderRegistry = PlaceholderRegistry()
        val target = "#{name}"
        val placeholder = Placeholder(target, "John")
        placeholderRegistry.addPlaceholder(placeholder)
        assertThat(placeholderRegistry.getPlaceholders()).contains(placeholder)
    }

    @Test
    fun t3() {
        val placeholderRegistry = PlaceholderRegistry()
        val target = "#{name}"
        val placeholder = Placeholder(target, "John")
        placeholderRegistry.addPlaceholders(placeholder, placeholder)
        assertThat(placeholderRegistry.getPlaceholders()).hasSize(1)
    }

    @Test
    fun t4() {
        val placeholderRegistry = PlaceholderRegistry()
        assertThatThrownBy {
            placeholderRegistry.addPlaceholders()
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
