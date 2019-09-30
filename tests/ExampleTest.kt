import com.example.RijndaelCrypt
import org.junit.Test
import org.junit.Assert.*

class ExampleTest {

    @Test
    fun simpleCipher() {
        val text = "Sample text"
        val key = "password"


        val rijndael = RijndaelCrypt(password = key)


        val encrypted = rijndael.encrypt(text.toByteArray())
        println(encrypted)

        //KF9kdgCRTHiemy9F1uyP1Q==000011
        assertEquals(rijndael.decrypt(encrypted!!), text)
    }

}