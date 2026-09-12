package com.pagrey.trashstopper.reputation

/** Metadata for an immutable reputation snapshot before activation. */
data class ReputationSnapshotMetadata(
    val version: String,
    val generatedAtEpochMs: Long,
    val contentSha256: String,
    val recordCount: Long,
    val schemaVersion: Int
)
