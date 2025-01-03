package com.example.alp_visualprogramming.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.view.template.FormDropdown
import com.example.alp_visualprogramming.view.template.FormTextField
import com.example.alp_visualprogramming.view.template.TripCard
import com.example.alp_visualprogramming.viewModel.ActivityFormViewModel

@Composable
fun ActivityFormView(activityFormViewModel: ActivityFormViewModel){

        Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFBF7E7))) {
            val submissionStatus = activityFormViewModel.submissionStatus
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.cityheader),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth().height(225.dp),

                    alignment = Alignment.TopStart,
                )
                IconButton(
                    onClick = { /* Handle back action here */ },
                    modifier = Modifier
                        .align(Alignment.TopStart) // Align to the top left corner
                        .padding(16.dp)            // Add padding for proper spacing
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Use an arrow-back icon
                        contentDescription = "Back",
                        tint = Color.White // Set the icon color (adjust as needed for contrast)
                    )
                }
            }
            Column {
                Text(
                    text = "Todo List Form",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                )

                FormTextField(
                    inputValue = activityFormViewModel.titleInput,
                    onValueChange = {
                        activityFormViewModel.changeTitleInput(it)
                        activityFormViewModel.checkNullFormValues()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    labelText = stringResource(R.string.name_text),
                    placeholderText = stringResource(R.string.name_text),
                    minLine = 1,
                    maxLine = 1
                )

                FormTextField(
                    inputValue = activityFormViewModel.titleInput,
                    onValueChange = {
                        activityFormViewModel.changeTitleInput(it)
                        activityFormViewModel.checkNullFormValues()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    labelText = stringResource(R.string.name_text),
                    placeholderText = stringResource(R.string.name_text),
                    minLine = 1,
                    maxLine = 1
                )

                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                ) {
                    FormDropdown(
                        dropdownMenuExpanded = todoListFormUIState.value.statusDropdownExpandedValue,
                        onDismissRequest = {
                            activityFormViewModel.dismissStatusDropdownMenu()
                        },
                        dropdownItems = todoListFormUIState.value.statusDropdownItems,
                        onDropdownItemClick = {
                            activityFormViewModel.changeStatusInput(it)
                            activityFormViewModel.checkNullFormValues()
                        },
                        onDropdownMenuBoxExpandedChange = {
                            activityFormViewModel.changeStatusExpandedValue(it)
                        },
                        selectedItem = activityFormViewModel.statusInput,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .weight(1f),
                        labelText = "Status"
                    )

                    FormDropdown(
                        dropdownMenuExpanded = todoListFormUIState.value.priorityDropdownExpandedValue,
                        onDismissRequest = {
                            todoListFormViewModel.dismissPriorityDropdownMenu()
                        },
                        dropdownItems = todoListFormUIState.value.priorityDropdownItems,
                        onDropdownItemClick = {
                            todoListFormViewModel.changePriorityInput(it)
                            todoListFormViewModel.checkNullFormValues()
                        },
                        onDropdownMenuBoxExpandedChange = {
                            todoListFormViewModel.changePriorityExpandedValue(it)
                        },
                        selectedItem = todoListFormViewModel.priorityInput,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .weight(1f),
                        labelText = "Priority"
                    )
                }

                // TODO: Date Picker
                FormDatePicker(
                    datePickerValue = todoListFormViewModel.dueDateInput,
                    showCalendarDialog = {
                        todoListFormViewModel.showDatePickerDialog(todoListFormViewModel.initDatePickerDialog(context))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        todoListFormViewModel.resetViewModel()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Gray)
                ) {
                    Text(text = stringResource(R.string.cancel_text))
                }

                when(submissionStatus) {
                    is StringDataStatusUIState.Loading -> CircleLoadingTemplate(
                        color = Color.Blue,
                        trackColor = Color.Transparent,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                    else -> Button(
                        onClick = {
                            // TODO: Check if the view model should update or create
                            if (todoListFormViewModel.isUpdate) {
                                todoListFormViewModel.updateTodo(token, getTodo =  {
                                    todoDetailViewModel.getTodo(token, todoListFormViewModel.todoId, navController, todoListFormViewModel.isUpdate)
                                })
                            } else {
                                todoListFormViewModel.createTodo(navController, token = token)
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = todoListFormUIState.value.saveButtonEnabled,
                        colors = ButtonDefaults.buttonColors(todoListFormViewModel.changeSaveButtonColor())
                    ) {
                        Text(text = stringResource(R.string.save_text))
                    }
                }

            }
        }
    }



@Preview(
    showBackground = true,
    showSystemUi = true
    )
@Composable
fun ActivityFormPreview() {
    ActivityFormView(
        activityFormViewModel = viewModel(factory = ActivityFormViewModel.Factory)
    )
}