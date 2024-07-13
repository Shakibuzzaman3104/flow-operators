package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    normalFlowTest()
    flowOfTest()
    asFlowTest()
    channelFlowTest()
}

fun normalFlowTest() = runBlocking {
    val data = flow { (1..5).forEach { emit(it) } }
    println("flow{}")
    data.collect { println(it) }
}

fun flowOfTest() = runBlocking {
    val data = flowOf(1, 2, 3, 4, 5)
    println("flowOf{}")
    data.collect { println(it) }
}

fun asFlowTest() = runBlocking {
    val data = (1..5).asFlow()
    println("asFlow{}")
    data.collect { println(it) }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun channelFlowTest() = runBlocking {
    val data = channelFlow { (1..5).forEach { send(it) } }
    println("channelFlow{}")
    data.collect { println(it) }
}