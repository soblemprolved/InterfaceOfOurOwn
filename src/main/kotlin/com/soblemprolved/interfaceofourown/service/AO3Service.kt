package com.soblemprolved.interfaceofourown.service

import com.soblemprolved.interfaceofourown.model.BookmarkFilterParameters
import com.soblemprolved.interfaceofourown.model.CollectionFilterParameters
import com.soblemprolved.interfaceofourown.model.WorkFilterParameters
import com.soblemprolved.interfaceofourown.service.converters.AO3ConverterFactory
import com.soblemprolved.interfaceofourown.service.converters.responsebody.*
import com.soblemprolved.interfaceofourown.service.models.*
import com.soblemprolved.interfaceofourown.service.models.LoginFieldMap
import com.soblemprolved.interfaceofourown.service.models.Tag
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
    /*
    There is a specific naming system for the methods.
    Methods beginning with "browse..." return a set of results, and can accept *optional* parameters for filtering.
    Methods beginning with "search..." require all parameters to be present.
    FIXME: this is a bad explanation
     */

    /**
     * Retrieves a list of up to 20 bookmark blurbs at the specified [page] that are associated with the specified tag.
     * Additional arguments can be specified in [parameters] with a [WorkFilterParameters] object.
     */
    @GET("tags/{tag}/bookmarks")
    suspend fun browseBookmarksByTag(
        @Path("tag") tag: Tag, // TODO: consider using interceptors to encode tags instead
        @Query("page") page: Int,
        @QueryMap parameters: BookmarkFilterParameters = BookmarkFilterParameters()
    ): AO3Response<BookmarksByTagConverter.Result>


    /**
     * Retrieves a list of up to 20 work blurbs at the specified [page] that are associated with the specified tag.
     * Additional arguments can be specified in [parameters] with a [WorkFilterParameters] object.
     */
    @GET("tags/{tag}/works")
    suspend fun browseWorksByTag(
        @Path("tag") tag: Tag,
        @Query("page") page: Int,
        @QueryMap parameters: WorkFilterParameters = WorkFilterParameters()
    ): AO3Response<WorksByTagConverter.Result>

    /**
     * Retrieves a list of up to 20 collection blurbs at the specified [page] from all collections.
     * Additional arguments can be specified in [parameters] with a [CollectionFilterParameters] object.
     */
    @GET("collections")
    suspend fun browseCollections(
        @Query("page") page: Int,
        @QueryMap parameters: CollectionFilterParameters = CollectionFilterParameters()
    ): AO3Response<CollectionsSearchConverter.Result>

    /**
     * Retrieves a CSRF token and sets the session cookie to match the token.
     */
    @GET("token_dispenser.json")
    suspend fun getCsrfToken(): AO3Response<Csrf>

    /**
     * Retrieves the work with the specified [id].
     *
     * May throw an error if the user is not logged in, and is trying to access a restricted work.
     */
    @GET("works/{id}?view_adult=true&view_full_work=true")
    suspend fun getWork(@Path("id") id: Long): AO3Response<WorkConverter.Result>

    /**
     * Logs in to AO3 with the specified username and password.
     *
     * This changes the
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
     */
    @FormUrlEncoded
    @POST("users/logout")
    suspend fun logout(
        @Field("authenticity_token") csrf: Csrf,
        @FieldMap(encoded = false) defaultFormParameters: LogoutFieldMap = LogoutFieldMap()
    ): AO3Response<Logout>

    /**
     * Retrieves a list of up to 15 tags that match the search [query].
     *
     * The types of the returned tags are constrained by the [type] specified.
     */
    @Headers("Accept: application/json")
    @GET("autocomplete/{type}")
    suspend fun searchAutocomplete(
        @Path("type") type: AutocompleteType,
        @Query("term") query: String
    ): AO3Response<AutocompleteConverter.Result>

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
    fun getWorkComments()
    fun getChapterComments()
    fun commentOnWork()
    fun giveKudosToWork()
    fun bookmarkWork()

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
