package com.example.alp_visualprogramming.repository

import com.example.alp_visualprogramming.models.ExploreItineraryResponse
import com.example.alp_visualprogramming.models.GeneralResponseModel
import com.example.alp_visualprogramming.models.GetAllItineraryResponse
import com.example.alp_visualprogramming.models.GetCreatedItineraryResponse
import com.example.alp_visualprogramming.models.GetItineraryResponse
import com.example.alp_visualprogramming.models.ItineraryRequest
import com.example.alp_visualprogramming.models.UserResponse
import com.example.alp_visualprogramming.models.UserRoleResponse
import com.example.alp_visualprogramming.service.ItineraryAPIService
import retrofit2.Call

interface ItineraryRepository {
    fun getAllItineraries(token: String): Call<GetAllItineraryResponse>

    fun getAllInvitedItineraries(token: String): Call<GetAllItineraryResponse>

    fun createItinerary(token: String, title: String): Call<GetCreatedItineraryResponse>

    fun getItinerary(token: String, todoId: Int): Call<GetItineraryResponse>

    fun updateItinerary(token: String, id: Int, title: String): Call<GeneralResponseModel>

    fun deleteItinerary(token: String, todoId: Int): Call<GeneralResponseModel>

    fun exploreItineraries(token: String): Call<ExploreItineraryResponse>


}

class NetworkItineraryRepository(
    private val itineraryAPIService: ItineraryAPIService
): ItineraryRepository {
    override fun getAllItineraries(token: String): Call<GetAllItineraryResponse> {
        return itineraryAPIService.getAllItinerary(token)
    }

    override fun getAllInvitedItineraries(token: String): Call<GetAllItineraryResponse> {
        return itineraryAPIService.getAllInvitedItinerary(token)
    }

    override fun createItinerary(
        token: String,
        title: String,
    ): Call<GetCreatedItineraryResponse> {
        return itineraryAPIService.createItinerary(
            token,
            ItineraryRequest(title)
        )
    }

    override fun exploreItineraries(token: String): Call<ExploreItineraryResponse> {
        return itineraryAPIService.exploreItineraries(token)
    }

    override fun getItinerary(token: String, id: Int): Call<GetItineraryResponse> {
        return itineraryAPIService.getItinerary(token, id)
    }



    override fun updateItinerary(
        token: String,
        id: Int,
        title: String
    ): Call<GeneralResponseModel> {
        return itineraryAPIService.updateItinerary(token, id, ItineraryRequest(title))
    }

    override fun deleteItinerary(token: String, todoId: Int): Call<GeneralResponseModel> {
        return itineraryAPIService.deleteItinerary(token, todoId)
    }
}