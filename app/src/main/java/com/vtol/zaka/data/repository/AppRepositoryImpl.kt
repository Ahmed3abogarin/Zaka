package com.vtol.zaka.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vtol.zaka.domain.models.auth.User
import com.vtol.zaka.domain.repository.AppRepository
import com.vtol.zaka.util.Constants.USERS_COLLECTION
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AppRepository {
    override fun getUser(): Flow<User> = callbackFlow {
        val currentUid = auth.currentUser?.uid
        if (currentUid == null) {
            close(IllegalStateException("User not found"))
            return@callbackFlow
        }

        val listener = firestore.collection(USERS_COLLECTION)
            .document(currentUid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                snapshot?.toObject(User::class.java)?.let { trySend(it) }
            }

        awaitClose { listener.remove() }
    }
}