package com.dhanush.corecomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhanush.corecomposeapp.ui.theme.CoreComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}
@Composable
fun MainScreen(){
    Surface(
        color =  Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            horizontalColorsBar(Color.Magenta)
            horizontalColorsBar(Color.Red)
            horizontalColorsBar(Color.Green)
            horizontalColorsBar(Color.Cyan)
        }


    }
}

@Composable
fun horizontalColorsBar(color: Color){
    Surface(color = color,
        modifier = Modifier
            .height(500.dp)
            .width(50.dp)
    ) {}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainScreen()
}