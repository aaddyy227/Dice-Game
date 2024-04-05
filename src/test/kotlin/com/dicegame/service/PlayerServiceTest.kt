package com.dicegame.service

import com.dicegame.dto.PlayerRegisterRequest
import com.dicegame.model.Player
import com.dicegame.model.Wallet
import com.dicegame.repository.PlayerRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus

internal class PlayerServiceTest {

    private lateinit var playerRepository: PlayerRepository
    private lateinit var playerService: PlayerService

    @BeforeEach
    fun setUp() {
        playerRepository = Mockito.mock(PlayerRepository::class.java)
        playerService = PlayerService(playerRepository)
    }

    @Test
    fun `register should save and return player when username does not exist`() {
        val player = PlayerRegisterRequest(name = "John", surname = "Doe", username = "john.doe")
        `when`(playerRepository.findByUsername("john.doe")).thenReturn(null)

        val response = playerService.register(player)

        assertEquals(player.username, response.username)
        assertEquals(1000,response.wallet.balance)
    }

    @Test
    fun `register should throw IllegalArgumentException when username already exists`() {
        // Arrange
        val existingPlayer = Player(id = null, name = "John", surname = "Doe", username = "john.doe", wallet = Wallet())
        val playerRequest = PlayerRegisterRequest(name = "John", surname = "Doe", username = "john.doe")

        `when`(playerRepository.findByUsername("john.doe")).thenReturn(existingPlayer)

        assertThrows(IllegalArgumentException::class.java) {
            playerService.register(playerRequest)
        }
    }

}