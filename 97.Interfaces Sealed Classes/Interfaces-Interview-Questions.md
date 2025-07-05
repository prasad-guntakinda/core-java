# Interfaces, Sealed Interfaces and all Enhancements After Java 8

<details>
<summary>What are the new enhancements for interfaces after Java 8?</summary>

Java 8 and later versions introduced several important enhancements to interfaces, making them more powerful and flexible:

<strong>Java 8 Enhancements:</strong>
- <strong>Default Methods:</strong> Interfaces can have concrete methods with a default implementation using the <code>default</code> keyword.
    - Allows interfaces to evolve without breaking existing implementations.
    - Example:

```java
interface MyInterface {
    default void show() {
        System.out.println("Default implementation");
    }
}
```
- <strong>Static Methods:</strong> Interfaces can have static methods (called on the interface, not on instances).
    - Example:

```java
interface MyInterface {
    static void util() {
        System.out.println("Static method in interface");
    }
}
```
- <strong>Functional Interfaces:</strong> Interfaces with a single abstract method can be used as lambda expressions (e.g., Runnable, Comparator).
    - Marked with <code>@FunctionalInterface</code> annotation (optional but recommended).

<strong>Java 9 Enhancements:</strong>
- <strong>Private Methods:</strong> Interfaces can have private methods (both static and instance) to share code between default methods.
    - Example:

```java
interface MyInterface {
    private void helper() {
        System.out.println("Private method");
    }
    default void show() {
        helper();
    }
}
```

<strong>Java 16 (Preview) and Java 17+:</strong>
- <strong>Sealed Interfaces:</strong> Interfaces can be declared <code>sealed</code> to restrict which classes or interfaces can implement them.
    - Example:

```java
public sealed interface Shape permits Circle, Rectangle {}
public final class Circle implements Shape {}
public final class Rectangle implements Shape {}
```

<strong>Summary Table:</strong>
| Version | Enhancement         | Description                                    |
|---------|--------------------|------------------------------------------------|
| Java 8  | Default Methods    | Concrete methods with default implementation   |
| Java 8  | Static Methods     | Static utility methods in interfaces           |
| Java 8  | Functional Interfaces | Single abstract method, lambda support      |
| Java 9  | Private Methods    | Code sharing between default methods           |
| Java 17 | Sealed Interfaces  | Restrict which classes can implement interface |

</details>
<hr>

## Default & Static Methods:

<details>
<summary>What is a default method in an interface? Why was it introduced?</summary>
A default method is a concrete method in an interface, declared with the <code>default</code> keyword. It allows interfaces to provide a default implementation so that new methods can be added to interfaces without breaking existing implementations. This was introduced to support backward compatibility with evolving APIs (e.g., Java 8's new methods in <code>Collection</code> interfaces).
</details>
<hr>

<details>
<summary>Can interfaces have static methods? How are they different from default methods?</summary>
Yes, since Java 8, interfaces can have static methods. Static methods belong to the interface itself (not to instances) and can only be called using the interface name. Default methods are instance methods and can be overridden by implementing classes.
</details>
<hr>

<details>
<summary>What is a functional interface? How does it relate to lambda expressions?</summary>
A functional interface is an interface with exactly one abstract method. It can have any number of default or static methods. Functional interfaces are the basis for lambda expressions and method references in Java. Examples: <code>Runnable</code>, <code>Comparator</code>, <code>Callable</code>.
</details>
<hr>

<details>
<summary>What are private methods in interfaces? Why are they useful?</summary>
Since Java 9, interfaces can have private methods (both static and instance). These methods are used to share common code between default methods within the same interface, improving code reuse and maintainability. Private methods are not accessible to implementing classes.
</details>
<hr>


<details>
<summary>Can you override default methods? What happens if two interfaces provide the same default method?</summary>
Yes, implementing classes can override default methods. If a class implements two interfaces with the same default method, the class must override the method to resolve the conflict (the "diamond problem").
</details>
<hr>

<details>
<summary>What is the diamond problem in interfaces? Explain with an example.</summary>

The <strong>diamond problem</strong> occurs when a class implements two interfaces that both provide a default method with the same signature. The compiler cannot decide which default implementation to use, so the class must override the method to resolve the conflict.

<strong>Example:</strong>

```java
interface A {
    default void greet() {
        System.out.println("Hello from A");
    }
}

interface B {
    default void greet() {
        System.out.println("Hello from B");
    }
}

class C implements A, B {
    // Compilation error if greet() is not overridden
    @Override
    public void greet() {
        // You must explicitly choose or combine the implementations
        A.super.greet(); // or B.super.greet();
        // Or provide your own implementation
        System.out.println("Hello from C");
    }
}

public class DiamondProblemDemo {
    public static void main(String[] args) {
        C c = new C();
        c.greet();
    }
}
```
- If <code>C</code> does not override <code>greet()</code>, the compiler will report an error due to ambiguity.
- You can resolve the conflict by overriding the method and optionally calling one or both super-interface implementations.

<strong>Summary:</strong>
The diamond problem in interfaces is resolved in Java by requiring the implementing class to override the conflicting default method.

</details>
<hr>

<details>
<summary>Give an example of using a functional interface with a lambda expression.</summary>
<strong>Example:</strong>

```java
@FunctionalInterface
interface MyFunc {
    int operate(int a, int b);
}

MyFunc add = (a, b) -> a + b;
System.out.println(add.operate(2, 3)); // Output: 5
```
</details>
<hr>

## Sealed Interfaces:

<details>
<summary>What is a sealed interface in Java? Why would you use it?</summary>
A sealed interface (introduced in Java 17) restricts which classes or interfaces can implement it, using the <code>permits</code> clause. This provides better control over the type hierarchy and helps model domain constraints more safely.
</details>
<hr>

<details>
<summary>How do you declare and use a sealed interface? Give an example.</summary>
<strong>Example:</strong>

```java
public sealed interface Shape permits Circle, Rectangle {}
public final class Circle implements Shape {}
public final class Rectangle implements Shape {}
```
- Only <code>Circle</code> and <code>Rectangle</code> can implement <code>Shape</code>.
- Any attempt to implement <code>Shape</code> in another class will result in a compilation error.
</details>
<hr>

<details>
<summary>What are the permitted subtypes of a sealed interface? How do you specify them?</summary>
Permitted subtypes are the only classes or interfaces allowed to implement or extend the sealed interface. They are specified using the <code>permits</code> keyword in the interface declaration.
</details>
<hr>

<details>
<summary>What are the possible modifiers for permitted subclasses of a sealed interface?</summary>
Permitted subclasses must be declared as <code>final</code> (cannot be extended), <code>sealed</code> (can further restrict subtypes), or <code>non-sealed</code> (removes sealing for that branch).
<strong>Example:</strong>

```java
public sealed interface Vehicle permits Car, Truck {}
public final class Car implements Vehicle {} // No further subclassing
public sealed class Truck implements Vehicle permits Pickup, Lorry {}
public non-sealed class Pickup extends Truck {} // Pickup can be subclassed freely
public final class Lorry extends Truck {}
```
</details>
<hr>

<details>
<summary>What are the benefits of using sealed interfaces?</summary>
- Better control over the type hierarchy
- Improved maintainability and security
- Enables exhaustive pattern matching (future Java features)
- Prevents unwanted or accidental implementations
</details>
<hr>

<details>
<summary>When should you use sealed vs non-sealed classes/interfaces? Explain with examples.</summary>

<strong>Sealed:</strong>
- Use <strong>sealed</strong> when you want to strictly control which classes or interfaces can extend or implement a type.
- This is useful for modeling closed hierarchies, enforcing domain rules, and enabling exhaustive checks (e.g., in switch expressions).
- Example use cases: algebraic data types, domain models where only a fixed set of subtypes is allowed.

<strong>Example:</strong>

```java
public sealed interface Payment permits CreditCard, Paypal {}
public final class CreditCard implements Payment {}
public final class Paypal implements Payment {}
```
- Only <code>CreditCard</code> and <code>Paypal</code> can implement <code>Payment</code>.

<strong>Non-sealed:</strong>
- Use <strong>non-sealed</strong> when you want to open up a branch of a sealed hierarchy for further extension.
- This is useful when you want to restrict most subtypes, but allow one or more to be extended freely (e.g., for plugins, extensibility, or frameworks).

<strong>Example:</strong>

```java
public sealed interface Vehicle permits Car, Truck {}
public final class Car implements Vehicle {}
public non-sealed class Truck implements Vehicle {} // Truck can be subclassed by anyone
```
- <code>Car</code> cannot be subclassed, but <code>Truck</code> can be extended by any class.

<strong>Summary:</strong>
- Use <strong>sealed</strong> for strict, closed hierarchies.
- Use <strong>non-sealed</strong> to allow further extension in specific branches of a sealed hierarchy.
- This gives you fine-grained control over your type system and helps model your domain more accurately.

</details>
<hr>