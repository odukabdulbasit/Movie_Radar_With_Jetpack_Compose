# Movie_Radar_With_Jetpack_Compose

# Movie Radar App

Movie Radar is a mobile application built using Jetpack Compose and Kotlin. It allows users to discover popular movies and provides detailed information about each movie, along with a unique feature to recommend films by shaking the phone.

## Features

- Display a list of popular movies
- View detailed information about each movie, including the overview
- Shake the phone to get a recommended film
- Integration with [The Movie Database (TMDb) API](https://www.themoviedb.org/)
- Offline support using Room database
- Background tasks using Work Manager
- Utilizes Retrofit for network requests
- JSON parsing with Moshi converter

## Screenshots

Include some visually appealing screenshots of your app to give readers a quick overview of the user interface.

## Getting Started

To get started with the Movie Radar app, follow these steps:

1. Clone the repository:
   ```shell
   git clone https://github.com/odukabdulbasit/Movie_Radar_With_Jetpack_Compose.git
   
2. Obtain an API key from The Movie Database (TMDb). (Instructions on how to obtain an API key can be found in their documentation.)

3. Replace `'YOUR_API_KEY'` with your TMDb API key in the `ApiService.kt` file.

4. Build and run the app on an emulator or a physical device.

## Dependencies

The Movie Radar app relies on the following dependencies:

- Jetpack Compose: A modern UI toolkit for building native Android apps.
- Kotlin: A statically-typed programming language for modern multiplatform applications.
A type-safe HTTP client for Android and Java.
- Room: A SQLite object mapping library for Android.
- Moshi: A modern JSON library for Kotlin and Java.
- Work Manager: An API to schedule deferrable, asynchronous tasks.
- Sensor: Android's built-in sensor framework for detecting shaking motion.

## Contributing

Contributions to the Movie Radar app are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License

The Movie Radar app is released under the [MIT License](https://opensource.org/licenses/MIT).
