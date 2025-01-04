package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.R

@Composable
fun ExploreCardView(
    title: String,
    destinations: Int,
    modifier: Modifier = Modifier,
    OswaldRegular: FontFamily = FontFamily(Font(R.font.oswald_regular)),
    onCardClick: () -> Unit
){
        Card(modifier = Modifier
            .width(334.dp)
            .height(188.dp)
            .clickable {
                onCardClick()
            },
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF5FEEDB),
            ),
            shape = RoundedCornerShape(20.dp),
        ){
            Row(modifier = Modifier.fillMaxSize().weight(1f),


            ) {
                Column(modifier = Modifier.padding(top = 10.dp).weight(0.5f),) {
                    Text(
                        text = title,
                        fontSize = 32.sp,
                        fontFamily = OswaldRegular,
                        color = Color(0xFFFBF7E7),
                        modifier = Modifier.padding( top = 10.dp, start = 10.dp),
                        style = TextStyle(
                            lineHeight = 35.sp
                        )
                    )
                    Text(
                        text = destinations.toString(),
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFEE417D),

                            ),
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                    )
                    Text(
                        "Destinations",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.tajawal_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF3E122B),

                            ),
                        modifier = Modifier.padding(start = 10.dp, top = 0.dp)
                    )
                }
                Image(
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.buildingstrip),
                    contentDescription = null,
                    modifier = Modifier

                        .height(180.dp)
                        .weight(0.5f)

                        .align(Alignment.Bottom)
                )
            }
        }

    }



@Preview
    (showBackground = true)
@Composable
fun ExploreCardViewPreview(){
    ExploreCardView("Bali Adventure", 5, modifier = Modifier, onCardClick = {})
}