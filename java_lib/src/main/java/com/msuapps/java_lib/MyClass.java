package com.msuapps.java_lib;

/*public class MyClass {
    public static void main(String[] args) {

        for (int i = 0; i <= 10; i = i + 2) {
            System.out.println(i);
        }
    }
}*/       //basic ForLoop


/*public class MyClass {
    public static void main(String[] args) {
        String[] cars = {"Jaguar", "BMW", "Volvo", "Ferrari", "Skoda", "I20"};

        //for (int i = 0; i < cars.length; i++) {
          //  System.out.println(cars[i]);        //we're dng the same in forEach
        //}

        for (String i : cars) {
            System.out.println(i);
        }
    }
}*/       //forEach Loop


/*public class MyClass {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            if (i == 4) {
                break;
            }

            System.out.println(i);
        }
    }
}*/       //forLoop Break Statement.


/*public class MyClass {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {

            if (i == 4) {
                continue;
            }
            System.out.println(i);
        }

        System.out.println("Welcome to Android.."); //here "ln" means It'll Provide NextLine Empty ToLook GoodView ForNextOutput.
        System.out.print(1 + 2);
        System.out.print(false);
    }
}*/       //forLoop Continue Statement. result: except 4 remaining digits output.


/*import java.util.ArrayList;
import java.util.Arrays;

public class MyClass {
    public static void main(String[] args) {
        String[] cars = {"Ferrari", "Lamborghini", "Mahindra XUV500", "Skoda", "Jaguar", "Volvo"};

        cars[0] = "Opel";
        System.out.println(cars[0]);

        int i = cars[1].length();               //length.
        System.out.println(cars[1] + " word letters length is: " + i);


        //Adding new values in existing array using arrayList.
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(cars));
        arrayList.add("Hyundai");
        arrayList.add("Honda");
        arrayList.add("Rolls Royce");

        cars = arrayList.toArray(cars);

        for (String a : cars) {
            System.out.println(a);
        }

    }
}*/       //taking position of value-index, length, adding new values in the existing array


/*public class MyClass {
    public static void myMethod(String fName, double age) {
        System.out.println(fName + "Uddin" + " is : " + age);
    }

    public static void main(String[] args) {
        myMethod("late(NanaSaab) - Nizam", 86);
        myMethod("Saif", 29.11);                       //methodWithArgumentsPassedInIt.
        myMethod("Salah", 2.10);
    }
}*/       //methodWithParametersPassedInIt.


/*public class MyClass {

    public static int myMethod(int x, int y) {

        return x + y;
    }

    public static void main(String[] args) {

        System.out.println(myMethod(14, 14));
    }
}*/       //Method with Return values  - inWch WeDon't have void InMethods Instead WeGet PrimitiveDataTypes(int,char..etc)


/*public class MyClass {

    public static void checkAge(int x) {
        if (x < 18) {
            System.out.println("Access denied - You are not old enough! :" + x);
        }
        else {
            System.out.println("Access granted - You are old enough! :" + x);
        }
    }

    public static void main(String[] args) {
        checkAge(20);
        checkAge(14);
    }
}*/       //Method with If...Else statements.


/*public class MyClass {

    public static int myMethod(int x, int y) {
        return x + y;
    }

    public static double myMethod(double x, double y) {
        return x + y;
    }

    public static void main(String[] args) {
        int z1 = myMethod(5, 10);
        System.out.println(z1);

        double z2 = myMethod(10.99, 5.50);
        System.out.println(z2);
    }
}*/       //Method Overloading, meansSame MethodName WeCan OverloadIt(reuse) But MustHave Different Parameters.


/*public class MyClass {
    int x = 5;

    public static void main(String[] args) {
        MyClass myObj = new MyClass();

        System.out.println(myObj.x);
    }
}*/       //Objects & Classes


/*public class MyClass {
    public static void myStaticMethod() {
        System.out.println("Static methods can be called without creating objects");
    }
                                                                //differenceBetween Static & Public Methods
    public void myPublicMethod() {
        System.out.println("Public methods must be called by creating objects");
    }

    public static void main(String[] args) {
        myStaticMethod();

        MyClass myObj = new MyClass();
        myObj.myPublicMethod();
    }
}  */       //Java Modifiers.


/*public class MyClass {
    int modelYear;
    String modelName;

    public MyClass(int year, String name) {
        modelYear = year;
        modelName = name;
    }

    public static void main(String[] args) {
        MyClass myCar = new MyClass(1992, "Jaguar");
        System.out.println(myCar.modelName + "  " + myCar.modelYear);
    }
}*/       //Constructor With Parameters


/*class Person {
    private String name;            //private = restricted access , EncapsulationProcess - ModelClass

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}

public class MyClass {

    public static void main(String[] args) {
        Person myObj = new Person();
        myObj.setName("Model Class which have Getter&Setters and its Officially Known in Java as ENCAPSULATION.");
        System.out.println(myObj.getName());
    }
}*/       //Getters&Setters


/*import java.util.Scanner;                   //ScannerIsJustAExampleToShowHowToImportTheThingsHere.

public class MyClass {

    public static void main(String[] args) {

        Scanner myScannerObj = new Scanner(System.in);

        System.out.println("Enter username:");

        String userName = myScannerObj.nextLine();

        System.out.println("UserName is: " + userName);

    }
}*/       //java Packages & API - UserInput


/*public class MyClass extends YourClass {           //hereWeAreUsingTwoClasses.usingOneClassAttributes,MethodsInAnotherClass
                                                    //wchIsKnownAsSubClass&SuperClass - " Inheritance Process ".
    String model = "I20";

    public static void main(String[] args) {
                                                        //subClass.
        MyClass myClass = new MyClass();

        myClass.vehicle();

        System.out.println(myClass.brand + "  " + myClass.model);
    }
}

class YourClass {                                       //superClass.
    String brand = "Hyundai";  //attribute or variable.

    public void vehicle() {
        System.out.println("Caar  Caar..!");        //method
    }
}*/       //Inheritance Process


/*class SuperClass {
      String job = "software Engg.";

      public void manager() {
          System.out.println("jobs mela Opened..!");
      }
}

public class MyClass extends SuperClass{
      String occupation = "Android Developer";

      public static void main(String[] args) {

          MyClass myClass = new MyClass();

          myClass.manager();

          System.out.println(myClass.job + "  " + myClass.occupation);
      }
}*/       //another Ex. for Inheritance as same above.


/*public class MyClass {
                                                    //Polymorphism means "many forms", and it occurs when we have many
    public static void main(String[] args) {        //classes that are related to each other by inheritance. source:w3Schools.com

        Animal animal = new Animal();
        Animal lion = new Lion();
        Animal cat = new Cat();

        animal.myAnimal();
        lion.myAnimal();
        cat.myAnimal();
    }
}                                       //Polymorphism. its a process whr multiClasses come together with related methods
                                             //by extending & gives output in Main Class.   source:myOwn.
class Animal {
    public void myAnimal() {
        System.out.println("Animals makes different Sounds");
    }
}

class Lion extends Animal {

    public void myAnimal() {
        System.out.println("Lion says something like Roar.. roar..!");
    }
}

class Cat extends Animal {
    public void myAnimal() {
        System.out.println("Cats says something like Meow.. meow..!");
    }
}*/       //Polymorphism process


/*public class MyClass {

    public static void main(String[] args) {

        OuterClass myOuterClass = new OuterClass();

        OuterClass.InnerClass myInnerClass = myOuterClass.new InnerClass();

        System.out.println(myOuterClass.x + myInnerClass.y);
    }
}

class OuterClass {
    int x = 20;

    class InnerClass {
        int y = 8;

    }
}*/       //InnerClasses - regular.


/*public class MyClass {
                                                        //hereWeCan'tAccessInnerClass&WillGetError,BcozOfPrivate.
    public static void main(String[] args) {

        OuterClass myOuterClass = new OuterClass();

        OuterClass.InnerClass myInnerClass = myOuterClass.new InnerClass();

        System.out.println(myOuterClass.x + myInnerClass.y);
    }
}

class OuterClass {
    int x = 20;

    private class InnerClass {
        int y = 8;

    }
}*/       //InnerClasses - private.


/*public class MyClass {
                                //differenceIsHereWhileCreatingObjectOfInnerClassWeDon'tTakeOuterClassObjectRef.
    public static void main(String[] args) {

        OuterClass myOuterClass = new OuterClass();

        OuterClass.InnerClass myInnerClass = new OuterClass.InnerClass();

        System.out.println(myOuterClass.x + myInnerClass.y);
    }
}

class OuterClass {
    int x = 20;

    static class InnerClass {
        int y = 8;
    }
}*/       //InnerClasses - static.


/*public class MyClass {

    public static void main(String[] args) {

        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass();

        System.out.println(innerClass.innerMethod());
    }
}

class OuterClass {
    int x = 28;

    class InnerClass {
        public int innerMethod() {
            return x;
        }
    }
}*/       //Access Outer Class From Inner Class. (InnerClasses Concept)


/*public class MyClass {
    public static void main(String[] args) {

        SecondClass secondClass = new SecondClass();

        secondClass.firstMethod();
        secondClass.secondMethod();
    }
}

interface Callbacks {
    void firstMethod();
                                            //eitherWeCanUseThisInterfaceOrJavaCallbacksInterfaceFileBothGivesSameResult.
    void secondMethod();             //ifWeUseJavaCallbacksThenThoseMethodsAreGettingActivatedWhileImplementingButNotHere.
}

class SecondClass implements JavaCallbacks {

    @Override
    public void firstMethod() {
        System.out.println("this is a 1st method of Interface process..");
    }

    @Override
    public void secondMethod() {
        System.out.println("this is a 2nd method of Interface process..");
    }
}*/       //Interface Process - whereAs Abstraction Process also have the same Concept.


/*import java.text.SimpleDateFormat;
import java.time.LocalDateTime;                 //importForDate&TimeWchGivesDefaultFormat
import java.time.format.DateTimeFormatter;      //importFormatterWhrWeCanFormatItInGoodView
import java.util.Calendar;
import java.util.Date;

public class MyClass {
    public static void main(String[] args) {
                                                                    
        LocalDateTime myDateTimeObj = LocalDateTime.now();
        System.out.println("before Formatting : " + myDateTimeObj);

        DateTimeFormatter dateTimeFormatObj = DateTimeFormatter.ofPattern("dd:MMM:yyyy   hh:mm a");
        String formattedDateTime =  myDateTimeObj.format(dateTimeFormatObj);
        System.out.println("After formatting : " + formattedDateTime);
        //thisOneLearnedFromW3Schools-JavaCourse.


        //thisBelowOneLearnedFromAndroidStudio&AlsoWeUseThisOne.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String getTime = timeFormat.format(calendar.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("E,MMM dd yyyy");    //here E shows the Day. ex: Mon,Tue,Wed
        String getDate = dateFormat.format(calendar.getTime());

        System.out.println("Time: " + getTime + "\n" + "Date: " + getDate);


        //thisBelowOneLooksEasyCompareToAbove2Processes
        String timeDateStamp = new SimpleDateFormat("hh:mm a   &   E,MMM dd yyyy").format(new Date());
        String timeStamp = new SimpleDateFormat("hh:mm a").format(new Date());
        String dateStamp = new SimpleDateFormat("E,MMM dd yyyy").format(new Date());
        System.out.println("Time: " + timeStamp + " & Date: " + dateStamp);

    }

}*/       //Java Date and Time


/*import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyClass {

    public static void main(String[] args) {

        LocalDate localDateObj = LocalDate.now();
        LocalTime localTimeObj = LocalTime.now();
        LocalDateTime localDateTimeObj = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatterObj = DateTimeFormatter.ofPattern("hh:mm a  --  E,MMM dd yyyy");

        String format = localDateTimeObj.format(dateTimeFormatterObj);

        System.out.println(localDateObj);
        System.out.println(localTimeObj);
        System.out.println(localDateTimeObj);
        System.out.println(format);
    }
}*/       //Java Date and Time - another example


/*import java.util.ArrayList;
import java.util.Collections;

public class MyClass {
    public static void main(String[] args) {
        ArrayList<String> stringArrayList = new ArrayList<String>();

        stringArrayList.add("elephant");
        stringArrayList.add("cat");
        stringArrayList.add("yank");
        stringArrayList.add("zebra");
        stringArrayList.add("apple");
        stringArrayList.add("frog");
        stringArrayList.add("bat");
        stringArrayList.add("dog");

        stringArrayList.add(0, "animals");      //adding new item.
        //stringArrayList.set(0, "animals");    //replacing old item with new one by its position.

        System.out.println(stringArrayList);        //normalOutput

        System.out.println("\n" + stringArrayList.get(0) + "\n");       //takingCertainOutput

        Collections.sort(stringArrayList);          //SortingTheListItemsInOrder.

        for (int i = 0; i < stringArrayList.size(); i++) {       //dngForLoop
            System.out.println(stringArrayList.get(i));
        }
    }
}*/       //ArrayList - add, set, sort, get()-index,

/*hereWeHave"LinkedList"too, but bothAreSameOverAll.
toAddNewItemsInArrayListWeDo " list.add(0, "car") " WhereAsInLinkedListWeDo " list.addFirst("car") ".*/


/*import java.util.HashMap;

public class MyClass {

    public static void main(String[] args) {

        HashMap<String, String> stringHashMap = new HashMap<>();

        stringHashMap.put("Car", "Hyundai-i20");
        stringHashMap.put("Bike", "Pulsar DTSi 150cc");
        stringHashMap.put("BiCycle", "Neon");
        stringHashMap.put("Home", "Duplex");
        stringHashMap.put("HolidayTrip", "Maldives");
        stringHashMap.put("RegionalTrip", "Mecca Madina Sharif - In'sha allah");

        System.out.println(stringHashMap + "\n");                          //NormalOutput

        System.out.println(stringHashMap.get("Car") + "\n");               //gettingCertainValueByMentioningKey

        System.out.println(stringHashMap.remove("Car") + "\n");       //removingCertainValueByMentioningKey

        System.out.println(stringHashMap.size() + "\n");                   //gettingNo.ofItemsInTheList.

        for (String i : stringHashMap.keySet()) {                   //gettingKeysByFor-EachLoop.
            System.out.println(i);
        }

        System.out.println("");
        for (String i : stringHashMap.values()) {                   //gettingValuesByFor-EachLoop.
            System.out.println(i);
        }

        System.out.println("");
        for (String i : stringHashMap.keySet()) {                   //gettingKeys&ValuesByFor-EachLoop.
            System.out.println("keys: " + i + "    " + "Values: " + stringHashMap.get(i));
        }

    }
}*/       //HashMap


/*import java.util.HashSet;
                                                                //HashSet
public class MyClass {              //SimilarToHashMapButHereWeDon'tHaveKeyValues,onlySingleValuesToAdd.

    public static void main(String[] args) {
        HashSet<Integer> integerHashSet = new HashSet<>();

        integerHashSet.add(1);
        integerHashSet.add(3);
        integerHashSet.add(6);
        integerHashSet.add(8);
        integerHashSet.add(10);
        integerHashSet.add(15);
        integerHashSet.add(17);
        integerHashSet.add(0);
        integerHashSet.add(14);

        System.out.println(integerHashSet.contains(6) + "\n");          //checkingWhetherAnItemExistsIn a HashSetOrNot.
                                                                                //givesTrueOrFalse.
        for (int i = 0; i <= integerHashSet.size(); i++) {
            if (integerHashSet.contains(i)) {
                System.out.println(i + " was found in the Set");
            }
            else {
                System.out.println(i + " was not found in the Set");
            }
        }
    }
}*/       //HashSet


/*import java.util.ArrayList;
import java.util.Iterator;

public class MyClass {

    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Electrical Devices");
        arrayList.add("Mobile");
        arrayList.add("Laptop");
        arrayList.add("Television");
        arrayList.add("Trimmer");
        arrayList.add("Power Bank");
        arrayList.add("Alexa Speaker");
        arrayList.add("IronBox");

        Iterator<String> iterator = arrayList.iterator();

        //System.out.println(iterator.next());          //forGettingFirstValue.

        while (iterator.hasNext()) {
            System.out.println(iterator.next());         //forGettingTotalValues.
        }

        ArrayList<Integer> arrayList1 = new ArrayList<>();      //2nd ArrayList
        arrayList1.add(2);
        arrayList1.add(4);
        arrayList1.add(6);
        arrayList1.add(8);
        arrayList1.add(10);
        arrayList1.add(12);
        arrayList1.add(14);
        arrayList1.add(16);
        arrayList1.add(18);
        arrayList1.add(20);
        arrayList1.add(22);

        Iterator<Integer> iterator1 = arrayList1.iterator();

        while (iterator1.hasNext()) {
            int i = iterator1.next();

            if (i < 10) {
                iterator1.remove();                 //Removing Items from a Collection wch r below 10 in the list.
            }
        }

        System.out.println(arrayList1);                 //Remaining items Output..
    }
}*/       //Iterator


/*public class MyClass {              //Wrapper Classes - inWchWeHave1stLetterOfDataTypesInUpperCaseLetter&fullWord.
                                                //justForKnowledge-veryLessUseful.
    public static void main(String[] args) {

        Integer myInt = 100;
        Double myDouble = 99.99;
        Character myChar = 'A';

        System.out.println(myInt.intValue());
        System.out.println(myDouble.doubleValue());
        System.out.println(myChar.charValue());

        String myString = myInt.toString();
        System.out.println(myString.length());

    }
}*/       //Wrapper Classes -  JustForKnowledge-veryLessUseful.


/*public class MyClass {                      //Exceptions
                                        //inThisChapterWeHaveTryCatchMethodsTooWchIKnowAlready.
     public static void myMethod(int age) {
        if (age < 18) {
            throw new ArithmeticException("Access denied - you must be at least 18 years Old..");
        }
        else {
            System.out.println("Access granted - you are Old enough..");
        }
    }

    public static void main(String[] args) {
        myMethod(36);
        myMethod(15);
    }
}*/       //Exceptions


/*import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyClass {
    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("jobs | for | dev", Pattern.CASE_INSENSITIVE);  //Pattern Class - Defines a pattern (to be used in a search)

        Matcher matcher = pattern.matcher("looking for android developer Jobs");    //Matcher Class - Used to search for the pattern

        boolean matcherFound = matcher.find();  //find() returns true if the pattern was found in the string and false if not found.

        if (matcherFound) {
            System.out.println("Match has found");
        } else {
            System.out.println("<Match has not found..");
        }

    }

}*/       //RegEx - Regular Expressions.


/*public class MyClass {
    //Program For MultiDimensional Array in java
    public static void main(String[] args) {

        int[][] twoDimensionalArray = new int[2][3];
        int[][] twoDArray = new int[2][];
        twoDArray[0] = new int[2];
        twoDArray[1] = new int[2];
        twoDArray[0][0] = 1;
        twoDArray[0][1] = 2;
        twoDArray[1][0] = 4;
        twoDArray[1][1] = 5;

        String[][] str = {{"Hello", "Mr"}, {"How", "You"}};
        String[][] str1 = new String[2][2];

        str1[0][0] = "Hello";
        str1[0][1] = "Mr";
        str1[1][0] = "How";
        str1[1][1] = "You";

        System.out.println("The Result for twoDimensionalArray is:");

        twoDimensionalArray[0][0] = 0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                twoDimensionalArray[i][j] = i + j;
                System.out.print(+twoDimensionalArray[i][j]);
                System.out.print("\t");
            }
            System.out.print("\n");
        }

        System.out.println("The Result for twoDArray is:");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(twoDArray[i][j]);
                System.out.print("\t");
            }

            System.out.print("\n");
        }

        System.out.println("The Result for String Array is:");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(str[i][j]);
                System.out.print("\t");
            }
            System.out.print("\n");
        }

        System.out.println("The Result for String Array by initializing in different way is:");

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {

                System.out.print(str[i][j]);
                System.out.print("\t");

            }
            System.out.print("\n");
        }
    }
}*/       //MultiDimensional Array


/*public class MyClass {

    public static void main(String[] args) {

        System.out.println("Enter the value of x and y");
        //Define variables
        int x = 23;
        int y = 43;
        System.out.println("before swapping: " + x + " " + y);
        //Swapping
        x = x + y;  //x = 23+43 ie; x = 66
        System.out.println("x = " + x);

        y = x - y;  //y = 66 - 43 ie; y = 23
        System.out.println("y = " + y);

        x = x - y;  //x = 66 - 23 ie; x = 43
        System.out.println("x = " + x);

        System.out.println("After swapping: " + x + "  " + y);
    }
}*/       //Swapping two variables without using a third variable


/*import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyClass {
    private static final List<String> listOfData = Arrays.asList("Android", "Byte", "Collude", "Debug");
    private static final List<String> stringList = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println(listOfData);
        //Collections.reverse(listOfData);    //1st Approach using Collections.reverse
        //System.out.println(listOfData);
                                                               //lastPositionWeHave=3,   lastSizWeHave=4
        for (int i = listOfData.size() - 1; i >= 0; i--) {    //hereWeAreTakingListOfDataPositionByCalculatingListOfDataSize.
            System.out.println(i + "");
            //System.out.println(listOfData.get(i));        //2nd Approach.

            stringList.add(listOfData.get(i));
        }

        System.out.println(stringList);
    }
}*/       //Collections.reverse


/*import java.util.LinkedHashSet;

public class MyClass {

    public static void main(String[] args) {
        int[] numbersArray = {1, 2, 5, 9, 9, 3, 4, 1, 1, 2};

        removeDuplicates(numbersArray);
    }

    public static void removeDuplicates(int[] a) {
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<Integer>();

        // adding elements to LinkedHashSet
        for (int i = 0; i < a.length; i++) {
            hashSet.add(a[i]);
        }

        // Print the elements of LinkedHashSet
        System.out.print(hashSet);
    }
}*/       //Removing Duplicates from the int Array using LinkedHashSet.