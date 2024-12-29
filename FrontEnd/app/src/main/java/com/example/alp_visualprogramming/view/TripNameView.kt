package com.example.alp_visualprogramming.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.R

@Composable
fun TripNameView() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
        Box(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(R.drawable.bgitineraryname),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(507.dp),

                alignment = Alignment.TopStart,
            )

            IconButton(
                onClick = { /* Handle back action here */ },
                modifier = Modifier
                    .align(Alignment.TopStart) // Align to the top left corner
                    .padding(16.dp)            // Add padding for proper spacing
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Use an arrow-back icon
                    contentDescription = "Back",
                    tint = Color.White // Set the icon color (adjust as needed for contrast)
                )
            }

            Text(text = "Start Planning",
                modifier = Modifier.align(Alignment.Center).padding(start = 10.dp),
                    style = TextStyle(
                        fontSize = 96.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFBF7E7),

                        )
                )
        }
        Column (modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
            Text(
                text = "Trip Name",
                style = TextStyle(
                    fontSize = 64.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEE417D),
                    textAlign = TextAlign.Center,
                )
            )


            Button(modifier = Modifier
                .width(372.dp)
                .height(62.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFEE417D)
                ),
                onClick={}
            ) {

                Text(text= "Continue",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFBF7E7),

                        )

                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TripNamePreview() {
    TripNameView()
}