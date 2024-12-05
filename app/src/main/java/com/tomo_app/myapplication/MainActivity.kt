package com.tomo_app.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomo_app.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold {
                    Greeting(modifier = Modifier.padding(it))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier) {
    val viewModel: MyViewModel = viewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val profile by viewModel.profile.collectAsState(initial = Profile("", 0))

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.saveData(data = profile.data, count = profile.count)
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        Modifier.fillMaxSize()
    ) {
        Text(
            text = profile.data,
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.LightGray),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = profile.count.toString(),
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.LightGray),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(25.dp))
        TextField(
            value = "saved text",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Spacer(modifier = Modifier.height(25.dp))
        TextButton(
            onClick = {},
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(width = 200.dp)
                .align(Alignment.CenterHorizontally)
                .shadow(2.dp, RoundedCornerShape(12.dp))
        ) {
            Text(
                text = "button",
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting(modifier = Modifier)
}