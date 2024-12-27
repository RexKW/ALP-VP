package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun ActivityCardView(
    name: String,
    time: String,
    onCardClick: () -> Unit = {}
){
    Row {
        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp).width(4.dp).height(138.dp).background(color = Color(0xFF440215))) {
        }
        Card(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                .width(312.07941.dp)
                .height(120.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF5FEEDB),
            ),
            shape = RoundedCornerShape(20.dp),


            ) {
            Row {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 35.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFEE417D),

                            )
                    )
                    Text(
                        text = time,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF440215),

                            )
                    )


                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityCardPreview() {
    ActivityCardView("Shopping", "15:00 - 15:30")
}