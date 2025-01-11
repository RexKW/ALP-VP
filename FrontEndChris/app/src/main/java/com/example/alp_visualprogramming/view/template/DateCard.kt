package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun DateCardView(
    month: String,
    dayYear: String,
    isSelected : Boolean,
    onClick: () -> Unit = {}
){
    Card(

        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .width(138.dp)
            .height(130.dp)

            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 4.dp,
                        color = Color(0xFFEE417D),
                        shape = RoundedCornerShape(20.dp)
                    )
                } else Modifier.clickable {
                    onClick()
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFBF7E7),
        )
    ) {
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                month,
                style = TextStyle(
                    fontSize = 48.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF440215),
                )
            )
            Text(
                dayYear,
                style = TextStyle(
                    fontSize = 31.61.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF440215),
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DateCardViewPreview(){
    DateCardView("Sep", "10 2024", true)
}