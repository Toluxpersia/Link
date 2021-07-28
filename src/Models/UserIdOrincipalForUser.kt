package com.Link.Models

import io.ktor.auth.*
import org.bson.types.ObjectId

data class UserIdPrincipalForUser(val userId: Int) : Principal