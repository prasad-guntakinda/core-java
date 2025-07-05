# Java-17 Feature Interview Questions

<details>
<summary>What are sealed classes and interfaces in Java 17? Why are they useful?</summary>
Sealed classes and interfaces restrict which other classes or interfaces can extend or implement them, using the <code>permits</code> clause. This provides better control over the type hierarchy and enables safer, more maintainable code.
</details>
<hr>

<details>
<summary>How do you declare a sealed class or interface? Give an example.</summary>
<strong>Example:</strong>

```java
public sealed interface Shape permits Circle, Rectangle {}
public final class Circle implements Shape {}
public final class Rectangle implements Shape {}
```
</details>
<hr>

<details>
<summary>What are the possible modifiers for permitted subclasses of a sealed class or interface?</summary>
Permitted subclasses must be declared as <code>final</code> (cannot be extended), <code>sealed</code> (can further restrict subtypes), or <code>non-sealed</code> (removes sealing for that branch).
</details>
<hr>

<details>
<summary>What is pattern matching for instanceof in Java 17? How does it improve code?</summary>
Pattern matching for <code>instanceof</code> allows you to test and cast an object in a single step, reducing boilerplate and improving readability.
<strong>Example:</strong>

```java
if (obj instanceof String s) {
    System.out.println(s.toUpperCase());
}
```
</details>
<hr>

<details>
<summary>What are records in Java? Are there any changes in Java 17?</summary>
Records are a special kind of class for modeling immutable data. They automatically provide equals, hashCode, toString, and a compact constructor. Java 17 made records a standard feature (no longer preview).
<strong>Example:</strong>

```java
public record Person(String name, int age) {}
```
</details>
<hr>

<details>
<summary>What is the new switch expression feature in Java 17?</summary>
Switch expressions (standardized in Java 14, but widely used in Java 17) allow switch to return a value and use arrow labels, making code more concise and less error-prone.
<strong>Example:</strong>

```java
String result = switch (day) {
    case MONDAY, FRIDAY -> "Work";
    case SATURDAY, SUNDAY -> "Rest";
    default -> "Unknown";
};
```
</details>
<hr>

<details>
<summary>What is the difference between sealed, non-sealed, and final in Java 17?</summary>
- <strong>sealed:</strong> Restricts which classes can extend/implement.
- <strong>non-sealed:</strong> Removes sealing for that branch, allowing further extension.
- <strong>final:</strong> Cannot be extended at all.
</details>
<hr>

<details>
<summary>What are some other notable features or improvements in Java 17?</summary>
- Strong encapsulation of JDK internals
- New API enhancements (e.g., <code>RandomGenerator</code> interface)
- Deprecation and removal of older features (e.g., Applet API)
- Performance and security improvements
</details>
<hr>

<details>
<summary>Explain Java records and their constructors with examples</summary>

<strong>What is a record?</strong>
A record is a special class in Java (introduced in Java 16, standardized in Java 17) designed to model immutable data. Records automatically provide implementations for <code>equals()</code>, <code>hashCode()</code>, <code>toString()</code>, and accessors for their fields.

<strong>Basic Syntax:</strong>

```java
public record Person(String name, int age) {}
```
- This defines a class <code>Person</code> with final fields <code>name</code> and <code>age</code>, and generates a constructor, accessors, and utility methods.

<strong>Constructors in Records:</strong>
1. <strong>Canonical Constructor:</strong>
   - The default constructor that takes all record components as parameters.
   - You can override it to add validation or custom logic.

```java
public record Person(String name, int age) {
    public Person {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
    }
}
```
- The parameter list is omitted; the body is for validation or logic.

2. <strong>Compact Constructor:</strong>
   - Same as the canonical constructor, but you only write the body (parameters are implied).
   - The constructor body runs after the fields are assigned, and you can add validation or normalization logic.

```java
public record Product(String name, double price) {
    public Product {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        name = name.trim(); // You can also normalize or modify values
    }
}
```

3. <strong>Custom Constructors:</strong>
   - You can define additional constructors, but they must delegate to the canonical constructor.

```java
public record Person(String name, int age) {
    public Person(String name) {
        this(name, 0); // Delegates to canonical constructor
    }
}
```

<strong>Summary:</strong>
- Records are concise, immutable data carriers.
- The canonical constructor can be customized for validation.
- Additional constructors must delegate to the canonical one.
- All fields are final and set at construction time.

</details>
<hr>

<details>
<summary>What is the difference between canonical and compact constructors in Java records?</summary>

<strong>Canonical Constructor:</strong>
- The canonical constructor has a parameter list that matches the record components exactly.
- You must explicitly declare all parameters and assign them to the fields (unless you use the compact form).
- Used when you want to customize parameter names or add logic before field assignment.

<strong>Example:</strong>

```java
public record Person(String name, int age) {
    public Person(String name, int age) {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        this.name = name;
        this.age = age;
    }
}
```

<strong>Compact Constructor:</strong>
- The compact constructor omits the parameter list; it is implicitly the record components.
- Field assignment is done automatically before the constructor body runs.
- Used for validation or normalization after fields are assigned.

<strong>Example:</strong>

```java
public record Product(String name, double price) {
    public Product {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        name = name.trim();
    }
}
```

<strong>Summary:</strong>
- Use the <strong>canonical constructor</strong> when you need full control over parameters and field assignment.
- Use the <strong>compact constructor</strong> for concise validation or logic, letting the compiler handle field assignment.

</details>
<hr>

