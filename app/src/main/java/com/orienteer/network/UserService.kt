package com.orienteer.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.orienteer.models.User
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// This is the local address for the emulator
private const val BASE_URL = "http://10.0.2.2:8081/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(UserJsonConverter())
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the methods of the backend treasure hunt service/
 */
interface UserService {
    /**
     * Returns a Coroutine [Deferred] of [User] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "user" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("hunts")
    @Wrapped
    fun getUser(): Deferred<User>
}


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object UserApi {
    val retrofitService: UserService by lazy { retrofit.create(UserService::class.java) }
}

class UserResponse {
    lateinit var user: User
}

/**
 * This converter is to take the json object and convert that json object to the list of treasure hunts
 */
class UserJsonConverter {
    @Wrapped
    @FromJson
    fun fromJson(json: UserResponse): User {
        return json.user
    }

    @ToJson
    fun toJson(@Wrapped value: User): UserResponse {
        throw UnsupportedOperationException()
    }
}