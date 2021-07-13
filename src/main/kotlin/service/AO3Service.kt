package service

import model.Work
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import service.query.WorkFilterQueryMap

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
        @QueryMap(encoded = true) filterQueryMap: WorkFilterQueryMap,   // disable encoding
        @Query("page") page: Int    // 1-indexed
    )
}
