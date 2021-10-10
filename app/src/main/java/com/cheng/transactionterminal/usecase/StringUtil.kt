package com.cheng.transactionterminal.usecase

import android.util.Base64
import java.lang.StringBuilder
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object StringUtil {

    fun matchesRegex(input: String, regString: String): Boolean {
        val regex = Regex(regString)
        return regex.matches(input)
    }

    // Could be any value or generated using a random number generator
    var iv = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    var ivspec: IvParameterSpec = IvParameterSpec(iv)
    // TODO: hardcoded password, should use some UI to let user input a password to encrypt,
    //  so that it is still possible to decrypt when switching to another storage
    const val ENCRYPT_PASSWORD = "P@ssw0rd!!!"

    fun encrypt(input: String, password: String): String {
        val adjustedPassword = adjustLength(password, 16)
        val secretKey: Key = SecretKeySpec(adjustedPassword.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec)

        val bytes = cipher.doFinal(input.toByteArray())
        val afterBase64 = Base64.encode(bytes, Base64.NO_WRAP)
        return String(afterBase64)
    }

    fun decrypt(input: String, password: String): String {
        val adjustedPassword = adjustLength(password, 16)
        val secretKey: Key = SecretKeySpec(adjustedPassword.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec)

        val decode: ByteArray = Base64.decode(input, Base64.NO_WRAP)
        val bytes = cipher.doFinal(decode)
        return String(bytes)
    }

    internal fun adjustLength(password: String, length: Int): String {
        if (length < 0) return ""

        val passwordLen = password.length
        return when {
            passwordLen < length -> {
                val builder = StringBuilder()
                builder.append(password)
                for (i in 0 until length-passwordLen) {
                    builder.append('0')
                }
                builder.toString()
            }
            passwordLen > length -> {
                password.substring(0, length)
            }
            else -> password
        }
    }

}
