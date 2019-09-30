package com.huebeiro.cypher

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

/**
 * @author Adilson Ribeiro
 * Date: 05/09/19
 */
object Rijndael {

    fun encrypt(plainText: String, key: ByteArray): String {
        try {
            val cipher = Cipher.getInstance("AES/ECB/NoPadding")
            val blockSize = cipher.blockSize
            val dataBytes = plainText.toByteArray()
            var plaintTextLength = dataBytes.size
            if (plaintTextLength % blockSize != 0) {
                plaintTextLength = plaintTextLength + (blockSize - plaintTextLength % blockSize)
            }
            val plainTextByte = ByteArray(plaintTextLength)
            System.arraycopy(dataBytes, 0, plainTextByte, 0, dataBytes.size)
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"))
            return Base64.encode(cipher.doFinal(plainTextByte)) + String.format("%06d", plainText.length)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        }

        return ""
    }

    fun decrypt(cipherText: String, key: ByteArray): String {
        var cipherText = cipherText
        val plainTextLength = Integer.parseInt(cipherText.substring(cipherText.length - 6))
        cipherText = cipherText.substring(0, cipherText.length - 6)
        try {
            val cipher = Cipher.getInstance("AES/ECB/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"))
            return String(cipher.doFinal(Base64.decode(cipherText.toByteArray()))).substring(0, plainTextLength)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        }

        return ""
    }

}