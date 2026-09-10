package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.listycity.ui.theme.ListyCityTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

/*
Used assistance from GenAI in order to make the cities selectable, and being able to remove the city
   Prompt : How can I make it such that each city can be selected and after that if I press the delete button it can be removed
   Title: Select City Delete Logic
   Date: 2026-09-10
   AI model: GPT-5.6 sol
*/


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()

        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it)},
                        onDeleteCity = {cityRepository.deleteCity((it))},
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    )
                }
            }
        }
    }
}

class CityRepository {
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin",
        "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"
    )

    val cities: List<String>
        get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }
    fun deleteCity(city: String) {
        _cities.remove(city)
    }
}
@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onDeleteCity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf(value = "") }
    var deleteCity by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(vertical = 35.dp, horizontal = 20.dp)) {
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.padding(all = 4.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Add City")
            }

            Button(
                onClick = {
                    deleteCity?.let {
                        onDeleteCity(it)
                        deleteCity = null
                    }
                }
            ) {
                Text("Remove City")
            }
        }

    }
    LazyColumn(modifier = modifier.padding(vertical = 45.dp)) {
        items(cities) { city ->
            CityRow(
                    city = city,
                    isSelected = city == deleteCity,
                    onClick =
                        { deleteCity = city
                        }
            )

        }
    }


}

@Composable
fun CityRow(city: String,
            isSelected: Boolean,
            onClick: () -> Unit
) {
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .background(
                if (isSelected) {
                    Color.LightGray
                } else {
                    Color.Transparent
                }
            )
            .padding(horizontal = 20.dp, vertical = 14.dp)
    )
}





