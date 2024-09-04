package me.choicore.samples.notification.template

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PlaceholderRegistryTests {
    @Test
    @DisplayName("PlaceholderRegistry 에 Placeholder 를 추가하면 해당 Placeholder 를 조회할 수 있다.")
    fun t1() {
        val sut = PlaceholderRegistry()
        // given
        val target = "#{name}"
        val placeholder = Placeholder(target, "John")

        // when
        sut.addPlaceholder(placeholder)

        // then
        assertThat(sut.getPlaceholder(target)).isEqualTo(placeholder)
    }

    @Test
    @DisplayName("PlaceholderRegistry 에 Placeholder 를 추가하고 존재 여부를 확인할 수 있다.")
    fun t2() {
        // sut
        val placeholderRegistry = PlaceholderRegistry()

        // given
        val target = "#{name}"
        val placeholder = Placeholder(target, "John")

        // when
        placeholderRegistry.addPlaceholder(placeholder)

        // then
        assertThat(placeholderRegistry.contains(target)).isTrue
        assertThat(placeholderRegistry.contains(placeholder)).isTrue
    }

    @Test
    @DisplayName("PlaceholderRegistry 에 같은 Placeholder 를 추가하면 중복되지 않고 마지막 추가된 Placeholder 로 대체된다.")
    fun t3() {
        // sut
        val placeholderRegistry = PlaceholderRegistry()

        // given
        val target = "#{name}"
        val placeholder = Placeholder(target, "John")

        // when
        placeholderRegistry.addPlaceholders(placeholder, placeholder)

        // then
        assertThat(placeholderRegistry.getPlaceholders()).hasSize(1)
    }

    @Test
    @DisplayName("PlaceholderRegistry 에 Placeholder 를 추가할 때, Placeholders 가 비어있으면 IllegalArgumentException 예외 발생")
    fun t4() {
        // sut
        val placeholderRegistry = PlaceholderRegistry()

        // when & then
        assertThatThrownBy {
            placeholderRegistry.addPlaceholders()
        }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
