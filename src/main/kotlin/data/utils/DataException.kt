package org.example.data.utils

open class DataException(message: String, cause: Exception? = null) : Exception(message, cause)

class NotFound(message: String) : DataException(message)

class StorageError(message: String, cause: Exception? = null) : DataException(message, cause)

class Unknown(message: String, cause: Exception? = null) : DataException(message, cause)