package com.Link.Authentication

import com.Link.CLAIM_USERID
import com.Link.CLAIM_USERNAME
import com.Link.JWT_ISSUER
import com.Link.Models.User
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtService {

    //private val issuer = "LinkServer"
    private val jwtSecret = System.getenv("JWT_SECRET") // 1
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    // 2
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(JWT_ISSUER)
        .build()

    // 3
    fun generateToken(userId: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(JWT_ISSUER)
        .withClaim(CLAIM_USERID, userId)
        //.withClaim(CLAIM_USERNAME, user.userName)
        .withExpiresAt(expiresAt())
        .sign(algorithm)

    private fun expiresAt() =
        Date(System.currentTimeMillis() + 3_600_000 * 24) // 24 hours
}
