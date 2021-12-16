package com.soblemprolved.orpheus.service

import com.soblemprolved.orpheus.model.BookmarkFilterParameters
import com.soblemprolved.orpheus.model.CollectionFilterParameters
import com.soblemprolved.orpheus.model.WorkFilterParameters
import com.soblemprolved.orpheus.service.converters.*
import com.soblemprolved.orpheus.service.models.AO3Response
import com.soblemprolved.orpheus.service.models.AutocompleteType
import retrofit2.http.*

interface AO3Service {
    /*
    There is a specific naming system for the methods.
    Methods beginning with "browse..." return a set of results, and can accept *optional* parameters for filtering.
    Methods beginning with "search..." require all parameters to be present.
    FIXME: this is a bad explanation
     */

    @GET("tags/{tag}/bookmarks")
    suspend fun browseBookmarksByTag(
        @Path("tag") encodedTag: String,
        @Query("page") page: Int,
        @QueryMap parameters: BookmarkFilterParameters = BookmarkFilterParameters()
    ): AO3Response<BookmarksByTagConverter.Result>

    @GET("tags/{tag}/works")
    suspend fun browseWorksByTag(
        @Path("tag") encodedTag: String,
        @Query("page") page: Int,
        @QueryMap parameters: WorkFilterParameters = WorkFilterParameters()
    ): AO3Response<WorksByTagConverter.Result>

    @GET("collections")
    suspend fun browseCollections(
        @Query("page") page: Int,
        @QueryMap parameters: CollectionFilterParameters = CollectionFilterParameters()
    ): AO3Response<CollectionsSearchConverter.Result>

    @GET("works/{id}?view_adult=true&view_full_work=true")
    suspend fun getWork(@Path("id") id: Long): AO3Response<WorkConverter.Result>

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
}