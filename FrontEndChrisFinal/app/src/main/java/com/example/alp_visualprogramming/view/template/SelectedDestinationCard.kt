package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun SelectedDestinationCard(
    name: String,
    province: String
){
    Card (modifier = Modifier
        .width(372.42453.dp)
        .height(230.85965.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF5FEEDB),
        ),
        shape = RoundedCornerShape(20.dp),

    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.cardbg),
                contentDescription = "contentDescription",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column {
                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 78.41.sp,
                        fontFamily = FontFamily(Font(R.font.oswald_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFBF7E7),

                        textAlign = TextAlign.Center,
                    )

                )

                Text(
                    text = province,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF440215),
                        textAlign = TextAlign.Center,)

                )
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedDestinationCardPreview() {
    SelectedDestinationCard("Samarinda", "East Kalimantan")
}