package com.example.nom.core.domain.models

/**
 * Item stored in the Explorer's backpack inventory.
 *
 * MVP scope: plants only (collected from scans, used to feed Spirit later).
 * Post-launch: crafted items, special foods, decorative items.
 */
data class BackpackItem(
    val id: String,
    /** The plant this item represents. Null for non-plant items (post-launch). */
    val plant: Plant? = null,
    /** Stack count. Multiple scans of the same species stack. */
    val quantity: Int = 1,
    /** Epoch millis when this item was first acquired. */
    val acquiredAt: Long
) {
    init {
        require(quantity > 0) { "Quantity must be positive, was $quantity" }
    }

    /** Display name derived from the plant, or "Unknown Item" for non-plant items. */
    val displayName: String
        get() = plant?.commonName ?: "Unknown Item"

    /** True if this item can be fed to the Spirit. */
    val isFeedable: Boolean
        get() = plant != null && quantity > 0

    /** Returns a copy with quantity decremented by 1. */
    fun consume(): BackpackItem {
        require(quantity > 0) { "Cannot consume item with zero quantity" }
        return copy(quantity = quantity - 1)
    }
}
