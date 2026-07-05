package com.vtol.zaka.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.vtol.zaka.domain.models.auth.AuthState
import com.vtol.zaka.domain.models.auth.User
import com.vtol.zaka.domain.repository.AuthRepository
import com.vtol.zaka.util.Constants.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRepository {

    override fun authState(): Flow<AuthState> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener {
            val user = it.currentUser

            trySend(if (user != null) AuthState.Authenticated else AuthState.Unauthenticated)
        }
        auth.addAuthStateListener(listener)

        awaitClose { auth.removeAuthStateListener(listener) }

    }

    override suspend fun register(
        user: User,
        password: String,
    ): Result<Unit> {
        return runCatching {

            //  Create user in Firebase Auth (WAIT for result)
            val authResult = auth
                .createUserWithEmailAndPassword(user.email, password)
                .await()

            val uid = authResult.user?.uid
                ?: throw IllegalStateException("User UID is null")

            // Create user object with correct UID
            val newUser = user.copy(uid = uid)

            // Save user in Firestore
            saveUserToFirestore(newUser)
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> {
        return runCatching { auth.signInWithEmailAndPassword(email, password).await() }

    }

    override suspend fun signInWithGoogle(idToken: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(
        email: String
    ): Result<Unit> {
        return runCatching { auth.sendPasswordResetEmail(email).await() }
    }

    override fun logout() {
//        CoroutineScope(Dispatchers.IO).launch{
//            db.clearAllTables()
//        }
        auth.signOut()
    }
    private suspend fun saveUserToFirestore(user: User){
        firestore.collection(USERS_COLLECTION)
            .document(user.uid)
            .set(user, SetOptions.merge()).await()
    }

    override suspend fun validateCurrentUser(): AuthState {
        val user = auth.currentUser ?: return AuthState.Unauthenticated

        return try {
            user.reload().await()
            AuthState.Authenticated
        } catch (_: FirebaseAuthInvalidUserException) {
            auth.signOut()
            AuthState.Unauthenticated
        } catch (_: Exception) {
            auth.signOut()
            AuthState.Unauthenticated
        }
    }
}