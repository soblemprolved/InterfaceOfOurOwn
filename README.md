# Orpheus

An unofficial API for interfacing with the Archive of Our Own. Written in Kotlin
for the JVM (that is, it can be used on JVM and Android projects, but not
Multiplatform).

This library uses `kotlinx.coroutines` under the hood, and relies on `OkHttp`
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
implementation("com.github.soblemprolved:orpheus:0.2.0-beta")
```

or `build.gradle`:

``` groovy
// build.gradle
implementation 'com.github.soblemprolved:orpheus:0.2.0-beta'
```

## What can it do?
So far, it can be used to retrieve the following:
1. Full works
   `/works/<work ID>`
2. Work summaries associated with a tag
   `/tags/<tag name>/works?<filter arguments>`
3. Autocomplete results for tags (any type) and users
   `/autocomplete/<type>?<search term>`

In the future, the following will be implemented, in no particular order:
1. User login
2. Bookmarks by tag
3. Collections
4. User Profiles (including works and bookmarks)
5. ...and much more!

## Example: Fetching a Work
### Initialising the client
Pass in your existing `OkHttpClient` instance to the client during initialisation.
A new client will be created from it that shares the same resources with the
original client, with all the necessary configurations made.

``` kotlin
// Let this be the OkHttpClient instance used by your application
val appOkHttpClient = OkHttpClient()

// Pass in your existing OkHttpClient instance into AO3Client
val ao3Client = AO3Client(appOkHttpClient)
```

### Creating and sending the request
With the client we created above, we can query the Archive for a response like so:

``` kotlin
// Creates a request for the work at https://archiveofourown.org/works/30267078
val workRequest = WorkRequest.withDefaultConverter(id = 30267078)

// Alternatively, you can specify a custom converter directly in the constructor
val workRequest = WorkRequest(id = 30267078, converter = MyWorkConverter)

// Pass the request to the client for suspending execution
val workResponse: AO3Response<Work> = viewModelScope.launch {
    ao3Client.execute(workRequest)  // network call will be executed on Dispatchers.IO
}

// Alternatively, use the blocking version to run outside of a CoroutineScope
// The network call will also be executed on Dispatchers.IO
val workResponse: AO3Response<Work> = ao3Client.executeBlocking(workRequest)

```
The response of the Archive will be wrapped in `AO3Response` and returned to the caller.

### Handling errors
There are many points at which a request can fail. For example, the user might lose their connection,
the resource might not be found at the existing location, or the user might not have authorisation
to access the resource.

The `AO3Response` wrapper provides a simple way to handle all (reasonable) scenarios that may occur.

``` kotlin
// continued from above
when (workResponse) {
    is Success -> // retrieve the Work here
    is ServerError -> // retrieve the specific error and handle it accordingly
    is NetworkError -> // display an error here
}
```

ServerErrors also contain the specific exception thrown, which can tell you if the
error arose due to things like a lack of authorisation, or if the work does not exist.

``` kotlin
// continued from within the "when" block above
is ServerError -> {
    when (response.error) {
        is NotFoundError -> // recover from a 404 error
        is NotLoggedInError -> // prompt the user to login
        else -> // use some generic error recovery strategy
    }
}
```

## Extensions
This library gives you a lot of flexibility; you are not bound to the types provided
by the library.

### Convert the response to a custom type
Create your own custom converter implementing `Converter<T>`, and pass this to the
request through the constructor.

### Request a resource from a new location
Create your own custom request implementing one of the subtypes of `AO3Request<T>`,
and pass in your custom converter to the request.

### Change the wrapper type
Create your own implementation of `AO3Client`.
