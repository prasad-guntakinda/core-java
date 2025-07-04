<details>
<summary>What is a lambda expression in Java and how is it different from an anonymous class?</summary>

A <strong>lambda expression</strong> is a concise way to represent an anonymous function (a block of code with parameters) in Java. It enables functional programming by allowing you to pass behavior as data.

- <strong>Lambda expression:</strong> <code>(parameters) -> expression or block</code>
- <strong>Anonymous class:</strong> An inline implementation of an interface or class, often verbose.

<strong>Differences:</strong>
- Lambdas are more concise and readable.
- Lambdas can only be used with functional interfaces (interfaces with a single abstract method).
- Anonymous classes can implement multiple methods and have state.
- Lambdas do not introduce a new scope for <code>this</code>; anonymous classes do.

<strong>Example:</strong>
```java
Runnable r1 = () -> System.out.println("Hello from lambda");
Runnable r2 = new Runnable() {
    public void run() {
        System.out.println("Hello from anonymous class");
    }
};
```

</details>

---

<details>
<summary>What are the rules for lambda syntax?</summary>

- Parentheses are optional for a single parameter: <code>x -> x * x</code>
- Parentheses required for multiple or no parameters: <code>(x, y) -> x + y</code>, <code>() -> 42</code>
- Braces and <code>return</code> are optional for single-expression bodies: <code>x -> x + 1</code>
- Braces and <code>return</code> required for multi-statement bodies:
  <code>(x, y) -> { int z = x + y; return z * 2; }</code>
- The parameter types can be omitted if they can be inferred.

</details>

---

<details>
<summary>What is a functional interface? Give examples from java.util.function.</summary>

A <strong>functional interface</strong> is an interface with exactly one abstract method. It can have default and static methods. Lambdas can be assigned to functional interfaces.

<strong>Examples from <code>java.util.function</code>:</strong>
- <code>Predicate&lt;T&gt;</code>: <code>boolean test(T t)</code>
- <code>Function&lt;T, R&gt;</code>: <code>R apply(T t)</code>
- <code>Consumer&lt;T&gt;</code>: <code>void accept(T t)</code>
- <code>Supplier&lt;T&gt;</code>: <code>T get()</code>
- <code>UnaryOperator&lt;T&gt;</code>: <code>T apply(T t)</code>
- <code>BinaryOperator&lt;T&gt;</code>: <code>T apply(T t1, T t2)</code>

</details>

---

<details>
<summary>Can lambda expressions throw checked exceptions?</summary>

Lambdas can throw checked exceptions only if the functional interface's abstract method declares them. Most standard functional interfaces in <code>java.util.function</code> do <strong>not</strong> declare checked exceptions, so you must handle or wrap them yourself.

<strong>Example:</strong>
```java
Function<String, String> f = s -> {
    try {
        return riskyMethod(s);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
};
```

</details>

---

<details>
<summary>How do lambdas interact with variable scope (effectively final)?</summary>

Lambdas can access variables from the enclosing scope only if they are <strong>effectively final</strong> (not modified after assignment). This is similar to anonymous classes.

<strong>Example:</strong>
```java
int x = 10;
Runnable r = () -> System.out.println(x); // OK
// x = 20; // Not allowed if used in lambda
```

</details>

---

<details>
<summary>How are lambdas implemented internally in JVM?</summary>

Lambdas in Java are implemented using the <code>invokedynamic</code> bytecode instruction. When you use a lambda, the JVM does not create an anonymous inner class. Instead, it uses a mechanism called <code>LambdaMetafactory</code> to generate a lightweight class at runtime. This approach is more efficient, allows for better performance optimizations (like caching), and enables serialization if the target type is serializable.

<strong>In summary:</strong>
- Lambdas use <code>invokedynamic</code> and <code>LambdaMetafactory</code>.
- No anonymous class is generated at compile time.
- The JVM creates the implementation dynamically at runtime.

<strong>Diagram:</strong>

```mermaid
flowchart TD
    A[Lambda Expression in Code] --> B[Compiler generates invokedynamic instruction]
    B --> C[At runtime: JVM uses LambdaMetafactory]
    C --> D[Synthetic class (lambda implementation) is created]
    D --> E[Instance of functional interface is returned]
```

</details>

---

<details>
<summary>What are method references and how do they differ from lambdas?</summary>

A <strong>method reference</strong> is a shorthand notation for a lambda expression that calls an existing method. It improves readability when a lambda simply calls a method.

- <strong>Lambda:</strong> <code>x -> System.out.println(x)</code>
- <strong>Method reference:</strong> <code>System.out::println</code>

<strong>Difference:</strong>
- Lambdas can contain custom logic; method references only refer to existing methods.
- Method references are more concise when you just want to call a method.

</details>

---

<details>
<summary>What are the types of method references in Java?</summary>

1. <strong>Static method reference:</strong> <code>Class::staticMethod</code>
   - Example: <code>Integer::parseInt</code>
2. <strong>Instance method of a particular object:</strong> <code>instance::method</code>
   - Example: <code>myList::size</code>
3. <strong>Instance method of an arbitrary object of a particular type:</strong> <code>Class::instanceMethod</code>
   - Example: <code>String::toLowerCase</code>
4. <strong>Constructor reference:</strong> <code>Class::new</code>
   - Example: <code>ArrayList::new</code>

</details>

---

<details>
<summary>When would you prefer method references over lambdas?</summary>

- Use method references when a lambda only calls an existing method and no extra logic is needed.
- They make code more concise and readable.
- Prefer lambdas if you need to add logic or combine multiple operations.

<strong>Example:</strong>
```java
List<String> list = Arrays.asList("a", "b");
list.forEach(System.out::println); // method reference
// vs
list.forEach(x -> System.out.println(x)); // lambda
```

</details>

---

<details>
<summary>What is the relationship between method references and functional interfaces?</summary>

- Method references can be used wherever a lambda can be used, i.e., as an implementation for a functional interface.
- The method referenced must match the signature of the functional interface's abstract method.
- Both method references and lambdas are interchangeable as implementations for functional interfaces.

<strong>Example:</strong>
```java
Function<String, Integer> f1 = s -> Integer.parseInt(s); // lambda
Function<String, Integer> f2 = Integer::parseInt;        // method reference
```

</details>

---




