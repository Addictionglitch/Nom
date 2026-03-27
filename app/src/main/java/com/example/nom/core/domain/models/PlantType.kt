package com.example.nom.core.domain.models

/**
 * Classification of plant species. Used as keys in [Spirit.dietComposition].
 *
 * Diet composition drives visual evolution — a Spirit fed mostly mushrooms
 * looks different from one fed mostly flowers. This is the "every Spirit
 * is unique" mechanic.
 */
enum class PlantType {
    FLOWER,
    TREE,
    MUSHROOM,
    FERN,
    SUCCULENT,
    HERB,
    GRASS,
    VINE,
    AQUATIC,
    UNKNOWN;

    companion object {
        /**
         * Attempts to map a Plant.id API taxonomy string to a [PlantType].
         * Falls back to [UNKNOWN] if no match.
         *
         * @param taxonomy raw taxonomy string from Plant.id API (e.g., "Fungi", "Magnoliopsida")
         */
        @JvmStatic
        fun fromTaxonomy(taxonomy: String): PlantType {
            val lower = taxonomy.lowercase()
            return when {
                lower.contains("fung") || lower.contains("mushroom") || lower.contains("mycet") -> MUSHROOM
                lower.contains("fern") || lower.contains("polyp") || lower.contains("pterid") -> FERN
                lower.contains("succul") || lower.contains("cact") || lower.contains("crassul") -> SUCCULENT
                lower.contains("herb") || lower.contains("mint") || lower.contains("basil") || lower.contains("lamiac") -> HERB
                lower.contains("grass") || lower.contains("poac") || lower.contains("gramin") -> GRASS
                lower.contains("vine") || lower.contains("climb") || lower.contains("liana") -> VINE
                lower.contains("aquatic") || lower.contains("water") || lower.contains("nymph") -> AQUATIC
                lower.contains("tree") || lower.contains("arbor") || lower.contains("pinacea") || lower.contains("oak") || lower.contains("pine") -> TREE
                lower.contains("flower") || lower.contains("bloom") || lower.contains("rosa") || lower.contains("aster") || lower.contains("lili") -> FLOWER
                else -> UNKNOWN
            }
        }
    }
}
