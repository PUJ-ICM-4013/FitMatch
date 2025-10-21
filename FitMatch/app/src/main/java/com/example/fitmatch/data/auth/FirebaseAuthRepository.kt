package com.example.fitmatch.data.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) cont.resume(auth.currentUser)
                    else cont.resumeWithException(task.exception ?: Exception("Sign in failed"))
                }
        }

    override suspend fun register(email: String, password: String): FirebaseUser? =
        suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) cont.resume(auth.currentUser)
                    else cont.resumeWithException(task.exception ?: Exception("Register failed"))
                }
        }

    override suspend fun signInWithGoogle(idToken: String): FirebaseUser? =
        suspendCancellableCoroutine { cont ->
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) cont.resume(auth.currentUser)
                    else cont.resumeWithException(task.exception ?: Exception("Google sign-in failed"))
                }
        }

    override suspend fun signInWithFacebook(accessToken: String): FirebaseUser? =
        suspendCancellableCoroutine { cont ->
            val credential = FacebookAuthProvider.getCredential(accessToken)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) cont.resume(auth.currentUser)
                    else cont.resumeWithException(task.exception ?: Exception("Facebook sign-in failed"))
                }
        }

    override suspend fun fetchSignInMethodsForEmail(email: String): List<String> {
        val result = auth.fetchSignInMethodsForEmail(email).await()
        return result.signInMethods ?: emptyList()
    }

    override suspend fun linkPendingCredentialWithEmail(email: String, password: String, pending: AuthCredential): FirebaseUser? {
        // 1) Sign in with email+password to get an authenticated user
        auth.signInWithEmailAndPassword(email, password).await()
        // 2) Link the pending credential with the signed-in user
        val current = auth.currentUser ?: throw Exception("No authenticated user when linking credential")
        current.linkWithCredential(pending).await()
        return auth.currentUser
    }

    override fun signOut() { auth.signOut() }

    override fun currentUser(): FirebaseUser? = auth.currentUser
}
