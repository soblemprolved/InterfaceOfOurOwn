# Initialising the service

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
