package cn.bestwu.fastdfs

/**
 * @author Peter Wu
 * @since 1.0.0
 */
class FastdfsException : RuntimeException {

    constructor() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}

    companion object {

        private val serialVersionUID = -6731101120307439764L
    }
}
