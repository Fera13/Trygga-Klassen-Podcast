package com.example.tryggaklassenpod.languages.otherAppsLink


import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PhoneLinkCompose() {
    val phoneNumber = "+46706255750"

    // dialer intent initialization
    val dialerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        // I may want to add something here in the future
    }

    // Composable Text element
    Text(
        text = phoneNumber,
        modifier = Modifier.clickable {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:$phoneNumber")

            dialerLauncher.launch(dialIntent)
        },
        color = Color(0xFF4DD9E6)
    )
}

@Preview(showBackground = true)
@Composable
fun PhonelinkPreview(){
    PhoneLinkCompose()
}
