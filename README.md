# Android Common

In this project I keep the list of most libraries and helper classes that I commonly use during the development of my Android apps. This project will be updated constantly as I find better libraries to do the tasks or when I improve code in one of my classes.

## Architecture

This project uses of the [MVVM](https://en.wikipedia.org/wiki/Model–view–viewmodel) pattern and makes de binding between the Views and the ViewModels through reactive programming, with RxKotlin.

## Libraries

I used a set of well-known Android libraries so we don't have to reinvent the wheel 🙃

* __Google Play Services:__ to make use of Google Maps API.
* __OkHttp:__ to help insert headers and cache the REST requests.
* __Retrofit:__ a REST client for Android.
* __RxBinding:__ binding APIs for Android UI widgets.
* __RxKotlin:__ to make use of functional reactive programming.
* __Timber:__ logging tool for Android.

## Classes

* `interface/Callback` - a generic callback interface using class templates.

## Code Style

This project uses [K&R indentation style](https://en.wikipedia.org/wiki/Indentation_style#K.26R). In general terms, each class and function have their opening brace at the next line, while any block inside them have the opening braces at the same line.

## Extracting the keystore signature

The keystore signature is often needed to authenticate the app access to services like Google Maps or Firebase:

### Debug

```
$ keytool -exportcert -list -v -alias androiddebugkey -keystore ~/.android/debug.keystore
```

### Production

```
$ keytool -exportcert -list -v -alias <key-name> -keystore <key-file>
```

## Build

To build the project you need Kotlin. In the project root folder type:

```
./gradlew assemble
```

## Author

Vinicius Egidio ([vinicius.io](http://vinicius.io))
