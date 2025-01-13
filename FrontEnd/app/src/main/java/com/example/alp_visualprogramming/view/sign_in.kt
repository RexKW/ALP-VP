package com.example.alp_vp.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.alp_visualprogramming.R
import com.example.alp_visualprogramming.ui.theme.ALP_VisualProgrammingTheme
import com.example.alp_visualprogramming.view.template.AuthenticationButton
import com.example.alp_visualprogramming.view.template.AuthenticationOutlinedTextField
import com.example.alp_visualprogramming.view.template.AuthenticationQuestion
import com.example.alp_visualprogramming.view.template.PasswordOutlinedTextField
import com.example.alp_visualprogramming.viewModel.AuthenticationViewModel

@Composable
fun SignIn(authenticationViewModel: AuthenticationViewModel,
           modifier: Modifier = Modifier,
           navController: NavHostController,
           context: Context
) {
    val loginUIState by authenticationViewModel.authenticationUIState.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBF7E7))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header dengan Gambar dan Teks Bersama
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            // Background Gambar
            Image(
                painter = painterResource(id = R.drawable.frame_2),
                contentDescription = "Top Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            // Teks di Atas Gambar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 25.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Begin Your",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
//                        .background(Color(0x80000000)) // Sedikit transparan di balik teks
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Text(
                    text = "Journey",
                    color = Color(0xFFEE4482),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
//                        .background(Color(0x80000000)) // Sedikit transparan di balik teks
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Form Sign In
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthenticationOutlinedTextField(
                inputValue = authenticationViewModel.emailInput,
                onInputValueChange = {
                    authenticationViewModel.changeEmailInput(it)
                    authenticationViewModel.checkLoginForm()
                },
                labelText = stringResource(id = R.string.emailText),
                placeholderText = stringResource(id = R.string.emailText),
                leadingIconSrc = painterResource(id = R.drawable.ic_email),
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardType = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                onKeyboardNext = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            Spacer(modifier = Modifier.padding(5.dp))

            PasswordOutlinedTextField(
                passwordInput = authenticationViewModel.passwordInput,
                onPasswordInputValueChange = {
                    authenticationViewModel.changePasswordInput(it)
                    authenticationViewModel.checkLoginForm()
                },
                passwordVisibilityIcon = painterResource(id = loginUIState.passwordVisibilityIcon),
                labelText = stringResource(id = R.string.passwordText),
                placeholderText = stringResource(id = R.string.passwordText),
                onTrailingIconClick = {
                    authenticationViewModel.changePasswordVisibility()
                },
                passwordVisibility = loginUIState.passwordVisibility,
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardImeAction = ImeAction.None,
                onKeyboardNext = KeyboardActions(
                    onDone = null
                )
            )

            AuthenticationButton(
                buttonText = stringResource(id = R.string.loginText),
                onButtonClick = {
                    authenticationViewModel.loginUser(navController = navController)
                },
                buttonModifier = Modifier
                    .padding(top = 30.dp),
                textModifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 15.dp),
                buttonEnabled = loginUIState.buttonEnabled,
                buttonColor = authenticationViewModel.checkButtonEnabled(loginUIState.buttonEnabled),
                userDataStatusUIState = authenticationViewModel.dataStatus,
                loadingBarModifier = Modifier
                    .padding(top = 30.dp)
                    .size(40.dp)
            )
        }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigasi ke Sign Up
        AuthenticationQuestion(
            questionText = stringResource(id = R.string.don_t_have_an_account_yet_text),
            actionText = stringResource(id = R.string.sign_up_text),
            onActionTextClicked = {
                authenticationViewModel.resetViewModel()
                navController.navigate("Register") {
                    popUpTo("Login") {
                        inclusive = true
                    }
                }
            },
            rowModifier = Modifier
                .align(Alignment.CenterHorizontally),
        )

    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignInPreview() {
//
//        SignIn(onNavigateToSignUp = {})
//
//}
