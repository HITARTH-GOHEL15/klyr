package com.example.hito_3.user_interface.bullet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hito_3.data.BulletRewriter.ResumeBulletRewriterModel
import com.example.hito_3.data.KlyrRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResumeBulletRewriterViewModel(
    private val repository: KlyrRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ResumeBulletRewriterModel?>(null)

    val state: StateFlow<ResumeBulletRewriterModel?> = _state

    private val _loading = MutableStateFlow(false)

    val loading: StateFlow<Boolean> = _loading

    fun resumeBulletRewriter(text1: String, text2: String, text3: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _state.value = repository.BulletRewriter(text1, text2, text3)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = ResumeBulletRewriterModel(
                    analysisMethod = "",
                    whyThisIsBetter = "",
                    rewrittenBullet = ""
                )
            } finally {
                _loading.value = false
            }
        }
    }
}