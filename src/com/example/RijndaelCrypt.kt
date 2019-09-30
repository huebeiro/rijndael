package com.example

import com.huebeiro.cypher.Util
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class RijndaelCrypt
/**
 * Constructor
 * @password Public key
 */
(password: String) {

    init {

        try {
            //Encode digest
            val digest: MessageDigest
            digest = MessageDigest.getInstance(DIGEST)
            _password = SecretKeySpec(digest.digest(password.toByteArray()), ALGORITHM)

            //Initialize objects
            _cipher = Cipher.getInstance(TRANSFORMATION)
            _IVParamSpec = IvParameterSpec(IV)

        } catch (e: NoSuchAlgorithmException) {
            System.err.println()
            System.err.println("No such algorithm $ALGORITHM")
        } catch (e: NoSuchPaddingException) {
            System.err.println("No such padding PKCS5")
        }

    }

    /**
     * Encryptor.
     *
     * @text String to be encrypted
     * @return Base64 encrypted text
     */
    fun encrypt(text: ByteArray): String? {

        val encryptedData: ByteArray

        try {

            _cipher!!.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec)
            encryptedData = _cipher!!.doFinal(text)

        } catch (e: InvalidKeyException) {
            System.err.println("Invalid key  (invalid encoding, wrong length, uninitialized, etc).")
            return null
        } catch (e: InvalidAlgorithmParameterException) {
            System.err.println("Invalid or inappropriate algorithm parameters for " + ALGORITHM)
            return null
        } catch (e: IllegalBlockSizeException) {
            System.err.println("The length of data provided to a block cipher is incorrect")
            return null
        } catch (e: BadPaddingException) {
            System.err.println("The input data but the data is not padded properly.")
            return null
        }

        return Base64.encodeToString(encryptedData, Base64.DEFAULT)

    }

    /**
     * Decryptor.
     *
     * @text Base64 string to be decrypted
     * @return decrypted text
     */
    fun decrypt(text: String): String? {

        try {
            _cipher!!.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec)

            val decodedValue = Base64.decode(text.toByteArray(), Base64.DEFAULT)
            val decryptedVal = _cipher!!.doFinal(decodedValue)
            return String(decryptedVal)


        } catch (e: InvalidKeyException) {
            System.err.println("Invalid key  (invalid encoding, wrong length, uninitialized, etc).")
            return null
        } catch (e: InvalidAlgorithmParameterException) {
            System.err.println("Invalid or inappropriate algorithm parameters for " + ALGORITHM)
            return null
        } catch (e: IllegalBlockSizeException) {
            System.err.println("The length of data provided to a block cipher is incorrect")
            return null
        } catch (e: BadPaddingException) {
            System.err.println("The input data but the data is not padded properly.")
            return null
        }

    }

    companion object {

        val TAG = "RijndaelCipher"

        private val TRANSFORMATION = "AES/CBC/PKCS5Padding"
        private val ALGORITHM = "AES"
        private val DIGEST = "MD5"

        private var _cipher: Cipher? = null
        private var _password: SecretKey? = null
        private var _IVParamSpec: IvParameterSpec? = null

        //16-byte private key
        //private val IV = "ThisIsUrPassword".toByteArray()
        private val IV = Util.getDefaultKey(32)
    }

}