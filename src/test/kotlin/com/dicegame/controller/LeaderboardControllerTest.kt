package com.dicegame.controller

import com.dicegame.dto.LeaderboardView
import com.dicegame.dto.PlayerWinnings
import com.dicegame.exception.NoBetsPlacedException
import com.dicegame.repository.TransactionRepository
import com.dicegame.service.LeaderboardService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(LeaderboardController::class)
class LeaderboardControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var leaderboardService: LeaderboardService
    @MockBean
    private lateinit var transactionRepository: TransactionRepository
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `getLeaderboard returns a list of PlayerWinnings`() {
        val leaderboard = listOf(
            LeaderboardView("testPlayer", 1500),
            LeaderboardView("testplayer2", 1200)
        )

        given(leaderboardService.getLeaderboard()).willReturn(leaderboard)

        mockMvc.perform(get("/leaderboard")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(leaderboard)))
    }

}
