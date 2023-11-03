package com.example.tryggaklassenpod.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.dataClasses.AdminDataClass
import com.example.tryggaklassenpod.partsOfScreens.OwnerPageShowEditAdmin
import com.example.tryggaklassenpod.partsOfScreens.addAdmin
import com.example.tryggaklassenpod.sealed.FetchingAdminDataState
import com.example.tryggaklassenpod.sealed.FetchingAdminIDsState
import com.example.tryggaklassenpod.viewModels.OwnerPageViewModel


val showAdmins = OwnerPageShowEditAdmin()

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OwnerPageContent(navController: NavHostController){

    val viewModel: OwnerPageViewModel = viewModel()
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ){
        TabbedPage(viewModel, navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabbedPage(viewModel: OwnerPageViewModel, navController: NavHostController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    // Labels for the tabs
    val tabLabels = listOf("Owner", "Admin")
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Create the TabRow with tabs
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                        )
                    }
                ) {
                    // Create tabs
                    tabLabels.forEachIndexed { index, label ->
                        Tab(
                            text = { Text(text = label) },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }
                // Display the content based on the selected tab
                when (selectedTabIndex) {
                    0 -> TabContent1(viewModel)
                    1 -> TabContent2(navController)
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TabContent1(viewModel: OwnerPageViewModel) {
    var adminIds by remember { mutableStateOf(mutableListOf<String>()) }
    val isDarkTheme = isSystemInDarkTheme()
    var admins by remember { mutableStateOf(mutableListOf<AdminDataClass>()) }

    val backgroundColor = if (isDarkTheme) {
        Color(0xFF4DD8E5) // Dark theme background color
    } else {
        Color(0xFF006971) // Light theme background color
    }
    Column(){
        Text(
            text = "Current admins",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_small)
                )
        )
        Box(
            modifier = Modifier
                .height(400.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                //.background(backgroundColor, shape = RoundedCornerShape(15.dp))
                .border(
                    BorderStroke(2.dp, SolidColor(backgroundColor)),
                    shape = RoundedCornerShape(15.dp)
                )
        ){
            when (val result2 = viewModel.fetchIDresponse.value) {
                is FetchingAdminIDsState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is FetchingAdminIDsState.Success -> {
                    adminIds = result2.data
                }
                is FetchingAdminIDsState.Failure -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = result2.message,
                            fontSize = 16.sp,
                        )
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.error_fetching_msg),
                            fontSize = 16.sp,
                        )
                    }
                }
            }
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
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.error_fetching_msg),
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
        Text(
            text = "Add a new admin",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_small),
                    top = dimensionResource(R.dimen.padding_small)
                )
        )
        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(2.dp, SolidColor(Color(0xFF006971))),
                    shape = RoundedCornerShape(15.dp)
                )
        ){
            addAdmin.AddAnAdminSection(viewModel)
        }
    }
}

@Composable
fun TabContent2(controller:NavController) {
    AdminScreen(controller)
}