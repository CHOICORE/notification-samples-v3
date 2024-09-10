package me.choicore.samples.notification

import me.choicore.samples.notification.template.Message
import me.choicore.samples.notification.template.Placeholder
import me.choicore.samples.notification.template.PlaceholderRegistry
import me.choicore.samples.notification.template.Template
import me.choicore.samples.notification.template.TemplateType
import org.assertj.core.api.Assertions
import java.util.UUID
import kotlin.test.Test

class ScenarioTests {
    @Test
    fun t1() {
        val registry: PlaceholderRegistry = getPlaceholderRegistry()
        val template: Template = getTemplate()

        Assertions
            .assertThatNoException()
            .isThrownBy {
                template.bind(registry)
            }
    }

    private fun getPlaceholderRegistry(): PlaceholderRegistry {
        val registry = PlaceholderRegistry()
        registry.addPlaceholder(Placeholder("#{name}", "회원"))
        registry.addPlaceholder(Placeholder("#{service}", "서비스"))
        return registry
    }

    private fun getTemplate(): Template =
        Template(
            id = 1,
            key = UUID.randomUUID().toString(),
            type = TemplateType.CONTENT,
            name = "welcome-template",
            description = "Welcome template",
            title =
                Message.Template(
                    key = "key",
                    label = Message.Label.TITLE,
                    value = "안녕하세요. #{name}님",
                    placeholderStartsWith = "#{",
                    placeholderEndsWith = "}",
                ),
            content =
                Message.Template(
                    key = "key",
                    label = Message.Label.CONTENT,
                    value = "저희 #{service}를 이용해주셔서 감사합니다.",
                    placeholderStartsWith = "#{",
                    placeholderEndsWith = "}",
                ),
            options =
                listOf(
                    Message.Static(
                        key = "key",
                        label = Message.Label.BUTTON,
                        value = "더 알아보기",
                    ),
                ),
        )
}
