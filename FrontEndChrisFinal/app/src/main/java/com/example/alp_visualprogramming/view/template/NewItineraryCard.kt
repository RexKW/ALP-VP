package com.example.alp_visualprogramming.view.template


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




@Composable
fun NewTripCard(modifier: Modifier,onCardClick: () -> Unit, navController: NavHostController = rememberNavController()){

    val OswaldRegular = FontFamily(Font(R.font.oswald_regular))

    Card(modifier = Modifier
        .width(334.dp)
        .height(206.dp)
        .clickable {
            onCardClick()
        }
        ,

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEE417D),
        ),
        shape = RoundedCornerShape(20.dp),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Add New Trip",
                    fontSize = 48.sp,
                    fontFamily = OswaldRegular,
                    color = Color(0xFFFBF7E7),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                Image(painter = painterResource(id = R.drawable.airport), contentDescription = "", modifier = Modifier.padding(top = 10.dp).width(235.dp).height(109.dp).align(Alignment.End))

            }




    }
}

@Preview(
    showBackground = true,

    )
@Composable
fun NewTripCardTemplatePreview() {
    NewTripCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onCardClick = {}
    )
}
