# Sygic Travel Android SDK


Sygic Travel Android SDK is a framework for embedding a rich set of Sygic Travel data within your
application. It gives you an access to millions of places covering the whole world.

For further details see [Full SDK documentation](http://docs.sygictravelapi.com/android-sdk/1.0.1).

## Requirements

- Android SDK 8.0.0 platform (O, API level 26)
- Build tools 26.0.2
- _API key_ for your business or project

## Deployment

- Target Android SDK version is *O 8.0.0 (API level 26)*
- Minimal supported Android SDK version is *Ice Cream Sandwich 4.0.3 (API level 15)*

## Installation
Add the internet permission to your `AndroidManifest.xml` file:
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### Gradle
Add repository to your project `build.gradle` file:
```gradle
repositories {
	maven {
		url  "http://dl.bintray.com/sygic-travel/maven"
	}
}
```

Add dependency to your application module `build.gradle` file:
```gradle
dependencies {
	compile ('com.sygic.travel:sdk:1.0.1@aar'){
		transitive=true;
	}
}
```
Note that `transitive` is set to `true` - this is necessary since the library has it's own dependencies.

### Download from Github
Download zipped project from our [Github](https://github.com/sygic-travel/android-sdk). The project's
`sdk` directory can be added to your own project as a new module. Then add dependency to your 
application module `build.gradle` file:
```gradle
dependencies {
	compile project(":sdk")
}
```

## Initialization

*API key* must be provided, otherwise every `Sdk` get method call will result in an error.

**Java/Kotlin:**
```java
Sdk sdk = Sdk("YOUR_API_KEY", context);
```
To obtain your *API key* contact us at https://travel.sygic.com/b2b/api-key.

## Usage Introduction

This example shows how to use the SDK to fetch a representative set of data. To define a set of places
you need to create a [placeQuery](http://docs.sygictravelapi.com/android-sdk/1.0.1/com/sygic/travel/sdk/model/placeQuery/Query.html)
which describes the places which will be fetched - see [API documentation](http://docs.sygictravelapi.com/1.0/#section-places).

Let's define a set of places we want:

- placed in _London_
- marked as _Points of interest_
- marked with category _Sightseeing_
- only the _Top 10_ of them

The callback has to implement Callback interface. The demo app contains custom UICallback helper class that will run the callback's methods on UI thread.

**Java:**
```java	
// Create placeQuery to get top 10 sightseeings in London
PlaceQuery placeQuery = new PlaceQuery();
placeQuery.setLevels(Collections.singletonList("poi"));
placeQuery.setCategories(Collections.singletonList("sightseeing"));
placeQuery.setParents(Collections.singletonList("city:1"));
placeQuery.setLimit(10);

// Create Callback
Callback<List<Place>> placesCallback = new UICallback<List<Place>>(this) { // this is activity
	@Override
	public void onUiSuccess(List<Place> places) {
		// success
	}

	@Override
	public void onUiFailure(Throwable exception) {
		// something went wrong
	}
};

// Perform placeQuery
sdk.getPlacesFacade().getPlaces(placeQuery, placesCallback);
```

## Basic Classes
For more details check our [documentation](http://docs.sygictravelapi.com/android-sdk/1.0.1).

Class               | Description
:-------------------|:---------------------
**`Sdk`**         | Singleton instance for fetching data
**`Callback<T>`**   | Callback interface with `onSuccess(T data)` and `onFailure(Throwable t)` methods. `T` is a generic type.
**`PlaceQuery`**    | Entity used when querying for `Places`
**`Place`**         | Basic `Place` entity
**`Detail`**        | Detailed object including additional `Place` properties, extends `Place`
**`Medium`**        | Basic `Medium` entity
**`Reference`**     | External `Reference` link
**`TourQuery`**     | Entity used when querying for `Tours`
**`Tour`**     		| Basic `Tour` entity

## License
This SDK is available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).
