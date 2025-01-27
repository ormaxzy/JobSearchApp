package com.example.data.api

import com.example.data.models.JobResponseDto
import retrofit2.http.GET

interface JobSearchApi {
    @GET("u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun getJobData(): JobResponseDto
}
