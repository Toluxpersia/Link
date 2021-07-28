package com.Link

import com.Link.Authentication.JwtService
import com.Link.Authentication.hash
import com.Link.DataBase.MongoDataHandler
import com.Link.Models.MySession
import com.Link.Models.UserIdPrincipalForUser
import com.Link.Routes.userRoutes
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.slf4j.event.Level
import kotlin.collections.mapOf
import kotlin.collections.set

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
//val client = KMongo.createClient().coroutine
//val database = client.getDatabase("dev")
//val userCollection = database.getCollection<User>()
val jwtService = JwtService()
val hashFunction = { s: String -> hash(s) }
val mongoDataHandler = MongoDataHandler()
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Authentication) {
        jwt{
            verifier(jwtService.verifier)
            realm = JWT_REALM
           validate {
               val userId = it.payload.getClaim(CLAIM_USERID).asInt()
               //val userName = it.payload.getClaim(CLAIM_USERNAME).asString()

               val user = mongoDataHandler.finduser(userId) // 4
              UserIdPrincipalForUser(user?.userId!!)
               }
        }
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    install(Locations) {
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
install(CallLogging){
    level = Level.INFO
    filter { call -> call.request.path().startsWith("/") }
}


    routing {

        userRoutes()
        authenticate {
            get("/allships"){
                //call.respond(mongoDataHandler.allSpaceShips())
                call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
            }
        }

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }

        get<MyLocation> {
            call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
            call.respondText("Inside $it")
        }
        get<Type.List> {
            call.respondText("Inside $it")
        }


    }
    }


@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@Location("/type/{name}") data class Type(val name: String) {
    @Location("/edit")
    data class Edit(val type: Type)

    @Location("/list/{page}")
    data class List(val type: Type, val page: Int)
}

//data class MySession(val count: Int = 0)

