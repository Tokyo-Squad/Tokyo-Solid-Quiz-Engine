package org.example.domain.utils

open class DomainException(message: String, cause: Exception? = null) : Exception(message, cause)

class QuizCreationFailed(message: String, cause: Exception? = null) : DomainException(message, cause)
class QuizValidationFailed(message: String) : DomainException(message)
class NoQuizStartedException(message: String) : DomainException(message)
class QuizUnknownError(message: String, cause: Exception? = null) : DomainException(message, cause)
class QuizNotFound(message: String) : DomainException(message)
class QuizStorageFailed(message: String, cause: Exception? = null) : DomainException(message, cause)
