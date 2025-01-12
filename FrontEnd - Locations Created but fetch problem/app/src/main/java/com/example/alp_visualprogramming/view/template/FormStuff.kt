package com.example.alp_visualprogramming.view.template

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alp_visualprogramming.R
import java.util.Calendar


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
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = labelText,
            modifier = Modifier.padding(bottom = 4.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF3E122B),
            )
        )
        TextField(
            value = inputValue,
            onValueChange = { onValueChange(it) },
            placeholder = { Text(text = placeholderText) },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFEAE5D2),
                    shape = RoundedCornerShape(8.dp)
                ) // Background color and rounded corners
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

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormNumberField(
    inputValue: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String,
    placeholderText: String,
    minLine: Int,
    maxLine: Int
) {
    Column {
        Text(
            text = labelText,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF3E122B),
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = inputValue.toString(),
            onValueChange = { onValueChange(it.toDouble()) },
            placeholder = { Text(text = placeholderText) },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFEAE5D2),
                    shape = RoundedCornerShape(8.dp)
                ) // Background color and rounded corners
                .padding(4.dp), // Padding for spacing inside the container
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEAE5D2), // Background color
                cursorColor = Color.Red,
                focusedIndicatorColor = Color.Transparent, // Remove underline when focused
                unfocusedIndicatorColor = Color.Transparent // Remove underline when unfocused
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp)
        )
    }


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
    Column {
        Text(
            text = labelText,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF3E122B),
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        ExposedDropdownMenuBox(
            expanded = dropdownMenuExpanded,
            onExpandedChange = onDropdownMenuBoxExpandedChange,
            modifier = modifier
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .width(165.dp)
                    .height(66.dp)
                    .background(
                        color = Color(0xFFEAE5D2),
                        shape = RoundedCornerShape(size = 10.dp)
                    ),
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownMenuExpanded)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFEAE5D2),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    disabledIndicatorColor = Color.Transparent
                ),
            )

            ExposedDropdownMenu(
                expanded = dropdownMenuExpanded,
                onDismissRequest = onDismissRequest,
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            ) {
                dropdownItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                color = Color.Black
                            )
                        },
                        onClick = {
                            onDropdownItemClick(item)
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .background(Color(0xFFEAE5D2))
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
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
        verticalAlignment = Alignment.CenterVertically,

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

@Composable
fun FormTimePicker(
    timePickerValue: String,
    showTimeDialog: () -> Unit,
    modifier: Modifier = Modifier,
    labelText: String
){
    Text(
        text = labelText,
        style = TextStyle(
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.oswald_regular)),
            fontWeight = FontWeight(400),
            color = Color(0xFF3E122B),
        ),
        modifier = Modifier.padding(bottom = 2.dp)
    )
    Column {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .width(165.dp)
                .height(53.dp)
                .background(color = Color(0xFFEAE5D2))
                .clickable { showTimeDialog() }
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (timePickerValue.isNotEmpty()) timePickerValue else "",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.oswald_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFEE417D),
                )
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDescriptionField(
    inputValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String,
    placeholderText: String,
    minLine: Int,
    maxLine: Int
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = labelText,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.oswald_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF3E122B),
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = inputValue,
            onValueChange = { onValueChange(it) },
            placeholder = { Text(text = placeholderText) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEAE5D2), shape = RoundedCornerShape(8.dp))
                .padding(4.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEAE5D2),
                cursorColor = Color.Red,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            minLines = 4,
            maxLines = maxLine,
            singleLine = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Default
            )
        )
    }

}






@OptIn(ExperimentalMaterial3Api::class)
@Preview
    (showBackground = true,
            showSystemUi = true)
@Composable
fun FormPreview() {

}


