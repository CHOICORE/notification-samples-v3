package me.choicore.samples.notification

import me.choicore.samples.notification.template.Message
import org.assertj.core.api.BDDAssertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PayloadTests {
    @Test
    @DisplayName("getAll 실행 시 모든 메시지를 반환한다.")
    fun t1() {
        val key = "key"
        val title = Message.Static(key = key, label = Message.Label.TITLE, value = "titleValue")
        val content = Message.Static(key = key, label = Message.Label.CONTENT, value = "contentValue")
        val button: Message.Static = Message.Static(key = key, label = Message.Label.BUTTON, value = "버튼")
        val link: Message.Template =
            Message.Template(
                key = key,
                label = Message.Label.LINK,
                value = "https://www.parkinghub.co.kr/#{endpoints}",
            )
        val payload = Payload(title = title, content = content, options = listOf(button, link))
        val allMessages: List<Message> = payload.all

        assertThat(payload.title).isEqualTo(title)
        assertThat(payload.content).isEqualTo(content)
        assertThat(payload.options).contains(button, link)
        assertThat(payload.options).hasSize(2)
        assertThat(payload.options).contains(button, link)
        assertThat(allMessages).containsExactly(title, content, button, link)
    }

    @Test
    @DisplayName("getAll 실행 시 제목이나 옵션이 제공되지 않으면 콘텐츠 메시지만 반환해야 한다.")
    fun t2() {
        val key = "key"
        val content = Message.Static(key = key, label = Message.Label.CONTENT, value = "contentValue")
        val payload = Payload(null, content)

        val allMessages: List<Message> = payload.all

        assertThat(allMessages).containsExactly(content)
    }

    @Test
    @DisplayName("getAll 이 호출되고 옵션이 제공되지 않으면 제목과 콘텐츠 메시지를 반환해야 한다.")
    fun t3() {
        val title = Message.Static(key = "titleKey", label = Message.Label.TITLE, value = "titleValue")
        val content = Message.Static(key = "contentKey", label = Message.Label.CONTENT, value = "contentValue")
        val payload = Payload(title, content)

        val allMessages: List<Message> = payload.all
        assertThat(allMessages).hasSize(2)
        assertThat(allMessages).containsExactly(title, content)
    }
}
