package me.choicore.samples.notification.template

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PlaceholderFormatterTests {
    @Test
    @DisplayName("Default PlaceholderFormatter should format a string")
    fun t1() {
        val formatter: PlaceholderFormatter = PlaceholderFormatter.DEFAULT
        assertThat(formatter.format("John")).isEqualTo("#{John}")
    }

    @Test
    @DisplayName("Custom PlaceholderFormatter should format a string")
    fun t2() {
        val formatter: PlaceholderFormatter = PlaceholderFormatter.ofPattern("\${", "}")
        assertThat(formatter.format("John")).isEqualTo("\${John}")
    }

    @Test
    @DisplayName("Default PlaceholderFormatter should parse a string")
    fun t3() {
        val formatter: PlaceholderFormatter = PlaceholderFormatter.DEFAULT
        assertThat(formatter.parse("#{John}")).isEqualTo("John")
    }

    @Test
    @DisplayName("Custom PlaceholderFormatter should parse a string")
    fun t4() {
        val formatter: PlaceholderFormatter = PlaceholderFormatter.ofPattern("{", "}")
        assertThat(formatter.parse("{John}")).isEqualTo("John")
    }

    @Test
    @DisplayName("supported brackets should be returned")
    fun t5() {
        Placeholder.WELL_KNOWN_PLACEHOLDER_BRACKETS.forEach { (start: String, end: String) ->
            val formatter: PlaceholderFormatter = PlaceholderFormatter.ofPattern(start, end)
            assertThat(formatter.format("John")).isEqualTo("${start}John$end")
        }
    }

    @Test
    @DisplayName("not supported brackets should throw an exception")
    fun t6() {
        assertThatThrownBy {
            PlaceholderFormatter.ofPattern("(", ")")
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("hasPlaceholders should return true if a string has placeholders")
    fun t7() {
        val formatter: PlaceholderFormatter = PlaceholderFormatter.DEFAULT
        assertThat(formatter.hasPlaceholders("Hello, #{name}")).isTrue
    }

    @Test
    @DisplayName("hasPlaceholders should return false if a string does not have placeholders")
    fun t8() {
        val formatter: PlaceholderFormatter = PlaceholderFormatter.DEFAULT
        assertThat(formatter.hasPlaceholders("Hello, John")).isFalse
    }
}
