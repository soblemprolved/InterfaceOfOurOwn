# Orpheus

An unofficial API for interfacing with the Archive of Our Own. Written in Kotlin for the JVM (that is, it can be used on
JVM/Android projects, but not Multiplatform).

Unfortunately, I cannot support Multiplatform at the moment as there is no html parsing library available
for Kotlin Native.

This library relies on `OkHttp` for networking.

## Example: Fetching a Work
### Initialising the client
Pass in your existing `OkHttpClient` instance to the client during initialisation.
A new client will be created from it that shares the same resources with the
original client.

```kotlin
// Let this be the OkHttpClient instance used by your application
val appOkHttpClient = OkHttpClient()

// Pass in your existing OkHttpClient instance into AO3Client
val ao3Client = AO3Client(appOkHttpClient)
```

### Creating and sending the request
With the client we created above, we can query the Archive for a response like so:

```kotlin
// Creates a request for the work located at https://archiveofourown.org/works/30267078
val workRequest = WorkRequest.from(30267078)

// Alternatively, you can specify a custom converter directly in the constructor
val workRequest = WorkRequest(id = 30267078, converter = WorkConverter)

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
There are many points at which a request can fail: the user might lose their connection,
the resource might not be found at the existing location, the user might not have authorisation
to access the resource, and so on and so forth.

The `AO3Response` wrapper provides a simple way to handle all (reasonable) scenarios that may occur.

```kotlin
when (workResponse) {
    is Success -> // retrieve the Work here
    is ServerError -> // retrieve the specific error and handle it accordingly
    is NetworkError -> // display an error here
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

### Use <other networking library> instead of OkHttp
Unfortunately, requests rely on `OkHttpClient` constructs as they are vastly superior 
to rolling my own. I can decouple them, but this will take some time.
