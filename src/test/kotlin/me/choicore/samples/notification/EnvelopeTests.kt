package me.choicore.samples.notification

import me.choicore.samples.notification.template.Message
import me.choicore.samples.notification.template.PlaceholderRegistry
import me.choicore.samples.notification.template.Template
import me.choicore.samples.notification.template.TemplateType.CONTENT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.UUID

class EnvelopeTests {
    @Test
    @DisplayName("템플릿에 정의된 플레이스홀더들을 변환해 메시지를 생성한다.")
    fun t1() {
        // given
        val template =
            Template(
                id = 1,
                key = UUID.randomUUID().toString(),
                type = CONTENT,
                name = "welcome-template",
                description = "Welcome template",
                title = null,
                content =
                    Message.Template(
                        key = "key",
                        label = Message.Label.CONTENT,
                        value = "안녕하세요. #{name}님 환영합니다. 저희 #{service}를 이용해 주셔서 감사합니다. #{reservation}에 예약이 완료되었습니다.",
                        placeholderStartsWith = "#{",
                        placeholderEndsWith = "}",
                    ),
                options = emptyList(),
            )

        val registry: PlaceholderRegistry =
            PlaceholderRegistry().apply {
                addPlaceholder("#{name}", "회원")
                addPlaceholder("#{service}", "서비스")
                addPlaceholder("#{reservation}", "2024-09-01")
            }

        // when
        val envelope =
            Envelope(
                template = template,
                placeholderRegistry = registry,
            )

        // then
        val rendered: String =
            envelope.template.content
                .render()

        assertThat(rendered).isEqualTo("안녕하세요. 회원님 환영합니다. 저희 서비스를 이용해 주셔서 감사합니다. 2024-09-01에 예약이 완료되었습니다.")
    }

    @Test
    @DisplayName("템플릿에 정의된 플레이스홀더들을 변환하지 못하면 에러 메시지를 생성한다.")
    fun t2() {
        // given
        val template =
            Template(
                id = 1,
                key = UUID.randomUUID().toString(),
                type = CONTENT,
                name = "welcome-template",
                description = "Welcome template",
                title = null,
                content =
                    Message.Template(
                        key = "key",
                        label = Message.Label.CONTENT,
                        value = "안녕하세요. #{name}님 환영합니다. 저희 #{service}를 이용해 주셔서 감사합니다. #{reservation}에 예약이 완료되었습니다.",
                        placeholderStartsWith = "#{",
                        placeholderEndsWith = "}",
                    ),
                options = emptyList(),
            )

        val registry: PlaceholderRegistry =
            PlaceholderRegistry().apply {
                addPlaceholder("#{name}", "회원")
                addPlaceholder("#{service}", "서비스")
            }

        // when
        val envelope =
            Envelope(
                template = template,
                placeholderRegistry = registry,
            )

        // then
        assertThat(envelope.errorMessage).isNotNull
        assertThat(envelope.crashed).isTrue
    }
}
