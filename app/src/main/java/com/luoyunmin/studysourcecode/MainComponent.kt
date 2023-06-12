package com.luoyunmin.studysourcecode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainComponent(
    onJavaCrashClick: () -> Unit,
    onNativeCrashClick: () -> Unit,
    onASMClick: () -> Unit
) {
    Column() {
        Column {
            StudyButton(text = "Java Crash", onClick = onJavaCrashClick)
            StudyButton(text = "Native Crash", onClick = onNativeCrashClick)
            StudyButton(text = "Test ASM", onClick = onASMClick)
        }
    }
}

@Composable
fun StudyButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(text = text)
    }
}