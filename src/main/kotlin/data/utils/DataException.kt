package org.example.data.utils

import org.example.domain.utils.DomainException

open class DataException(message: String, cause: Exception? = null) : Exception(message, cause) {

    class NotFound(message: String) : DataException(message)

    class ValidationFailed(message: String) : DomainException(message)
}