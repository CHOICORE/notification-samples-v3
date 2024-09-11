package me.choicore.samples.notification

import me.choicore.samples.notification.template.Message
import me.choicore.samples.notification.template.PlaceholderRegistry
import me.choicore.samples.notification.template.TemplateDefinition
import me.choicore.samples.notification.template.TemplateRegistry
import me.choicore.samples.notification.template.TemplateType.TITLE_AND_CONTENT
import me.choicore.samples.notification.template.TestTemplateRepository
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class OutboxRegistrarTests {
    private val templateRegistry: TemplateRegistry = TemplateRegistry(TestTemplateRepository())
    private val outboxRegistrar: OutboxRegistrar = OutboxRegistrar(TestOutboxRepository(), templateRegistry)

    @Test
    @DisplayName("보낼 메시지를 등록한다.")
    fun t1() {
        val definition =
            TemplateDefinition(
                key = UUID.randomUUID().toString(),
                type = TITLE_AND_CONTENT,
                name = "welcome-kakaotalk-template",
                description = "Welcome template",
                title = Message.Static(key = "key", label = Message.Label.CONTENT, value = "안녕하세요. 회원님"),
                content = Message.Template(key = "key", label = Message.Label.CONTENT, value = "#{content}"),
                options = emptyList(),
            )

        templateRegistry.register(definition)

        val strategy =
            DispatchStrategy.Scheduled(
                channel = DispatchChannel.KAKAO_TALK,
                scheduledAt = LocalDateTime.now().plusDays(1),
                retryable = Retryable.DEFAULT,
            )

        assertThatNoException().isThrownBy {
            outboxRegistrar.register(
                dispatchStrategy = strategy,
                templateName = "welcome-kakaotalk-template",
                placeholderRegistry =
                    PlaceholderRegistry().apply {
                        addPlaceholder("#{name}", "회원")
                        addPlaceholder("#{service}", "서비스")
                        addPlaceholder("#{content}", "내용")
                    },
                recipient = Recipient(fullName = "John Doe", emailAddress = "core@abc.com", phoneNumber = "1234567890"),
            )
        }
    }
}
