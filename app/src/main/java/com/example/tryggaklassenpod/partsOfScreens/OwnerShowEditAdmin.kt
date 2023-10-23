package com.example.tryggaklassenpod.partsOfScreens

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.dataClasses.AdminDataClass
import com.example.tryggaklassenpod.helperFunctions.PasswordHash
import com.example.tryggaklassenpod.helperFunctions.ValidatePassword
import com.example.tryggaklassenpod.screens.showAdmins
import com.example.tryggaklassenpod.sealed.DeleteAdminState
import com.example.tryggaklassenpod.sealed.FetchingAdminDataState
import com.example.tryggaklassenpod.sealed.UpdateAdminState
import com.example.tryggaklassenpod.viewModels.OwnerPageViewModel

val addAdmin = OwnerAddingAdmin()

class OwnerPageShowEditAdmin {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ShowLazyList(viewModel: OwnerPageViewModel, admins: MutableList<AdminDataClass>, adminIds: MutableList<String>) {
        LazyColumn {
            itemsIndexed(admins) { index, admin ->
                val adminId = adminIds.getOrNull(index) // Get the corresponding admin ID
                AdminItem(viewModel, admin, adminId, // Pass both admin and adminId to your AdminItem composable
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_small),
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AdminItem(
        viewModel: OwnerPageViewModel,
        admin: AdminDataClass,
        adminId: String?,
        modifier: Modifier = Modifier
    ) {
        var keepUpdating by remember { mutableStateOf(false) }

        var expanded by remember { mutableStateOf(false) }
        var editable by remember { mutableStateOf(false) }
        var updatedInfo by remember { mutableStateOf(mapOf<String, Any>()) }
        var updatedPermissions by remember { mutableStateOf(mapOf("podcastPoster" to false, "podcastEditor" to false, "commentReviewer" to false) ) }
        Card(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    //Log.i(TAG, "Hi " + admin.username)
                    val pairReceived = adminInformation(editable, admin.username!!, admin.school!!,
                        admin.password!!
                    )
                    updatedInfo = pairReceived.first
                    keepUpdating = pairReceived.second
                    Spacer(Modifier.weight(1f))
                    AdminItemButton(
                        expanded = expanded,
                        onClick = { expanded = !expanded },
                        editable
                    )
                }
                if (expanded) {
                    updatedPermissions = viewAdminPermissions(editable, admin.permissions)
                    if (adminId != null) {
                        editable = adminOptions( keepUpdating, adminId, updatedPermissions, updatedInfo, viewModel,  modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        ))
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun adminInformation(
        editable:Boolean,
        adminName: String,
        adminSchool: String,
        adminPass: Map<String, String>,
        modifier: Modifier = Modifier
    ): Pair<Map<String, Any>, Boolean> {
        var name by remember { mutableStateOf(adminName) }
        var school by remember { mutableStateOf(adminSchool) }

        var newName by remember { mutableStateOf("") }
        var newPass by remember { mutableStateOf("") }
        var newSchool by remember { mutableStateOf("") }

        var showBadPass by remember { mutableStateOf(false) }

        var newInfo by remember { mutableStateOf<Map<String, Any>>((
                mapOf(
                    "name" to adminName,
                    "password" to adminPass,
                    "school" to adminSchool
                ))) }

        Column(modifier = modifier) {
            if(!editable){
                if (adminName != null) {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = "Name: " + name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
                        )
                    }

                }
                if (adminSchool != null) {
                    Text(
                        text = "School: "+ school,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
                    )
                }
            } else{
                TextField(
                    value = name,
                    onValueChange = {
                        newName = it
                        name = it
                        newInfo = if(name != null){
                            newInfo + mapOf("name" to newName)
                        } else {
                            newInfo + mapOf("name" to adminName)
                        }

                    },
                    label = { Text("Name") }
                )


                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = school,
                    onValueChange = {
                        newSchool = it
                        school = it
                        newInfo =
                            if(school != null){
                                newInfo + mapOf("school" to newSchool)
                            } else {
                                newInfo + mapOf("school" to adminSchool)
                            }
                    },
                    label = { Text("School") }
                )

                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = newPass,
                    onValueChange = {
                        newPass = it
                        val passValid = ValidatePassword()
                        val passStatus = passValid.execute(newPass)
                        if(newPass.isNullOrEmpty()){
                            newInfo = newInfo + mapOf("password" to adminPass)
                            showBadPass = false
                        } else if(newPass != null && passStatus){
                            val hashedPass = PasswordHash.hashPassword(newPass)
                            newInfo = newInfo + mapOf("password" to mapOf("salt" to hashedPass.second, "hashedPass" to hashedPass.first))
                            showBadPass = false
                        } else if(newPass != null && !passStatus){
                            showBadPass = true
                        }
                    },
                    label = { Text("New Password") }
                )
            }
            if(showBadPass){
                Text(
                    text = stringResource(R.string.Password_not_good),
                    color = Color.Red,
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium), top = dimensionResource(
                        R.dimen.padding_medium)
                    ))
            }
        }
        return Pair(newInfo, showBadPass)
    }

    @Composable
    private fun AdminItemButton(
        expanded: Boolean,
        onClick: () -> Unit,
        editable: Boolean,
        modifier: Modifier = Modifier
    ) {
        IconButton(
            onClick =
            if (editable) {
                /* Do nothing when editable is true */ {}
            } else {
                onClick
            },
            modifier = modifier
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "See more or less options for an Admin",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }

    @Composable
    fun viewAdminPermissions(
        editable:Boolean,
        adminPermissions:Map<String, Boolean>?)
            : Map<String, Boolean>
    {
        val podcastPoster by remember { mutableStateOf(adminPermissions?.get("podcastPoster")) }
        val podcastEditor by remember { mutableStateOf(adminPermissions?.get("podcastEditor")) }
        val commentReviewer by remember { mutableStateOf(adminPermissions?.get("commentReviewer")) }

        var newPodcastPosterState by remember { mutableStateOf(true) }
        var newPodcastEditorState by remember { mutableStateOf(true) }
        var newCommentReviewerState by remember { mutableStateOf(true) }

        var permissions by remember { mutableStateOf(mapOf("podcastPoster" to false, "podcastEditor" to false, "commentReviewer" to false) ) }

        newPodcastPosterState = podcastPoster?.let { addAdmin.sentenceSwitch(it, editable, "Can post podcasts") }!!
        permissions = permissions + mapOf("podcastPoster" to newPodcastPosterState)

        newPodcastEditorState = podcastEditor?.let { addAdmin.sentenceSwitch(it, editable,  "Can edit podcasts") }!!
        permissions = permissions + mapOf("podcastEditor" to newPodcastEditorState)

        newCommentReviewerState = commentReviewer?.let { addAdmin.sentenceSwitch(it, editable,  "Can review comments") }!!
        permissions = permissions + mapOf("commentReviewer" to newCommentReviewerState)
        Spacer(modifier = Modifier.height(8.dp))
        return permissions
    }

    @Composable
    fun adminOptions(
        keepUpdating: Boolean,
        adminId:String,
        adminPermissions:Map<String, Boolean>?,
        adminInfo:Map<String, Any>?,
        viewModel: OwnerPageViewModel,
        modifier: Modifier = Modifier
    ):Boolean {
        var editable by remember { mutableStateOf(false) }
        var deleteStatusMessage by remember { mutableStateOf(false) }
        var updateStatusMessage by remember { mutableStateOf(false) }

        var buttonText by remember { mutableStateOf("Edit info") }
        Log.i(ContentValues.TAG, "Hi " + adminPermissions)
        Log.i(ContentValues.TAG, "Hi " + adminInfo)

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Options",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            if (!keepUpdating) {
                                if (editable) {
                                    buttonText = "Edit info"
                                    editable = false
                                    if (adminPermissions != null) {
                                        viewModel.editAdminInfo(
                                            adminId,
                                            adminInfo?.get("name")!! as String,
                                            adminInfo["password"]!! as Map<String, String>,
                                            adminInfo["school"]!! as String,
                                            adminPermissions
                                        )
                                    }
                                    updateStatusMessage = true
                                    deleteStatusMessage = false // Reset delete message flag
                                } else {
                                    editable = true
                                    buttonText = "Submit changes"
                                    updateStatusMessage = false
                                    deleteStatusMessage = false // Reset delete message flag
                                }
                            }
                        }
                    ) {
                        Text(buttonText)
                    }
                    Button(
                        onClick = {
                            viewModel.deleteAdminById(adminId)

                            deleteStatusMessage = true
                            updateStatusMessage = false
                        }
                    ) {
                        Text("Delete Admin")
                    }
                }
            }
            if (updateStatusMessage) {
                val updateMessage = viewModel.updateMessage.value
                if (updateMessage is UpdateAdminState.Success) {
                    Text(
                        text = updateMessage.message ?: "",
                        color = Color(0xFF46B44A) // Color for success message
                    )
                } else if (updateMessage is UpdateAdminState.Failure) {
                    Text(
                        text = updateMessage.error ?: "",
                        color = Color.Red // Color for error message
                    )
                }
            }
            if (deleteStatusMessage) {
                val deleteMessage = viewModel.deleteMessage.value // Use a separate LiveData for delete messages
                if (deleteMessage is DeleteAdminState.Success) {
                    Text(
                        text = deleteMessage.message,
                        color = Color(0xFF46B44A) // Color for success message
                    )
                } else if (deleteMessage is DeleteAdminState.Failure) {
                    Text(
                        text = deleteMessage.error,
                        color = Color.Red // Color for error message
                    )
                }
            }
        }
        return editable
    }
}
/*@Composable
fun fetchAdmins(viewModel: OwnerPageViewModel){
    when (val result = viewModel.fetchAdminresponse.value) {
        is FetchingAdminDataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is FetchingAdminDataState.Success -> {
            admins = result.data
            showAdmins.ShowLazyList(viewModel, admins, adminIds)
        }
        is FetchingAdminDataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                    fontSize = 16.sp,
                )
            }
        }
}*/