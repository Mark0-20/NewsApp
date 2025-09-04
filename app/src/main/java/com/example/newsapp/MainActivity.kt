package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.*
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewsScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun NewsScreen() {

    var selectedTab by remember { mutableStateOf("Noticias") }
    var searchText by remember { mutableStateOf("") }

    val latestNews = listOf(
        "El presidente de EE.UU. no muestra signos de arrepentimiento...",
        "Bañarse en la piscina del desierto de Cleopatra"
    )

    val worldNews = listOf(
        "Gigantes tecnológicos",
        "El rover de Marte envía nuevas imágenes",
        "Cumbre mundial de medio ambiente",
        "Nuevo avance en inteligencia artificial"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Search bar con icono “home + add”
        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = {
                    // Combina home + add en un mismo espacio
                    Box {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Agregar",
                            modifier = Modifier
                                .size(12.dp)
                                .offset(x = 12.dp, y = (-4).dp)
                        )
                    }
                },
                placeholder = { Text("Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(24.dp)),
                singleLine = true
            )
        }

        // Tabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Noticias", "Eventos", "Clima").forEach { tab ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextButton(onClick = { selectedTab = tab }) {
                            Text(
                                text = tab,
                                fontSize = 18.sp,
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTab == tab) Color.Black else Color.LightGray
                            )
                        }

                        if (selectedTab == tab) {
                            Box(
                                modifier = Modifier
                                    .height(4.dp)
                                    .width(40.dp)
                                    .background(Purple, shape = RoundedCornerShape(2.dp))
                            )
                        } else {
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

        when (selectedTab) {
            "Noticias" -> {

                // Últimas noticias
                item { Text("Últimas noticias", fontWeight = FontWeight.Bold, fontSize = 22.sp) }

                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(latestNews.filter { it.contains(searchText, ignoreCase = true) || searchText.isEmpty() }) { news ->
                            Card(
                                modifier = Modifier.size(width = 230.dp, height = 140.dp),
                                colors = CardDefaults.cardColors(containerColor = Purple),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = news,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        maxLines = 3
                                    )
                                    Text(
                                        text = "febrero 08 – 2024",
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Alrededor del mundo
                item { Text("Alrededor del mundo", fontWeight = FontWeight.Bold, fontSize = 22.sp) }

                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .height(400.dp)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(worldNews.filter { it.contains(searchText, ignoreCase = true) || searchText.isEmpty() }) { news ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    contentAlignment = Alignment.BottomStart
                                ) {
                                    Text(
                                        text = news,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
        NewsScreen()
    }
}