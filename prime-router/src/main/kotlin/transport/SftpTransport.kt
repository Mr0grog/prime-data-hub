package gov.cdc.prime.router.transport

import gov.cdc.prime.router.SFTPTransportType
import gov.cdc.prime.router.Secrets
import gov.cdc.prime.router.TransportType
import gov.cdc.prime.router.azure.DatabaseAccess
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import net.schmizz.sshj.xfer.InMemorySourceFile
import java.io.InputStream

class SftpTransport : ITransport {

    override fun send(
        orgName: String,
        transportType: TransportType,
        header: DatabaseAccess.Header,
        contents: ByteArray
    ): Boolean {

        val sftpTransportType = transportType as SFTPTransportType

        val user = Secrets[orgName, "user"]
        val pass = Secrets[orgName, "pass"]

        val fileDir = sftpTransportType.filePath.removeSuffix("/")

        val path = "$fileDir/${orgName.replace('.', '-')}-${header.task.reportId}.csv"
        val host: String = sftpTransportType.host
        val port: String = sftpTransportType.port

        var success: Boolean

        uploadFile(host, port, user, pass, path, contents)
        success = true

        return success
    }

    private fun uploadFile(
        host: String,
        port: String,
        user: String,
        pass: String,
        path: String,
        contents: ByteArray
    ) {
        SSHClient().use {

            it.addHostKeyVerifier(PromiscuousVerifier())
            it.connect(host, port.toInt())
            it.authPassword(user, pass)

            it.newSFTPClient().use {
                it.put(
                    object : InMemorySourceFile() {
                        override fun getName(): String { return "test.csv" }
                        override fun getLength(): Long { return contents.size.toLong() }
                        override fun getInputStream(): InputStream { return contents.inputStream() }
                    },
                    path
                )
            }
        }
    }
}