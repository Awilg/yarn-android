package com.orienteer.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.orienteer.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// localhost to computer when on emulator
private const val BASE_URL = "http://10.0.2.2:8080/"
// TODO (01) Create an enum full of constants to match the query values our web service expects

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(
        PolymorphicJsonAdapterFactory.of(BaseClue::class.java, "type")
        .withSubtype(ClueText::class.java, ClueType.Text.name)
        .withSubtype(ClueLocation::class.java, ClueType.Location.name))
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
interface TreasureHuntService {

    /**
     * Returns a Coroutine [Deferred] [List] of [Adventure] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "hunts" endpoint will be requested with the GET
     * HTTP method
     */
    // TODO (02) Add filter @Query value to the getProperties method
    @GET("adventure/{id}")
    fun getTreasureHuntsById(): Deferred<AdventureResponse>

    /**
     * Returns a Coroutine [Deferred] [List] of [Adventure] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "hunts" endpoint will be requested with the GET
     * HTTP method
     */
    // TODO (02) Add filter @Query value to the getProperties method
    @POST("adventure")
    fun saveAdventure(@Body adventure: AdventureCreate): Deferred<AdventureResponse>

    /**
     * Returns a Coroutine [Deferred] [List] of [Adventure] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "hunts" endpoint will be requested with the GET
     * HTTP method
     */
    // TODO (02) Add filter @Query value to the getProperties method
    @GET("hunts/user/{id}")
    fun getTreasureHuntsByUserId(): Deferred<List<AdventureResponse>>

    /**
     * Returns a Coroutine [Deferred] [List] of [Adventure] which can be fetched with await() if
     * in a Coroutine scope. Returns all the [Adventure]s within a given distance
     */
    @GET("adventure/findNearby")
    fun getTreasureHuntsByLocation(@Query("lng") longitude: String,
                                   @Query("lat") latitude: String
    ): Deferred<List<AdventureResponse>>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TreasureHuntApi {
    val service: TreasureHuntService by lazy { retrofit.create(TreasureHuntService::class.java) }
}