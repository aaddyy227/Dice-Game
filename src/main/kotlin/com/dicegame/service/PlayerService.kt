package com.dicegame.service

import com.dicegame.dto.PlayerRegisterRequest
import com.dicegame.model.Player
import com.dicegame.model.Wallet
import com.dicegame.repository.PlayerRepository
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerRepository: PlayerRepository
) {

    @Transactional
    fun register(player: PlayerRegisterRequest): Player {

        playerRepository.findByUsername(player.username)?.let {
            throw IllegalArgumentException("Username already exists: ${player.username}")
        }

        val savedPlayer = Player(
            id = null,
            surname = player.surname,
            name = player.name,
            username = player.username
        )

        playerRepository.save(savedPlayer)
        return savedPlayer
    }

}