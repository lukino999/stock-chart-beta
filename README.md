# Stock Chart

I built this application as an exercise to implement what I learned about Android development.
I implemented a MVVM pattern using LiveData, the ViewModel class and the Room library.

The application displays the historical prices of a given company as a candlestick chart.

On launch we are presented with a scrollable list of companies which can be filtered by company name or ticker symbol.
Tapping on a company tile to retrieve its candlestick chart. The chart can be scrolled and zoomed in both X and Y axes.

The data is provided by the [IEX Cloud REST API](https://iexcloud.io/docs/api/) and the calls to the API are performed by the [Retrofit](https://square.github.io/retrofit/) library.

As chart library I used [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) available on github.

The list of available companies is kept on the device on a SQLite database and it is abstracted through the [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room)

The UI accesses the list data through a [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel) class which is lifecycle aware and allows data to survive configuration changes.

The list is displayed by a custom [Recycler View](https://developer.android.com/reference/android/support/v7/widget/RecyclerView)
