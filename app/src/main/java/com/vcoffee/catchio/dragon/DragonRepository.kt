package com.vcoffee.catchio.dragon

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DragonRepository(db: FirebaseFirestore) {

    private val dragonCollection = db.collection("dragons")
    private val stableCollection = db.collection("dragonStable")
    private val userCollection = db.collection("users")

    suspend fun addDragon(dragon: FireStoreDragon): DocumentReference? {
        return try {
            dragonCollection.add(dragon).await()
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd dodawania smoka: ${e.message}")
            null
        }
    }

    suspend fun getDragonStable(userId: String): DragonStable? {
        return try {
            val querySnapshot = stableCollection
                .whereEqualTo("userRef", userCollection.document(userId))
                .get()
                .await()
            if (querySnapshot.documents.isNotEmpty()) {
                val document = querySnapshot.documents.first()
                val stable = document.toObject(DragonStable::class.java)
                stable?.let {
                    DragonStable(it.userRef, it.dragons)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd pobierania stajni: ${e.message}")
            null
        }
    }

    suspend fun createDragonStable(userId: String): String? {
        return try {
            val userRef = userCollection.document(userId)
            val newStable = DragonStable(userRef, emptyList())
            val documentReference = stableCollection.add(newStable).await()
            documentReference.id
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd tworzenia stajni: ${e.message}")
            null
        }
    }

    suspend fun updateDragonStable(stableId: String, dragonRef: DocumentReference) {
        try {
            val stableRef = stableCollection.document(stableId)
            stableRef.update("dragons", FieldValue.arrayUnion(dragonRef)).await()
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd aktualizacji stajni: ${e.message}")
        }
    }

    suspend fun getDragonsFromStable(dragonRefs: List<DocumentReference>): List<FireStoreDragon> {
        return try {
            dragonRefs.mapNotNull { dragonRef ->
                dragonRef.get().await().toObject(FireStoreDragon::class.java)
            }
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd pobierania smoków ze stajni: ${e.message}")
            emptyList()
        }
    }
}