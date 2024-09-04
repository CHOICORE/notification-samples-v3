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

class TemplateDefinitionTests {
    @Test
    @DisplayName("템플릿 유형이 SUBJECT_AND_CONTENT 일 때 제목이 비어있으면 IllegalArgumentException 발생")
    fun t1() {
        assertThatThrownBy {
            TemplateDefinition(
                key = UUID.randomUUID().toString(),
                type = TITLE_AND_CONTENT,
                name = "welcome-template",
                description = "Welcome template",
                title = Message.Static(key = "key", label = Message.Label.CONTENT, value = ""),
                content = Message.Template(key = "key", label = Message.Label.CONTENT, value = "#{content}"),
                options = listOf(Message.Static(key = "key", label = Message.Label.CONTENT, value = "알아보기")),
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("템플릿 유형이 SUBJECT_AND_CONTENT 일 때 제목이 null이면 IllegalArgumentException 발생")
    fun t2() {
        assertThatThrownBy {
            TemplateDefinition(
                key = UUID.randomUUID().toString(),
                type = TITLE_AND_CONTENT,
                name = "welcome-template",
                description = "Welcome template",
                title = null,
                content = Message.Template(key = "key", label = Message.Label.CONTENT, value = "#{content}"),
                options = emptyList(),
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("템플릿 유형이 SUBJECT_AND_CONTENT 일 때 제목과 내용이 모두 주어지면 정상적으로 생성된다.")
    fun t3() {
        assertThatNoException().isThrownBy {
            TemplateDefinition(
                key = UUID.randomUUID().toString(),
                type = TITLE_AND_CONTENT,
                name = "welcome-template",
                description = "Welcome template",
                title = Message.Static(key = "key", label = Message.Label.CONTENT, value = "#{subject}"),
                content = Message.Template(key = "key", label = Message.Label.CONTENT, value = "#{content}"),
                options = emptyList(),
            )
        }
    }

    @Test
    @DisplayName("템플릿 유형 CONTENT 일 때 제목이 비어있어도 정상적으로 생성된다.")
    fun t4() {
        assertThatNoException()
            .isThrownBy {
                TemplateDefinition(
                    key = UUID.randomUUID().toString(),
                    type = CONTENT,
                    name = "welcome-template",
                    description = "Welcome template",
                    title = Message.Static(key = "key", label = Message.Label.CONTENT, value = ""),
                    content = Message.Template(key = "key", label = Message.Label.CONTENT, value = "#{content}"),
                    options = emptyList(),
                )
            }
    }

    @Test
    @DisplayName("템플릿 유형 CONTENT일 때 제목이 null이어도 정상적으로 생성된다.")
    fun t5() {
        assertThatNoException()
            .isThrownBy {
                TemplateDefinition(
                    key = UUID.randomUUID().toString(),
                    type = CONTENT,
                    name = "welcome-template",
                    description = "Welcome template",
                    title = null,
                    content = Message.Template(key = "key", label = Message.Label.CONTENT, value = "#{content}"),
                    options = emptyList(),
                )
            }
    }
}
