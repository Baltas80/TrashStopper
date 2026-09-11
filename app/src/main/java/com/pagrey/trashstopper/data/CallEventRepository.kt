package com.pagrey.trashstopper.data

class CallEventRepository(private val dao: CallEventDao) {
    suspend fun record(event: CallEventEntity) = dao.insert(event)
    suspend fun recent(limit: Int = 50): List<CallEventEntity> = dao.recent(limit)
}
