# Sygic Travel Android SDK

[![Build Status](https://travis-ci.org/sygic-travel/android-sdk.svg?branch=master)](https://travis-ci.org/sygic-travel/android-sdk)
![Minimal API version level 15](https://img.shields.io/badge/API_level-15-green.svg)

Sygic Travel Android SDK is a framework for embedding a rich set of Sygic Travel data within your
application. It gives you an access to millions of places covering the whole world.


## Installation

Add jCenter repository and dependency to your application's `build.gradle` file:

```gradle
repositories {
	jcenter()
}

dependencies {
	implementation 'com.sygic.travel:sdk:3.0.0'
}
```

Development builds may be obtained from [JitPack](https://jitpack.io/#sygic-travel/android-sdk).

## Available Modules

- PlacesFacade - places retrieval & searching
- ToursFacade - tours retrieval & searching
- DirectionsFacade - calculating directions between places
- AuthFacade - managing an user session
- FavoritesFacade - user's favorite management
- TripsFacade - user's trips management
- SynchronizationFacade - user's data synchronization

## Documentation

- [SDK Wiki documentation](https://github.com/sygic-travel/android-sdk/wiki)
- [SDK API documentation](http://docs.sygictravelapi.com/android-sdk/3.0.0)
- [JSON API documentation](http://docs.sygictravelapi.com/)

## License

This SDK is available under the [MIT License](http://www.opensource.org/licenses/mit-license.php).
