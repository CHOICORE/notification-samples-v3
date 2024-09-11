package me.choicore.samples.notification

data class Sender(
    val name: String,
    val emailAddress: String,
    val contactNumber: String,
) {
    companion object {
        val ADMINISTRATOR =
            Sender(
                name = "파킹허브",
                emailAddress = "info@parkinghub.co.kr",
                contactNumber = "1234567890",
            )
    }
}
