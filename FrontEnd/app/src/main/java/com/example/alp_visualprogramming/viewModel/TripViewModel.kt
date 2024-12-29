//package com.example.alp_visualprogramming.viewModel
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.alp_visualprogramming.repository.UserRepository
//import com.example.alp_visualprogramming.view.uiState.DestinationDataStatusUIState
//import com.example.alp_visualprogramming.view.uiState.TripDataStatusUIState
//import com.google.gson.Gson
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.IOException
//
//class TripViewModel(
//    private val userRepository: UserRepository
//): ViewModel() {
//    var dataStatus: DestinationDataStatusUIState by mutableStateOf(DestinationDataStatusUIState.Start)
//        private set
//    val token: StateFlow<String> = userRepository.currentUserToken.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = ""
//    )
//
//
//    fun getAllTrips(token: String) {
//        viewModelScope.launch {
//            Log.d("token-home", "TOKEN AT HOME: ${token}")
//
//            dataStatus = TripDataStatusUIState.Loading
//
//            try {
//                val call = Repository.getAllTodos(token)
//                call.enqueue(object : Callback<GetAllTodoResponse> {
//                    override fun onResponse(call: Call<GetAllTodoResponse>, res: Response<GetAllTodoResponse>) {
//                        if (res.isSuccessful) {
//                            dataStatus = TodoDataStatusUIState.Success(res.body()!!.data)
//
//                            Log.d("data-result", "TODO LIST DATA: ${dataStatus}")
//                        } else {
//                            val errorMessage = Gson().fromJson(
//                                res.errorBody()!!.charStream(),
//                                ErrorModel::class.java
//                            )
//
//                            dataStatus = TodoDataStatusUIState.Failed(errorMessage.errors)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<GetAllTodoResponse>, t: Throwable) {
//                        dataStatus = TodoDataStatusUIState.Failed(t.localizedMessage)
//                    }
//
//                })
//            } catch (error: IOException) {
//                dataStatus = TodoDataStatusUIState.Failed(error.localizedMessage)
//            }
//        }
//    }
//}