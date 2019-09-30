package com.huebeiro.cypher

import java.nio.charset.StandardCharsets
import kotlin.experimental.and

object Util{

    fun byteArrayOfInt(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }

    fun getDefaultKey(size: Int) : ByteArray {

        val byteArray = ByteArray(size)

        for (i in 0 until size){
            byteArray[i] = i.toByte()
        }

        return byteArray
    }

    fun hexStringToByteArray(hex: String): ByteArray {
        val l = hex.length
        val data = ByteArray(l / 2)
        var i = 0
        while (i < l) {
            data[i / 2] = ((Character.digit(hex[i], 16) shl 4) + Character.digit(hex[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    fun getDefaultText() : String {
        return String(hexStringToByteArray(
                "00112233445566778899aabbccddeeff"
        ), StandardCharsets.UTF_8)
    }

    fun printHexString(array : ByteArray){ 
        for(byte in array){
            print("${if(Integer.toHexString(byte.toInt()).length == 1) "0" else ""}${Integer.toHexString(byte.toInt())} ")
        }
        println()
    }

    fun toHexString(bytes: ByteArray): String {
        val hexArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v / 16]
            hexChars[j * 2 + 1] = hexArray[v % 16]
        }
        return String(hexChars)
    }
}