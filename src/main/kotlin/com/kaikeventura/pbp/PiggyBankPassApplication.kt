package com.kaikeventura.pbp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PiggyBankPassApplication

fun main(args: Array<String>) {
	runApplication<PiggyBankPassApplication>(*args)
}
