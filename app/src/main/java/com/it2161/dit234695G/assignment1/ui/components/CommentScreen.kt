package com.it2161.dit234695G.assignment1.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit234695G.assignment1.AppBarState
import com.it2161.dit234695G.assignment1.MovieRaterApplication
import com.it2161.dit234695G.assignment1.data.Comments


@Composable
fun CommentMovieScreen(
    modifier: Modifier = Modifier,
    onAppbarChange: (AppBarState) -> Unit,
    navController: NavController = rememberNavController(),
    moviename: String? = null,
    comment: Comments,
){

    val context = LocalContext.current
    val movieList = MovieRaterApplication.instance.data

    val movie = movieList.find { it.title == moviename }

    if (movie != null) {
        onAppbarChange(
            AppBarState(
                title = movie.title,

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("details/${movie.title}") }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        )
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ){
        Row(

        ){
            // User Initials
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = comment.user.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Comment Details
            Column {
                Text(
                    text = "Date${comment.date}",
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Time: ${comment.time}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Full Comment Section
        Text(
            text = "Comments:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = comment.comment,
            fontSize = 16.sp,
            color = Color.Black
        )

    }


}


@Composable
fun AddMovieCommentScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    moviename: String? = null,
    onAppbarChange: (AppBarState) -> Unit
)
{
    val context = LocalContext.current
    val movieList = MovieRaterApplication.instance.data

    val userProfile = MovieRaterApplication.instance.userProfile

    val targetmovie = moviename
    val movie = movieList.find { it.title == targetmovie }

    var commentText by remember { mutableStateOf("") }
    val currentDate = "01-12-2024" // Replace with actual date logic
    val currentTime = "10:00 AM"  // Replace with actual time logic

    var overflowExpanded by remember { mutableStateOf(false) }

    if (movie != null) {
        onAppbarChange(
            AppBarState(
                title = movie.title,

                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("details/${movie.title}") }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        )

        LazyColumn(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

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

            item {
                Text(
                    text = "Add Comments",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = modifier.padding(bottom = 16.dp)
                )

            }
            item {
                Text(
                    text = "Your Name: ${userProfile?.userName}",
                )

                TextField(
                    value = commentText,
                    onValueChange = { commentText = it  },
                    label = { Text("Your Comment") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(8.dp)
                )

            }

            item {
                Button(
                    onClick = {
                        if (commentText.isBlank()) {
                            Toast.makeText(
                                context,
                                "Please fill out both fields.",
                                Toast.LENGTH_SHORT
                            )
                        } else {
                            val newComment = Comments(
                                user = userProfile?.userName ?: "Unknown",
                                comment = commentText,
                                date = currentDate,
                                time = currentTime
                            )


                            // Convert the comment list to a mutable list and add the new comment at the top
                            val updatedComments = movie.comment.toMutableList()
                            updatedComments.add(0, newComment)

                            // Update the movie item with the new comment list
                            movie.comment = updatedComments

                            // Update the data list, triggering save automatically via the setter
                            MovieRaterApplication.instance.data = MovieRaterApplication.instance.data

                            // Navigate to the MovieDetailScreen with the updated movie
                            navController.navigate("details/${movie.title}")

                        }
                    }
                ) {
                    Text(text = "Submit")
                }
            }
        }

    }


}



@Preview
@Composable
fun CommentMovieScreenPreview() {

    CommentMovieScreen(
        modifier = TODO(),
        onAppbarChange = TODO(),
        navController = TODO(),
        moviename = TODO(),
        comment = TODO()
    )
}