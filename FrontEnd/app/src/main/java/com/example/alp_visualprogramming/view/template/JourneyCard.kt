package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.R

@Composable
fun JourneyCardView(
    modifier: Modifier,
    name: String?,
    startDate: String,
    endDate: String,
    onCardClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    canEdit:Boolean
    ){
    Column(modifier = Modifier.width(342.dp)) {
        Card(modifier = Modifier
            .width(342.dp)
            .height(235.42712.dp).clickable {
                onCardClick()
            },
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF5FEEDB),
            ),
            shape = RoundedCornerShape(40.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxWidth().padding(20.dp).padding(top = 35.dp)){

                    Text(text = name ?: "Unknown",
                        style = TextStyle(
                            fontSize = 42.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFBF7E7),

                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Start Date  ",
                            style = TextStyle(
                                fontSize = 12.16.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF492E40),

                                ))
                        Text(text = startDate,
                            style = TextStyle(
                                fontSize = 16.21.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFEE417D),

                                ))

                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "End Date    ",style = TextStyle(

                            fontSize = 12.16.sp,
                            fontFamily = FontFamily(Font(R.font.oswald_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF492E40),

                            ))
                        Text(text = endDate,
                            style = TextStyle(
                                fontSize = 16.21.sp,
                                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFEE417D),

                                ))
                    }


                }
                Image(
                    painter = painterResource(id = R.drawable.destinationscard),
                    modifier = Modifier.width(342.dp)
                        .height(98.38745.dp).align(Alignment.BottomStart),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row (modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly){
            Card (colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEE417D),
            ), shape = RoundedCornerShape(size = 10.dp)){
                Icon(
                    imageVector = Icons.Default.Face,
                    modifier = Modifier.padding(20.dp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Card (colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEE417D),
            ), shape = RoundedCornerShape(size = 10.dp)) {
                Icon(
                    imageVector = Icons.Default.Face,
                    modifier = Modifier.padding(20.dp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Card (colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEE417D),
            ), shape = RoundedCornerShape(size = 10.dp)) {
                Icon(
                    imageVector = Icons.Default.Home,
                    modifier = Modifier.padding(20.dp),
                    contentDescription = null,
                    tint = Color.White
                )
            }
            if(canEdit) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEE417D),
                    ), shape = RoundedCornerShape(size = 10.dp),
                    modifier = Modifier.clickable {
                        onEditClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        modifier = Modifier.padding(20.dp),
                        contentDescription = null,
                        tint = Color.White

                    )

                }
            }
        }

    }
}

@Preview
@Composable
fun DestinationsTripCardPreview(){
    JourneyCardView(modifier = Modifier,"Aceh Singkil", "22 Sep 2024", "24 Sep 2024", onCardClick = {}, onEditClick = {}, true)
}