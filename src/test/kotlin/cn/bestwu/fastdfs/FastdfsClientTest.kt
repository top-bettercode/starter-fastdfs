package cn.bestwu.fastdfs

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.util.*

/**
 * @author Peter Wu
 * @since 1.0.0
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [FastdfsAutoConfiguration::class], properties = ["fastdfs.tracker-servers=10.13.8.193:22122"])
class FastdfsClientTest {

    @Autowired
    lateinit var client: FastdfsClient

    @Test
    @Throws(IOException::class)
    fun upload() {
        var fileId = client.upload(ClassPathResource("test.png").file.absolutePath)
        Assert.assertNotNull(fileId)
        System.err.println(fileId)
        fileId = client.upload(ClassPathResource("test.png").file.absolutePath, extraInfo = Collections.singletonMap("auther", "peter"))
        Assert.assertNotNull(fileId)
        System.err.println(fileId)
    }

    @Test
    @Throws(IOException::class)
    fun download() {
        val fileInfo = client.download(client.upload(ClassPathResource("test.png").file.absolutePath, extraInfo =
        Collections.singletonMap("auther", "peter")))
        Assert.assertNotNull(fileInfo)
        System.err.println(fileInfo)
        System.err.println(fileInfo!!.bytes)
        val toFile = fileInfo.toFile("build/test.png")
        Assert.assertTrue(toFile.exists())
        System.err.println(toFile)
    }

    @Test
    @Throws(IOException::class)
    fun delete() {
        val fileId = client.upload(ClassPathResource("test.png").file.absolutePath,
                extraInfo = Collections.singletonMap("auther", "peter"))
        var fileInfo = client.download(fileId)
        Assert.assertNotNull(fileInfo)
        client.delete(fileId)
        fileInfo = client.download(fileId)
        Assert.assertNull(fileInfo)
    }
}