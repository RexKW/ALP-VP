package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    inputValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String,
    placeholderText: String,
    minLine: Int,
    maxLine: Int
) {
    TextField(
        value = inputValue,
        onValueChange = { onValueChange(it) },
        label = { Text(text = labelText) },
        placeholder = { Text(text = placeholderText) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEAE5D2), shape = RoundedCornerShape(8.dp)) // Background color and rounded corners
            .padding(4.dp), // Padding for spacing inside the container
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFEAE5D2), // Background color
            cursorColor = Color.Red,
            focusedIndicatorColor = Color.Transparent, // Remove underline when focused
            unfocusedIndicatorColor = Color.Transparent // Remove underline when unfocused
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDropdown(
    dropdownMenuExpanded: Boolean,
    onDropdownMenuBoxExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    dropdownItems: List<String>,
    onDropdownItemClick: (String) -> Unit,
    selectedItem: String,
    labelText: String
) {
    ExposedDropdownMenuBox(
        expanded = dropdownMenuExpanded,
        onExpandedChange = onDropdownMenuBoxExpandedChange,
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(),
            value = selectedItem,
            label = {
                Text(text = labelText)
            },
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownMenuExpanded)
            },
        )
        
        ExposedDropdownMenu(
            expanded = dropdownMenuExpanded,
            onDismissRequest = onDismissRequest
        ) {
            dropdownItems.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = item)
                    },
                    onClick = {
                        onDropdownItemClick(item)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@Composable
fun FormDatePickerStart(
    datePickerValue: String,
    showCalendarDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .width(374.dp)
            .height(66.dp)
            .background(color = Color(0xFF5FEEDB))
            .clickable { showCalendarDialog() }
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Start Date",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF3E122B),
            )
        )

        Text(
            text = if (datePickerValue.isNotEmpty()) datePickerValue else "DD/MM/YYYY",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFFEE417D),
            )
        )

        // Calendar icon
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Composable
fun FormDatePickerEnd(
    datePickerValue: String,
    showCalendarDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .width(374.dp)
            .height(66.dp)
            .background(color = Color(0xFF5FEEDB))
            .clickable { showCalendarDialog() }
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label for the date picker
        Text(
            text = "End Date",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF3E122B),
            )
        )

        Text(
            text = if (datePickerValue.isNotEmpty()) datePickerValue else "DD/MM/YYYY",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFFEE417D),
            )
        )

        // Calendar icon
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}