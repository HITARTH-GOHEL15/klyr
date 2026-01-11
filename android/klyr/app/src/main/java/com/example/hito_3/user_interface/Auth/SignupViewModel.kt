package com.example.hito_3.user_interface.Auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hito_3.data.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogUpViewModel(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow<LogUpState>(LogUpState.Nothing)
    val state = _state.asStateFlow()

    fun logUp(email: String, name: String, password: String) {
        _state.value = LogUpState.Loading

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let { user ->
                        // Update Firebase Auth profile
                        user.updateProfile(
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        )?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                // Create Firestore profile
                                viewModelScope.launch {
                                    val result = firestoreRepository.createInitialProfile(
                                        uid = user.uid,
                                        fullName = name,
                                        email = email
                                    )

                                    _state.value = if (result.isSuccess) {
                                        LogUpState.Success
                                    } else {
                                        LogUpState.Error
                                    }
                                }
                            } else {
                                _state.value = LogUpState.Error
                            }
                        }
                        return@addOnCompleteListener
                    }
                    _state.value = LogUpState.Error
                } else {
                    _state.value = LogUpState.Error
                }
            }
    }
}

sealed class LogUpState {
    object Nothing : LogUpState()
    object Loading : LogUpState()
    object Success : LogUpState()
    object Error : LogUpState()
}