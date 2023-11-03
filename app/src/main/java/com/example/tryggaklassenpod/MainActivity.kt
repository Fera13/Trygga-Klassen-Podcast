package com.example.tryggaklassenpod

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.tryggaklassenpod.navigations.Navigation
import com.example.tryggaklassenpod.ui.theme.TryggaKlassenPodTheme
import com.example.tryggaklassenpod.viewModels.HomeViewModel
import com.google.firebase.FirebaseApp
import java.io.File

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: HomeViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        FirebaseApp.initializeApp(this)
        setContent {
            TryggaKlassenPodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(viewModel)
                }
            }
        }
    }
}

