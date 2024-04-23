package com.example.monstermath.Utils

import java.security.MessageDigest
/// https://gist.github.com/lovubuntu/164b6b9021f5ba54cefc67f60f7a1a25

object PasswordHashing {
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}