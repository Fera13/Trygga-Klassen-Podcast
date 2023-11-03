package com.example.tryggaklassenpod.languages.contactus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.languages.abouts.AppBarWithMenuAndOptions
import com.example.tryggaklassenpod.languages.otherAppsLink.EmailLinkCompose
import com.example.tryggaklassenpod.languages.otherAppsLink.PhoneLinkCompose
import com.example.tryggaklassenpod.screens.Screen


@Composable
fun ContactEng(navController: NavController){

    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState()),

        ){
        ContactsAppBar("English", navController)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF006971))
                    .padding(16.dp)
            ) {
                // Image
                Image(
                    painter = painterResource(id = R.drawable.po),
                    contentDescription = null, // Provide a suitable description
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Text content
                Text(
                    text = "Are you interested in using the Tryggra Klassen at school? Send us a message, e-mail or call and we can tell you more about us, the concept, and the digital tools we use.",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contact information
                Text(
                    text = "Contact:",
                    color = Color(0xFF4DD8E5)
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Row {
                    Text(
                        text = "Email: ",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            // Handle the email click action here
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    EmailLinkCompose()
                }
                Spacer(modifier = Modifier.padding(3.dp))

                Row {
                    Text(
                        text = "Phone: ",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            // Handle the phone click action here
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    PhoneLinkCompose()
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsAppBar(lang: String, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var menuTxt by remember { mutableStateOf(lang)    }

    TopAppBar(
        title = {Text(
            text = "Contact us",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                color = Color(0xFF00363B),
                fontFamily = FontFamily.Serif,
                fontSize = 25.sp,
            )
        )},
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = menuTxt)
                IconButton(
                    onClick = { expanded = true }
                ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Open Menu")
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {

                DropdownMenuItem(
                    text = { Text(text = "English") },
                    onClick = {
                        expanded = false
                        navController.popBackStack()
                        navController.navigate(Screen.ContactEng.route)
                        menuTxt = "English"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Swedish") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.ContactSwe.route)
                        menuTxt = "Swedish"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "French") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.ContactFr.route)
                        menuTxt = "French"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Spanish") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.ContactSpa.route)
                        menuTxt = "Spanish"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Estonian") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.ContactEst.route)
                        menuTxt = "Estonian"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Arabic") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.ContactAr.route)
                        menuTxt = "Arabic"
                    }
                )

            }
        },

        )
}


@Preview(showBackground = true)
@Composable
fun ContactEngPreview(){
    ContactEng(rememberNavController())
}