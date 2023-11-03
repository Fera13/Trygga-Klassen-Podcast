package com.example.tryggaklassenpod.languages.abouts


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
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
import com.example.tryggaklassenpod.ui.theme.md_theme_light_primary


@Composable
fun AboutFr(navController: NavController){

    Column (
        modifier = Modifier
            .background(Color(0xFF006971))
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBarWithMenuAndOptions("French", navController)

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Le Podcast Trygga Klassen est votre ressource essentielle pour obtenir des " +
                "perspectives sur l'amélioration du bien-être des enfants et des jeunes. À travers" +
                " des conversations engageantes avec des experts, nous abordons des questions " +
                "cruciales telles que l'abus de substances, le harcèlement, la santé mentale, " +
                "ainsi que des thèmes comme le harcèlement, les menaces, la violence, la sexualité, " +
                "les drogues, l'alcool, la sécurité informatique et sur internet, la santé des " +
                "étudiants et les problèmes de santé mentale. Notre objectif est de fournir des " +
                "solutions pratiques pour les parents, les enseignants et les élèves sur ces " +
                "sujets essentiels.\n\n" +
                "Visitez notre site web:",
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



@Preview(showBackground = true)
@Composable
fun AboutFrPreview(){
    AboutFr(rememberNavController())
}