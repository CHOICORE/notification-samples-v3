package me.choicore.samples.notification

data class Recipient(
    val userId: Long? = null,
    val fullName: String,
    val emailAddress: String?,
    val phoneNumber: String?,
) {
    constructor(
        fullName: String,
        emailAddress: String?,
        phoneNumber: String?,
    ) : this(null, fullName, emailAddress, phoneNumber)
}
