@file:OptIn(ExperimentalMaterial3Api::class)

package com.it2161.dit99999x.assignment1.ui.components


import android.widget.RadioGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock

import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit99999x.assignment1.AppBarState
import com.it2161.dit99999x.assignment1.MovieRaterApplication
import com.it2161.dit99999x.assignment1.data.UserProfile

import java.time.Year



/*
Registration Input must allow user to
o user name
o password
o confirm password
o email
o gender
o mobile number
o to receive updates via email
o year of birth
o submit registration
o cancel registration
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 0.dp),
    navController: NavController = rememberNavController(),
    onAppbarChange: (AppBarState) -> Unit
) {

    var context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }

    var selectedgender by remember { mutableStateOf("") }
    var listofgenders = listOf("Male", "Female", "Non-Binary", "Prefer not to say")
    //var genderExpanded by remember { mutableStateOf(false) }

    var mobilenumber by remember { mutableStateOf("") }

    var receiveupdates by remember { mutableStateOf(false) }

    var currentYear = Year.now().value
    var years = (1920..currentYear).toList().reversed() // Create a list of years
    var yearofbirth by remember { mutableStateOf("") }
    var yearExpanded by remember { mutableStateOf(false) }

    var errorMessage = ""


    onAppbarChange(
        AppBarState(

            title = "Register",
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.navigate("login")
                    }
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },

            )
    )

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 0.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{        //username
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("UserID") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 45.dp,
                        vertical = 4.dp
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

        }

        item{
            //password
            //add Visual transformation for password
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 45.dp,
                        vertical = 4.dp
                    ),
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
                            Icon(imageVector = Icons.Filled.Lock, "visible")
                        }
                    }
                }

            )
            Spacer(modifier = Modifier.height(8.dp))

            //Confirm Password
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 45.dp,
                        vertical = 4.dp
                    ),
                value = confirmpassword,
                onValueChange = { confirmpassword = it },
                label = { Text("Confirm Password") },
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
                            Icon(imageVector = Icons.Filled.Lock, "visible")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item{
            //Email
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 45.dp,
                        vertical = 4.dp
                    ),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        //Gender
        item{
            listofgenders.forEach { gender ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 45.dp,
                            vertical = 4.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Left

                ) {

                    RadioButton(
                        selected = (gender == selectedgender),
                        onClick = { selectedgender = gender },

                        )
                    Text(
                        text = gender,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                }

            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item{        //Mobile Number
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 45.dp,
                        vertical = 4.dp
                    ),
                value = mobilenumber,
                onValueChange = { mobilenumber = it },
                label = { Text("Enter your number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }



        item{
            //Dropdown for Year of Birth
            ExposedDropdownMenuBox(
                modifier = Modifier,
                expanded = yearExpanded,
                onExpandedChange = { yearExpanded = !yearExpanded }
            ) {
                TextField(
                    readOnly = true,
                    value = yearofbirth,
                    onValueChange = {},
                    label = { Text("Select an option") },
                    trailingIcon = {
                        TrailingIcon(
                            expanded = yearExpanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(
                            horizontal = 45.dp,
                            vertical = 4.dp
                        )

                )
                ExposedDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    expanded = yearExpanded,
                    onDismissRequest = { yearExpanded = false }

                )
                {
                    years.forEach { selected ->
                        DropdownMenuItem(
                            onClick = {
                                yearofbirth = selected.toString()
                                yearExpanded = false
                            },
                            text = { Text(selected.toString()) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        item{//for recieving email updates
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 45.dp,
                        vertical = 4.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Left

            ) {
                Checkbox(
                    checked = receiveupdates,
                    onCheckedChange = { receiveupdates = it }
                )
                Text("Receive email updates")
            }

        }

        item {
            Text(errorMessage)
        }




        item{
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 45.dp,
                        vertical = 4.dp
                    ),
                onClick = {

                    if (!password.isEmpty()) {
                        if (password == confirmpassword) {
                            if (validateEmail(email)) {
                                val userProfile = UserProfile(
                                    userName = username,
                                    password = password,
                                    email = email,
                                    gender = selectedgender,
                                    mobile = mobilenumber,
                                    updates = receiveupdates,
                                    yob = yearofbirth
                                )

                                RegisterSucssful(userProfile, navController)

                            } else {
                                Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
                                errorMessage = "Invalid Email"
                            }
                        } else {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                            errorMessage = "Passwords do not match"
                        }
                    } else {
                        Toast.makeText(context, "Passwords field is empty", Toast.LENGTH_SHORT).show()
                        errorMessage = "Passwords field is empty"
                    }

                    //check for any other validation necessary
                }

            ) {
                Text("Register")
            }
        }
    }
}

fun validateEmail(email: String): Boolean {
    val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    return email.matches(emailRegex)
}

fun RegisterSucssful(userProfile: UserProfile, navController: NavController)
{
    MovieRaterApplication.instance.userProfile = userProfile
    MovieRaterApplication.instance.saveProfileToFile(MovieRaterApplication.instance.applicationContext)

    navController.navigate("login")
}


@Preview
@Composable
fun RegisterUserScreenPreview() {
    RegisterUserScreen(
        modifier = Modifier,
        onAppbarChange = {}
    )

}