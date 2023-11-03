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
fun AboutAr(navController: NavController){

    Column (
        modifier = Modifier
            .background(Color(0xFF006971))
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBarWithMenuAndOptions("العربية", navController)

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "\u202Eبودكاست تريجا كلاسن هو مصدرك الأساسي للرؤى حول تعزيز رفاهية الأطفال والشباب. من خلال المحادثات المثيرة للاهتمام مع الخبراء المختصين، سنتناول قضايا حيوية مثل الإدمان على المخدرات والتنمر والصحة النفسية، بالإضافة إلى مواضيع تدور حول العنف والجنس والكحول والأمان على الانترنت وصحة الطلاب والأمراض النفسية.  هدفنا هو تقديم حلول عملية للآباء و الأمهات والمعلمين والطلاب حول هذه المواضيع الحيوية.\u202C\n",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.End,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,)
            ),
            color = Color.White

        )

        Text(
            text = "\u202Eزوروا موقعنا على الرابط التالي:\u202C",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.End,
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
fun AboutArPreview(){
    AboutAr(rememberNavController())
}