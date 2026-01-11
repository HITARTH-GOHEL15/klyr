package com.example.hito_3.user_interface.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hito_3.data.FirestoreRepository
import com.example.hito_3.data.UserProfileModel.UserProfile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _loading.value = true

            val result = firestoreRepository.getUserProfile()

            if (result.isSuccess) {
                _userProfile.value = result.getOrNull()
            } else {
                // Fallback to Firebase Auth data
                val currentUser = FirebaseAuth.getInstance().currentUser
                _userProfile.value = UserProfile(
                    uid = currentUser?.uid ?: "",
                    fullName = currentUser?.displayName ?: "",
                    email = currentUser?.email ?: ""
                )
            }

            _loading.value = false
        }
    }

    fun updateProfile(
        fullName: String,
        email: String,
        phoneNumber: String,
        location: String,
        bio: String
    ) {
        viewModelScope.launch {
            _loading.value = true

            val currentProfile = _userProfile.value ?: UserProfile()
            val updatedProfile = currentProfile.copy(
                fullName = fullName,
                email = email,
                phoneNumber = phoneNumber,
                location = location,
                bio = bio
            )

            val result = firestoreRepository.saveUserProfile(updatedProfile)

            if (result.isSuccess) {
                _userProfile.value = updatedProfile
            }

            _loading.value = false
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _userProfile.value = null
    }
}