package com.teamteem.util

import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream
import javax.faces.bean.ManagedBean

@ManagedBean(name = "randomNumbers")
class RandomNumbers {

    /**
     * @return A number between 1 and 10.
     */
    fun number(): Int {
        return Random().nextInt(9) + 1
    }

    /**
     * @param length The length of the numbers.
     * @return A list of random numbers between 1 and 10 of length `length`.
     */
    fun numbers(length: Int): List<Int> {

        return IntStream.range(0, length) // From 0 to length,
                .map { this.number() } // Generate random numbers.
                .boxed() // Box them into Integer,
                .collect(Collectors.toList())// And make it an ArrayList.
    }

    fun numbers(): List<Int> {
        return this.numbers(5)
    }

    companion object {

        /*
        Small tests.
        TODO make unit tests.
        */
        @JvmStatic
        fun main(args: Array<String>) {
            assert(RandomNumbers().numbers(10).size == 10)
        }
    }

}
