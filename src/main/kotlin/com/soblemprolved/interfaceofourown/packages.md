# Module InterfaceOfOurOwn
For information about the fields of the various model classes, look at
[com.soblemprolved.interfaceofourown.model].

For information about the return types of the functions in [AO3Service], look at
[com.soblemprolved.interfaceofourown.converters].

For information on the internals of [AO3Response], look at
[com.soblemprolved.interfaceofourown.service].

# Package com.soblemprolved.interfaceofourown.model
Contains all the model classes used in the library.

# Package com.soblemprolved.interfaceofourown.converters
Contains Retrofit converters used by the library.
This is split into two subpackages; one for converters from a raw response body to a model,
and one for converters from a wrapper class to a string.

# Package com.soblemprolved.interfaceofourown.converters.responsebody
Contains Retrofit converters that convert the raw response body to model classes.
This is the most important package.

# Package com.soblemprolved.interfaceofourown.converters.string
Contains Retrofit converters to convert between wrapper classes and strings.

# Package com.soblemprolved.interfaceofourown.service
Contains all classes related to the internal operation of the library.
