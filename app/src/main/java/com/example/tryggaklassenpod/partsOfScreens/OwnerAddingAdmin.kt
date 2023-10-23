package com.example.tryggaklassenpod.partsOfScreens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.helperFunctions.PasswordHash
import com.example.tryggaklassenpod.helperFunctions.ValidatePassword
import com.example.tryggaklassenpod.sealed.InsertAdminDataState
import com.example.tryggaklassenpod.viewModels.OwnerPageViewModel

class OwnerAddingAdmin {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddAnAdminSection(viewModel: OwnerPageViewModel) {
        var name by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var school by remember { mutableStateOf("") }
        val podcastPoster by remember { mutableStateOf(true) }
        val podcastEditor by remember { mutableStateOf(true) }
        val commentReviewer by remember { mutableStateOf(true) }
        var newPodcastPosterState by remember { mutableStateOf(true) }
        var newPodcastEditorState by remember { mutableStateOf(true) }
        var newCommentReviewerState by remember { mutableStateOf(true) }
        var permissions by remember { mutableStateOf(mapOf("podcastPoster" to false, "podcastEditor" to false, "commentReviewer" to false) ) }

        val passValid = ValidatePassword()
        var showBadPass by remember { mutableStateOf(false) }
        var insertionStatusMessage by remember { mutableStateOf(false) } // Track if the message is shown

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = school,
                onValueChange = { school = it },
                label = { Text("School") }
            )
            Spacer(modifier = Modifier.height(20.dp))

            newPodcastPosterState = podcastPoster.let { sentenceSwitch(it, true,
                stringResource(R.string.permission_1_explination)
            ) }!!
            permissions = permissions + mapOf("podcastPoster" to newPodcastPosterState)

            newPodcastEditorState = podcastEditor.let { sentenceSwitch(it, true,
                stringResource(R.string.permission_2_explination)
            ) }!!
            permissions = permissions + mapOf("podcastEditor" to newPodcastEditorState)

            newCommentReviewerState = commentReviewer.let { sentenceSwitch(it, true, stringResource(
                R.string.permission_3_explination
            )
            ) }!!
            permissions = permissions + mapOf("commentReviewer" to newCommentReviewerState)

            Button(
                onClick = {
                    val passStatus = passValid.execute(password)
                    if (passStatus){
                        val hashedPass = PasswordHash.hashPassword(password)
                        //Log.i(TAG, "Hi " + hashedPass.first)
                        viewModel.addNewAdmin(name, school, mapOf("salt" to hashedPass.second, "hashedPass" to hashedPass.first), permissions)
                        showBadPass = false
                        name = ""
                        password = ""
                        school = ""
                        insertionStatusMessage = true
                    }
                    else{
                        showBadPass = true
                        insertionStatusMessage = false
                    }
                }
            ) {
                Text("Add Admin")
            }
            Spacer(modifier = Modifier.height(8.dp))
            if(showBadPass){
                Text(
                    text = stringResource(R.string.Password_not_good),
                    color = Color.Red)
            }
            if(insertionStatusMessage) {
                // Display the toast with a message
                val message = viewModel.message.value
                if (message is InsertAdminDataState.Success) {
                    val toast = callToast(sentence = (message).message)
                    toast.show()
                } else if (message is InsertAdminDataState.Failure) {
                    val toast = callToast(sentence = (message).error)
                    toast.show()
                }
            }
            /*if(insertionStatusMessage) {
                // Display the message under the button
                val message = viewModel.message.value
                if (message is InsertAdminDataState.Success) {
                    Text(
                        text = (message).message,
                        color = Color(0xFF46B44A) // Color for success message
                    )
                } else if (message is InsertAdminDataState.Failure) {
                    Text(
                        text = (message).error,
                        color = Color.Red // Color for error message
                    )
                }
            }*/
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    @Composable
    fun sentenceSwitch(startingState:Boolean, enableSwitcher:Boolean, sentence:String, modifier: Modifier = Modifier):Boolean {
        val isDarkTheme = isSystemInDarkTheme()

        var checked by remember { mutableStateOf(startingState) }
        var switchColor by remember { mutableStateOf(Color(0xFF006971).copy(0.5f)) }

        switchColor =
            if(!enableSwitcher && isDarkTheme){
                Color(0xFF4DD8E5).copy(0.4f)
            } else if(enableSwitcher && isDarkTheme ){
                Color(0xFF4DD8E5)
            } else if(!enableSwitcher && !isDarkTheme ){
                Color(0xFF006971).copy(0.5f)
            } else {
                Color(0xFF006971)
            }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_large),
                    end = 60.dp
                ),
        )
        {
            Text(text = sentence,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = dimensionResource(R.dimen.padding_small)),
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    if (enableSwitcher) {
                        checked = it
                    }

                },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    null
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = switchColor,
                )
            )
        }
        return checked
    }
    @SuppressLint("ShowToast")
    @Composable
    fun callToast(sentence:String): Toast {
        val context = LocalContext.current
        val toast = Toast.makeText(
            context, sentence,
            Toast.LENGTH_LONG)
        return toast
    }
}