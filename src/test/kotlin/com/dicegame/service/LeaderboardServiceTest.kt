package com.dicegame.service

import com.dicegame.dto.LeaderboardView
import com.dicegame.dto.PlayerWinnings
import com.dicegame.exception.NoBetsPlacedException
import com.dicegame.model.Player
import com.dicegame.model.Wallet
import com.dicegame.repository.PlayerRepository
import com.dicegame.repository.TransactionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageRequest

internal class LeaderboardServiceTest {

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @Mock
    private lateinit var leaderboardService: LeaderboardService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        leaderboardService = LeaderboardService(transactionRepository, playerRepository)
    }

    @Test
    fun `getLeaderboard should retrieve top two because of only two winning players`() {
        // Prepare mock data
        val playerWinningsList = listOf(
            PlayerWinnings(1L, 500L),
            PlayerWinnings(2L, 7800L)
        )
        val players = listOf(
            Player(1L, "player1", "player1surename", "player1", Wallet(500)),
            Player(2L, "player2", "player2surename", "player2", Wallet(7800))
        )
        val expectedLeaderboard = listOf(
            LeaderboardView("player1", 500),
            LeaderboardView("player2", 7800)
        )

        // Mock repository responses
        val topFivePageRequest = PageRequest.of(0, 5)
        `when`(transactionRepository.findPlayersTotalWinnings(topFivePageRequest)).thenReturn(playerWinningsList)
        `when`(playerRepository.findAllById(listOf(1L, 2L))).thenReturn(players)

        // Execute the test
        val actualLeaderboard = leaderboardService.getLeaderboard()

        // Assertions
        assertEquals(
            expectedLeaderboard,
            actualLeaderboard,
            "The leaderboard entries should match the expected results"
        )
    }
    @Test
    fun `getLeaderboard should retrieve top five leaderboard entries`() {
        val playerWinningsList = listOf(
            PlayerWinnings(1L, 500L),
            PlayerWinnings(2L, 7800L),
            PlayerWinnings(3L, 1500L),
            PlayerWinnings(4L, 3200L),
            PlayerWinnings(5L, 2400L)
        )
        val players = listOf(
            Player(1L, "player1name", "player1surname", "player1", Wallet(500)),
            Player(2L, "player2name", "player2surname", "player2", Wallet(7800)),
            Player(3L, "player3name", "player3surname", "player3", Wallet(1500)),
            Player(4L, "player4name", "player4surname", "player4", Wallet(3200)),
            Player(5L, "player5name", "player5surname", "player5", Wallet(2400))
        )
        val expectedLeaderboard = listOf(
            LeaderboardView("player1", 500),
            LeaderboardView("player2", 7800),
            LeaderboardView("player3", 1500),
            LeaderboardView("player4", 3200),
            LeaderboardView("player5", 2400)
        )

        // Mock repository responses
        val topFivePageRequest = PageRequest.of(0, 5)
        `when`(transactionRepository.findPlayersTotalWinnings(topFivePageRequest)).thenReturn(playerWinningsList)
        `when`(playerRepository.findAllById(listOf(1L, 2L, 3L, 4L, 5L))).thenReturn(players)

        // Execute the test
        val actualLeaderboard = leaderboardService.getLeaderboard()

        // Assertions
        assertEquals(
            expectedLeaderboard,
            actualLeaderboard,
            "The leaderboard entries should match the expected results"
        )
    }
    @Test
    fun `getLeaderboard throws NoBetsPlacedException when there are no winnings`() {
        // Arrange
        val emptyList = emptyList<PlayerWinnings>()
        val topFive = PageRequest.of(0, 5)
        `when`(transactionRepository.findPlayersTotalWinnings(topFive)).thenReturn(emptyList)

        // Act and Assert
        assertThrows(NoBetsPlacedException::class.java) {
            leaderboardService.getLeaderboard()
        }
    }
}