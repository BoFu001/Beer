# Beer

This is an Android app written in Kotlin, which displays a list of beers. The user can filter beers brewed in a specific time period. The orientation of the Activity is not blocked.

<br>

#### This app has following packages:
1. **activities**: It contains activities interacting with the user.
2. **adapters**: It contains adapters which provide access to the data items.
3. **extensions**: Utility classes.
4. **models**: It contains model objects.
5. **services**: It contains services and APIs.
6. **viewModels**: It contains all the viewModels.

#### This is a MVVM kotlin project with Jetpack and I used the following features:
1. **Retrofit** to consume REST API. In particular I used Punk API to get all beer information such as name, tagline, imageUrl etc.
2. **ViewModel** to save and manage UI-related data.
3. **LiveData** to create observable objects that respects lifecycle of other app components.
4. **Coroutines** to convert async callbacks for long-running tasks into sequential code.
5. **View Binding** to generate a binding class for each XML layout file.
5. **Espresso** to write concise and reliable Android UI tests.

<p align="center">

  <img src="readme/graph.png">
 
</p>


N.B.: The Crawl graph above shows the test result of Firebase Test Lab which lasts 1 min 36 s with 70 actions.
 
