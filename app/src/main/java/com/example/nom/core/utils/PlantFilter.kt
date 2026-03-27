package com.example.nom.core.utils

import com.example.nom.core.domain.models.Plant
import com.example.nom.core.domain.models.PlantType
import com.example.nom.core.domain.models.Rarity

/**
 * Filter and sort criteria for the plant journal/collection screen.
 *
 * All filtering is done locally (Room queries + in-memory for complex filters).
 * This class is immutable — create new instances to update filters.
 */
data class PlantFilter(
    /** Free-text search query. Matches against common name and scientific name. */
    val searchQuery: String? = null,
    /** Filter to a specific plant type. Null = show all types. */
    val plantType: PlantType? = null,
    /** Filter to a specific rarity. Null = show all rarities. */
    val rarity: Rarity? = null,
    /** Show only plants discovered after this timestamp. Null = no lower bound. */
    val discoveredAfter: Long? = null,
    /** Show only plants discovered before this timestamp. Null = no upper bound. */
    val discoveredBefore: Long? = null,
    /** Show only toxic plants. Null = show all. True = toxic only. False = non-toxic only. */
    val isToxic: Boolean? = null,
    /** Sort order for results. */
    val sortBy: SortBy = SortBy.DISCOVERED_NEWEST,
    /** Show only new discoveries (scanned once). */
    val newDiscoveriesOnly: Boolean = false
) {
    /** True if any filter is active (not all defaults). */
    val hasActiveFilters: Boolean
        get() = searchQuery != null || plantType != null || rarity != null ||
                discoveredAfter != null || discoveredBefore != null ||
                isToxic != null || newDiscoveriesOnly

    /** Number of active filter dimensions (for "3 filters active" badge). */
    val activeFilterCount: Int
        get() = listOfNotNull(
            searchQuery,
            plantType,
            rarity,
            discoveredAfter,
            discoveredBefore,
            isToxic,
            if (newDiscoveriesOnly) true else null
        ).size

    /**
     * Applies this filter to a list of plants in memory.
     * Use this for complex filters that Room can't handle in a single query.
     *
     * @param plants the unfiltered plant list
     * @return filtered and sorted list
     */
    fun apply(plants: List<Plant>): List<Plant> {
        var result = plants.asSequence()

        // Text search (case-insensitive, matches common or scientific name)
        searchQuery?.let { query ->
            val lower = query.lowercase().trim()
            if (lower.isNotEmpty()) {
                result = result.filter {
                    it.commonName.lowercase().contains(lower) ||
                    it.scientificName.lowercase().contains(lower)
                }
            }
        }

        // Type filter
        plantType?.let { type ->
            result = result.filter { it.type == type }
        }

        // Rarity filter
        rarity?.let { r ->
            result = result.filter { it.rarity == r }
        }

        // Date range
        discoveredAfter?.let { after ->
            result = result.filter { it.firstDiscoveredAt >= after }
        }
        discoveredBefore?.let { before ->
            result = result.filter { it.firstDiscoveredAt <= before }
        }

        // Toxicity filter
        isToxic?.let { toxic ->
            result = result.filter { it.isToxic == toxic }
        }

        // New discoveries only
        if (newDiscoveriesOnly) {
            result = result.filter { it.timesScanned == 1 }
        }

        // Sort
        val sorted = when (sortBy) {
            SortBy.DISCOVERED_NEWEST -> result.sortedByDescending { it.firstDiscoveredAt }
            SortBy.DISCOVERED_OLDEST -> result.sortedBy { it.firstDiscoveredAt }
            SortBy.NAME_AZ -> result.sortedBy { it.commonName.lowercase() }
            SortBy.NAME_ZA -> result.sortedByDescending { it.commonName.lowercase() }
            SortBy.RARITY_HIGHEST -> result.sortedByDescending { it.rarity.ordinal }
            SortBy.RARITY_LOWEST -> result.sortedBy { it.rarity.ordinal }
            SortBy.MOST_SCANNED -> result.sortedByDescending { it.timesScanned }
        }

        return sorted.toList()
    }

    /** Returns a copy with all filters cleared to defaults. */
    fun clear(): PlantFilter = PlantFilter()
}

/**
 * Sort options for the plant journal.
 */
enum class SortBy {
    DISCOVERED_NEWEST,
    DISCOVERED_OLDEST,
    NAME_AZ,
    NAME_ZA,
    RARITY_HIGHEST,
    RARITY_LOWEST,
    MOST_SCANNED
}
