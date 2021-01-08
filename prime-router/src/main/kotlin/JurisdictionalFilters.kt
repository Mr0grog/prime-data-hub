package gov.cdc.prime.router

import tech.tablesaw.api.Table
import tech.tablesaw.selection.Selection

/**
 * A *JurisdictionalFilter* can be used in the jurisdictionalFilter property in an OrganizationService.
 * It allowed you to create arbitrarily complex filters on data.
 * Each filter in the list does a "and" boolean operation with the other filters in the list.
 * Here is an example use:
 *  `jurisdictionalFilter: { FilterByPatientOrFacilityLoc(AZ, Pima) }`
 *
 * The name `filterByPatientOrFacility` then maps via pseudo-reflection to an implementation of JurisdictionalFilter
 * here.
 *
 * If you add a implementation here, you have to add it to the list of jurisdictionalFilters in Metadata.kt.
 *
 * A JurisdictionFilter is stateless.   It has a name property, which should be used in the filter definition -
 * basically a simple way of implementing Reflection.
 *
 * Currently JurisdictionFilter implements its filtering by just re-using the very rich `Selection` functionality already in tablesaw.
 *
 * Hoping we implement some geospatial searches someday.
 *
 */
interface JurisdictionalFilter {
    /**
     * Name of the filter function
     */
    val name: String

    /**
     * @args values passed to the filter
     *
     * @return the Selection object to be used to filter the
     * tablesaw table.
     */
    fun getSelection(args: List<String>, table: Table): Selection
}

/**
 * Implements the most basic filter.
 * matches(columnName, regex)
 */
class Matches : JurisdictionalFilter {
    override val name = "matches"

    override fun getSelection(args: List<String>, table: Table): Selection {
        if (args.size != 2) error("Expecting two args to filter $name:  (columnName, regex)")
        val columnName = args[0]
        val pattern = args[1]
        val columnNames = table.columnNames()
        return if (columnNames.contains(columnName))
            table.stringColumn(columnName).matchesRegex(pattern)
        else
            Selection.withRange(0, 0)
    }
}

/**
 * Implements the opposite of the matches filter.
 * Regexes have a hard time with "not", and this just seemed clearner
 * and more obvious to the user what's going on.
 * does_not_match(columnName, val, val, ...)
 *
 * A row of data is "allowed" if it does not match any of the values.
 */
class DoesNotMatch : JurisdictionalFilter {
    override val name = "doesNotMatch"

    override fun getSelection(args: List<String>, table: Table): Selection {
        if (args.size < 2) error("Expecting two or more args to filter $name:  (columnName, value, value, ...)")
        val columnName = args[0]
        // val pattern = args[1]
        val values = args.subList(1, args.size)
        val columnNames = table.columnNames()
        return if (columnNames.contains(columnName))
            table.stringColumn(columnName).isNotIn(values)
        else
            Selection.withRange(0, table.rowCount())
    }
}

/**
 * This may or may not be a unicorn.
 */
class FilterByCounty : JurisdictionalFilter {
    override val name = "filterByCounty"

    // @todo need tons of error checking.
    override fun getSelection(args: List<String>, table: Table): Selection {
        if (args.size != 2) error("Expecting two args to filter $name:  (TwoLetterState, County)")
        // Try to be very loose on county matching.   Anything with the county name embedded is ok.
        val countyRegex = "(?i).*${args[1]}.*"

        // Dev Note: Assume that the patient_county has 1 and ordering_facility_county has 0 or 1 cardinality.
        // If this assumption is no-longer true, then other places in the code will catch this
        val columnNames = table.columnNames()
        val patientSelection = if (columnNames.contains("patient_state") && columnNames.contains("patient_county")) {
            val patientState = table.stringColumn("patient_state")
            val patientCounty = table.stringColumn("patient_county")
            patientState.isEqualTo(args[0]).and(patientCounty.matchesRegex(countyRegex))
        } else error("Schema Error: missing patient_state or patient_county columns")

        val facilitySelection = if (columnNames.contains("ordering_facility_state") && columnNames.contains("ordering_facility_county")) {
            val facilityState = table.stringColumn("ordering_facility_state")
            val facilityCounty = table.stringColumn("ordering_facility_county")
            facilityState.isEqualTo(args[0]).and(facilityCounty.matchesRegex(countyRegex))
        } else null

        // Overall, this is "true" if either the patient is in the county/state
        //   OR, if the facility is in the county/state.
        return if (facilitySelection != null)
            patientSelection.or(facilitySelection)
        else
            patientSelection
    }
}

object JurisdictionalFilters {
    /**
     * filterFunction must be of form "funName(arg1, arg2, etc)"
     */
    fun parseJurisdictionalFilter(filterFunction: String): Pair<String, List<String>> {
// REMOVE        val match = Regex("([a-zA-Z0-9]+)\\x28([a-z, \\x2E_\\x2DA-Z0-9]*)\\x29").find(filterFunction)
        // Using a permissive match in the (arg1, arg2) section, to allow most regexs to be passed as args.
        // Somehow this works with "(?i).*Pima.*", I guess because the \\x29 matches rightmost ')' char
        val match = Regex("([a-zA-Z0-9]+)\\x28(.*)\\x29").find(filterFunction)
            ?: error("JurisdictionalFilter field $filterFunction does not parse")
        return match.groupValues[1] to match.groupValues[2].split(',').map { it.trim() }
    }
}