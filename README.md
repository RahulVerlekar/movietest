# Movie App

This is the Kotlin Codebase for the movie application.

## Concepts
In this codebase we will use the MVVM architecture and stick as close to possible to the Clean Architecture. SOLID pricicples will be followed and used. 
The idea is that the domain layer will be framework independent and we can take advantage of Kotlin MultiPlatform to use this same code in other platforms.
The data layer also can be made platform independent using Ktor, And instead of hilt we can use Koin for DI. 

## Basic understanding
The Code base will have use cases defined in the domain and the implementation of these will be present in the data layers. We currently have on usecase and 2 implementations for it. 
One implementation is local storage and the other is a network storage, These instances will be used in the domain layer to guide business logic.
We use Hilt for dependency injection also this is Viewmodel heavy codebase where viewmodel will do most work and the UI, Activity fragments will be lite.

For navigation we are using the jetpack navigation library and this will let us abstract out the navigation, We can also write another abstraction on top of this and then use Hilt to provide navigations to viewmodels.
This is essentially useful when navigation decisions come from server or navigation logic is fluid.

