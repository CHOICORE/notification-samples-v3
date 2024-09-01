package me.choicore.samples.notification

import me.choicore.samples.notification.template.Component
import me.choicore.samples.notification.template.Placeholder
import me.choicore.samples.notification.template.PlaceholderRegistry
import me.choicore.samples.notification.template.Template
import java.util.UUID
import kotlin.test.Test

class ScenarioTests {
    @Test
    fun t1() {
        val registry: PlaceholderRegistry = getPlaceholderRegistry()
        val template: Template = getTemplate()

        template.bind(registry)

        template.components.forEach {
            when (it) {
                is Component.Template -> {
                    println(it.render())
                    println(it.placeholders)
                }

                else -> {
                    println(it.render())
                }
            }
        }
    }

    private fun getPlaceholderRegistry(): PlaceholderRegistry {
        val registry = PlaceholderRegistry()
        registry.addPlaceholder(Placeholder("#{name}", "회원"))
        registry.addPlaceholder(Placeholder("#{service}", "서비스"))
        registry.addPlaceholder(Placeholder("#{website}", "choicore.me"))
        return registry
    }

    private fun getTemplate(): Template {
        val key: String = UUID.randomUUID().toString()

        val subject =
            Component.Template(
                key = key,
                type = Component.Type.TITLE,
                value = "안녕하세요. #{name}님",
            )
        val content =
            Component.Template(
                key = key,
                type = Component.Type.CONTENT,
                value = "환영합니다. 저희 #{service}를 이용해 주셔서 감사합니다.",
            )

        val link: Component.Template =
            Component.Template(
                key = key,
                type = Component.Type.CONTENT,
                value = "https://#{website}",
            )

        return Template(
            key = key,
            name = "welcome-template",
            description = "환영 메시지",
            components = listOf(subject, content, link),
        )
    }
}
