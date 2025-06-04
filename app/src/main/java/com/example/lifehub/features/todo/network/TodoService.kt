package com.example.lifehub.features.todo.network

import com.example.core.data.ViewState
import com.example.lifehub.features.todo.data.TodoData
import com.example.lifehub.features.todo.data.TodoItem
import com.example.lifehub.network.Service
import com.example.lifehub.network.auth.FirebaseAuthService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TodoService @Inject constructor(
    private val service: FirebaseAuthService,
    firebaseFirestore: FirebaseFirestore
) : Service {
    private val todoCollection = firebaseFirestore.collection("todos")

    suspend fun saveTodos(
        data: TodoData
    ): Unit? {
        val userId = service.currentUserId() ?: return null

        val todoData = hashMapOf(
            "items" to data.items.map { item ->
                hashMapOf(
                    "id" to item.id,
                    "text" to item.text,
                    "isComplete" to item.isComplete
                )
            },
            "date" to data.date
        )

        return try {
            todoCollection
                .document(userId)
                .collection("entries")
                .document(data.id)
                .set(todoData)
                .await()
            Unit
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getTodos(): ViewState<List<TodoData>> {
        val userId = service.currentUserId() ?: return ViewState.Error()

        return try {
            val snapshot = todoCollection
                .document(userId)
                .collection("entries")
                .get()
                .await()

            val todos = snapshot.documents.mapNotNull { doc ->
                val items = (doc["items"] as? List<Map<String, Any?>>)?.map { map ->
                    TodoItem(
                        id = map["id"] as? String ?: "",
                        text = map["text"] as? String ?: "",
                        isComplete = map["isComplete"] as? Boolean ?: false
                    )
                } ?: return@mapNotNull null

                val date = doc["date"] as? Long ?: return@mapNotNull null

                TodoData(id = doc.id, items = items, date = date)
            }

            ViewState.Content(todos)
        } catch (e: Exception) {
            ViewState.Error()
        }
    }

    suspend fun getTodoById(todoId: String): ViewState<TodoData> {
        val userId = service.currentUserId() ?: return ViewState.Error()

        return try {
            val doc = todoCollection
                .document(userId)
                .collection("entries")
                .document(todoId)
                .get()
                .await()

            if (!doc.exists()) return ViewState.Error()

            val items = (doc["items"] as? List<Map<String, Any?>>)?.map { map ->
                TodoItem(
                    id = map["id"] as? String ?: "",
                    text = map["text"] as? String ?: "",
                    isComplete = map["isComplete"] as? Boolean ?: false
                )
            } ?: emptyList()

            val date = doc["date"] as? Long ?: return ViewState.Error()

            ViewState.Content(TodoData(id = todoId, items = items, date = date))
        } catch (e: Exception) {
            ViewState.Error()
        }
    }
}