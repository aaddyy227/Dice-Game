package com.dicegame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class DicegameApplication

fun main(args: Array<String>) {
    runApplication<DicegameApplication>(*args)
}
