# Lambdas And Functional Interfaces

        - Lambda Expressions
        - Functional Interfaces
        - Method References

- Functional programming is the way of writing code more declarative than imperative. It specifies what to do not how to do it.
- Examples for declarative languages: SQL, all functional programming languages
- Functional Programming uses Lambda to write the code.
## Lambda Expressions
- A Lambda Expression is a block of code that passed around
- In java we can think of lambda as an unnamed method inside the anonymous class.
- Lambdas works based on the concept called **_Deferred Execution_** means code is specified now will run later

__Examples:__

````java
public record Bird(String name, boolean canFly, boolean canSwim) {
}

interface BirdChecker {
    //tests on the bird object
    boolean test(Bird b);
}

public class MainApp {

    static void flyChecker(Bird bird, BirdChecker checker) {
        System.out.println(" Is "+bird.name()+" Can Fly? " + checker.test(bird));
    }

    public static void main(String[] args) {
        BirdChecker checker = new BirdChecker() {
            @Override
            public boolean test(Bird b) {
                return b.canFly();
            }
        };
        
        Bird bird = new Bird("Sparrow", true, false);
        
        print(bird, checker);
    }

}
````

- In the above code we have an interface `BirdChecker` with a single abstract method `boolean test(Bird b);`
- Whenever we want to use `BirdChecker` as a dependency then we have to create an implementation class either Anonymous or regular class
- From Java-8 onwards if any interface follows Single Abstract Method (SAM) Rule that is called as **_Functional Interface_**
- Whenever we see a `Functional Interface` instead of creating an implementation class, we can simpy use lambda expressions.
- Let us use Lambda in the above code:
````java
public record Bird(String name, boolean canFly, boolean canSwim) {
}

interface BirdChecker {
    //tests on the bird object
    boolean test(Bird b);
}

public class MainApp {

    static void flyChecker(Bird bird, BirdChecker checker) {
        System.out.println(" Is "+bird.name()+" Can Fly? " + checker.test(bird));
    }

    public static void main(String[] args) {
        print(new Bird("Sparrow", true, false), b->b.canFly());
    }

}
````
````java
   b -> b.canFly();
````
- above is a lambda expression


### Lambda Syntax Rules:
- In Lambda Expression Syntax we see majorly 3 parts

        1. Parameters & Parentheses 
        2. Arrow (->)
        3. Body ({})
__1. Parameters & Parentheses:__

__Parentheses are optional:__ Only one parameter without specifying the parameter type

__Parentheses are Mandatory:__

      - Whenever lambda has no params 
      - When we specify the parameter type
      - More than one parameters

__2. Lambda Body:__
    
    - curly braces are optional {}: When only one statemnt inside the body
    - More than one statements curly braces are mandatory.
    - Without return: follow only curly braces rule
    - With return: 
            - with single statement + curly braces:  then mandatory to use return keyword+end with semicolon
            - with single statement + no curly braces:  then should not use return keyword
            - with multiple statements: 
                - Mandatory to use curly braces
                - If return is required then include return keyword and it should end with semicolon(;) 

__3. Variables Scope In Lambdas:__



| Lambda                              | Valid/Invalid | Reason                                                                                                                                  |
|-------------------------------------|---------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| __Parameters & Parenthesis__        |               |                                                                                                                                         |
| ()->true                            | Valid         | Can be mapped to BooleanSupplier SAM                                                                                                    |
| s ->{}                              | Valid         | Can be mapped to  Consumer SAM                                                                                                          |
| (s) ->{}                            | Valid         | Can be mapped to  Consumer SAM                                                                                                          |
| (String s) -> {}                    | Valid         | Can be mapped to  Consumer SAM                                                                                                          |
| (x,y) -> {}                         | Valid         | Can be mapped to BiConsumer SAM                                                                                                         |
| (String x, String y) -> {}          | Valid         | Can be mapped to BiConsumer SAM                                                                                                         |
|                                     |               |                                                                                                                                         |
| -> true                             | Invalid       | **Missing parentheses:**  Lambda has No params, So Parentheses are mandatory                                                            |
| String s -> {}                      | Invalid       | **Missing parentheses:**  When we specified the type, Parentheses are mandatory                                                         |
| x,y -> {}                           | Invalid       | **Missing parentheses:**  Lambda has more than one params, Parentheses are mandatory                                                    |
| ()->{}                              | Invalid       | **No Functional Interface**  No functional interface with the given SAM syntax                                                          |
|                                     |               |                                                                                                                                         |
| __Lambda Body__                     |               |                                                                                                                                         |
| ()->true                            | Valid         | Curly braces optional for single statement                                                                                              |
| s ->{ s+"hello";s+"world";}         | Valid         | Curly braces mandatory for more than one statement                                                                                      |
| s-> s.startsWith("hello")           | Valid         | Single statement without curly braces return keyword should not be used                                                                 |
| s-> {return s.startsWith("hello");} | Valid         | Single statement with curly braces + return keyword + semicolon is mandatory                                                            |
|                                     |               |                                                                                                                                         |
| s -> s+"hello";s+"world";           | Invalid       | __Missing Curly Braces__ More than one statements, curly braces are mandatory                                                           |
| s-> return s.startsWith("hello");   | Invalid       | __Invalid Usage of Return Statement__ Single statement without curly braces then should not use return keyword                          |
| s-> {s.startsWith("hello")}         | Invalid       | __Missing Return Statement__ + __Missing Semicolon:__ Single statement with curly braces return keyword + semicolon is mandatory        |
| s-> {return s.startsWith("hello")}  | Invalid       | __Missing Semicolon:__  Single statement with curly braces return keyword + semicolon is mandatory                                      |
| var a = s->true                     | Invalid       | __Less Context to infer the type:__ Java needs clear context to infer the functional interface type, so var type won't work for lambdas |
|                                     |               |                                                                                                                                         |
|                                     |               |                                                                                                                                         |
|                                     |               |                                                                                                                                         |
|                                     |               |                                                                                                                                         |



## Functional Interfaces:
- Any interface that follows (Single Abstract Method) SAM rule we call it as a **_Functional Interface_**
- When we are creating the functional interface, we can use optional annotation `@FunctionalInterface`
- Why not mandatory because of backward compatibility, we want to use Runnable, Comparator, ..etc interface as Functional Interfaces.
- Advantage of  `@FunctionalInterface` annotation: if you mark your interface as  `@FunctionalInterface` then you cannot add more than one Abstract Method, if you add you will get compilation error.
- Always remember that SAM rule is making an interface as a Functional Interface not the annotation. Annotation is optional

#### Adding java.lang.Object methods without violating SAM Rule:
- Functional Interfaces can declare object methods as an abstract methods without violating SAM rule.
- Why?: Any class that implements the interface by default it will have the implementations of java.lang.Object methods it means
- Either it can override or default implementations are available in the super class java.lang.Object
````java
@FunctionalInterface
interface FlyChecker {
    boolean canFly(Bird b);
  //java.lang.Object Methods
    String toString(); 
    boolean equals(Object o);
    int hashCode();
}
//Still, it is a valid Functional Interface
````
- Invalid Functional Interface:
````java
@FunctionalInterface
interface FlyChecker {
    String toString(); 
}
//It is considered toString() is not a abstract method and as No SAM in this functional interface it does not compile
//Unexpected @FunctionalInterface annotation  FlyChecker is not a functional interface no abstract method found in interface FlyChecker
````

