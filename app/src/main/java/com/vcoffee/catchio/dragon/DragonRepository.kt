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
            val query = stableCollection
                .whereEqualTo("userRef", userCollection.document(userId))
                .limit(1)

            val snapshot = query.get().await()
            if (snapshot.isEmpty) {
                Log.d("DragonRepo", "Brak stajni dla użytkownika $userId")
                return null
            }

            val document = snapshot.documents[0]
            DragonStable(
                userRef = document.getDocumentReference("userRef")!!,
                dragons = document.get("dragons") as? List<DocumentReference> ?: emptyList(),
                battleDragons = document.get("battleDragons") as? List<DocumentReference> ?: emptyList()
            ).also {
                Log.d("DragonRepo", "Pobrano stajnię: $it")
            }

        } catch (e: Exception) {
            Log.e("DragonRepo", "Błąd pobierania stajni: ${e.message}")
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

    suspend fun getDragonsFromStable(refs: List<DocumentReference>): List<FireStoreDragon> {
        return try {
            refs.mapNotNull { ref ->
                ref.get().await().toObject(FireStoreDragon::class.java)
                    ?.also { Log.d("DragonRepo", "Pobrano smoka: ${it.name}") }
            }
        } catch (e: Exception) {
            Log.e("DragonRepo", "Błąd pobierania smoków: ${e.message}")
            emptyList()
        }
    }

    suspend fun addDragonToBattle(stableId: String, dragonRef: DocumentReference): Boolean {
        return try {
            val stableRef = stableCollection.document(stableId)
            val stableSnapshot = stableRef.get().await()
            val currentBattleDragons = stableSnapshot["battleDragons"] as? List<DocumentReference> ?: emptyList()

            if (currentBattleDragons.size < 4) {
                stableRef.update("battleDragons", FieldValue.arrayUnion(dragonRef)).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd dodawania smoka do battleDragons: ${e.message}")
            false
        }
    }

    suspend fun getBattleDragonsSize(stableId: String): Int {
        return try {
            val stableRef = stableCollection.document(stableId)
            val stableSnapshot = stableRef.get().await()
            val battleDragons = stableSnapshot["battleDragons"] as? List<DocumentReference> ?: emptyList()
            battleDragons.size
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd pobierania rozmiaru battleDragons: ${e.message}")
            0
        }
    }

    suspend fun getDragonsSize(stableId: String): Int {
        return try {
            val stableRef = stableCollection.document(stableId)
            val stableSnapshot = stableRef.get().await()
            val dragons = stableSnapshot["dragons"] as? List<DocumentReference> ?: emptyList()
            dragons.size
        } catch (e: Exception) {
            Log.e("DragonRepository", "Błąd pobierania rozmiaru dragons: ${e.message}")
            0
        }
    }

}