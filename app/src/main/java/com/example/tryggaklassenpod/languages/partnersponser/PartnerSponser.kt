package com.example.tryggaklassenpod.languages.partnersponser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tryggaklassenpod.languages.otherAppsLink.EmailLinkCompose
import com.example.tryggaklassenpod.R

@Composable
fun PartnerSponser(navController: NavController){
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(Color(0xFF006971)),
        ){
        SPtopAppBar(navController)
        CurrentPartners()
        Spacer(modifier = Modifier.padding(10.dp))
        sponserContent()
    }
}

@Composable
fun sponserContent(){
    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = "Trygga Klassen Offers companies to become a preventive partner - sponsor," +
                " we offer two options.",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,)
            ),
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "Option 1 Gold partner",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            ),
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Text(
            text = "The company logo can be seen in our podcast Gold partner. As a gold partner, " +
                    "we offer the company Lotus model's digital preventive training to all employees" +
                    " of the company. The company receives a specific username and password. " +
                    "The training is aimed at managers and supervisors as well as all employees. " +
                    "The aim of the training is to increase knowledge about alcohol and drugs, " +
                    "everything is evaluated with digital tools and that there is the \"red button\"," +
                    " for advice and support. The license right to the training is 12 months. " +
                    "\n\nPrice: SEK 19,900 excluding VAT\n" +
                    "Order via email to: ",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,
                )
            ),
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(3.dp))
        EmailLinkCompose("kontakt@lotusmodellen.se")

        Spacer(modifier = Modifier.padding(12.dp))
        Text(text = "Option 2 Silver partner",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            ),
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Text(
            text = "The company logo is visible in our podcast under Silver partner. " +
                    "\n\nPrice: 3,000 excluding VAT\n" +
                    "Order via email to: ",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,
                )
            ),
            color = Color.White
        )
        Spacer(modifier = Modifier.padding(3.dp))
        EmailLinkCompose("kontakt@lotusmodellen.se")
        Spacer(modifier = Modifier.padding(10.dp))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SPtopAppBar(navController: NavController){
    TopAppBar(title = { Text(
        text = "Our Sponsers / Partners",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            color = Color(0xFF00363B),
            fontFamily = FontFamily.Serif,
            fontSize = 25.sp,
        )
    ) },
    navigationIcon = {
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
    },
    )
}

@Composable
fun CurrentPartners(){
    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "Gold Partners: ",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    )
            ),
            color = Color.Yellow)
        Spacer(modifier = Modifier.padding(5.dp))
        Image(
            painter = painterResource(id = R.drawable.goldpartner),
            contentDescription = "logo"
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = "Silver Partners: ",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                )
            ),
            color = Color(0xFF899294))
        Spacer(modifier = Modifier.padding(5.dp))
        Image(
            painter = painterResource(id = R.drawable.silverpartner),
            contentDescription = "logo"
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PartnerSponserPreview(){
    PartnerSponser(rememberNavController() )
}