package com.myapps.androidconcepts.z_kotlin.helpers

/*fun main() {
                                            //DataTypes
    var name = "Saifuddin, "
    var id: Int = 543
    var dept: String?
    dept = ",  Computer Science"

    print("Hello World")
    println(",  Welcome to 40 days of Kotlin..")
    println(name + id + dept)
}*/

/*fun main() {
                                            //Taking Input From User
    println("Enter Name: ")
    var inputFullName = readLine()              //here readLine() works as editText.

    print("Enter RollNo.")
    var inputRollNum: Int = readLine()!!.toInt()

    print("Enter Dept: ")
    var inputDept = readLine()

    print("Enter College: ")
    var inputClg: String?
    inputClg = readLine()

    println(inputFullName + "\n" + inputRollNum + "\n" + inputDept + "\n" + inputClg + "\n\n")
}*/

/*fun main() {
                            //Datatype Conversion
    var a: Int = 10
    var b: Int?
    var c: String = "20"

    b = c.toInt()
    println("value of a : " + a)
    println("value of b : " + b)

    var d: Double = 3.99
    println("value of d : " + d.toInt())

}*/

/*fun main() {
                            //Null Value Rules
    var name: String?
    name = null
    println(name)
}*/

/*fun main() {
                            //stepwise Priorities would be taken in Integers.
                            //     1. ()           weCanAlsoRememberThisByOurOldMathConcept"BODMAS Rule"wchStandsFor
                            //     2. ^            B-Bracket,O-Of,D-Division,M-Multiplication
                            //     3. * , /        A-Addition,S-Subtraction
                            //     4. + , -
    var a: Int = 10
    var b: Int = 20
    var c: Int = 30

    var sum: Int?
    sum = a + b * c - 10
    println("Result of wrongFormat Sum is: $sum")

    sum = (a + b) * c - 10
    println("Result of rightFormat Sum is: $sum")
}*/

/*fun main() {
                    //if, elseIf Statements
    print("Enter Grade: ")
    var grade = readLine()!!.toDouble()

    if (grade >= 90.99) {
        println("Your Grade is A+")
    }
    else if (grade >= 80 && grade <= 90) {
        println("Your grade is A")
    }
    else if (grade >= 70 && grade <= 80) {
        println("Your grade is B")
    }
    else if (grade >= 60 && grade <= 70) {
        println("Your grade is C")
    }
    else{
        println("your Grade is D")
    }
}*/

/*fun main() {
                            //forLoop
    for (x in 0..5) {
        println("Hello Kotlin..")
    }


    for (x in 1..3) {       //nestedForLoop - meansWillHaveInnerLoopsToo.
        println("Outer Loop")
        for (y in 1..3) {
            println("Inner Loop")
        }
    }


    var x = 4                   //doWhileLoop

    do {
        println("doWhile Loop")
        x++
    }
    while (x <= 5)
}*/

/*fun main() {
                                //forLoopWithCondition
    for (x in 1..5) {
        if (x != 3) {
            println("Saifuddin")
        }
        println("Value of x: $x")
    }


    for (x in 1..5) {          //forLoopWithContinueStatement
        if (x == 3) {
            continue
            println("Saifuddin")
        }
        println("Value of x: $x")
    }

}*/

/*import kotlin.collections.ArrayList

fun main() {
    var arraylist = ArrayList<String>()             //arrayList

    arraylist.add("AndroidStudio")
    arraylist.add("Kotlin")
    arraylist.add("Java")
    arraylist.add("SQLite DB")
    arraylist.add("Shared Preference")
    arraylist.add("Core Java")
    arraylist.add("OOPS Concepts")
    arraylist.add("Firebase DB")

    println(arraylist)

    for (item in arraylist) {
        println(item)
    }
}*/

/*
fun main() {
    var result = sum(10, 20)
    println("Sum is: $result")

    result = sum(20, 30)
    println("Sum is: $result")

    display(50)

}

fun sum(x: Int, y: Int): Int {          //functions (methodsInJava)
    var result = x + y

    return result
}

fun display(a:Int):Unit {           //ifYouDon'tWantToPassReturnValueThenUseUnitFeature.
    println("Value passed is: $a")
}*/
