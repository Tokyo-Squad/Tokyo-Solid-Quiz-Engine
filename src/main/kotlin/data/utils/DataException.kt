package org.example.data.utils

open class DataException(message: String, cause: Exception? = null) : Exception(message, cause)

class StorageError(message: String, cause: Exception? = null) : DataException(message, cause)

class ValidationError(message: String) : DataException(message)

class Unknown(message: String, cause: Exception? = null) : DataException(message, cause)

class NotFound(message: String) : DataException(message)