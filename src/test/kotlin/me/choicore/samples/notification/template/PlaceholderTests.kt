package me.choicore.samples.notification.template

import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PlaceholderTests {
    @Test
    @DisplayName("Placeholder should be created")
    fun t1() {
        assertThatNoException().isThrownBy {
            Placeholder("#{name}", "John")
        }
    }
}
