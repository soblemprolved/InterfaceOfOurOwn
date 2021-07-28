package com.soblemprolved.orpheus.model

enum class Rating(val description: String, val code: String) {
    NONE("Not Rated", "9"),
    GENERAL("General Audiences", "10"),
    TEEN("Teen And Up Audiences", "11"),
    MATURE("Mature", "12"),
    EXPLICIT("Explicit", "13");

    companion object {
        private val mapByDescription = values().associateBy { it.description }
            .toMutableMap()
            // This is a special case. To reproduce, exclude all ratings from work search, then check the rating text.
            .apply {
                this.put("No rating", NONE)
                this.put("Teen & Up Audiences", TEEN)
            }
            .toMap()

        private val mapByCode = values().associateBy { it.code }

        fun fromName(name: String): Rating {
            return mapByDescription[name]!!
        }

        fun fromCode(code: String): Rating {
            return mapByCode[code]!!
        }
    }
}

enum class Category(val description: String, val code: String) {
    GEN("Gen", "21"),
    FEMALE_MALE("F/M", "22"),
    MALE_MALE("M/M", "23"),
    OTHER("Other", "24"),
    FEMALE_FEMALE("F/F", "116"),
    MULTI("Multi", "2246");

    companion object {
        private val mapByDescription = values().associateBy { it.description }
        private val mapByCode = values().associateBy { it.code }

        fun fromName(name: String): Category? {
            return mapByDescription[name]
        }

        fun fromCode(code: String): Category {
            return mapByCode[code]!!
        }
    }
}

enum class Warning(val description: String, val code: String) {
    CREATOR_CHOSE_NOT_TO_USE_WARNINGS("Creator Chose Not To Use Archive Warnings", "14"),
    NO_WARNINGS("No Archive Warnings Apply", "16"),
    GRAPHIC_VIOLENCE("Graphic Depictions Of Violence", "17"),
    MAJOR_CHARACTER_DEATH("Major Character Death", "18"),
    RAPE("Rape/Non-Con", "19"),
    UNDERAGE("Underage", "20");

    companion object {
        private val mapByDescription = values().associateBy { it.description }
        private val mapByCode = values().associateBy { it.code }

        fun fromName(name: String): Warning {
            return mapByDescription[name]!!
        }

        fun fromCode(code: String): Warning {
            return mapByCode[code]!!
        }
    }
}

enum class WorkSortCriterion(val code: String) {
    AUTHOR("authors_to_sort_on"),
    TITLE("title_to_sort_on"),
    DATE_POSTED("created_at"),
    DATE_UPDATED("revised_at"),
    WORD_COUNT("word_count"),
    HITS("hits"),
    KUDOS("kudos_count"),
    COMMENTS("comments_count"),
    BOOKMARKS("bookmarks_count")
}

enum class BookmarkType {
    RECOMMENDATION, PUBLIC, PRIVATE, HIDDEN
}

enum class BookmarkSortCriterion(val code: String) {
    DATE_BOOKMARKED("created_at"),
    DATE_UPDATED("bookmarkable_date")
}

enum class CollectionsSortCriterion(val code: String) {
    TITLE("collections.title"),
    DATE_CREATED("collections.created_at")
}

enum class SortDirection(val code: String) {
    ASCENDING("ASC"),
    DESCENDING("DESC")
}

enum class ChallengeType(val code: String) {
    GIFT_EXCHANGE("gift_exchange"),
    PROMPT_MEME("prompt_meme"),
    NO_CHALLENGE("no_challenge")
}

enum class Language(val description: String, val code: String) {
    ALL("All Languages", ""),
    SO("af Soomaali", "so"),
    AFR("Afrikaans", "afr"),
    AR("العربية", "ar"),
    ARC("ܐܪܡܝܐ | ארמיא", "arc"),
    HY("հայերեն", "hy"),
    ID("Bahasa Indonesia", "id"),
    MS("Bahasa Malaysia", "ms"),
    BG("Български", "bg"),
    BN("বাংলা", "bn"),
    JV("Basa Jawa", "jv"),
    BE("беларуская", "be"),
    BOS("Bosanski", "bos"),
    BR("brezhoneg", "br"),
    CA("Català", "ca"),
    CS("Čeština", "cs"),
    CHN("Chinuk Wawa", "chn"),
    CY("Cymraeg", "cy"),
    DA("Dansk", "da"),
    DE("Deutsch", "de"),
    ET("eesti keel", "et"),
    EL("Ελληνικά", "el"),
    EN("English", "en"),
    ES("Español", "es"),
    EO("Esperan", "eo"),
    EU("Euskara", "eu"),
    FA("فارسی", "fa"),
    FIL("Filipino", "fil"),
    FR("Français", "fr"),
    FUR("Furlan", "fur"),
    GA("Gaeilge", "ga"),
    GD("Gàidhlig", "gd"),
    GL("Galego", "gl"),
    GOT("𐌲𐌿𐍄𐌹𐍃𐌺𐌰", "got"),
    HAK("中文-客家话", "hak"),
    KO("한국어", "ko"),
    HAU("Hausa | هَرْشَن هَوْسَ", "hau"),
    HI("हिन्दी", "hi"),
    HR("Hrvatski", "hr"),
    IA("Interlingua", "ia"),
    ZU("isiZulu", "zu"),
    IS("Íslenska", "is"),
    IT("Italiano", "it"),
    HE("עברית", "he"),
    KAT("ქართული", "kat"),
    QKZ("Khuzdul", "qkz"),
    KIR("Кыргызча", "kir"),
    SW("Kiswahili", "sw"),
    HT("kreyòl ayisyen", "ht"),
    FCS("Langue des signes québécoise", "fcs"),
    LV("Latviešu valoda", "lv"),
    LB("Lëtzebuergesch", "lb"),
    LT("Lietuvių kalba", "lt"),
    LA("Lingua latina", "la"),
    HU("Magyar", "hu"),
    MK("македонски", "mk"),
    ML("മലയാളം", "ml"),
    MNC("ᠮᠠᠨᠵᡠ ᡤᡳᠰᡠᠨ", "mnc"),
    MR("मराठी", "mr"),
    MON("ᠮᠣᠩᠭᠣᠯ ᠪᠢᠴᠢᠭ᠌ | Монгол Кирилл үсэг", "mon"),
    MY("မြန်မာဘာသာ", "my"),
    NAN("中文-闽南话 臺語", "nan"),
    NL("Nederlands", "nl"),
    JA("日本語", "ja"),
    NO("Norsk", "no"),
    PS("پښتو", "ps"),
    NDS("Plattdüütsch", "nds"),
    PL("Polski", "pl"),
    PTBR("Português brasileiro", "ptBR"),
    PTPT("Português europeu", "ptPT"),
    PA("ਪੰਜਾਬੀ", "pa"),
    QYA("Quenya", "qya"),
    RO("Română", "ro"),
    RU("Русский", "ru"),
    SCO("Scots", "sco"),
    SQ("Shqip", "sq"),
    SJN("Sindarin", "sjn"),
    SI("සිංහල", "si"),
    SK("Slovenčina", "sk"),
    SLV("Slovenščina", "slv"),
    GEM("Sprēkō Þiudiskō", "gem"),
    SR("српски", "sr"),
    FI("Suomi", "fi"),
    SV("Svenska", "sv"),
    TA("தமிழ்", "ta"),
    TH("ไทย", "th"),
    TQX("Thermian", "tqx"),
    BOD("བོད་སྐད་", "bod"),
    VI("Tiếng Việt", "vi"),
    TLH("tlhIngan-Hol", "tlh"),
    QTP("ki Pona", "qtp"),
    TR("Türkçe", "tr"),
    UK("Українська", "uk"),
    UIG("ئۇيغۇر تىلى", "uig"),
    WUU("中文-吴语", "wuu"),
    YI("יידיש", "yi"),
    YUE("中文-广东话 粵語", "yue"),
    ZH("中文-普通话 國語", "zh");

    companion object {
        private val mapByDescription = values().associateBy { it.description }
        private val mapByCode = values().associateBy { it.code }

        fun fromName(name: String): Language {
            return mapByDescription[name]!!
        }

        fun fromCode(code: String): Language {
            return mapByCode[code]!!
        }
    }
}
