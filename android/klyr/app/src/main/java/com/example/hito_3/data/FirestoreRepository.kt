package com.example.hito_3.data

import com.example.hito_3.data.UserProfileModel.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Save user profile to Firestore
    suspend fun saveUserProfile(userProfile: UserProfile): Result<Unit> {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")

            firestore.collection("users")
                .document(userId)
                .set(userProfile.copy(uid = userId, updatedAt = System.currentTimeMillis()))
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get user profile from Firestore
    suspend fun getUserProfile(): Result<UserProfile> {
        return try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")

            val document = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            val profile = document.toObject(UserProfile::class.java)
                ?: throw Exception("Profile not found")

            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Create initial profile after signup
    suspend fun createInitialProfile(uid: String, fullName: String, email: String): Result<Unit> {
        return try {
            val userProfile = UserProfile(
                uid = uid,
                fullName = fullName,
                email = email
            )

            firestore.collection("users")
                .document(uid)
                .set(userProfile)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}