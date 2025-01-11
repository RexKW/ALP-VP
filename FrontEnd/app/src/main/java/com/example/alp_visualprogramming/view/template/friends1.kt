package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.ui.theme.ALP_VisualProgrammingTheme

// 🔹 Data Class untuk User
data class User(
    val name: String,
    val role: String,
    val isEditable: Boolean,
    val isKickable: Boolean
)

// 🔹 Dummy Data untuk List User
val sampleUsers = listOf(
    User("You", "Owner", isEditable = false, isKickable = false),
    User("Alice", "Admin", isEditable = true, isKickable = true),
    User("Bob", "Member", isEditable = true, isKickable = true),
    User("Charlie", "Member", isEditable = true, isKickable = true),
    User("Add Member", "", isEditable = false, isKickable = false)
)

// 🔹 Fungsi Utama untuk Halaman Friends
@Composable
fun friends1Page(name: String, modifier: Modifier = Modifier, onBackClick: () -> Unit = {}) {
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
                .padding(top = 300.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Bagian Navigasi di Bawah
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Journey",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3E122B),
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "Budget",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF3E122B),
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "Travellers",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFEE417D),
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Daftar Pengguna
            sampleUsers.forEach { user ->
                UserCard(user)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// 🔹 Card untuk Setiap User
@Composable
fun UserCard(user: User) {
    Box(
        modifier = Modifier
            .width(377.dp)
            .height(139.dp)
            .background(
                color = when (user.role) {
                    "Owner", "Admin" -> Color(0xFF5FEEDB)
                    "Member" -> Color(0xFF94284D)
                    else -> Color(0xFFEE417D)
                },
                shape = RoundedCornerShape(size = 20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (user.role.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_circle),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Gray, shape = CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = user.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (user.role == "Member") Color(0xFFFBF7E7) else Color(0xFF3E122B),
                            textAlign = TextAlign.Start,
                        )
                        Text(
                            text = user.role,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFEE417D),
                            textAlign = TextAlign.Start,
                        )
                    }
                }

                // 🔹 Tombol Aksi (Edit & Kick)
                Row {
                    if (user.isEditable) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "Edit Icon",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                        )
                    }

                    if (user.isKickable) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_kick),
                            contentDescription = "Kick Icon",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        } else {
            // 📌 Box untuk Add Member
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = user.name,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFBF7E7),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 16.dp)
                )
            }
        }
    }
}

// 🔹 Preview
@Preview(showBackground = true)
@Composable
fun friends1Preview() {
    ALP_VisualProgrammingTheme {
        friends1Page("Android")
    }
}
