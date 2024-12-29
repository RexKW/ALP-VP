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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.alp_visualprogramming.view.template.TripCard

@Composable
fun ActivityDetailView(modifier: Modifier = Modifier){

        Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
            Box(modifier = Modifier.fillMaxWidth()) {

                Image(
                    painter = painterResource(R.drawable.cityheader),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth().height(225.dp),

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
            }
            Column (modifier = Modifier.padding(start = 32.dp, top = 5.dp, end = 32.dp)) {
                Text(
                    text = "Flight",
                    style = TextStyle(
                        fontSize = 64.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFEE417D),
                        textAlign = TextAlign.Center,
                    )
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Row(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(Color(0xFFEE417D)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBF7E7))
                        Text(text = "10:00 - 15:00", style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFBF7E7),

                            ))
                    }
                    Row(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(Color(0xFF5FEEDB)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBF7E7))
                        Text(text = "Djuanda Airport",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFEE417D),

                                ))
                    }
                    Row(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(Color(0xFFEE417D)).padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBF7E7))
                        Text(text = "En Route", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFBF7E7),

                            ))
                    }
                }
                Column(modifier = Modifier.padding(top = 16.dp).clip(RoundedCornerShape(20.dp)).background(Color(0xFF94284D)).fillMaxWidth().padding(20.dp))

                        {
                    Text("Description", style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFBF7E7),

                        ))
                            Text(text ="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                                modifier = Modifier.padding(top = 10.dp),
                                    style = TextStyle(
                                    fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.tajawal_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFBF7E7),

                                ))

                }
                Row(modifier = Modifier.padding(top = 16.dp).clip(RoundedCornerShape(20.dp)).fillMaxWidth().background(Color(0xFF5FEEDB)).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFBF7E7))
                    Text(text= "Cost",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFBF7E7),

                            )

                    )
                    Text(text = "Rp 400.000",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEE417D),

                            ),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }



@Preview(
    showBackground = true,
    showSystemUi = true,
    )
@Composable
fun ActivityDetailPreview() {
    ActivityDetailView(

    )
}