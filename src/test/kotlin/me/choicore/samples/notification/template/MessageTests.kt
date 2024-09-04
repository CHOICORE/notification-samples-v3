package me.choicore.samples.notification.template

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MessageTests {
    @Test
    @DisplayName("템플릿 메시지 플레이스홀더 바인딩")
    fun t1() {
        val registry: PlaceholderRegistry =
            PlaceholderRegistry().apply {
                addPlaceholder("#{name}", "회원")
                addPlaceholder("#{service}", "서비스")
                addPlaceholder("#{endpoints}", "api/v1/endpoints")
            }

        val title: Message.Template =
            Message
                .Template(
                    key = "key",
                    label = Message.Label.TITLE,
                    value = "안녕하세요. #{name}님, 저희 #{service}를 이용해 주셔서 감사합니다.",
                ).apply { setPlaceholders(registry.getPlaceholders()) }

        assertThat(title.placeholders).containsExactly(
            Placeholder("#{name}", "회원"),
            Placeholder("#{service}", "서비스"),
        )

        val content: Message.Template =
            Message
                .Template(
                    key = "key",
                    label = Message.Label.CONTENT,
                    value = "안녕하세요. #{name}님 환영합니다 #{service}를 이용해 주셔서 감사합니다.",
                ).apply { setPlaceholders(registry.getPlaceholders()) }

        assertThat(content.placeholders).containsExactly(
            Placeholder("#{name}", "회원"),
            Placeholder("#{service}", "서비스"),
        )

        val link: Message.Template =
            Message
                .Template(
                    key = "key",
                    label = Message.Label.LINK,
                    value = "https://www.parkinghub.co.kr/#{endpoints}",
                ).apply { setPlaceholders(registry.getPlaceholders()) }

        assertThat(link.placeholders).containsExactly(
            Placeholder("#{endpoints}", "api/v1/endpoints"),
        )
    }

    @Test
    @DisplayName("템플릿에 정의된 플레이스홀더 브라켓의 포맷과 다른 포맷의 플레이스홀더가 바인딩 된 경우 IllegalArgumentException 발생")
    fun t2() {
        val key = "key"

        val message: Message.Template =
            Message.Template(
                key = key,
                label = Message.Label.TITLE,
                value = "안녕하세요. #{name}님",
            )

        val name = Placeholder("\${name}", "회원")
        val service = Placeholder("\${service}", "서비스")
        val endpoints = Placeholder("#{endpoints}", "/endpoints")

        assertThatThrownBy {
            message.setPlaceholders(listOf(name, service, endpoints))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun t3() {
        val button: Message.Static =
            Message.Static(
                key = "key",
                label = Message.Label.BUTTON,
                value = "버튼",
            )

        assertThat(button.value).isEqualTo("버튼")
        assertThat(button.render()).isEqualTo("버튼")
    }
}
