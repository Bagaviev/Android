package com.example.fourthexample

/**
 * @author Bulat Bagaviev
 * @created 30.10.2023
 */
data class TestingEmptyCons(val abc: String) {
 val abg: Int = 9

 constructor() : this(abc = "sc")

}