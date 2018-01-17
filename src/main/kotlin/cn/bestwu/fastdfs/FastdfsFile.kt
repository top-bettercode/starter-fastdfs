package cn.bestwu.fastdfs

import org.csource.common.NameValuePair
import org.springframework.util.StreamUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

/**
 * @author Peter Wu
 * @since 1.0.0
 */
class FastdfsFile
(
        /**
         * 内容
         */
        var bytes: ByteArray?, metadata: Array<NameValuePair>) {

    private var metadata: MutableMap<String, String>? = null

    init {
        this.metadata = HashMap(metadata.size)
        for (metadatum in metadata) {
            this.metadata!!.put(metadatum.name, metadatum.value)
        }
    }

    /**
     * 转存到指定路径
     *
     * @param filePath 文件路径
     * @return 文件
     * @throws IOException IOException
     */
    @Throws(IOException::class)
    fun toFile(filePath: String): File {
        val file = File(filePath)
        StreamUtils.copy(bytes!!, FileOutputStream(file))
        return file
    }

    /**
     * 输出到指定流
     *
     * @param outputStream 输出流
     * @throws IOException IOException
     */
    @Throws(IOException::class)
    fun toOutputStream(outputStream: OutputStream) {
        StreamUtils.copy(bytes!!, outputStream)
    }

    override fun toString(): String {
        return "FileInfo{" +
                "metadata=" + metadata +
                '}'
    }

    fun getMetadata(): Map<String, String>? {
        return metadata
    }

    fun setMetadata(metadata: MutableMap<String, String>) {
        this.metadata = metadata
    }

}
