package com.osaka.cashbalancerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CashBalancerApiApplication

fun main(args: Array<String>) {
    runApplication<CashBalancerApiApplication>(*args)
}
