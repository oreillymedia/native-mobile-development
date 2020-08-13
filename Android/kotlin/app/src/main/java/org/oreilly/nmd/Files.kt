package org.oreilly.nmd

import java.io.*

object Files {

    @Throws(IOException::class)
    fun readStringFromFile(file: File): String {
        val stream = FileInputStream(file)
        return getStringFromStream(stream)
    }

    @Throws(IOException::class)
    fun getStringFromStream(stream: InputStream): String {
        stream.use { s ->
            val builder = StringBuilder()
            var b = s.read()
            while (b != -1) {
                builder.append(b.toChar())
                b = s.read()
            }
            return builder.toString()
        }
    }

    @Throws(IOException::class)
    fun writeToFile(file: File, data: ByteArray) {
        var outputStream: OutputStream? = null
        try {
            if (!file.exists()) {
                // we need to create all the parent directories first
                file.parentFile.mkdirs()
                // now we can create an empty file
                file.createNewFile()
            }
            // let's get a stream handle to so we can write to it
            outputStream = FileOutputStream(file)
            // and write the data
            outputStream.write(data)
        } finally {
            outputStream?.close()
        }
    }

}
