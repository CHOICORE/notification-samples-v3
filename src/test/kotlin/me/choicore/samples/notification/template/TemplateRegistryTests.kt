package me.choicore.samples.notification.template

import me.choicore.samples.notification.template
    .TemplateType.CONTENT
import me.choicore.samples.notification.template
    .TemplateType.TITLE_AND_CONTENT
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.DisplayName
import java.util.UUID
import kotlin.test.Test

class TemplateRegistryTests {
    private val templateRegistry: TemplateRegistry = TemplateRegistry(TestTemplateRepository())

    @Test
    @DisplayName("템플릿 유형이 SUBJECT_AND_CONTENT 일 때 제목과 내용이 모두 주어지면 정상적으로 생성된다.")
    fun t1() {
        val definition =
            TemplateDefinition(
                key = UUID.randomUUID().toString(),
                type = TITLE_AND_CONTENT,
                name = "welcome-email-template",
                description = "Welcome template",
                title = Message.Static(key = "", label = Message.Label.TITLE, value = "#{subject}"),
                content =
                    Message.Template(
                        key = "",
                        label = Message.Label.CONTENT,
                        value = "#{content}",
                        placeholderStartsWith = "#{",
                        placeholderEndsWith = "}",
                    ),
            )

        assertThatNoException()
            .isThrownBy {
                templateRegistry.register(definition)
            }
    }

    @Test
    @DisplayName("템플릿 유형이 CONTENT 일 때 제목이 비어있어도 정상적으로 생성된다.")
    fun t2() {
        val definition =
            TemplateDefinition(
                key = UUID.randomUUID().toString(),
                type = CONTENT,
                name = "welcome-push-template",
                description = "Welcome template",
                title = null,
                content =
                    Message.Template(
                        key = "",
                        label = Message.Label.CONTENT,
                        value = "#{content}",
                        placeholderStartsWith = "#{",
                        placeholderEndsWith = "}",
                    ),
            )

        assertThatNoException()
            .isThrownBy {
                templateRegistry.register(definition)
            }
    }
}
