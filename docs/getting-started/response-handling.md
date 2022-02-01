# Response Handling

The results of all methods in `AO3Service` are wrapped in an `AO3Response`,
before being returned to the caller. This is done to make error handling
easier, as we can handle them programmatically using `when` statements
instead of try-catch statements, which improves readability in your code.
An `AO3Response` can either be a `Success` or a `Failure`.

## Success

A `Success` represents a response from the server that successfully fulfills
the request. It holds the result in its `value` field.

We will examine how to process a `Success` below when retrieving a `Work` from
the Archive, for example. Other operations are also processed in a similar
manner, albeit with different destructuring declarations based on the return
type of the operation.

First, we make the network call to the Archive.

```kotlin
val workResponse: AO3Response<WorkConverter.Result> = viewModelScope.launch { service.getWork(id = 12345) }
```

Next, we will check if the response is a `Success`, and specify programme
behaviour to handle the result in the event of a `Success`.

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

## Failure

A `Failure` is returned when the operation is unsuccessful (i.e. the request
cannot be fulfilled for some reason). It encapsulates the error encountered
in its `error` field as one of the subtypes of `AO3Error`.

We show an example of how to process a `Failure` in the context of retrieving
a `Work` from the Archive below. The process is the same for other operations,
albeit with different types of errors for different operations.

```kotlin
// continued from above
is Failure -> {
    when (response.error) {
        is ConnectionError -> // retry
        is NotFoundError -> // handle 404
        is NotLoggedInError -> // prompt user for login
        // etc...
        else -> // use some generic error handling strategy
    }
}
```
