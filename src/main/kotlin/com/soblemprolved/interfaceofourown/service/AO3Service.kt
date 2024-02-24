package com.soblemprolved.interfaceofourown.service

import com.soblemprolved.interfaceofourown.features.authentication.Login
import com.soblemprolved.interfaceofourown.features.autocomplete.AutocompletePage
import com.soblemprolved.interfaceofourown.features.collections.filter.CollectionsFilterParameters
import com.soblemprolved.interfaceofourown.features.collections.filter.CollectionsFilterPage
import com.soblemprolved.interfaceofourown.features.common.filterparameters.BookmarksFilterParameters
import com.soblemprolved.interfaceofourown.features.tags.bookmarks.TagBookmarksPage
import com.soblemprolved.interfaceofourown.features.tags.works.TagWorksPage
import com.soblemprolved.interfaceofourown.features.common.filterparameters.WorksFilterParameters
import com.soblemprolved.interfaceofourown.features.works.WorkPage
import com.soblemprolved.interfaceofourown.model.*
import com.soblemprolved.interfaceofourown.model.Tag
import com.soblemprolved.interfaceofourown.service.*
import com.soblemprolved.interfaceofourown.features.authentication.LoginFieldMap
import com.soblemprolved.interfaceofourown.features.authentication.Logout
import com.soblemprolved.interfaceofourown.features.authentication.LogoutFieldMap
import com.soblemprolved.interfaceofourown.features.users.works.UserWorksPage
import com.soblemprolved.interfaceofourown.service.response.AO3Response
import com.soblemprolved.interfaceofourown.service.response.AO3ResponseCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.*
import java.net.CookieManager
import java.net.CookiePolicy

/**
 * This is the retrofit interface for generating the retrofit service that interacts with AO3.
 *
 * All functions in here are suspending for now. Calls will be included at a later date.
 */
interface AO3Service {
    /* Tag Actions ****************************************************************************************************/

    /**
     * Retrieves a list of up to 20 bookmark blurbs at the specified [page] that are associated with the specified tag.
     *
     * Additional arguments can be specified in [parameters] with a [WorksFilterParameters] object.
     *
     * @param tag Name of the tag
     * @param page Page to be retrieved
     * @param parameters Additional parameters for filtering the results
     */
    @GET("tags/{tag}/bookmarks")
    suspend fun browseBookmarksByTag(
        @Path("tag") tag: Tag, // TODO: consider using interceptors to encode tags instead
        @Query("page") page: Int,
        @QueryMap parameters: BookmarksFilterParameters = BookmarksFilterParameters()
    ): AO3Response<TagBookmarksPage>

    /**
     * Retrieves a list of up to 20 work blurbs at the specified [page] that are associated with the specified tag.
     *
     * Additional arguments can be specified in [parameters] with a [WorksFilterParameters] object.
     *
     * @param tag Name of the tag
     * @param page Page to be retrieved
     * @param parameters Additional parameters for filtering the results
     */
    @GET("tags/{tag}/works")
    suspend fun browseWorksByTag(
        @Path("tag") tag: Tag,
        @Query("page") page: Int,
        @QueryMap parameters: WorksFilterParameters = WorksFilterParameters()
    ): AO3Response<TagWorksPage>

    /**
     * Retrieves a list of up to 20 collection blurbs at the specified [page] from all collections.
     *
     * Additional arguments can be specified in [parameters] with a [CollectionsFilterParameters] object.
     *
     * @param page Page to be retrieved
     * @param parameters Additional parameters for filtering the results
     */
    @GET("collections")
    suspend fun browseCollections(
        @Query("page") page: Int,
        @QueryMap parameters: CollectionsFilterParameters = CollectionsFilterParameters()
    ): AO3Response<CollectionsFilterPage>


    /* Authentication Actions *****************************************************************************************/

    /**
     * Retrieves a CSRF token and sets the session cookie to match the token.
     */
    @Headers("Accept: application/json")
    @GET("token_dispenser.json")
    suspend fun getCsrfToken(): AO3Response<Csrf>

    /**
     * Retrieves the work with the specified [id].
     *
     * May throw an error if the user is not logged in, and is trying to access a restricted work.
     *
     * @param id Unique numerical ID of the work
     */
    @GET("works/{id}?view_adult=true&view_full_work=true")
    suspend fun getWork(@Path("id") id: Long): AO3Response<WorkPage>

    /**
     * Logs in to AO3 with the specified username and password.
     *
     * All further network calls to AO3 made using the backing [OkHttpClient] (e.g. this [AO3Service] instance)
     * will be treated as if the user is logged in, until [logout] is called.
     *
     * @param username Username of the account
     * @param password Password of the account
     * @param csrf Most recent CSRF token. Use [getCsrfToken] to retrieve the latest CSRF token immediately before calling this method.
     * @param defaultFormParameters Non-accessible field used to pass in additional parameters required by AO3.
     */
    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("user[login]") username: String,
        @Field("user[password]") password: String,
        @Field("authenticity_token") csrf: Csrf,
        @FieldMap(encoded = false) defaultFormParameters: LoginFieldMap = LoginFieldMap()
    ): AO3Response<Login>

    /**
     * Logs out of AO3.
     *
     * All further network calls to AO3 made using the backing [OkHttpClient] (e.g. this [AO3Service] instance
     * will be treated as if the user is logged out.
     *
     * @param csrf Most recent CSRF token. Use [getCsrfToken] to retrieve the latest CSRF token immediately before calling this method.
     * @param defaultFormParameters Non-accessible field used to pass in additional parameters required by AO3.
     */
    @FormUrlEncoded
    @POST("users/logout")
    suspend fun logout(
        @Field("authenticity_token") csrf: Csrf,
        @FieldMap(encoded = false) defaultFormParameters: LogoutFieldMap = LogoutFieldMap()
    ): AO3Response<Logout>


    /* Miscellaneous Actions ******************************************************************************************/

    /**
     * Retrieves a list of up to 15 tags that match the search [query].
     *
     * The types of the returned tags are constrained by the [type] specified.
     *
     * @param type Restricts the type of results that are returned
     * @param query Search term
     */
    @Headers("Accept: application/json")
    @GET("autocomplete/{type}")
    suspend fun searchAutocomplete(
        @Path("type") type: AutocompleteType,
        @Query("term") query: String
    ): AO3Response<AutocompletePage>


    /* User Profile Actions *******************************************************************************************/

    /**
     * Retrieves a list of up to 20 work blurbs at the specified [page] that are associated with the user.
     *
     * Additional arguments can be specified in [parameters] with a [WorksFilterParameters] object.
     *
     * @param user Name of the user
     * @param page Page to be retrieved
     * @param parameters Additional parameters for filtering the results
     */
    @GET("users/{user}/works")
    suspend fun browseWorksByUser(
        @Path("user") user: String,
        @Query("page") page: Int,
        @QueryMap parameters: WorksFilterParameters = WorksFilterParameters()
    ): AO3Response<UserWorksPage>

    /**
     * Retrieves a list of up to 20 work blurbs at the specified [page] that are associated with the user.
     *
     * Additional arguments can be specified in [parameters] with a [WorksFilterParameters] object.
     *
     * Function overloaded to accept a pseudonym.
     *
     * @param user Name of the user
     * @param page Page to be retrieved
     * @param parameters Additional parameters for filtering the results
     */
    @GET("users/{user}/pseuds/{pseud}/works")
    suspend fun browseWorksByUser(
        @Path("user") user: String,
        @Path("pseud") pseudonym: String,
        @Query("page") page: Int,
        @QueryMap parameters: WorksFilterParameters = WorksFilterParameters()
    ): AO3Response<UserWorksPage>

    /**
     * Retrieves a list of up to 20 bookmark blurbs at the specified [page] that are associated with the user.
     *
     * Additional arguments can be specified in [parameters] with a [WorksFilterParameters] object.
     *
     * @param user Name of the user
     * @param page Page to be retrieved
     * @param parameters Additional parameters for filtering the results
     */
    @GET("users/{user}/works")
    suspend fun browseBookmarksByUser(
        @Path("user") user: String,
        @Query("page") page: Int,
        @QueryMap parameters: WorksFilterParameters = WorksFilterParameters()
    ): AO3Response<UserWorksPage>

    /**
     * Retrieves a list of up to 20 bookmark blurbs at the specified [page] that are associated with the user.
     *
     * Additional arguments can be specified in [parameters] with a [WorksFilterParameters] object.
     *
     * Function overloaded to accept a pseudonym.
     *
     * @param user Name of the user
     * @param page Page to be retrieved
     * @param parameters Additional parameters for filtering the results
     */
    @GET("users/{user}/pseuds/{pseud}/works")
    suspend fun browseBookmarksByUser(
        @Path("user") user: String,
        @Path("pseud") pseudonym: String,
        @Query("page") page: Int,
        @QueryMap parameters: BookmarksFilterParameters = BookmarksFilterParameters()
    ): AO3Response<UserWorksPage>


    /*
    // I'm going to list all the functions in the final API here, even if there is no request analog/not complete
    // TODO: we will make this a suspending interface

    /* User accounts */
    fun login()     // users/login      TODO: In progress
    fun logout()    // users/logout     TODO: In progress

    /* Account-exclusive actions */

    /* Fandoms */
    fun getFandomCategories()   // media                            Note: this is quite useless
    fun getFandomsByCategory()  // media/$CATEGORY_NAME/fandoms     TODO

    /* Browsing by tag */
    fun getTagInfo()            // tags/$TAG_NAME               TODO
    fun browseWorksByTag()      // tags/$TAG_NAME/works         Completed
    fun browseBookmarksByTag()  // tags/$TAG_NAME/bookmarks     Completed

    /* Browsing */
    // These functionalities are quite useless imo, so I will assign them to the lowest priority
    // these are non-paginated functions that only get a fixed amount of items
    fun getRecentWorks()                            // works                            TODO
    fun getRecentBookmarks()                        // bookmarks                        TODO
    fun getMostPopularTags()                        // tags                             TODO
    fun getRandomTags()                             // tags?show=random                 TODO
    fun getOpenChallengesClosingSoonest()           // collections/list_challenges      TODO
    fun getGiftExchangeChallengesClosingSoonest()   // collections/list_ge_challenges   TODO
    fun getPromptMemeChallengesClosingSoonest()     // collections/list_pm_challenges   TODO

    // These are more useful, as they are actually paginated
    fun browseTagSets()         // tag_sets         TODO
    fun browseCollections()     // collections      Completed

    /* Searching */
    fun searchWorks()       // works/search         TODO
    fun searchBookmarks()   // bookmarks/search     TODO
    fun searchTags()        // tags/search          TODO
    fun searchUsers()       // people/search        TODO

    /* User Profiles */
    fun getUserProfile()        // users/$USER_NAME/pseuds/$PSEUD_NAME              TODO
    fun getWorksByUser()        // users/$USER_NAME/pseuds/$PSEUD_NAME/works        TODO
    fun getBookmarksByUser()    // users/$USER_NAME/pseuds/$PSEUD_NAME/bookmarks    TODO

    /* Works and work-related stuff */
    fun getWork()
    fun getWorkComments()       TODO
    fun getChapterComments()    TODO
    fun commentOnWork()         TODO
    fun giveKudosToWork()       TODO
    fun bookmarkWork()          TODO

    /* Comments */
    fun viewChildComments()
    fun replyToComment()

    /* Series */
    fun getWorksBySeries()      // series/$SERIES_ID                TODO: also return series info
    fun subscribeToSeries()     // users/$USER_NAME/subscriptions   TODO: Members only
    fun bookmarkSeries()        //                                  TODO: Members only

    /* Collections and collection-related stuff */
    */

    companion object Factory {
        /**
         * Factory method to create an instance of an [AO3Service].
         *
         * Specify the [baseUrl] if you wish to use this to perform testing.
         *
         * Specify the [okHttpClient] if you have an existing [OkHttpClient] in your application. Retrofit will share resources
         * with the existing client, but it will use its own configuration for its own requests.
         */
        fun create(
            baseUrl: String = "https://archiveofourown.org/",
            okHttpClient: OkHttpClient? = null,
            interceptors: List<Interceptor> = listOf(),
            converterFactories: List<Converter.Factory> = listOf(),
            callAdapterFactories: List<CallAdapter.Factory> = listOf()
        ) : AO3Service {
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

            val client = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
                .apply {
                    for (interceptor in interceptors) {
                        this.addInterceptor(interceptor)
                    }
                }
                .followRedirects(false)
                .cookieJar(JavaNetCookieJar(cookieManager))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(AO3ConverterFactory())
                .addCallAdapterFactory(AO3ResponseCallAdapterFactory())
                .apply {
                    for (converterFactory in converterFactories) {
                        this.addConverterFactory(converterFactory)
                    }
                }
                .apply {
                    for (callAdapterFactory in callAdapterFactories) {
                        this.addCallAdapterFactory(callAdapterFactory)
                    }
                }
                .build()

            return retrofit.create(AO3Service::class.java) // TODO: should I also return the retrofit and okhttp client?
        }
    }
}
