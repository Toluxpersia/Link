package com.Link.Routes

import com.Link.DataBase.MongoDataHandler
import com.Link.Models.MySession
import com.Link.hashFunction
import com.Link.jwtService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*

const val API_VERSION = "/v1"
const val USERS = "$API_VERSION/users"
const val USER_LOGIN = "$USERS/login"
const val USER_CREATE = "$USERS/create"
val mongohandler = MongoDataHandler()
@Location(USERS)
class UserRoute{
}
@Location(USER_CREATE)
class RegisterRoute{
}
@Location(USER_LOGIN)
class LoginRoute{
}
fun Routing.userRoutes(){

post<RegisterRoute>{
    val signupParameters = call.receiveParameters()
    val password = signupParameters["password"]
        ?: return@post call.respond(
            HttpStatusCode.Unauthorized, "Missing Fields")
    val userName = signupParameters["userName"]
        ?: return@post call.respond(
            HttpStatusCode.Unauthorized, "Missing Fields")
    val email = signupParameters["email"]
        ?: return@post call.respond(
            HttpStatusCode.Unauthorized, "Missing Fields")
    val hash = hashFunction(password)

    try {
val user = mongohandler.adduser(email, userName, hash)
        user?.userId?.let{
            call.respondText(
                jwtService.generateToken(user.userId!!.toString()),
                status = HttpStatusCode.Created
            )
        }
        }catch (e: Throwable) {
        application.log.error("Failed to register user", e)
        call.respond(HttpStatusCode.BadRequest, "Problems creating User")
    }
    }

    post<LoginRoute> { // 1
        val signinParameters = call.receive<Parameters>()
        val password = signinParameters["password"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")
        val email = signinParameters["email"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields")
        val hash = hashFunction(password)
        try {
            val currentUser = mongohandler.finduserByEmail(email) // 2
            currentUser?.userId?.let {
              //  call.respondText(jwtService.generateToken(currentUser.userId!!.toString()))
                if (currentUser.passwordHash == hash) { // 3
                    call.sessions.set(MySession(it)) // 4
                    call.respondText(jwtService.generateToken(currentUser.userId!!.toString())) // 5
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest, "Problems retrieving User") // 6
                }
            }
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode.BadRequest, "Problems retrieving User")
        }
    }
}

