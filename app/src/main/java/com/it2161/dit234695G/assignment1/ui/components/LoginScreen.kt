package com.it2161.dit234695G.assignment1.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit234695G.assignment1.AppBarState
import com.it2161.dit234695G.assignment1.MovieRaterApplication
import com.it2161.dit234695G.assignment1.R
import com.it2161.dit234695G.assignment1.data.UserProfile

/*
Login page must allow users to:
    -Enter username
    -Enter password
    -Submit Credentials
    -Allow users to register

 */



@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    onAppbarChange: (AppBarState) -> Unit
) {



    val context = LocalContext.current
    val registeredusers = MovieRaterApplication.instance.userProfile



    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    onAppbarChange(
        AppBarState(

            title = "Login",


            )
    )



    LazyColumn(
        modifier = modifier
            .padding(8.dp, vertical = 0.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        item{        //Profile Picture
            Image(
                painter = painterResource(id = R.drawable.movie_viewer_logo),
                contentDescription = "Profile Picture",
                alignment = Alignment.TopStart,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(328.dp, 328.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item{
            // UserID
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),

                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, contentDescription = "Password")
                },
                trailingIcon = {
                    if (passwordVisibility) {
                        IconButton(onClick = { passwordVisibility = false })
                        {
                            Icon(imageVector = Icons.Filled.Lock, "visible")
                        }
                    } else {
                        IconButton(onClick = { passwordVisibility = true })
                        {
                            Icon(imageVector = Icons.Outlined.Lock, "visible")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Login Button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {

                    var userProfile = UserProfile(
                        userName = username,
                        password = password, email = "",
                        gender = "",
                        mobile = "",
                        updates = false,
                        userImage = R.drawable.avatar_1,

                    )
                    if (registeredusers != null) {
                        if (userProfile.userName == registeredusers.userName && userProfile.password == registeredusers.password) {
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                            navController.navigate("landing")

                        }
                    } else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                    }


                }
            ) {
                Text("Login")
            }

            //Register Button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    navController.navigate("register")
                }
            )
            {
                Text("Register")
            }
        }
    }




}

@Preview
@Composable
fun LoginUIPreview() {


    LoginScreen(
        modifier = Modifier
            .fillMaxWidth(),
        navController = rememberNavController(),
        onAppbarChange = {}
    )

}

