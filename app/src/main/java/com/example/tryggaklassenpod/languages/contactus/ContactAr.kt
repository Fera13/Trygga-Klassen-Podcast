package com.example.tryggaklassenpod.languages.contactus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.languages.otherAppsLink.EmailLinkCompose
import com.example.tryggaklassenpod.languages.otherAppsLink.PhoneLinkCompose

@Composable
fun ContactAr(navController: NavController){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        ContactsAppBar("العربية", navController)
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
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(" Trygga klassen ", color = Color.White,)
                    Text("\u202Eهل لديك اهتمام باستخدام \u202C", color = Color.White,)
                }

                Text(
                    text = "\u202Eفي المدرسة؟ تواصل معنا عبر البريد الإلكتروني أو اتصل بنا وسنخبرك المزيد عنا وعن المفهوم والأدوات الرقمية التي نستخدمها.\u202C",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.End
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contact information
                Text(
                    text = "\u202Eمعلومات التواصل:\u202C",
                    color = Color(0xFF4DD9E6),
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.padding(3.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    EmailLinkCompose()
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("\u202Eبريد إلكتروني: \u202C", color = Color.White,)
                }
                Spacer(modifier = Modifier.padding(3.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    PhoneLinkCompose()
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("\u202Eهاتف: \u202C", color = Color.White,)
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ContactArPreview(){
    ContactAr(rememberNavController())
}