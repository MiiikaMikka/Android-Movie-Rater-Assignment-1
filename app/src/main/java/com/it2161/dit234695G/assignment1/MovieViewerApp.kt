package com.it2161.dit234695G.assignment1

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.it2161.dit234695G.assignment1.data.Comments
import com.it2161.dit234695G.assignment1.ui.components.AddMovieCommentScreen

import com.it2161.dit234695G.assignment1.ui.components.CommentMovieScreen
import com.it2161.dit234695G.assignment1.ui.components.EditProfileScreen
import com.it2161.dit234695G.assignment1.ui.components.LandingScreen
import com.it2161.dit234695G.assignment1.ui.components.LoginScreen
import com.it2161.dit234695G.assignment1.ui.components.MovieDetailScreen
import com.it2161.dit234695G.assignment1.ui.components.ProfileScreen
import com.it2161.dit234695G.assignment1.ui.components.RegisterUserScreen

data class AppBarState(
    var title: String = "",
    var navigationIcon: @Composable () -> Unit = {},
    var actions: @Composable RowScope.() -> Unit = {},
    val modifier: Modifier = Modifier
)


enum class MovieScreen() {
    Login,
    Register,
    Landing,
    Details,
    Comments,
    AddComents,
    Profile,
    EditProfile
}



@Composable
fun MovieViewerApp(
    navController: NavHostController = rememberNavController()
){

    var stateofAppbar by remember { mutableStateOf(AppBarState())}
    var currentscreen by remember { mutableStateOf(MovieScreen.Login.name) }

    var context = LocalContext.current

    Scaffold(
        topBar = {
            BaseAppBar(stateofAppbar)
        }

    ) { innerPadding ->
        //Try to put app bar here


        var modifier = Modifier
            //.fillMaxSize()
            .padding(innerPadding)


        Log.d("App data : ", "" + MovieRaterApplication.instance.data.size)
        if (MovieRaterApplication.instance.userProfile != null) {
            Log.d("User profile : ", "" + MovieRaterApplication.instance.userProfile!!.userName)
        } else {
            Log.d("User profile : ", "No user profile saved")
        }

        navController.addOnDestinationChangedListener { navController: NavController,
                                                        navDestination: NavDestination,
                                                        bundle: Bundle? ->

            currentscreen = navDestination.route ?: MovieScreen.Login.name

        }


        NavHost(
            navController = navController,
            startDestination = MovieScreen.Login.name,
            modifier = modifier
        ) {

            //Login Screen
            composable(route = "login") {
                LoginScreen(
                    modifier = modifier
                        .fillMaxWidth(),
                    navController = navController,
                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState }
                )
            }

            //Register Screen
            composable(route = "register")
            {
                RegisterUserScreen(
                    modifier = modifier,
                    navController = navController,
                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState }
                )
            }

            //Landing Screen
            composable(
                route = "landing",

            ) {
                LandingScreen(
                    modifier = modifier,
                    navController = navController,

                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState }
                )
            }
            //Profile Screen
            composable(
                route = "profile",

            ) {
                ProfileScreen(
                    modifier = modifier,
                    navController = navController,

                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState }

                )
            }

            //Edit Profile Screen
            composable(
                route = "editprofile",

            ) {
                EditProfileScreen(
                    modifier = modifier,
                    navController = navController,

                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState }

                )
            }


            //Movie Details Screen
            composable(
                route = "details/{moviename}",
                arguments = listOf(navArgument("moviename") { type = NavType.StringType }) // Define argument type
            ) { backStackEntry ->
                val moviename = backStackEntry.arguments?.getString("moviename") // Retrieve the passed argument

                MovieDetailScreen(
                    modifier = modifier,
                    navController = navController,
                    moviename = moviename, // Pass the argument to the composable
                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState }
                )
            }


            //Add Comments
            composable(route = "addcomment/{moviename}",
                arguments = listOf(navArgument("moviename") { type = NavType.StringType }) // Define argument type
            ) { backStackEntry ->
                val moviename =
                    backStackEntry.arguments?.getString("moviename") // Retrieve the passed argument

                AddMovieCommentScreen(
                    modifier = modifier,
                    navController = navController,
                    moviename = moviename, // Pass the argument to the composable
                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState }
                )
            }

            //View Comment / higher detail of comment
            composable(
                "viewComment/{movieName}/{user}/{comment}/{date}/{time}",
                arguments = listOf(
                    navArgument("movieName") { type = NavType.StringType },
                    navArgument("user") { type = NavType.StringType },
                    navArgument("comment") { type = NavType.StringType },
                    navArgument("date") { type = NavType.StringType },
                    navArgument("time") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val movieName = backStackEntry.arguments?.getString("movieName") ?: ""
                val user = backStackEntry.arguments?.getString("user") ?: ""
                val commentText = backStackEntry.arguments?.getString("comment") ?: ""
                val date = backStackEntry.arguments?.getString("date") ?: ""
                val time = backStackEntry.arguments?.getString("time") ?: ""

                CommentMovieScreen(
                    modifier = modifier,
                    onAppbarChange = { newAppBarState -> stateofAppbar = newAppBarState },
                    navController = navController,
                    moviename = movieName,
                    comment = Comments(user, commentText, date, time)
                )
            }


            //Comments Screen

            //Add Comments Screen
//            composable(
//                route = "add_comment/{movieName}",
//                arguments = listOf(navArgument("movieName") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val movieName = backStackEntry.arguments?.getString("movieName") ?: ""
//
//                AddCommentScreen(
//                    modifier = modifier,
//                    movieName = movieName,
//                    onCommentAdded = { newComment ->
//                        val movieRaterApplication = MovieRaterApplication.instance
//                        val movie = movieRaterApplication.findMovieByName(movieName)
//                        movie?.let {
//                            it.comment += newComment // Add the new comment
//                            movieRaterApplication.saveListFile(LocalContext.current) // Save updated list
//                        }
//                    }
//                )
//            }

        }
    }
}











@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseAppBar(
    appbarstate: AppBarState

) {

    var overflowExpanded by remember { mutableStateOf(false) }


    CenterAlignedTopAppBar(

        title = { Text(appbarstate.title) },
        modifier = appbarstate.modifier,
        navigationIcon = appbarstate.navigationIcon,
        actions = appbarstate.actions,

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
    )


}

/*

    //Upper App bar
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary) // Optional: Background color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp), // Adjust vertical padding for desired height
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back Button

            /*
            IconButton(
                onClick = { BackonClick() },
                modifier = Modifier.weight(0.1f)
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
             */
            appbarstate.navigationIcon

            // Title
            Text(
                text = appbarstate.title,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge, // Adjust typography style
                color = MaterialTheme.colorScheme.onPrimary // Optional: Content color
            )

            appbarstate.actions

            /*
            // Overflow Button
            IconButton(
                onClick = { overflowExpanded = !overflowExpanded },
                modifier = Modifier.weight(0.1f)
            ) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Overflow")
            }

            DropdownMenu(
                expanded = overflowExpanded,
                onDismissRequest = { overflowExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Save") },
                    onClick = { /* Handle Save action */ }
                )
            }
             */



        }
    }

*/

//    TopAppBar(
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary,
//        ),
//        title = { Text(appbarstate.title)},
//        modifier = appbarstate.modifier,
//        navigationIcon = appbarstate.showBackButton,
//        actions = appbarstate.showMenu
//    )





@Preview
@Composable
fun PreviewMainApp()
{
    MovieViewerApp()
}



