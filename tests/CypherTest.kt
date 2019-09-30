import com.huebeiro.cypher.Rijndael
import com.huebeiro.cypher.Util
import org.junit.Test
import org.junit.Assert.*

class CypherTest {

    @Test
    fun simpleCipher() {
        val text = Util.getDefaultText()
        val key = Util.getDefaultKey(32)

        print("Key: ")
        Util.printHexString(key)

        println("String text: $text")
        print("Text: ")
        println(Util.toHexString(text.toByteArray()))

        val encrypted = Rijndael.encrypt(text, key)

        print("Encrypted: ")
        Util.printHexString(encrypted.toByteArray())

        assertEquals(Rijndael.decrypt(encrypted, key), text)
    }
}