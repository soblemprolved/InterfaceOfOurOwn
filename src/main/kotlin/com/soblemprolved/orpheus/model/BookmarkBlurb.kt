package com.soblemprolved.orpheus.model

sealed interface BookmarksBlurb {
    val bookmarks: List<Bookmark>
}

data class WorkBookmarksBlurb(val workBlurb: WorkBlurb, override val bookmarks: List<Bookmark>) : BookmarksBlurb
data class SeriesBookmarksBlurb(val seriesBlurb: SeriesBlurb, override val bookmarks: List<Bookmark>) : BookmarksBlurb
data class ExternalWorkBookmarksBlurb(
    val externalWorkBlurb: ExternalWorkBlurb,
    override val bookmarks: List<Bookmark>
) : BookmarksBlurb
