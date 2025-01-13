package com.example.alp_vp.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alp_visualprogramming.R
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alp_visualprogramming.view.template.AuthenticationButton
import com.example.alp_visualprogramming.view.template.AuthenticationOutlinedTextField
import com.example.alp_visualprogramming.view.template.AuthenticationQuestion
import com.example.alp_visualprogramming.view.template.PasswordOutlinedTextField
import com.example.alp_visualprogramming.viewModel.AuthenticationViewModel
import com.example.todolistapp.uiStates.AuthenticationStatusUIState

@Composable
fun SignUp(authenticationViewModel: AuthenticationViewModel,
           modifier: Modifier = Modifier,
           navController: NavHostController,
           context: Context
) {
    val registerUIState by authenticationViewModel.authenticationUIState.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(authenticationViewModel.dataStatus) {
        val dataStatus = authenticationViewModel.dataStatus
        if (dataStatus is AuthenticationStatusUIState.Failed) {
            Toast.makeText(context, dataStatus.errorMessage, Toast.LENGTH_SHORT).show()
            authenticationViewModel.clearErrorMessage()
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFBF7E7))
    ) {
        Image(
            painter = painterResource(id = R.drawable.group_47),
            contentDescription = "Top Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 25.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Create Your",
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Text(
                text = "Account",
                color = Color(0xFFEE4482),
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp)
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(200.dp))

            OutlinedTextField(
                value = authenticationViewModel.emailInput,
                onValueChange = {
                    authenticationViewModel.changeEmailInput(it)
                    authenticationViewModel.checkRegisterForm()
                },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            AuthenticationOutlinedTextField(
                inputValue = authenticationViewModel.usernameInput,
                onInputValueChange = {
                    authenticationViewModel.changeUsernameInput(it)
                    authenticationViewModel.checkRegisterForm()
                },
                labelText = stringResource(id = R.string.usernameText),
                placeholderText = stringResource(id = R.string.usernameText),
                leadingIconSrc = painterResource(id = R.drawable.ic_username),
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardType = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                onKeyboardNext = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            PasswordOutlinedTextField(
                passwordInput = authenticationViewModel.passwordInput,
                onPasswordInputValueChange = {
                    authenticationViewModel.changePasswordInput(it)
                    authenticationViewModel.checkRegisterForm()
                },
                passwordVisibilityIcon = painterResource(id = registerUIState.passwordVisibilityIcon),
                labelText = stringResource(id = R.string.passwordText),
                placeholderText = stringResource(id = R.string.passwordText),
                onTrailingIconClick = {
                    authenticationViewModel.changePasswordVisibility()
                },
                passwordVisibility = registerUIState.passwordVisibility,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardImeAction = ImeAction.Next,
                onKeyboardNext = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            PasswordOutlinedTextField(
                passwordInput = authenticationViewModel.confirmPasswordInput,
                onPasswordInputValueChange = {
                    authenticationViewModel.changeConfirmPasswordInput(it)
                    authenticationViewModel.checkRegisterForm()
                },
                passwordVisibilityIcon = painterResource(id = registerUIState.confirmPasswordVisibilityIcon),
                labelText = stringResource(id = R.string.confirm_password_text),
                placeholderText = stringResource(id = R.string.confirm_password_text),
                onTrailingIconClick = {
                    authenticationViewModel.changeConfirmPasswordVisibility()
                },
                passwordVisibility = registerUIState.confirmPasswordVisibility,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardImeAction = ImeAction.None,
                onKeyboardNext = KeyboardActions(
                    onDone = null
                )
            )


            Spacer(modifier = Modifier.height(24.dp))

            AuthenticationButton(
                buttonText = stringResource(id = R.string.registerText),
                onButtonClick = {
                    authenticationViewModel.registerUser(navController)
                },
                buttonModifier = Modifier
                    .padding(top = 30.dp),
                textModifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 15.dp),
                buttonEnabled = registerUIState.buttonEnabled,
                buttonColor = authenticationViewModel.checkButtonEnabled(registerUIState.buttonEnabled),
                userDataStatusUIState = authenticationViewModel.dataStatus,
                loadingBarModifier = Modifier
                    .padding(top = 30.dp)
                    .size(40.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthenticationQuestion(
                questionText = stringResource(id = R.string.already_have_an_account_text),
                actionText = stringResource(id = R.string.sign_in_text),
                onActionTextClicked = {
                    authenticationViewModel.resetViewModel()
                    navController.navigate("Login") {
                        popUpTo("Register") {
                            inclusive = true
                        }
                    }
                },
                rowModifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignUpPreview() {
//
//        SignUp(initialEmail = "test@example.com", navController = rememberNavController())
//
//}
