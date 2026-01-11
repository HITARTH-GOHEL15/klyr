package com.example.hito_3.user_interface.resumeAnalyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hito_3.data.KlyrRepository
import com.example.hito_3.data.AnalyzeModel.ResumeTextAnalysisModel
import com.example.hito_3.data.AnalyzeModel.SkillsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnalyzeViewModel(
    private val repository: KlyrRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ResumeTextAnalysisModel?>(null)
    val state: StateFlow<ResumeTextAnalysisModel?> = _state

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun analyzeTextResume(text: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _state.value = repository.analyzeText(text)
            } catch (e: Exception) {
                _state.value = ResumeTextAnalysisModel(
                    atsScore = 0,
                    strengths = emptyList(),
                    weaknesses = listOf("Failed to analyze resume"),
                    skills = SkillsModel(emptyList() , emptyList()),
                    missingSections = emptyList(),
                    improvementSuggestions = listOf(e.message ?: "Unknown error"),
                    analysisMethod = "error"
                )
            }
            _loading.value = false
        }
    }

    fun analyzePdf(bytes: ByteArray) {
        viewModelScope.launch {
            _loading.value = true
            _state.value = repository.analyzePdf(bytes)
            _loading.value = false
        }
    }

}