package org.example

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    mapTest()
    flatMapLatestTest()
    flatMapConcatTest()
    flatMapMergeTest()
    filterTest()
    transformTest()
    onEachTest()
    catchTest()
    flowOnTest()
    zipTest()
    conflatedTest()
    distinctUntilChangedTest()
    retryTest()
}

/**
 * Runs a test that demonstrates the usage of the `map` operator on a flow.
 * The `map` operator transforms each emitted value by applying a given function.
 */
fun mapTest() = runBlocking {

    print("map = ")
    // Create a flow from a range of integers (1 to 5)
    val data = (1..5).asFlow()

    // Apply the `map` operator to transform each integer to a string with its position
    // Collect and print each transformed value
    data.map { "$it" }.collect { print("$it ") }

    println()
}

fun flatMapLatestTest() = runBlocking {
    print("flatMapLatest = ")
    val data = (1..5).asFlow()
    data.flatMapLatest {
        delay(500)
        flowOf(it)
    }.collect { print("$it ") }
    println()
}

fun flatMapConcatTest() = runBlocking {
    print("flatMapConcat = ")

    val data = (1..5).asFlow()
    data.flatMapConcat {
        delay(500)
        flowOf(it)
    }.collect { print("$it ") }
    println()
}

fun flatMapMergeTest() = runBlocking {
    print("flatMapMerge = ")
    val data = (1..5).asFlow()
    data.flatMapMerge {
        delay(500)
        flowOf(it)
    }.collect { print("$it ") }
    println()
}

fun filterTest() = runBlocking {
    print("filter = ")
    val data = (1..5).asFlow()
    data.filter {
        delay(500)
        it % 2 == 0
    }.collect { print("$it ") }
    println()
}

fun transformTest() = runBlocking {
    print("transformTest = ")
    val data = (1..5).asFlow()
    data.transform {
        delay(500)
        emit("multiplication: ${it * 2}")
        emit("Addition: ${it + 2}")
    }.collect { print("$it ") }
    println()
}

fun onEachTest() = runBlocking {
    print("onEachTest = ")
    val data = (1..5).asFlow()
    data.onEach {
        print("Before delay ")
        delay(500)
        print("After delay")
    }.collect { print(" $it ") }
    println()
}

fun catchTest() = runBlocking {
    print("onEachTest = ")
    val data = flow {
        emit(1)
        emit(2)
        emit(3)
        throw RuntimeException("Error Occurred")
        emit(4)
        emit(5)
    }
    data.catch {
        println("Caught an exception: $it")
    }.collect { print(" $it ") }
    println()
}


fun flowOnTest() = runBlocking {
    print("flowOnTest = ")
    val data = flow {
        emit(1)
        emit(2)
        emit(3)
        emit(4)
        emit(5)
    }.flowOn(Dispatchers.IO)
        .map { it * 2 }
    data.collect { print(" ${Thread.currentThread()} ") }
    println()
}

fun zipTest() = runBlocking {
    print("zipTest = ")
    val data1 = (1..5).asFlow()
    val data2 = (6..10).asFlow()
    data1.zip(data2) { a, b -> a + b }.collect { print("$it ") }
    println()
}

fun conflatedTest() = runBlocking {
    print("conflatedTest = ")
    val data = (1..5).asFlow()
    data.conflate().collect { print("$it ") }
    println()
}

fun distinctUntilChangedTest() = runBlocking {
    print("distinctUntilChangedTest = ")
    val data = listOf(1,2,3,3,4,4,5).asFlow()
    data.distinctUntilChanged().collect { print("$it ") }
    println()
}

fun retryTest() = runBlocking {
    var count = 0
    print("retryTest = ")
    val data = flow {
        emit(1)
        emit(2)
        throw RuntimeException("Error Occurred")
    }.catch {
        println("Caught an exception: $it")
    }
    data.retry {
        println("Retrying")
        it is RuntimeException && count++ < 2
    } .collect { print("$it ") }
    println()
}
