package com.Link.DataBase

import com.Link.Models.User
import com.mongodb.client.model.Filters
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class MongoDataHandler {
    val client = KMongo.createClient().coroutine
    val database = client.getDatabase("dev")
    val userCollection = database.getCollection<User>()
//val userCollection: CoroutineCollection<User>
//init {
//
//
//}

//    val database: MongoDatabase
//    val userCollection: MongoCollection<User>
//    init{
//        val pojoCodecRegistry: CodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
//        val codecRegistry: CodecRegistry = fromRegistries(
//            MongoClientSettings.getDefaultCodecRegistry(),
//            pojoCodecRegistry)
//
//        val clientSettings = MongoClientSettings.builder()
//            .codecRegistry(codecRegistry)
//            .build()
//
//
//        val mongoClient = MongoClients.create(clientSettings)
//        database = mongoClient.getDatabase("development");
//        userCollection = database.getCollection(User::class.java.name, User::class.java)
//        //userCollection.insertOne(User(userId = null, email= "good", userName = "username", passwordHash = "password"))
//        //initSpaceShips()
//    }

//    fun initSpaceShips(){
//        spaceshipsCollection.insertOne(SpaceShip(null, "test1", 62.3f ))
//        spaceshipsCollection.insertOne(SpaceShip(null, "test2", 12.3f ))
//        spaceshipsCollection.insertOne(SpaceShip(null, "test3", 22.3f ))
//        spaceshipsCollection.insertOne(SpaceShip(null, "test4", 36.3f ))
//    }

//    fun allSpaceShips(): List<SpaceShip>{
//        val mongoResult = spaceshipsCollection.find()
//        mongoResult.forEach{
//            print("ship: $it")
//        }
//        return mongoResult.toList()
//    }
    suspend fun adduser(email: String, username: String, password: String): User? {
        userCollection.insertOne(User(userId = null, email = email, userName = username, passwordHash = password))
    return userCollection.findOne(User::email eq email )
        //return userCollection.find(Filters.eq("email", email)).first()

    }
    suspend fun finduser(hexid: Int): User?{
        return userCollection.findOne(User::userId eq hexid )
       // return userCollection.find(Filters.eq("_id",hexid)).first()

    }
    suspend fun finduserByEmail(email: String): User?{
        return userCollection.findOne(User::email eq email )
       // return userCollection.find(Filters.eq("email",email)).first()

    }
}