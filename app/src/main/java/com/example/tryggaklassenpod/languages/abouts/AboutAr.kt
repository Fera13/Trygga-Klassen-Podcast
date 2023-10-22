package com.example.tryggaklassenpod.languages.abouts


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import com.example.tryggaklassenpod.ui.theme.md_theme_light_primary


@Composable
fun AboutAr(navController: NavController){

    Column (
        modifier = Modifier.fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "بودكاست Trygga Klassen هو مصدرك الأساسي للتحليلات والنظريات حول تعزيز " +
                "رفاهية الأطفال والشباب. من خلال محادثات جذابة مع خبراء متخصصين، نتناول " +
                "قضايا حيوية مثل إساءة استخدام المواد الكيميائية والتنمر والصحة " +
                "النفسية، بالإضافة إلى مواضيع مثل التنمر والتهديدات والعنف والجنس " +
                "والمخدرات والكحول وسلامة الكمبيوتر والإنترنت وصحة الطلاب والأمراض النفسية. " +
                "هدفنا هو تقديم حلاً عمليًا للآباء والأمهات والمعلمين والطلاب حول هذه " +
                "المواضيع الحيوية.\n\n" +
                "زوروا موقعنا على الويب:",
            modifier = Modifier.fillMaxWidth(.9f),
            textAlign = TextAlign.Justify,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeight = 1.5.em,)
            )

        )
        Spacer(modifier = Modifier.padding(5.dp))

        Image(
            painter = painterResource(id = R.drawable.tryggaklassen_logo1),
            contentDescription = "logo",
            modifier = Modifier.fillMaxWidth(.6f),
        )

        Spacer(modifier = Modifier.padding(5.dp))
        TextWithLink(txt = "Trggaklassen", lnk = "https://tryggaklassen.se/")

    }
}


@Composable
fun TextWithLink5(txt: String, lnk: String){
    val context = LocalContext.current

    val annotatedString = buildAnnotatedString {
        //append("Visit us at ")
        pushStringAnnotation(
            tag = "LINK",
            annotation = lnk
        )
        withStyle(
            style = SpanStyle(
                color = md_theme_light_primary,
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


@Preview(showBackground = true)
@Composable
fun AboutArPreview(){
    AboutAr(rememberNavController())
}