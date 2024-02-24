package com.soblemprolved.interfaceofourown.service

import com.soblemprolved.interfaceofourown.features.common.parameterconverters.AutocompleteTypeConverter
import com.soblemprolved.interfaceofourown.features.common.parameterconverters.CsrfConverter
import com.soblemprolved.interfaceofourown.features.common.parameterconverters.TagUrlConverter
import com.soblemprolved.interfaceofourown.features.authentication.*
import com.soblemprolved.interfaceofourown.features.authentication.GetCsrfConverter
import com.soblemprolved.interfaceofourown.features.authentication.LoginConverter
import com.soblemprolved.interfaceofourown.features.authentication.LogoutConverter
import com.soblemprolved.interfaceofourown.features.autocomplete.AutocompleteConverter
import com.soblemprolved.interfaceofourown.features.autocomplete.AutocompletePage
import com.soblemprolved.interfaceofourown.features.collections.filter.CollectionsFilterConverter
import com.soblemprolved.interfaceofourown.features.collections.filter.CollectionsFilterPage
import com.soblemprolved.interfaceofourown.features.tags.bookmarks.TagBookmarksConverter
import com.soblemprolved.interfaceofourown.features.tags.bookmarks.TagBookmarksPage
import com.soblemprolved.interfaceofourown.features.tags.works.TagWorksConverter
import com.soblemprolved.interfaceofourown.features.tags.works.TagWorksPage
import com.soblemprolved.interfaceofourown.features.users.works.UserWorksConverter
import com.soblemprolved.interfaceofourown.features.users.works.UserWorksPage
import com.soblemprolved.interfaceofourown.features.works.WorkConverter
import com.soblemprolved.interfaceofourown.features.works.WorkPage
import com.soblemprolved.interfaceofourown.model.AutocompleteType
import com.soblemprolved.interfaceofourown.model.Csrf
import com.soblemprolved.interfaceofourown.model.Tag
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class AO3ConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return when (type) {
            AutocompletePage::class.java        -> AutocompleteConverter
            TagBookmarksPage::class.java        -> TagBookmarksConverter
            CollectionsFilterPage::class.java   -> CollectionsFilterConverter
            WorkPage::class.java                -> WorkConverter
            TagWorksPage::class.java            -> TagWorksConverter
            Csrf::class.java                    -> GetCsrfConverter
            Login::class.java                   -> LoginConverter
            Logout::class.java                  -> LogoutConverter
            UserWorksPage::class.java           -> UserWorksConverter
            else                                -> super.responseBodyConverter(type, annotations, retrofit)
        }
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return when (type) {
            AutocompleteType::class.java    -> AutocompleteTypeConverter
            Tag::class.java                 -> TagUrlConverter
            Csrf::class.java                -> CsrfConverter
            else                            -> super.stringConverter(type, annotations, retrofit)
        }
    }
}
