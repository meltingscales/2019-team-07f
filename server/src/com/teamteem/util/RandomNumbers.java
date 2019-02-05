package com.teamteem.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("WeakerAccess")
@ManagedBean(name = "randomNumbers")
public class RandomNumbers {

    /**
     * @return A number between 1 and 10.
     */
    public int number() {
        return new Random().nextInt(9) + 1;
    }

    /**
     * @param length The length of the numbers.
     * @return A list of random numbers between 1 and 10 of length `length`.
     */
    public List<Integer> numbers(int length) {

        return IntStream.range(0, length) // From 0 to length,
                .map(i -> this.number()) // Generate random numbers.
                .boxed() // Box them into Integer,
                .collect(Collectors.toList()); // And make it an ArrayList.
    }

    public List<Integer> numbers() {
        return this.numbers(10);
    }

    /*
    Small tests.
    TODO make unittests.
    */
    public static void main(String[] args) {
        assert (new RandomNumbers().numbers(10).size() == 10);
    }

}
