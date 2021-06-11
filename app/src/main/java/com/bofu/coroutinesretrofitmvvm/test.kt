package com.bofu.coroutinesretrofitmvvm

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {

        val time = measureTimeMillis {
            reduceOperator()
        }
        println(time)
    }
}


suspend fun reduceOperator(){

    val size = 10

    val factorial = (1..size).asFlow().reduce { accumulator, value ->
        delay(1000L)
        println("$accumulator $value")
        accumulator + value
    }
    println("factorial of $size is $factorial")

}