package service

import model.Work
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface AO3Service {
    @GET("autocomplete/{type}")
    fun listAutocompleteResults(
        @Path("type") type: AutocompleteType,
        @Query("term") term: String
    ) : Call<List<String>>

    @GET("work/{id}")
    fun getWork(
        @Path("id") id: Long,
        @Query("view_adult") viewAdult: Boolean = true,
        @Query("view_full_work") viewFullWork: Boolean = true
    ) : Call<Work>

    @GET("tags/{tag}/works")
    fun listWorks(
        @Path("tag") tag: String,    // should be encoded? should i dedicate a class to it?
        @QueryMap filterParameters: Map<String, String> // should use another class for this?
    )
}
