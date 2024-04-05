package com.dicegame.controller

import com.dicegame.dto.BetRequest
import com.dicegame.dto.BetResult
import com.dicegame.service.GameService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest(GameController::class)
class GameControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var gameService: GameService

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    @Test
    fun `placeBet endpoint returns BetResult on valid request`() {
        // Given
        val betRequest = BetRequest(playerId = 1L, betAmount = 100, chosenNumber = 5)
        val betResult = BetResult(
            playerId = betRequest.playerId,
            oldBalance = 1000,
            newBalance = 1100,
            betAmount = betRequest.betAmount,
            chosenNumber = betRequest.chosenNumber,
            generatedNumber = 5,
            winAmount = 100,
            betOutcome = "WIN"
        )

        given(gameService.placeBet(betRequest)).willReturn(betResult)

        // When & Then
        mockMvc.perform(post("/game/placebet")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(betRequest)))
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(betResult)))
    }
}
