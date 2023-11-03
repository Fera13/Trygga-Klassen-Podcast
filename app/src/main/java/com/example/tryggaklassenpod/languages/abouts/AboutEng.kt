package com.example.tryggaklassenpod.languages.abouts

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.externalResources.CallYoutubeInstance
import com.example.tryggaklassenpod.screens.Screen
import com.example.tryggaklassenpod.ui.theme.md_theme_light_primary


@Composable
fun AboutEng(navController: NavController){

    Column (
        modifier = Modifier
            .background(Color(0xFF006971))
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBarWithMenuAndOptions("English", navController)

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "The Trygga Klassen Podcast is your essential resource for insights into " +
                "enhancing the well-being of children and young people. Through engaging " +
                "conversations with experts, we address critical issues such as substance abuse,  " +
                "mental health, as well as themes like bullying, threats, violence, sexuality, " +
                "drugs, alcohol, computer and internet safety, student health, and mental illness. " +
                "Our goal is to provide practical solutions for parents, teachers, and students on " +
                "these vital topics.\n\n" +
                "Visit our website:",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,)
            ),
            color = Color.White

        )
        Spacer(modifier = Modifier.padding(5.dp))

        Image(
            painter = painterResource(id = R.drawable.tryggaklassen_logo1),
            contentDescription = "logo",
            modifier = Modifier.fillMaxWidth(.6f),
        )

        Spacer(modifier = Modifier.padding(5.dp))
        TextWithLink(txt = "Trggaklassen", lnk = "https://tryggaklassen.se/")

        Spacer(modifier = Modifier.padding(10.dp))

        CallYoutubeInstance()

        Spacer(modifier = Modifier.padding(10.dp))

    }
}


@Composable
fun TextWithLink(txt: String, lnk: String){
    val context = LocalContext.current

    val annotatedString = buildAnnotatedString {
        //append("Visit us at ")
        pushStringAnnotation(
            tag = "LINK",
            annotation = lnk
        )
        withStyle(
            style = SpanStyle(
                color = Color(0xFF4DD8E5),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        ) {
            append(txt)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "LINK",
                start = offset,
                end = offset
            ).firstOrNull()?.let { annotation ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithMenuAndOptions(lang: String, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var menuTxt by remember { mutableStateOf(lang)    }

    TopAppBar(
        title = {Text(
            text = "About us",
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
                        navController.navigate(Screen.AboutEng.route)
                        menuTxt = "English"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Swedish") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.AboutSwe.route)
                        menuTxt = "Swedish"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "French") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.AboutFr.route)
                        menuTxt = "French"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Spanish") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.AboutSp.route)
                        menuTxt = "Spanish"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Estonian") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.AboutEst.route)
                        menuTxt = "Estonian"
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Arabic") },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(Screen.AboutAr.route)
                        menuTxt = "Arabic"
                    }
                )

            }
        },

        )
}



@Preview(showBackground = true)
@Composable
fun AboutPreview(){
    AboutEng(rememberNavController())
}