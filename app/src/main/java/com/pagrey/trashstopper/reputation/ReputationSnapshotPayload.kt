package com.pagrey.trashstopper.reputation

import com.pagrey.trashstopper.data.NumberEntity

/** Validated client-side representation ready for transactional import. */
data class ReputationSnapshotPayload(
    val snapshotVersion: String,
    val numbers: List<NumberEntity>
)
