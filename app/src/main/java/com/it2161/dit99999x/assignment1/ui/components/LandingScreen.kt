package com.it2161.dit99999x.assignment1.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.AppBarState
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.data.UserProfile


@Composable
fun LandingScreen(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize(),
    navController: NavController = rememberNavController(),
    onAppbarChange: (AppBarState) -> Unit,

) {

    val context = LocalContext.current
    val movieList = MovieRaterApplication.instance.data

    var overflowExpanded by remember { mutableStateOf(false) }

    onAppbarChange(
        AppBarState(


            title = "PopcornMovie",
            actions = {
                // Overflow Button
                IconButton(
                    onClick = { overflowExpanded = !overflowExpanded },
                    //modifier = Modifier.weight(0.1f)
                ) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Overflow")
                }

                DropdownMenu(
                    expanded = overflowExpanded,
                    onDismissRequest = { overflowExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("View Profile") },
                        onClick = {
                            navController.navigate("profile")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = { navController.navigate("login") }
                    )
                }
            }


        )
    )

    Toast.makeText(context, "Loading Landing Screen", Toast.LENGTH_SHORT).show()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ){

        items(movieList){
                movie ->
            //Movie List Should use ForEach and also include a card for each movie or at least try to, do when awake



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("details/${movie.title}")
                    },


                ){
                val image = try {
                    MovieRaterApplication().getImgVector(movie.image)
                } catch (e: Exception) {
                    Log.e("LandingScreen", "Error loading image for movie: ${movie.title}", e)
                    null
                }


                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "${movie.title} Image",
                        modifier = Modifier
                            .size(100.dp)
                            .aspectRatio(1f)
                            .padding(16.dp)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        text = movie.title,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movie.synopsis,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${movie.ratings_score}",
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }

    }
    Toast.makeText(context, "Finished Landing Screen", Toast.LENGTH_SHORT).show()





}

@Preview
@Composable
fun LandingScreenPreview() {

    LandingScreen(
        modifier = TODO(),
        onAppbarChange = TODO(),
        navController = TODO(),

    )
}




