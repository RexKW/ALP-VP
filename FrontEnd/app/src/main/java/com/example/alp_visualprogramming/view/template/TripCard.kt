package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.R

val OswaldRegular = FontFamily(Font(R.font.oswald_regular))


@Composable
fun TripCard(title: String, travellers: Int, startDate: String, endDate: String, modifier: Modifier, onCardClick: () -> Unit){
    Card(modifier = Modifier
        .width(334.dp)
        .height(206.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF5FEEDB),
        ),
        shape = RoundedCornerShape(20.dp),
        onClick = onCardClick

    ) {
        Row(modifier =Modifier.fillMaxSize()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 32.sp,
                    fontFamily = OswaldRegular,
                    color = Color(0xFFFBF7E7),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                Text(text = "From: " + startDate,
                    style = TextStyle(
                        fontSize = 10.29.sp,
                        fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF3E122B),

                        ),
                    modifier = Modifier.padding(start = 16.dp, top = 5.dp)

                )
                Text(text = "To: " + endDate,
                    style = TextStyle(
                        fontSize = 10.29.sp,
                        fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF3E122B),

                        ),
                    modifier = Modifier.padding(start = 16.dp, top = 3.dp)
                    )
                Text(text = travellers.toString(),
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFEE417D),

                        ),
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp))
                Text("Travellers",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF3E122B),

                        ),
                    modifier = Modifier.padding(start = 16.dp, top = 0.dp))
            }
            Image(painter = painterResource(id = R.drawable.buildingstrip),
                contentDescription = null,
                modifier = Modifier.padding()
                    .width(185.dp)
                    .height(197.dp)
                    .align(Alignment.Bottom),
            )
        }

    }
}

@Preview(
    showBackground = true,

)
@Composable
fun TripCardTemplatePreview() {
    TripCard(
        title = "Bali Adventure",
        travellers = 5,
        startDate = "20 September 2022",
        endDate = "21 September 2022",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onCardClick = {}
    )
}
