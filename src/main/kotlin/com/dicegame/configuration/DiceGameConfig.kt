package com.dicegame.configuration

import com.dicegame.utils.BetRandomNumberGenerator
import com.dicegame.utils.RandomNumberGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DiceGameConfig {
    @Bean
    fun randomNumberGenerator(): RandomNumberGenerator = BetRandomNumberGenerator()
}