package com.example.hito_3.user_interface.skillGap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hito_3.data.KlyrRepository
import com.example.hito_3.data.SkillGapModel.LearningRecommendationModel
import com.example.hito_3.data.SkillGapModel.SkillGapModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SkillGapAnalyzeViewModel(
    private val repository: KlyrRepository
) : ViewModel() {
    private val _state = MutableStateFlow<SkillGapModel?>(null)

    val state: StateFlow<SkillGapModel?> = _state

    private val _loading = MutableStateFlow(false)

    val loading: StateFlow<Boolean> = _loading

    fun skillGapAnalyze(text1: List<String> , text2: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _state.value = repository.skillGapAnalyze(text1 , text2)
            } catch (e: Exception) {

                e.printStackTrace()
                _state.value = SkillGapModel(
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