package com.pagrey.trashstopper.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagrey.trashstopper.data.NumberEntity
import com.pagrey.trashstopper.data.NumberRepository
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import com.pagrey.trashstopper.screening.RiskEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: NumberRepository) : ViewModel() {
    private val _result = MutableStateFlow<SearchResult>(SearchResult.Idle)
    val result: StateFlow<SearchResult> = _result.asStateFlow()

    fun search(input: String) {
        val normalized = PhoneNumberNormalizer.normalize(input)
        if (normalized.isBlank()) return
        _result.value = SearchResult.Loading
        viewModelScope.launch {
            val number = repository.find(normalized)
            _result.value = SearchResult.Found(
                number = number,
                risk = RiskEngine.evaluate(number)
            )
        }
    }

    sealed interface SearchResult {
        data object Idle : SearchResult
        data object Loading : SearchResult
        data class Found(val number: NumberEntity?, val risk: RiskEngine.Result) : SearchResult
    }
}
