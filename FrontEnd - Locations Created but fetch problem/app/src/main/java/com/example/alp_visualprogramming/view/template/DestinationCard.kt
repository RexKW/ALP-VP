package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun DestinationCard(
    name: String,
    province: String,
    onCardClick: () -> Unit = {}
){
    Card (modifier = Modifier
        .width(180.75716.dp)
        .height(112.04829.dp)
        .clickable {
            onCardClick()
        }
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF5FEEDB),
        ),
        shape = RoundedCornerShape(20.dp),

        ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(R.drawable.bglocation),
                contentDescription = "contentDescription",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = name,
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFBF7E7),

                        textAlign = TextAlign.Start,
                    )
                )
                Text(text = province,
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF440215),

                        ))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DestinationCardPreview() {
    DestinationCard("Samarinda", "East Kalimantan")
}