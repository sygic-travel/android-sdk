# Sygic Travel Android SDK

![Minamal API version level 15](https://img.shields.io/badge/minimal_API_level-15-green.svg)

Sygic Travel Android SDK is a framework for embedding a rich set of Sygic Travel data within your
application. It gives you an access to millions of places covering the whole world.


## Installation

Add repository to your project's `build.gradle` file:
```gradle
repositories {
	maven { url "http://dl.bintray.com/sygic-travel/maven" }
}
```

Add dependency to your application module's `build.gradle` file:
```gradle
dependencies {
	implementation ('com.sygic.travel:sdk:1.0.1')
}
```

Development builds may be obtained from [JitPack](https://jitpack.io/#sygic-travel/android-sdk).

## Initialization

Add the internet permission to your `AndroidManifest.xml` file:
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

You have to obtain an *Client Id* and *API key*, contact us at https://travel.sygic.com/b2b/api-key.

```java
import com.sygic.travel.sdk.Sdk;
import com.sygic.travel.sdk.SdkConfig;

Sdk sdk = new Sdk(applicationContext, new SdkConfig() {
    @NotNull @Override public String getClientId() {
        return "your-client-id";
    }

    @NotNull @Override public String getApiKey() {
        return "your-api-key";
    }
});
```

## Available Modules

SDK provide few modules:

- PlacesFacade - places retrieval & searching
- ToursFacade - tours retrieval & searching
- DirectionsFacade - calculating directions between places
- AuthFacade - managing an user session
- FavoritesFacade - user's favorite management
- TripsFacade - user's trips management
- SynchronizationFacade - user's data synchronization

Each module is directly available on the `Sdk` instance.

## Usage Introduction

This example shows how to use the SDK to fetch a representative set of data. To define a set of places
you need to create a PlaceQuery object, which describes fetching conditions.
See [API documentation](http://docs.sygictravelapi.com/1.0/#section-places).

Let's define a set of places:

- placed in _London_
- marked as _Points of interest_
- marked with category _Sightseeing_
- only the _Top 10_ of them

**All SDK calls are synchronous and must be called from a background thread.**

To ease the implementation, you may use RX Java library as follows:

```java
// Create placeQuery to get top 10 sightseeings in London
Single.fromCallable(new Callable<List<PlaceInfo>>() {
    @Override
    public List<PlaceInfo> call() throws Exception {
        PlacesQuery placeQuery = new PlacesQuery();
        placeQuery.setLevels(Collections.singletonList("poi"));
        placeQuery.setCategories(Collections.singletonList("sightseeing"));
        placeQuery.setParents(Collections.singletonList("city:1"));
        placeQuery.setLimit(10);

        return sdk.getPlacesFacade().getPlaces(placeQuery);
    }
})
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Consumer<List<PlaceInfo>>() {
        @Override
        public void accept(List<PlaceInfo> placeInfos) throws Exception {
            // do something with fetched places
        }
    });
```

For further details see [SDK API documentation](http://docs.sygictravelapi.com/android-sdk/1.0.1), [JSON API documentation](http://docs.sygictravelapi.com/).


## License

This SDK is available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).
