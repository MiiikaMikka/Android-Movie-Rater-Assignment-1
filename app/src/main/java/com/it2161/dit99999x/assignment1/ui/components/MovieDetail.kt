package com.it2161.dit99999x.assignment1.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.AppBarState
import com.it2161.dit99999x.assignment1.MovieRaterApplication

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    moviename: String? = null,
    onAppbarChange: (AppBarState) -> Unit
){
    val context = LocalContext.current
    val movieList = MovieRaterApplication.instance.data

    val targetmovie = moviename
    val movie = movieList.find { it.title == targetmovie }

    var overflowExpanded by remember { mutableStateOf(false) }

    onAppbarChange(
        AppBarState(
            title = "${movie?.title}",
            navigationIcon = {
                IconButton(
                    onClick = { navController.navigate("landing") }
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
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
                        text = { Text("Add Comments") },
                        onClick = {
                            navController.navigate("addcomment/${movie?.title}")
                        }
                    )

                }
            }
        )
    )

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Remember to set a custom app bar for this and for everythign else
        if (movie != null)
        {
            //Movie Image
            item {
                val image = try {
                    movie?.let { MovieRaterApplication().getImgVector(it.image) }
                } catch (e: Exception) {
                    if (movie != null) {
                        Log.e("LandingScreen", "Error loading image for movie: ${movie.title}", e)
                    }
                    null
                }


                image?.let {
                    if (movie != null) {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "${movie.title} Image",
                            modifier = Modifier
                                .size(250.dp, 250.dp)
                                .aspectRatio(1f)
                                .padding(16.dp)
                        )
                    }
                }
            }

            //Set Higher and/or bigger padding making the text more centered and away from the sides don't forget to set  the comments and finish up profies

            //Movie Details

            //Title is in present in the App Bar

            //Synopsis
            item {
                Text(
                    text = "${movie?.synopsis}",
                    modifier = Modifier.padding(16.dp)
                )
            }

            //Directors

            item{
                Text(
                    text = "Director: ${movie?.director}",
                    style = typography.bodyMedium
                )
            }

            //Genre
            item{
                Text(
                    text = "Genre: ${movie?.genre}",
                    style = typography.bodyMedium

                )
            }

            //Actors

            item {
                Text(
                    text = "Actors: ${movie?.actors}",
                    style = typography.bodyMedium
                )

            }

            //Length

            item{
                Text(
                    text = "Length: ${movie?.length}",
                    style = typography.bodyMedium
                )

            }


            //Ratings
            item {
                Text(
                    text = "ratings: ${movie?.ratings_score}",
                    style = typography.bodyMedium
                )
            }

            // Comments Section
            if (movie.comment.isNotEmpty()) {
                item {
                    Text(
                        text = "Comments:",
                        style = typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(movie.comment)
                { comment ->

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                navController.navigate("viewComment/${movie.title}/${comment.user}/${comment.comment}/${comment.date}/${comment.time}")
                            },
                    ) {

                        // User Initials (Circle)
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = comment.user.take(2).uppercase(), // Take first two initials
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            // User Name and Duration
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = comment.user,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = comment.time,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Comment Text
                            Text(
                                text = comment.comment,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )


                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else{
                item{
                    Text(
                        text = "No comments available.",
                        style = typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }




        //Comments
        //Use for as many to put in many comments


    }


}

@Preview
@Composable
fun MovieDetailScreenPreview() {


    //MovieDetailScreen()

}