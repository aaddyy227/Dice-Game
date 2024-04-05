package com.dicegame.controller

import com.dicegame.dto.PlayerRegisterRequest
import com.dicegame.model.Player
import com.dicegame.model.Transaction
import com.dicegame.repository.PlayerRepository
import com.dicegame.repository.TransactionRepository
import com.dicegame.service.PlayerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/players")
class PlayerController(
    val playerRepository: PlayerRepository,
    val transactionRepository: TransactionRepository,
    val playerService: PlayerService
) {

    @PostMapping("/register")
    fun registerPlayer(@RequestBody player: PlayerRegisterRequest): ResponseEntity<Player> {
        return ResponseEntity.ok(playerService.register(player))

    }

    @GetMapping("/{playerId}/transactions")
    fun getPlayerTransactions(@PathVariable playerId: Long): ResponseEntity<List<Transaction>> {
        val transactions = transactionRepository.findByPlayerId(playerId)
        return ResponseEntity.ok(transactions)
    }


    @GetMapping("/all")
    fun getAllPlayers(): ResponseEntity<List<Player>> {
        val players = playerRepository.findAll()
        return ResponseEntity.ok(players)
    }

}