package com.dicegame.controller

import com.dicegame.dto.PlayerRegisterRequest
import com.dicegame.model.Player
import com.dicegame.model.Transaction
import com.dicegame.model.Wallet
import com.dicegame.repository.PlayerRepository
import com.dicegame.repository.TransactionRepository
import com.dicegame.service.PlayerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@WebMvcTest(PlayerController::class)
class PlayerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var playerRepository: PlayerRepository

    @MockBean
    private lateinit var transactionRepository: TransactionRepository

    @MockBean
    private lateinit var playerService: PlayerService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `registerPlayer creates and returns player on valid request`() {
        val playerRequest = PlayerRegisterRequest(name = "John", surname = "Doe", username = "johndoe")
        val player = createTestPlayer(name = "John", surname = "Doe", username = "johndoe")
        given(playerService.register(any())).willReturn(player)

        val requestJson = objectMapper.writeValueAsString(playerRequest)

        mockMvc.perform(
            post("/players/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(player)))
    }


    @Test
    fun `getPlayerTransactions returns player transactions`() {
        val playerId = 1L
        val transactions = listOf(
            Transaction(1L, playerId, 100, "deposit", LocalDateTime.of(2024, 4, 5, 12, 0)),
            Transaction(2L, playerId, -50, "withdrawal", LocalDateTime.of(2024, 4, 6, 14, 30))
        )
        given(transactionRepository.findByPlayerId(playerId)).willReturn(transactions)

        mockMvc.perform(
            get("/players/{playerId}/transactions", playerId)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$").isArray)
            .andExpect(jsonPath("\$.length()").value(transactions.size))
            .andExpect(jsonPath("\$[0].id").value(transactions[0].id))
            .andExpect(jsonPath("\$[0].playerId").value(transactions[0].playerId))
            .andExpect(jsonPath("\$[0].amount").value(transactions[0].amount))
            .andExpect(jsonPath("\$[1].id").value(transactions[1].id))
            .andExpect(jsonPath("\$[1].playerId").value(transactions[1].playerId))
            .andExpect(jsonPath("\$[1].amount").value(transactions[1].amount))
    }

    @Test
    fun `getAllPlayers returns all players`() {
        val players = listOf(
            createTestPlayer(id = 1L, name = "John", surname = "Doe", username = "johndoe"),
            createTestPlayer(id = 2L, name = "Jane", surname = "Smith", username = "janesmith")
        )
        given(playerRepository.findAll()).willReturn(players)

        mockMvc.perform(
            get("/players/all")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$").isArray)
            .andExpect(jsonPath("\$.length()").value(players.size))
            .andExpect(jsonPath("\$[0].id").value(players[0].id))
            .andExpect(jsonPath("\$[0].name").value(players[0].name))
            .andExpect(jsonPath("\$[0].surname").value(players[0].surname))
            .andExpect(jsonPath("\$[0].username").value(players[0].username))
            .andExpect(jsonPath("\$[1].id").value(players[1].id))
            .andExpect(jsonPath("\$[1].name").value(players[1].name))
            .andExpect(jsonPath("\$[1].surname").value(players[1].surname))
            .andExpect(jsonPath("\$[1].username").value(players[1].username))
    }

    @Test
    fun `registerPlayer returns bad request when username already exists`() {
        val playerRequest = PlayerRegisterRequest(name = "John", surname = "Doe", username = "existing.user")
        given(playerService.register(any())).willThrow(IllegalArgumentException("Username already exists: existing.user"))

        val requestJson = objectMapper.writeValueAsString(playerRequest)

        mockMvc.perform(
            post("/players/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(status().isBadRequest)
    }

    fun createTestPlayer(
        id: Long? = null,
        name: String = "John",
        surname: String = "Doe",
        username: String = "johndoe",
        wallet: Wallet = Wallet(1000)
    ): Player {
        return Player(id = id, name = name, surname = surname, username = username, wallet = wallet)
    }
}


