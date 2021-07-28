package com.Link.DataBase

import com.Link.Models.User
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider

class MongoDataHandler {

//val userCollection: CoroutineCollection<User>
//init {
//    val client = KMongo.createClient("mongodb://127.0.0.1:27017").coroutine
//    val database = client.getDatabase("dev")
//    userCollection = database.getCollection<User>()
//
//}

    val database: MongoDatabase
    val userCollection: MongoCollection<User>
    init{
        val pojoCodecRegistry: CodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
        val codecRegistry: CodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            pojoCodecRegistry)

        val clientSettings = MongoClientSettings.builder()
            .codecRegistry(codecRegistry)
            .build()


        val mongoClient = MongoClients.create(clientSettings)
        database = mongoClient.getDatabase("development");
        userCollection = database.getCollection(User::class.java.name, User::class.java)
        //userCollection.insertOne(User(userId = null, email= "good", userName = "username", passwordHash = "password"))
        //initSpaceShips()
    }

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
     fun adduser(email: String, username: String, password: String): User? {
        userCollection.insertOne(User(userId = null, email = email, userName = username, passwordHash = password))
        return userCollection.find(Filters.eq("email", email)).first()
        //return userCollection.findOne(User::email eq email )
    }
     fun finduser(hexid: String): User?{
        return userCollection.find(Filters.eq("_id",hexid)).first()

    }
    fun finduserByEmail(email: String): User?{
        return userCollection.find(Filters.eq("email",email)).first()

    }
}