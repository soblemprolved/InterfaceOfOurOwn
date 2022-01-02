# Interface Of Our Own

[![Release](https://jitpack.io/v/soblemprolved/orpheus.svg)](https://jitpack.io/#soblemprolved/orpheus)

An unofficial API for interfacing with the Archive of Our Own. Written in Kotlin
for the JVM (that is, it can be used on JVM and Android projects, but not
Multiplatform).

This library uses `kotlinx.coroutines` under the hood, and relies on `Retrofit`
for networking, `jsoup` for parsing HTML responses, and `kotlinx.serialization`
for deserializing JSON responses.

Requires at least Java 8 or Android API 21, due to the requirements of`OkHttp`.

## Download
Add `jitpack` to your project root `build.gradle.kts`:

``` kotlin
// build.gradle.kts
allprojects {
    repositories {
        // ...
        maven { setUrl("https://jitpack.io") }
    }
}
```

or `build.gradle`:

``` groovy
// build.gradle
allprojects {
    repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
}
```

Then add the dependency to your module's `build.gradle.kts`:

``` kotlin
// build.gradle.kts
implementation("com.github.soblemprolved:interfaceofourown:0.3.0")
```

or `build.gradle`:

``` groovy
// build.gradle
implementation 'com.github.soblemprolved:interfaceofourown:0.3.0'
```

## What can it do?
So far, it can be used to retrieve the following:
1. Full works
   `/works/<work ID>`
2. Work summaries associated with a tag
   `/tags/<tag name>/works?<filter arguments>`
3. Autocomplete results for tags (any type) and users
   `/autocomplete/<type>?<search term>`
4. Bookmarks associated with a tag
   `/tags/<tag name>/bookmarks?<filter arguments>`
5. Collections
   `/collections?<filter arguments>`

In the future, the following will be implemented, in no particular order:
1. User login
2. User Profiles (including works and bookmarks)
3. ...and much more!

## Initialising the service
Initialise the service using the default `AO3Service.create()` factory method 
like so:

```kotlin
val service = AO3Service.create()
```

You can also configure the base url used by retrofit as shown below, in case 
you wish to use okhttp's `MockWebServer` or anything similar.

```kotlin
val service = AO3Service.create(baseUrl = "<Your URL here>")
```

If you have an existing `OkHttpClient` instance, you can pass it to the method 
like so. A new client will be created from it that shares the same resources with 
the original client, with all the necessary configurations made. This new client 
will be used by the service, and the old client may be used by your application 
as per normal.

``` kotlin
val service = AO3Service.create(okHttpClient = existingOkHttpClient)
```

Additional interceptors, converter factories, and call adapter factories can be 
passed to the method as lists. They will be added to the retrofit instance 
during initialisation.

```kotlin
val service = AO3Service.create(interceptors = listOf(interceptor1, interceptor2),
                                converterFactories = listOf(factory1, factory2),
                                callAdapterFactories = listOf(callAdapterFactory))
```

## Retrieving Works
We can retrieve a `Work` from the Archive, given that we know its work ID, like so. 
Note that the API is suspending, so methods have to be launched from 
within coroutines.

```kotlin
val workResponse: AO3Response<WorkConverter.Result> = viewModelScope.launch { service.getWork(id = 12345) }
```

The content will be wrapped in an `AO3Response`, which is a simple wrapper class
for all network operations. An `AO3Response` can either be a `Success` or
a `Failure`.

An example workflow for processing a `Success` is shown below.

```kotlin
when (workResponse) {
    is Success -> {
        val (work, csrf) = workResponse.value
        // Perform your own processing on the work below
        when (work) {
            is SingleChapterWork -> // this is a oneshot
            is MultiChapterOrIncompleteWork -> // this is a multi-chapter/incomplete work
        }
        // Use the CSRF token to comment, give kudos, and login.
    }
    is Failure -> ... // continued in Error Handling
}
```

## Error Handling
`AO3Response` allows us to wrap our failures nicely and represent them using 
a finite number of states. This allows us to handle errors in a type-safe 
manner and avoid try-catch hell at the same time.

A sample usage of `Failure` is shown below.

```kotlin
is Failure -> {
    when (response.error) {
        is ConnectionError -> // retry
        is NotFoundError -> // handle 404
        is NotLoggedInError -> // prompt user for login
        ...
        else -> // use some generic error handling strategy
    }
}
```
