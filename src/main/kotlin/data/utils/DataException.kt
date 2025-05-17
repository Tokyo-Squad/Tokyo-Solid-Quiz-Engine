package org.example.data.utils

open class DataException(message: String) : Exception(message) {

    class NotFound(message: String = "no quiz found") : DataException(message)

    class ValidationError(message: String = "quiz have a validate problem in creation") : DataException(message)
}