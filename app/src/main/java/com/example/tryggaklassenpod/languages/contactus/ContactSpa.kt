package com.example.tryggaklassenpod.languages.contactus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.languages.otherAppsLink.EmailLinkCompose
import com.example.tryggaklassenpod.languages.otherAppsLink.PhoneLinkCompose


@Composable
fun ContactSp(navController: NavController){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        ContactsAppBar("Spanish", navController)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        )  {
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
                    text = "¿Estás interesado en utilizar Trygga Klassen en la escuela? Envíanos un mensaje, un correo electrónico o llama, y podremos contarte más sobre nosotros, el concepto y las herramientas digitales que utilizamos.",
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contact information
                Text(
                    text = "Contacto:",
                    color = Color(0xFF4DD9E6)
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Row {
                    Text(
                        text = "Correo Electrónico: ",
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
                        text = "Teléfono: ",
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


@Preview(showBackground = true)
@Composable
fun ContactSpPreview(){
    ContactSp(rememberNavController())
}