package com.example.alp_vp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.alp_visualprogramming.R

@Composable
fun friends2(name: String, modifier: Modifier = Modifier, onBackClick: () -> Unit = {}) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val userList = listOf("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Isaac", "Jack")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 🔙 Tombol Back di Kiri Atas
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Button",
            modifier = Modifier
                .size(70.dp)
                .padding(16.dp)
                .align(Alignment.TopStart)
                .clickable { onBackClick() }
                .zIndex(1f)
        )

        // 🖼️ Gambar Header
        Image(
            painter = painterResource(id = R.drawable.group_21),
            contentDescription = "Top Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
        )

        // 📜 Konten Scrollable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // 🔹 Teks "Travellers"
            Text(
                text = "Travellers",
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFEE417D),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔍 Search Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color(0xFFF0F0F0), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                if (searchQuery.text.isEmpty()) {
                    Text(
                        text = "Search user...",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 👤 Daftar Pengguna (Scrollable)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(userList.filter { it.contains(searchQuery.text, ignoreCase = true) }) { user ->
                    UserProfileBox(userName = user)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// 👤 Composable untuk Box Profil Pengguna
@Composable
fun UserProfileBox(userName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFF5FEEDB), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 🖼️ Profile Picture
            Image(
                painter = painterResource(id = R.drawable.ic_profile_circle),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.LightGray, shape = androidx.compose.foundation.shape.CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 📝 Nama Pengguna
            Text(
                text = userName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

// 🌟 Preview untuk friends2
@Preview(showBackground = true)
@Composable
fun friends2Preview() {

        friends2("Android")

}

// 🌟 Preview untuk UserProfileBox dengan Dummy Data
@Preview(showBackground = true)
@Composable
fun UserProfileBoxPreview() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val dummyUsers = listOf(
                "Alice",
                "Bob",
                "Charlie",
                "David",
                "Eve"
            )

            dummyUsers.forEach { user ->
                UserProfileBox(userName = user)
            }
        }

}
