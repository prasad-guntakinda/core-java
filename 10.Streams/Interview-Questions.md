<details>

<summary>What are intermediate and terminal operations in the Stream API? Give examples.</summary>

**Intermediate operations** in the Stream API return a new stream and are used to transform or filter elements (e.g., map, filter, sorted). They are lazy and do not trigger processing until a terminal operation is invoked.

**Terminal operations** produce a result or a side-effect (e.g., collect, forEach, reduce, count). They trigger the processing of the stream pipeline.

**Examples:**

```java
List<String> words = Arrays.asList("apple", "banana", "cherry");

// Intermediate operations: filter and map
Stream<String> filtered = words.stream()
    .filter(w -> w.length() > 5)   // intermediate
    .map(String::toUpperCase);     // intermediate

// Terminal operation: collect
List<String> result = filtered.collect(Collectors.toList());
System.out.println(result); // Output: [BANANA, CHERRY]
```

</details>

---


<details>
<summary>What is the difference between map() and flatMap()?</summary>

- **map()** transforms each element of a stream into another object, returning a stream of the same structure (e.g., Stream<Stream<T>> if mapping to streams).
- **flatMap()** both transforms and flattens the result, so if each element is mapped to a stream, flatMap() merges all those streams into a single stream (e.g., Stream<T>).

- **map()** is used when you want to apply a function to each element and keep the structure.
- **flatMap()** is used when each element produces multiple results (like a list or stream), and you want a single, flat stream of all results.
- Use **flatMap()** when your mapping function returns a stream or collection, and you want to avoid nested structures.
- **In short:**  ``map() = transform & flatMap() = transform + flatten ``

```java

// Example of map()
List<String> words = Arrays.asList("hello", "world");
List<Integer> lengths = words.stream()
    .map(String::length)
    .collect(Collectors.toList());
System.out.println("Lengths: " + lengths); // Output: [5, 5]

// Example of flatMap()
List<String> sentences = Arrays.asList("hello world", "java stream");
List<String> allWords = sentences.stream()
    .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
    .collect(Collectors.toList());
System.out.println("All words: " + allWords); // Output: ["hello", "world", "java", "stream"]

```

</details>

---

<details>
<summary>What are reduction operations and how do reduce() and collect() differ?</summary>

Reduction operations process a stream of elements and combine them into a single result. They “reduce” the stream to a summary value, such as a sum, average, concatenation, or a collection. Common reduction operations include <code>sum()</code>, <code>min()</code>, <code>max()</code>, <code>count()</code>, <code>reduce()</code>, and <code>collect()</code>.

<strong>How do reduce() and collect() differ?</strong>

- <code>reduce()</code>:
  - Used for combining stream elements into a single value (e.g., sum, product, concatenation).
  - Takes a binary operator (and optionally an identity value).
  - Returns a single result (e.g., an <code>int</code>, <code>String</code>, or custom object).
  - Example:
    ```java
    int sum = numbers.stream().reduce(0, Integer::sum); // or (a, b) -> a + b
    ```

- <code>collect()</code>:
  - Used for mutable reduction, typically to accumulate elements into a collection (like a <code>List</code>, <code>Set</code>, or <code>Map</code>), or to build a complex result.
  - Takes a <code>Collector</code> (like <code>Collectors.toList()</code>).
  - Returns a collection or another complex result.
  - Example:
    ```java
    List<String> upper = words.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());
    ```

<strong>Summary:</strong>
- Use <code>reduce()</code> for combining elements into a single value.
- Use <code>collect()</code> for accumulating elements into a collection or building a more complex result.

<strong>Examples:</strong>

```java
// Example of reduce(): Summing a list of numbers
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
    .reduce(0, Integer::sum); // or (a, b) -> a + b
System.out.println("Sum: " + sum); // Output: 15

// Example of collect(): Collecting elements into a List
List<String> words = Arrays.asList("java", "stream", "api");
List<String> upper = words.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
System.out.println("Uppercase words: " + upper); // Output: [JAVA, STREAM, API]
```

</details>

---

<details>
<summary>What is the difference between collect(Collectors.toList()) and collect(Collectors.toSet())?</summary>

- <code>collect(Collectors.toList())</code> collects the stream elements into a <code>List</code>. A <code>List</code> preserves the order of elements and allows duplicates.
- <code>collect(Collectors.toSet())</code> collects the stream elements into a <code>Set</code>. A <code>Set</code> does not allow duplicates and does not guarantee order (unless you use a specific set implementation like <code>LinkedHashSet</code>).

**Example:**

```java
List<String> list = Stream.of("a", "b", "a").collect(Collectors.toList());
// list: ["a", "b", "a"]

Set<String> set = Stream.of("a", "b", "a").collect(Collectors.toSet());
// set: ["a", "b"] (order not guaranteed)
```

</details>

---

<details>
<summary>How does the peek() method work and when should you use it?</summary>

The <code>peek()</code> method in the Stream API is an intermediate operation that allows you to perform a side-effect (like logging or debugging) on each element as it flows through the stream pipeline, without modifying the elements.

- It is mainly used for debugging or inspecting the elements at a certain point in the stream.
- <code>peek()</code> does not trigger stream processing by itself; a terminal operation (like <code>collect()</code> or <code>forEach()</code>) is still required.

<strong>Example:</strong>

```java
List<String> result = Stream.of("one", "two", "three")
    .filter(s -> s.length() > 3)
    .peek(s -> System.out.println("Filtered value: " + s)) // side-effect
    .map(String::toUpperCase)
    .peek(s -> System.out.println("Mapped value: " + s))   // side-effect
    .collect(Collectors.toList());
// Output:
// Filtered value: three
// Mapped value: THREE
```

<strong>When to use:</strong>
Use <code>peek()</code> for debugging or logging intermediate values in a stream pipeline. Avoid using it for modifying data or producing side effects that affect program logic.

</details>

---

<details>
<summary>What is the difference between Stream.of() and Arrays.stream()?</summary>

- <code>Stream.of()</code> creates a stream from a fixed set of values or from an array (but treats the whole array as a single element if you pass it directly).
  - Example:
    ```java
    Stream<String> s1 = Stream.of("a", "b", "c"); // stream of 3 elements
    Stream<int[]> s2 = Stream.of(new int[]{1, 2, 3}); // stream of 1 element (the array)
    ```

- <code>Arrays.stream()</code> creates a stream from the elements of an array (primitive or object), treating each array element as a stream element.
  - Example:
    ```java
    int[] arr = {1, 2, 3};
    IntStream s3 = Arrays.stream(arr); // stream of 3 ints: 1, 2, 3
    ```

<strong>Summary:</strong>
- Use <code>Stream.of()</code> for a fixed set of values or when you want the array as a single element.
- Use <code>Arrays.stream()</code> to stream the elements of an array.

</details>

---

<details>
<summary>How do you handle parallelism in streams? What is parallelStream() and its caveats?</summary>

- Java streams can process data in parallel to leverage multi-core processors and improve performance for large data sets.
- You can create a parallel stream by calling <code>parallelStream()</code> on a collection or by calling <code>parallel()</code> on an existing stream.

<strong>Example:</strong>
```java
List<String> list = Arrays.asList("a", "b", "c", "d");
list.parallelStream()
    .forEach(System.out::println); // Elements may be printed in any order
```

<strong>Caveats of parallelStream():</strong>
- Parallel streams split the data and process chunks in multiple threads, so the order of processing is not guaranteed (unless you use <code>forEachOrdered</code>).
- Not all operations benefit from parallelism; for small data sets or simple operations, parallel streams can be slower due to thread overhead.
- Avoid using parallel streams when:
  - The source is not easily splittable (e.g., <code>LinkedList</code>).
  - The operations are not thread-safe or have side effects.
  - You need to preserve encounter order and use unordered operations.
- Always test and measure performance before using parallel streams in production.

<strong>Summary:</strong>
Use <code>parallelStream()</code> for large, CPU-intensive, stateless, and thread-safe operations, but be aware of potential issues with ordering, thread safety, and overhead.

</details>

---

<details>
<summary>What is the short-circuiting behavior in streams?</summary>

Short-circuiting in streams refers to the ability of certain operations to stop processing as soon as a condition is met, rather than processing all elements.

- <strong>Short-circuiting operations</strong> include methods like <code>anyMatch()</code>, <code>allMatch()</code>, <code>noneMatch()</code>, <code>findFirst()</code>, <code>findAny()</code>, and also <code>limit()</code> and <code>skip()</code>.
- These operations can terminate the stream pipeline early, improving performance by avoiding unnecessary computation.

<strong>Example:</strong>
```java
List<String> list = Arrays.asList("apple", "banana", "cherry");
boolean hasB = list.stream()
    .anyMatch(s -> s.startsWith("b")); // returns true after checking "apple" and "banana"
System.out.println(hasB); // Output: true
```

<strong>Summary:</strong>
Short-circuiting operations allow streams to finish processing early when the result is already determined, making them efficient for certain tasks.

</details>

---

<details>
<summary>What is the role of Collectors.groupingBy() and how do you perform multi-level grouping?</summary>

- <code>Collectors.groupingBy()</code> is used to group stream elements by a classifier function, returning a <code>Map</code> where the keys are the result of applying the classifier, and the values are Lists of items.
- It is similar to the SQL <code>GROUP BY</code> operation.

<strong>Example: Grouping by a single property</strong>
```java
List<String> words = Arrays.asList("apple", "banana", "apricot", "blueberry");
Map<Character, List<String>> grouped = words.stream()
    .collect(Collectors.groupingBy(w -> w.charAt(0)));
// grouped: {a=[apple, apricot], b=[banana, blueberry]}
```

<strong>Multi-level grouping:</strong>
You can nest <code>groupingBy()</code> to perform multi-level grouping (like SQL's GROUP BY multiple columns).

<strong>Example: Grouping by first letter, then by length</strong>
```java
Map<Character, Map<Integer, List<String>>> multiGrouped = words.stream()
    .collect(Collectors.groupingBy(
        w -> w.charAt(0),
        Collectors.groupingBy(String::length)
    ));
// multiGrouped: {a={5=[apple], 7=[apricot]}, b={6=[banana], 9=[blueberry]}}
```

<strong>Summary:</strong>
- <code>groupingBy()</code> is powerful for creating nested maps and organizing data hierarchically from streams.

</details>

---

<details>
<summary>What is the difference between groupingBy and partitioningBy in Java streams?</summary>

- <code>Collectors.groupingBy()</code> groups elements by a classifier function, producing a <code>Map&lt;K, List&lt;T&gt;&gt;</code> where <code>K</code> is the group key. It can create any number of groups based on the classifier.
  - Example: Grouping words by their length.
    ```java
    Map<Integer, List<String>> grouped = words.stream()
        .collect(Collectors.groupingBy(String::length));
    // e.g., {5=[apple], 6=[banana], 7=[apricot], 9=[blueberry]}
    ```

- <code>Collectors.partitioningBy()</code> splits elements into two groups (true/false) based on a predicate, producing a <code>Map&lt;Boolean, List&lt;T&gt;&gt;</code>.
  - Example: Partitioning words by whether they start with 'a'.
    ```java
    Map<Boolean, List<String>> partitioned = words.stream()
        .collect(Collectors.partitioningBy(w -> w.startsWith("a")));
    // {true=[apple, apricot], false=[banana, blueberry]}
    ```

<strong>Summary:</strong>
- <code>groupingBy()</code> can create multiple groups based on a classifier.
- <code>partitioningBy()</code> always creates two groups (true/false) based on a predicate.

</details>

---

<details>
<summary>What are the performance implications of chaining multiple stream operations?</summary>

- Stream operations are evaluated lazily; intermediate operations (like <code>map()</code>, <code>filter()</code>, <code>sorted()</code>) are not executed until a terminal operation is invoked.
- Chaining multiple intermediate operations does not create multiple passes over the data. Instead, the stream pipeline is fused, and elements flow through all operations in a single pass (vertical processing).
- This design is efficient, but:
  - Each element passes through all intermediate operations before the next element is processed.
  - Adding many complex operations can increase per-element processing time.
  - Some operations (like <code>sorted()</code> or <code>distinct()</code>) require buffering and can impact memory and performance, especially on large streams.
  - Short-circuiting operations (like <code>limit()</code>, <code>findFirst()</code>, <code>anyMatch()</code>) can stop processing early, improving performance.
- Avoid unnecessary or expensive operations in the pipeline, and be mindful of the order (e.g., filter before map when possible).

<strong>Summary:</strong>
- Chaining is efficient due to lazy evaluation and vertical processing.
- Expensive or stateful operations (like <code>sorted()</code>, <code>distinct()</code>) can impact performance.
- Use short-circuiting and order operations wisely for best results.

</details>

---

<details>
<summary>What is vertical processing in Java streams?</summary>

Vertical processing means that, in a stream pipeline, each element passes through all intermediate operations before the next element is processed. This is different from horizontal processing, where one operation would process all elements before moving to the next operation.

<strong>Example:</strong>
Suppose you have:
```java
List<String> words = Arrays.asList("apple", "banana", "cherry");
words.stream()
    .filter(w -> {
        System.out.println("filter: " + w);
        return w.length() > 5;
    })
    .map(w -> {
        System.out.println("map: " + w);
        return w.toUpperCase();
    })
    .forEach(System.out::println);
```

<strong>Output:</strong>
```
filter: apple
filter: banana
map: banana
BANANA
filter: cherry
map: cherry
CHERRY
```

Notice that for each element, <code>filter</code> and <code>map</code> are called in sequence before moving to the next element. This is vertical processing.

<strong>Summary:</strong>
- Each element flows through the entire pipeline before the next element is processed.
- This enables lazy evaluation and efficient short-circuiting.

</details>






