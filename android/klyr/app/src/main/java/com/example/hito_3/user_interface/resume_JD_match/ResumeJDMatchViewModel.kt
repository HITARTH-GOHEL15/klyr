package com.example.hito_3.user_interface.resume_JD_match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hito_3.data.KlyrRepository
import com.example.hito_3.data.resumeMatch.Resume_JD_matchModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResumeJDMatchViewModel(
    private val repository: KlyrRepository
) : ViewModel() {
    private val _state = MutableStateFlow<Resume_JD_matchModel?>(null)

    val state: StateFlow<Resume_JD_matchModel?> = _state

    private val _loading = MutableStateFlow(false)

    val loading: StateFlow<Boolean> = _loading

    fun ResumeJDMatch(text1: String, text2: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _state.value = repository.resume_JD_match(text1, text2)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = Resume_JD_matchModel(
                    matchPercentage = 0,
                    analysisMethod = "error",
                    learningRecommendations = emptyList(),
                    matchedSkills = emptyList(),
                    missingSkills = emptyList(),
                    skillGapSummary = e.localizedMessage ?: "Unknown error"
                )
            } finally {
                _loading.value = false
            }
        }
    }
}