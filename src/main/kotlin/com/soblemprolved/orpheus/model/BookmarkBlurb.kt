package com.soblemprolved.orpheus.model

data class BookmarksBlurb(
    val item: Item,
    val bookmarks: List<Bookmark>
) {
    sealed class Item {
        data class WorkItem(val workBlurb: WorkBlurb): Item()
        data class SeriesItem(val seriesBlurb: SeriesBlurb): Item()
    }
}
