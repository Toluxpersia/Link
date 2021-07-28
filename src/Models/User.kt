package com.Link.Models

import io.ktor.auth.*
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.io.Serializable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class User(
    @BsonId
    val userId: Int?,
    val email: String= "email",
    val userName: String = "username",
    val passwordHash: String= "password"
)
//class User(userId: ObjectId?,
//           email: String= "email",
//           userName: String = "username",
//           passwordHash: String= "password"): Serializable{
//    @BsonId
//    var userId: ObjectId?
//    var email: String
//   // @BsonProperty(value = "userName")
//    var userName: String
//    var passwordHash: String
//
//    constructor() : this(null, "not_set", "not set"){}
//    init{
//        this.userId = userId
//        this.email = email
//        this.userName = userName
//        this.passwordHash = passwordHash
//    }
//}

//class UserShip(id: ObjectId?, name: String, fuel: Float) {
//    @BsonId
//    var id: ObjectId?
//    var fuel: Float
//    @BsonProperty(value = "shipname")
//    var name: String
//
//    constructor() : this(null, "not_set", 5.0f){}
//    init{
//        this.id = id
//        this.fuel = fuel
//        this.name = name
//    }
//}

