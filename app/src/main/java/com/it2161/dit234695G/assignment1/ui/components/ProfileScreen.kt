package com.it2161.dit234695G.assignment1.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.it2161.dit234695G.assignment1.AppBarState
import com.it2161.dit234695G.assignment1.MovieRaterApplication
import com.it2161.dit234695G.assignment1.R
import com.it2161.dit234695G.assignment1.data.UserProfile
import java.time.Year

/*
Profile Screen should include:
Title - "Profile" at centered position at the top
Image Centered between Title and User details
User Details:


 */


@Composable
fun ProfileScreen(

    modifier: Modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize(),
    navController: NavController = rememberNavController(),
    onAppbarChange: (AppBarState) -> Unit,

){

    var context = LocalContext.current


    var userProfile = MovieRaterApplication.instance.userProfile



    //just so there isn't any error
    if (userProfile != null)
    {
        Toast.makeText(context, "Profile Loaded, Showing ${userProfile.userName}", Toast.LENGTH_SHORT).show()


    }
    else
    {
        Toast.makeText(context, "Profile not found", Toast.LENGTH_SHORT).show()
    }

    var passwordVisibility by remember { mutableStateOf(false ) }

    var overflowExpanded by remember { mutableStateOf(false) }

    fun BackonClick()
    {
        navController.navigate("landing")
    }

    onAppbarChange(
        AppBarState(
            title = "Profile",

            navigationIcon = {
                IconButton(
                    onClick = { BackonClick() }
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
                        text = { Text("Edit") },
                        onClick = {
                            navController.navigate("editprofile")
                        }
                    )
                }
            }

        )
    )

    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (userProfile != null) {

            item{
                //Profile Picture


                Image(
                    painter = painterResource(userProfile.userImage),
                    contentDescription = "Profile Picture",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier //Remember to add Modifier
                )


            }
            item{

                Column {

                }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ){
                    //Display Username
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        text = "Username: \n ${userProfile.userName}",
                        textAlign = TextAlign.Left
                    )

                    //Display Password?

                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        text = "Password: "

                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Left
                    ) {

                        // Basic Text Field for Password Input
                        BasicTextField(
                            readOnly = true,
                            value = userProfile!!.password,
                            onValueChange = {},
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f) // Take up remaining space
                                .padding(vertical = 8.dp)
                        )

                        // Trailing Icon for Password Visibility Toggle
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Filled.Lock else Icons.Outlined.Lock,
                                contentDescription = "Toggle Password Visibility"
                            )
                        }
                    }


                    //Display Email
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        text = "Email: \n ${userProfile.email}",
                        textAlign = TextAlign.Left
                    )

                    //Display Gender
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        text = "Gender: \n ${userProfile.gender}",
                        textAlign = TextAlign.Left
                    )

                    //Display Mobile Number
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        text = "Mobile Number: \n ${userProfile.mobile}",
                        textAlign = TextAlign.Left
                    )

                    //Display Receive Updates
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        text = "To Receive Email Updates: \n ${userProfile.updates.toString()}",
                        textAlign = TextAlign.Left
                    )

                    //Display Year of Birth
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        text = "Year of Birth: \n ${userProfile.yob}",
                        textAlign = TextAlign.Left
                    )
                }
            }
        }


        //Try using elevated card for USer details
        /*
        Should include the following:
        1. Username
        2. Password?
        3. Email
        4. Gender
        5. Mobile Number
        6. Year of Birth
        7. Receive Updates?
         */


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(

    modifier: Modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize(),
    navController: NavController = rememberNavController(),
    onAppbarChange: (AppBarState) -> Unit,

)
{
    var userProfile = MovieRaterApplication.instance.userProfile

    var passwordVisibility by remember { mutableStateOf(false ) }



    var userName = userProfile?.userName
    var password = userProfile?.password
    var email = userProfile?.email

    var userImage = userProfile?.userImage
    val listofimages = listOf(R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3)

    var currentgender = userProfile?.gender
    var listofgenders = listOf("Male", "Female", "Non-Binary", "Prefer not to say")


    //var genderExpanded by remember { mutableStateOf(false) }

    var receiveupdates = userProfile?.updates

    var mobile = userProfile?.mobile

    var currentYear = Year.now().value
    var years = (1920..currentYear).toList().reversed() // Create a list of years
    //var yearofbirth by remember { mutableStateOf("") }
    var yearExpanded by remember { mutableStateOf(false) }

    var yob = userProfile?.yob

    var overflowExpanded by remember { mutableStateOf(false) }

    onAppbarChange(
        AppBarState(
            title = "Edit Profile",
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.navigate("profile")
                    }
                ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                // Overflow Button
                IconButton(
                    onClick = {
                        userProfile = receiveupdates?.let {

                                UserProfile(

                                    userImage = userImage?: 2130968577,

                                    gender = currentgender ?: "",
                                    userName = userName ?: "",
                                    password = password ?: "",
                                    email = email ?: "",

                                    mobile = mobile ?: "",
                                    updates = it,
                                    yob = yob ?: ""
                                )

                        }

                        MovieRaterApplication.instance.userProfile = userProfile
                        MovieRaterApplication.instance.saveProfileToFile(MovieRaterApplication.instance.applicationContext)

                        navController.navigate("profile")

                    },
                    //modifier = Modifier.weight(0.1f)
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "Overflow")
                }
            }
        )
    )



    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item{
            if (userImage != null) {
                Image(
                    painter = painterResource(userImage!!),
                    contentDescription = "Profile Picture",
                    alignment = Alignment.TopStart,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(328.dp, 328.dp)
                )
            }

            var imageAlreadySelected by remember { mutableStateOf(true) }

            listofimages.forEach{image ->
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
                    if (userImage == image) {
                        imageAlreadySelected = false
                    } else {
                        imageAlreadySelected = true
                    }

                    RadioButton(

                        enabled = imageAlreadySelected,

                        selected = (image == userImage),
                        onClick = { userImage = image },

                        )
                    Text(
                        text = image.toString(),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

        }




        item{
            if (userProfile != null) {
                //Edit Username

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 45.dp,
                            vertical = 4.dp
                        ),
                    value = userName ?: "",
                    onValueChange = { userName = it },
                    label = { Text("Username") },

                    )

                //Edit Password
                password?.let {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 45.dp,
                                vertical = 4.dp
                            ),
                        value = it,
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
                }

                //Edit Email

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 45.dp,
                            vertical = 4.dp
                        ),
                    value = email ?: "",
                    onValueChange = { email = it },
                    label = { Text("Email") }

                )

                //Edit Gender

                var enabled by remember { mutableStateOf(true) }

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
                        if (currentgender == gender) {
                            enabled = false
                        } else {
                            enabled = true
                        }

                        RadioButton(

                            enabled = enabled,

                            selected = (gender == currentgender),
                            onClick = { currentgender = gender },

                            )
                        Text(
                            text = gender,
                            modifier = Modifier.padding(start = 4.dp)
                        )

                    }

                }

                //Edit Mobile Number

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 45.dp,
                            vertical = 4.dp
                        ),
                    value = mobile ?: "",
                    onValueChange = { mobile = it },
                    label = { Text("Mobile Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                //Year of Birth

                //Dropdown for Year of Birth
                ExposedDropdownMenuBox(
                    modifier = Modifier,
                    expanded = yearExpanded,
                    onExpandedChange = { yearExpanded = !yearExpanded }
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(
                                horizontal = 45.dp,
                                vertical = 4.dp
                            ),
                        readOnly = true,
                        value = yob ?: "",
                        onValueChange = {},
                        label = { Text("Select an option") },
                        trailingIcon = {
                            TrailingIcon(
                                expanded = yearExpanded
                            )
                        },


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
                                    yob = selected.toString()
                                    yearExpanded = false
                                },
                                text = { Text(selected.toString()) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))



                //Receive Updates

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
                    receiveupdates?.let {
                        Checkbox(
                            checked = it,
                            onCheckedChange = { receiveupdates = it }
                        )
                    }
                    Text("Receive email updates")
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 45.dp,
                            vertical = 4.dp
                        ),
                    onClick = {
                        userProfile = receiveupdates?.let {

                                UserProfile(

                                    userImage = userImage?: 2130968577,

                                    gender = currentgender ?: "",
                                    userName = userName ?: "",
                                    password = password ?: "",
                                    email = email ?: "",

                                    mobile = mobile ?: "",
                                    updates = it,
                                    yob = yob ?: ""
                                )

                        }
                        MovieRaterApplication.instance.userProfile = userProfile
                        MovieRaterApplication.instance.saveProfileToFile(MovieRaterApplication.instance.applicationContext)

                        navController.navigate("profile")
                    }
                )
                {
                    Text("Save")
                }


            }
        }

        //Username



        //Password


        /*
        Edit Section here so that users can edit their details,
        Either Placeholder or values should be tha same value, can be both
         */

        //Try using elevated card for USer details
        /*
        Should include the following:
        1. Username
        2. Password?
        3. Email
        4. Gender
        5. Mobile Number
        6. Year of Birth
        7. Receive Updates?
         */


    }

}


@Preview
@Composable
fun ProfileScreenPreview() {


//    ProfileScreen(
//        modifier = TODO(),
//        navController = TODO(),
//        onAppbarChange = TODO(),
//        userProfile = UserProfile()
//    )

}