# Sygic Travel Android SDK


Sygic Travel Android SDK is a framework for embedding a rich set of Sygic Travel data within your
application. It gives you an access to millions of places covering the whole world.

For further details see [Full SDK documentation](http://docs.sygictravelapi.com/android-sdk/master).

## Requirements

- Android SDK 7.1.1 platform (Nougat, API level 25)
- Build tools 25.0.3
- _API key_ for your business or project

## Deployment

- Target Android SDK version is *Nougat 7.1.1 (API level 25)*
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
	compile ('com.sygic.travel:sdk:0.2.0@aar'){
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

*API key* must be provided, otherwise every `StSDK` get method call will result in an error.

```java
// initialize SDK - in onCreate() method of your Application class or a launcher Activity
// Insert real API key instead of YOUR_API_KEY in the line below. The API key can be
// defined in the strings.xml resource file.

StSDK.initialize("YOUR_API_KEY", context);
```
To obtain your *API key* contact us at https://travel.sygic.com/b2b/api-key.

## Usage Introduction

This example shows how to use the SDK to fetch a representative set of data. To define a set of places
you need to create a [Query](http://docs.sygictravelapi.com/android-sdk/0.2.0/com/sygic/travel/sdk/model/query/Query.html)
which describes the places which will be fetched - see
[API documentation](http://docs.sygictravelapi.com/0.2/#section-places).

Let's define a set of places we want:

- placed in _London_
- marked as _Points of interest_
- marked with category _Sightseeing_
- only the _Top 10_ of them

```java	
// Create query to get top 10 sightseeings in London
Query query = new Query();
query.setLevels(Collections.singletonList("poi"));
query.setCategories(Collections.singletonList("sightseeing"));
query.setParents(Collections.singletonList("city:1"));
query.setLimit(10);
	
// Create Callback
Callback<List<Place>> placesCallback = new Callback<List<Place>>() {
	@Override
	public void onSuccess(List<Place> places) {
		// success
	}

	@Override
	public void onFailure(Throwable t) {
		// something went wrong
	}
};

// Perform query
StSDK.getInstance().getPlaces(query, placesCallback);
```

Since the SDK uses RxAndroid it is important to unsubscribe an observable, when an activity comes 
to background:
```java
@Override
protected void onPause() {
	super.onPause();

	// Observables need to be unsubscribed, when the activity comes to background
	StSDK.getInstance().unsubscribeObservable();
}
```

## Basic Classes
For more details check our [documentation](http://docs.sygictravelapi.com/android-sdk/0.2.0).

Class               | Description
:-------------------|:---------------------
**`StSDK`**         | Singleton instance for fetching data
**`Callback<T>`**   | Callback class with `onSuccess(T data)` and `onFailure(Throwable t)` methods. `T` is a generic type.
**`Query`**         | Entity used when querying for `Places`
**`Place`**         | Basic `Place` entity
**`Detail`**        | Detailed object including additional `Place` properties, extends `Place`
**`Medium`**        | Basic `Medium` entity
**`Reference`**     | External `Reference` link

## License
This SDK is available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).