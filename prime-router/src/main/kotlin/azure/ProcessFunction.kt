package gov.cdc.prime.router.azure

import com.azure.core.http.ContentType
import com.google.common.net.MediaType
import gov.cdc.prime.router.Metadata
import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.HttpTrigger
import com.microsoft.azure.functions.annotation.StorageAccount
import gov.cdc.prime.router.CsvConverter
import gov.cdc.prime.router.Receiver
import java.io.ByteArrayInputStream
import java.net.http.HttpHeaders
import java.net.http.HttpRequest
import java.util.*
import java.util.logging.Level


/**
 * Azure Functions with HTTP Trigger. Write to blob.
 */
class ProcessFunction {
    @FunctionName("Process")
    @StorageAccount("AzureWebJobsStorage")
    fun run(
        @HttpTrigger(
            name = "request",
            methods = [HttpMethod.POST],
            authLevel = AuthorizationLevel.ANONYMOUS
        )
        request: HttpRequestMessage<Optional<String>>,
        context: ExecutionContext
    ): HttpResponseMessage {
        context.logger.info("HTTP trigger processed a ${request.httpMethod.name} request.")
        try {
            val baseDir = System.getenv("AzureWebJobsScriptRoot")
            Metadata.loadAll("$baseDir/metadata")

            val schemaName = request.queryParameters["schema"] ?: error("Expected a schema query parameter")
            val contentType = request.headers["content-type"] ?: error("Expected content type header")
            if (!contentType.equals("text/csv", ignoreCase = true)) error("Expected csv content type")
            val schema = Metadata.findSchema(schemaName) ?: error("Didn't find a matching schema for $schemaName")
            val body = request.body.orElseThrow()
            val inputTable = ByteArrayInputStream(body.toByteArray()).use {
                CsvConverter.read("processed", schema, it)
            }

            val outputTables = Receiver.filterAndMapByReceiver(inputTable, Metadata.receivers)

            val container = TableStorage.getBlobContainer("processed")
            outputTables.forEach { (table, receiver) ->
                TableStorage.uploadBlob(container, table, receiver)
            }

            return request.createResponseBuilder(HttpStatus.OK).build()
        } catch (e: Exception) {
            context.logger.log(Level.SEVERE, "top-level catch", e)
            // TODO: Need to distinguish between BAD_REQUEST_ERRORS and internal errors
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message).build()
        }
    }
}
