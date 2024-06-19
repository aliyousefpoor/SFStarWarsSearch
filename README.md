# StarWars Search Application

This Android application interfaces with the Star Wars API (SWAPI) to provide information about characters, planets, species, and films from the Star Wars universe. The app features a search page to look up characters (people) and a detail page to display detailed information about selected characters, including their associated planet, species, and films data.

In project you'll find:
*   User Interface built with **[Jetpack Compose](https://developer.android.com/jetpack/compose)** 
*   A single-activity architecture, using **[Navigation Compose]([https://developer.android.com/jetpack/compose/navigation](https://developer.android.com/jetpack/androidx/releases/hilt))**.
*   A presentation layer that contains a Compose screen (View) and a **ViewModel** per screen.
*   Using **[Flow](https://developer.android.com/kotlin/flow)** and **[coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** for asynchronous operations.
*   Dependency injection using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).
*   Write Test for **SearchViewModel** And **DetailViewModel**


## Screenshots

*Search Page*

<img src="https://github.com/aliyousefpoor/SFStarWarsSearch/blob/develop/app/src/main/res/drawable/search_loading.jpg" width="300" height="500"> <img src="https://github.com/aliyousefpoor/SFStarWarsSearch/blob/develop/app/src/main/res/drawable/search_result.jpg" width="300" height="500">


*Detail Page*

<img src="https://github.com/aliyousefpoor/SFStarWarsSearch/blob/develop/app/src/main/res/drawable/detail_loading.jpg" width="300" height="500"> <img src="https://github.com/aliyousefpoor/SFStarWarsSearch/blob/develop/app/src/main/res/drawable/detail_result1.jpg" width="300" height="500">
<img src="https://github.com/aliyousefpoor/SFStarWarsSearch/blob/develop/app/src/main/res/drawable/detail_result2.jpg" width="300" height="500">



## Architecture

This project is organized following the MVVM architecture pattern with Clean Architecture principles, which divides the code into multiple layers:

### Presentation Layer

- **ViewModels**: Manage UI-related data and handle user interactions. Implements MVVM architecture with Jetpack Compose.
- **Compose UI**: Jetpack Compose is used to build the UI components.

### Domain Layer

- **Use Cases**: Encapsulate business logic and are invoked by the ViewModels.
- **Entities**: Represent the core data structures of the application.

### Data Layer

- **Repositories**: Abstract the data sources and provide data to the use cases.
- **Data Sources**: Include remote  data sources.

## APIs Used

This project integrates with the following endpoints of the Star Wars API (SWAPI):

- **People Search API**: Used for searching characters (people) on the search page.
- **Planet API**: Provides detailed information about planets on the detail page.
- **Species API**: Provides detailed information about species on the detail page.
- **Films API**: Provides detailed information about films on the detail page.

For more information on these APIs, refer to the [SWAPI Documentation](https://swapi.dev/documentation).

## Dependency Injection

The project uses Hilt for dependency injection.

### Installation

1. **Clone the repository**:
    ```bash
    https://github.com/aliyousefpoor/SFStarWarsSearch.git
    ```

2. **Open the project** in Android Studio.

3. **Build and run** the project on an emulator or physical device.

## Usage

### Search Page

- **Functionality**: Allows users to search for items using a search bar.
- **Components**: `SearchViewModel`, `SearchScreen`, and related UI components.

### Detail Page

- **Functionality**: Displays detailed information about a selected item.
- **Components**: `DetailViewModel`, `DetailScreen`, and related UI components.
