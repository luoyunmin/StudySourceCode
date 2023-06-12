package com.luoyunmin.studysourcecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.luoyunmin.studysourcecode.theme.StudySourceCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySourceCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComponent(
                        onJavaCrashClick = { onJavaCrashClick() },
                        onNativeCrashClick = { onNativeCrashClick() },
                        onASMClick = { onTestASM() })
                }
            }
        }
    }

    private fun onJavaCrashClick() {
        StudyJavaCrash.performJavaCrash()
    }

    private fun onNativeCrashClick() {

    }

    private fun onTestASM() {
        TestASM.b()
    }
}