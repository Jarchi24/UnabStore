package me.jarvierlunadiaz.unabstore

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import me.jarvierlunadiaz.unabstore.Producto

class ProductoRepository {
    private val db = Firebase.firestore

    fun agregarProducto(producto: Producto, callback: (Boolean) -> Unit) {
        db.collection("productos")
            .add(producto)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun obtenerProductos(callback: (List<Producto>) -> Unit) {
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.map { doc ->
                    doc.toObject(Producto::class.java).copy(id = doc.id)
                }
                callback(productos)
            }
            .addOnFailureListener { callback(emptyList()) }
    }

    fun eliminarProducto(id: String, callback: (Boolean) -> Unit) {
        db.collection("productos").document(id)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}


