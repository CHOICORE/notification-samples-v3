package me.choicore.samples.notification.template

import me.choicore.samples.notification.template
    .TemplateType.CONTENT
import me.choicore.samples.notification.template
    .TemplateType.TITLE_AND_CONTENT
import org.assertj.core.api.Assertions.assertThatNoException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.UUID

class TemplateTests {
    @Test
    @DisplayName("템플릿 유형이 CONTENT 일 때 제목이 비어있어도 정상적으로 생성된다.")
    fun t1() {
        assertThatNoException()
            .isThrownBy {
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
                            value = "#{content}",
                            placeholderStartsWith = "#{",
                            placeholderEndsWith = "}",
                        ),
                    options = emptyList(),
                )
            }
    }

    @Test
    @DisplayName("템플릿 유형이 TITLE_AND_CONTENT 일 때 제목이 비어있으면 IllegalArgumentException 발생")
    fun t2() {
        assertThatThrownBy {
            Template(
                id = 1,
                key = UUID.randomUUID().toString(),
                type = TITLE_AND_CONTENT,
                name = "welcome-template",
                description = "Welcome template",
                title = null,
                content =
                    Message.Template(
                        key = "key",
                        label = Message.Label.CONTENT,
                        value = "#{content}",
                        placeholderStartsWith = "#{",
                        placeholderEndsWith = "}",
                    ),
                options = emptyList(),
            )
        }
    }
}
