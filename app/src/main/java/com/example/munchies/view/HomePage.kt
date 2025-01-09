package com.example.munchies.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.munchies.viewmodel.HomeViewModel
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.lazy.items
import com.example.munchies.api.RestaurantData
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.example.munchies.api.FilterData

//The HomePage composable receives the HomeViewModel as a parameter. This implies that the ViewModel is created and managed outside of the HomePage(MainActivity)
//if the project was bigger it would use DI like Hilt
@Composable
fun HomePage(modifier: Modifier = Modifier, viewModel: HomeViewModel, navController: NavController) {
    // Trigger data fetching of restaurant-list and filter-list when the composable is first displayed
    //LaunchedEffect(Unit) same as useEffect with empty dependency array
    LaunchedEffect(Unit) {
        viewModel.getRestaurantData()
    }

    // Observe the state of RestaurantData, FilterData and loading and error states
    val restaurantData = viewModel.filteredRestaurants.observeAsState(emptyList())
    val filters = viewModel.filters.observeAsState(emptyList())


    val isLoading = viewModel.isLoading.observeAsState(false)
    val error = viewModel.error.observeAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with Logo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // Replace with logo
            Text(text = "Logo")
        }

        // Horizontal Scroll List for the filters
        if (filters.value.isNotEmpty()) {
            FilterList(filters = filters.value, onFilterClick = { filterId ->
                viewModel.filterRestaurantsByFilterId(filterId)
            })
        } else {
            Log.d("HomePage", "No filters to display")
        }



        // Vertical Scrollable List for the Restaurant Cards
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            error.value?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            } ?: run {
                if (restaurantData.value.isEmpty()) {
                    Text(text = "No restaurants found")
                } else {
                    RestaurantList(restaurantData = restaurantData.value, navController = navController)

                }
            }
        }
    }
}

@Composable
fun FilterList(filters: List<FilterData>, onFilterClick: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            FilterItem(filter, onFilterClick)
        }
    }
}

@Composable
fun FilterItem(filter: FilterData, onFilterClick: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onFilterClick(filter.id) },

    ) {
        AsyncImage(
            model = filter.imageUrl,
            contentDescription = filter.name,
            modifier = Modifier.size(48.dp)
        )
        Text(text = filter.name, style = MaterialTheme.typography.bodySmall)
    }
}



@Composable
fun RestaurantList(restaurantData: List<RestaurantData>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(restaurantData) { restaurant ->
            RestaurantCard(
                name = restaurant.name,
                rating = restaurant.rating,
                deliveryTime = restaurant.deliveryTimeMinutes,
                imageUrl = restaurant.imageUrl,
                restaurantId = restaurant.id,
                navController = navController
            )
        }
    }
}

@Composable
fun RestaurantCard(name: String, rating: Double, deliveryTime: Int, imageUrl: String, restaurantId: String, navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable{ navController.navigate("detail/$restaurantId") },
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                contentScale = ContentScale.FillWidth,
                model = imageUrl,
                contentDescription = "Restaurant Image",
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TitleValueText(title = "Name: ", value = name)
                TitleValueText(title = "Rating: ", value = rating.toString())
                TitleValueText(title = "Delivery Time: ", value = "$deliveryTime min")
            }
        }

    }
}

@Composable
fun TitleValueText(title: String, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(title)
            }
            append(value)
        },
        style = MaterialTheme.typography.bodyLarge
    )
}