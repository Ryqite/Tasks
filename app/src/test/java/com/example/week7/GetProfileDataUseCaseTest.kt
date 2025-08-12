package com.example.week7

import com.example.week9.Domain.Data.ProfileParametersData
import com.example.week9.Domain.ProfileParameters
import com.example.week9.Domain.UseCases.GetProfileDataUseCase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetProfileDataUseCaseTest {

    @Mock
    private lateinit var mockRepository: ProfileParameters

    private lateinit var getProfileDataUseCase: GetProfileDataUseCase
    @Before
    fun setUp() {
        getProfileDataUseCase = GetProfileDataUseCase(mockRepository)
    }
    @Test
    fun `getProfileDataUseCase's invoke should return data from repository`(){
        val testData = ProfileParametersData(
            img = 224,
            nickName = "Fila",
            fullName = "G T O"
        )
        whenever(mockRepository.getProfileData()).thenReturn(testData)
        val result = getProfileDataUseCase()
        assertEquals(result,testData)
        verify(mockRepository, times(1)).getProfileData()
    }

}