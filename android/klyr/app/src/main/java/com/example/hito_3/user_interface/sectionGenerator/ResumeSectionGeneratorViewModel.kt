package com.example.hito_3.user_interface.sectionGenerator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hito_3.data.KlyrRepository
import com.example.hito_3.data.SkillGapModel.SkillGapModel
import com.example.hito_3.data.resumeSectionGenerator.ResumeSectionGeneratorModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResumeSectionGeneratorViewModel(
    private val repository: KlyrRepository
) : ViewModel()  {

    private val _state = MutableStateFlow<ResumeSectionGeneratorModel?>(null)

    val state: StateFlow<ResumeSectionGeneratorModel?> = _state

    private val _loading = MutableStateFlow(false)

    val loading: StateFlow<Boolean> = _loading

    fun ResumeSectionGenerator(text1: String, text2: String, text3: String, text4: List<String>) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _state.value = repository.resumeSectionGeneroter(text1,text2,text3,text4)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = ResumeSectionGeneratorModel(
                    analysisMethod = "",
                    generatedSection = ""
                )
            } finally {
                _loading.value = false
            }
        }
    }
}