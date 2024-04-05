package com.dicegame.service

import com.dicegame.dto.LeaderboardView
import com.dicegame.exception.NoBetsPlacedException
import com.dicegame.repository.PlayerRepository
import com.dicegame.repository.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class LeaderboardService(
    private val transactionRepository: TransactionRepository,
    private val playerRepository: PlayerRepository
) {
    @Transactional
    fun getLeaderboard(): List<LeaderboardView> {
        val topFive = PageRequest.of(0, 5)

        // Fetch the top five players' winnings
        val playerWinnings = transactionRepository.findPlayersTotalWinnings(topFive)
        return if (playerWinnings.isEmpty()) {
            throw NoBetsPlacedException("There are no winners yet!")
        } else {
            // Map the result to LeaderboardView
            playerWinnings.map { (playerId, totalWinnings) ->
                val username = getUsernameById(playerId)
                LeaderboardView(username, totalWinnings.toInt())
            }
        }
    }

    private fun getUsernameById(playerId: Long): String {
        return playerRepository.findById(playerId)
            .orElseThrow { NoSuchElementException("No player found for ID: $playerId") }
            .username
    }
}
