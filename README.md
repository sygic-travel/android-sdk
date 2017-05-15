# Sygic Travel Android SDK


Sygic Travel Android SDK is a framework for embedding a rich set of Sygic Travel data within your application. It gives you an access to millions of Places covering the whole world.

For further details see [Full SDK documentation](http://docs.sygictravelapi.com/android-sdk/master).

## Requirements

- Android SDK 7.1.1 (Nougat, API level 25)
- Build tools 25.0.3
- _API key_ for your business or project

## Deployment

- Target Android SDK version is *Nougat 7.1.1 (API level 25)*
- Minimal supported Android SDK version is *Ice Cream Sandwich 4.0.3 (API level 15)*

## Installation
TODO

## Initialization

*API key* must be provided, otherwise any `StSDK` method call will result in error.

```java
// initialize SDK - in onCreate() method of your Application class or first Activity
// Insert real API key instead of YOUR_API_KEY in the line below. The API key can be
// defined in the strings.xml resorce file.

StSDK.initialize("YOUR_API_KEY", context);
```

## Usage Introduction

This example shows how to use the SDK to fetch a representative set of data.

Let's define a set of Places we want:

- placed in _London_
- marked as _Points of interest_
- marked with category _Sightseeing_
- only the _Top 10_ of them

```java	
// Create query to get Top 10 sightseeings in London
Query query = new Query();
query.setParents("city:1");
query.setLevel("poi");
query.setCategories("sightseeing");
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

## Basic Classes

Class               | Description
:-------------------|:---------------------
**`StSDK`**         | Singleton instance for fetching data
**`Callback<T>`**   | Callback class with `onSuccess(T data)` and `onFailure(Throwable t)` methods. `T` is a generic type.
**`Query`**         | Entity used when querying for `Places`
**`Place`**         | Basic `Place` entity
**`Detail`**        | Detailed object including additional `Place` properties, extends `Place`
**`Medium`**        | Basic `Medium` entity
**`Reference`**     | External `Reference` link
