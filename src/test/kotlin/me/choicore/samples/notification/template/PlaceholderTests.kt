package me.choicore.samples.notification.template

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PlaceholderTests {
    @ParameterizedTest
    @DisplayName("지원하는 enclose 포맷인 경우 Placeholder 를 생성한다.")
    @ValueSource(strings = ["#{key}", "\${key}", "{key}"])
    fun t1(key: String) {
        // given
        val value = "value"

        // when
        val (target: String, replacement: String) = Placeholder(key, value)

        // then
        assertThat(target).isEqualTo(target)
        assertThat(replacement).isEqualTo(value)
    }

    @ParameterizedTest
    @DisplayName("Placeholder 의 target 값이 지원하지 않는 enclose 포맷인 경우 IllegalArgumentException 예외 발생")
    @ValueSource(strings = ["key", "<key>", "[key]", "@key@", "%key%", "{{key}}", "{key", "key}", "{{key}", "{key}}"])
    fun t2(key: String) {
        assertThatThrownBy {
            Placeholder(key, "서비스")
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("Placeholder 의 target 이 공백이면 IllegalArgumentException 예외 발생")
    fun t3() {
        assertThatThrownBy {
            Placeholder("", "value")
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @ParameterizedTest
    @ValueSource(strings = ["#{key}", "\${key}", "{key}"])
    @DisplayName("Placeholder 의 replacement 이 공백이면 IllegalArgumentException 예외 발생")
    fun t4(key: String) {
        assertThatThrownBy {
            Placeholder(key, "")
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
