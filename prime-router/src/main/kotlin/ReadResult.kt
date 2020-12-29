package gov.cdc.prime.router

/**
 * Used by converter/formatters to return results
 */
data class ReadResult(
    /**
     * The report generated by the read may be empty because of read validation errors
     */
    val report: Report?,
    /**
     * The list of errors that caused a item to not be reported
     */
    val errors: List<ResultDetail> = emptyList(),
    /**
     * An list of warnings that could be a data error
     */
    val warnings: List<ResultDetail> = emptyList(),
) {

    fun errorsToString(): String {
        return errors.joinToString("\n") { "Error in $it" }
    }

    fun warningsToString(): String {
        return warnings.joinToString("\n") { "Warning in $it" }
    }
}