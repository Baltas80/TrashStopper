package com.pagrey.trashstopper.ui.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagrey.trashstopper.data.CallEventEntity
import com.pagrey.trashstopper.data.CallEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: CallEventRepository) : ViewModel() {
    private val _events = MutableStateFlow<List<CallEventEntity>>(emptyList())
    val events: StateFlow<List<CallEventEntity>> = _events.asStateFlow()

    fun load(limit: Int = 50) {
        viewModelScope.launch { _events.value = repository.recent(limit) }
    }
}
