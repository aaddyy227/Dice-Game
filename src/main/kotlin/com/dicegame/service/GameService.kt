package com.dicegame.service

import com.dicegame.dto.BetRequest
import com.dicegame.dto.BetResult
import com.dicegame.exception.NotEnoughBalanceException
import com.dicegame.exception.UserIdNotFoundException
import com.dicegame.model.Bet
import com.dicegame.model.Player
import com.dicegame.repository.BetRepository
import com.dicegame.repository.PlayerRepository
import com.dicegame.utils.RandomNumberGenerator
import org.springframework.http.ResponseEntity

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.abs

@Service
class GameService(
    private val playerRepository: PlayerRepository,
    private val betRepository: BetRepository,
    private val randomNumberGenerator: RandomNumberGenerator,
    private val transactionService: TransactionService
) {
    @Transactional
    fun placeBet(betRequest: BetRequest): ResponseEntity<BetResult> {
        val player = playerRepository.findById(betRequest.playerId)
            .orElseThrow { UserIdNotFoundException("No player found with ID: ${betRequest.playerId}") }
        val oldBalance = player.wallet.balance
        if (oldBalance < betRequest.betAmount)
            throw NotEnoughBalanceException("You don't have enough balance to make this bet!")

        player.adjustBalance(-betRequest.betAmount)
        val generatedNumber = randomNumberGenerator.nextInt(1, 11)
        val winnings = calculateWinnings(betRequest.chosenNumber, generatedNumber, betRequest.betAmount)
        player.adjustBalance(winnings)

        val outcome = if (winnings > 0) "WIN" else "LOSS"
        val transactionType = if (outcome == "WIN") "WIN" else "LOSS"
        transactionService.recordTransaction(
            betRequest.playerId,
            if (outcome == "WIN") winnings else -betRequest.betAmount,
            transactionType
        )

        playerRepository.save(player)
        val netResult = winnings - betRequest.betAmount
        betRepository.save(Bet(betRequest.playerId, betRequest.betAmount.toLong(), betRequest.chosenNumber, netResult))
        val betResult = BetResult(
            playerId = betRequest.playerId,
            oldBalance = oldBalance,
            newBalance = player.wallet.balance,
            betAmount = betRequest.betAmount,
            chosenNumber = betRequest.chosenNumber,
            generatedNumber = generatedNumber,
            winAmount = if (outcome == "WIN") winnings else 0,
            betOutcome = outcome
        )
        return ResponseEntity.ok(betResult)
    }

    private fun calculateWinnings(chosenNumber: Int, generatedNumber: Int, betAmount: Int): Int =
        when (abs(chosenNumber - generatedNumber)) {
            0 -> betAmount * 10
            1 -> betAmount * 5
            2 -> betAmount / 2
            else -> 0
        }

    private fun Player.adjustBalance(amount: Int) {
        this.wallet.balance += amount
    }
}
