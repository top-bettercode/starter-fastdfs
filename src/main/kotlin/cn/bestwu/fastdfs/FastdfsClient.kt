package cn.bestwu.fastdfs

import org.csource.common.NameValuePair
import org.csource.fastdfs.*
import java.io.Closeable
import java.net.InetSocketAddress

/**
 * @author Peter Wu
 * @since 1.0.0
 */
class FastdfsClient(fastdfsProperties: FastdfsProperties) {

    private var trackerClient: TrackerClient? = null

    init {
        trackerClient = TrackerClient()
        ClientGlobal.setG_connect_timeout(fastdfsProperties.connectTimeout * 1000)
        ClientGlobal.setG_network_timeout(fastdfsProperties.networkTimeout * 1000)
        ClientGlobal.setG_charset(fastdfsProperties.charset)
        ClientGlobal.setG_anti_steal_token(fastdfsProperties.isAntiStealToken)
        ClientGlobal.setG_secret_key(fastdfsProperties.secretKey)
        ClientGlobal.setG_tracker_http_port(fastdfsProperties.trackerHttpPort)
        val trackerServers = fastdfsProperties.trackerServers
        val trackerServerAddresses = arrayOfNulls<InetSocketAddress>(trackerServers.size)

        for (i in trackerServers.indices) {
            val trackerServer = trackerServers[i]
            val idx = trackerServer.lastIndexOf(':')
            val ip = trackerServer.substring(0, idx)
            val port = Integer.valueOf(trackerServer.substring(idx + 1))
            trackerServerAddresses[i] = InetSocketAddress(ip, port)
        }

        val trackerGroup = TrackerGroup(trackerServerAddresses)
        ClientGlobal.setG_tracker_group(trackerGroup)
        trackerClient = TrackerClient(trackerGroup)
    }


    /**
     * @param filePath local filename to upload
     * @param fileExt file ext name, do not include dot(.), null to extract ext name from the local filename
     * @param extraInfo meta info
     * @return file id(including group name and filename) if success, <br></br>
     * return null if fail
     */
    fun upload(filePath: String, fileExt: String? = null, extraInfo: Map<String, String>? = null): String {
        return doUpload({ storageClient, metaList ->
            storageClient
                    .upload_file1(filePath, fileExt, metaList)
        }, extraInfo)
    }

    /**
     * @param fileBytes file content/buff
     * @param fileExt file ext name, do not include dot(.), null to extract ext name from the local filename
     * @param extraInfo meta info
     * @return file id(including group name and filename) if success, <br></br>
     * return null if fail
     */
    fun upload(fileBytes: ByteArray, fileExt: String? = null, extraInfo: Map<String, String>? = null): String {
        return doUpload({ storageClient, metaList ->
            storageClient
                    .upload_file1(fileBytes, fileExt, metaList)
        }, extraInfo)
    }

    /**
     * @param fileId the file id(including group name and filename)
     * @return FileInfo
     */
    fun download(fileId: String): FastdfsFile? {

        return call({ storageClient ->
            val content = storageClient.download_file1(fileId)

            if (content == null || content.isEmpty()) {
                return@call null
            }
            val metadata = storageClient.get_metadata1(fileId)
            FastdfsFile(content, metadata)
        })

    }


    /**
     * @param fileId the file id(including group name and filename)
     * @return true for success, false for fail
     */
    fun delete(fileId: String): Boolean {
        return call({ storageClient -> storageClient.delete_file1(fileId) == 0 })
    }

    private fun doUpload(uploadClosure: (StorageClient1, Array<NameValuePair>?) -> String,
                         extraInfo: Map<String, String>?): String {
        return call({ storageClient ->
            uploadClosure(storageClient, extraInfo?.map { (key, value) -> NameValuePair(key, value) }?.toTypedArray())
        })
    }


    fun <T> call(closure: (StorageClient1) -> T): T {
        try {
            AutoCloseableTrackerServer(trackerClient!!.connection).use { trackerServer ->
                val storageClient = StorageClient1(trackerServer.delegate, null)
                return closure(storageClient)
            }
        } catch (e: Exception) {
            throw FastdfsException("fastdfs error", e)
        }
    }

    private inner class AutoCloseableTrackerServer(val delegate: TrackerServer) : Closeable {

        @Throws(Exception::class)
        override fun close() {
            delegate.close()
        }
    }

}
