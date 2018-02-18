package io.vinicius.androidcommon.model

data class User
(
    var isLoggedIn: Boolean = false,
    var loginType: String = "email",
    var email: String? = null
)