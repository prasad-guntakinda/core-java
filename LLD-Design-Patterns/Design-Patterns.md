# Design Pattern Interview Questions

<details>
<summary>Commonly Used Design Patterns in Java</summary>

Java developers frequently use a variety of design patterns to solve recurring software design problems. Here are some of the most commonly used patterns, grouped by category:

### 1. Creational Patterns
- **Singleton:** Ensures a class has only one instance and provides a global point of access.
- **Factory Method:** Defines an interface for creating an object, but lets subclasses alter the type of objects that will be created.
- **Abstract Factory:** Provides an interface for creating families of related or dependent objects without specifying their concrete classes.
- **Builder:** Separates the construction of a complex object from its representation.
- **Prototype:** Creates new objects by copying an existing object (the prototype).

### 2. Structural Patterns
- **Adapter:** Allows incompatible interfaces to work together.
- **Decorator:** Adds new functionality to an object dynamically.
- **Facade:** Provides a simplified interface to a complex subsystem.
- **Proxy:** Provides a surrogate or placeholder for another object to control access.
- **Composite:** Composes objects into tree structures to represent part-whole hierarchies.
- **Bridge:** Decouples an abstraction from its implementation so the two can vary independently.
- **Flyweight:** Reduces memory usage by sharing as much data as possible with similar objects.

### 3. Behavioral Patterns
- **Observer:** Defines a one-to-many dependency so that when one object changes state, all its dependents are notified.
- **Strategy:** Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
- **Command:** Encapsulates a request as an object, thereby letting you parameterize clients with different requests.
- **Template Method:** Defines the skeleton of an algorithm, deferring some steps to subclasses.
- **Iterator:** Provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation.
- **State:** Allows an object to alter its behavior when its internal state changes.
- **Chain of Responsibility:** Passes a request along a chain of handlers.
- **Mediator:** Defines an object that encapsulates how a set of objects interact.
- **Memento:** Captures and restores an object's internal state.
- **Visitor:** Lets you define a new operation without changing the classes of the elements on which it operates.

---

> These patterns are widely used in Java frameworks and libraries (e.g., Spring, Java Collections, Java I/O). Understanding them helps you write more maintainable, flexible, and robust code.

</details>
<hr>

<details>
<summary>How are design patterns segregated? On what basis?</summary>

Design patterns are typically segregated into three main categories based on the nature of the problem they solve and the aspect of software design they address:

### 1. Creational Patterns
- **Purpose:** Deal with object creation mechanisms, trying to create objects in a manner suitable to the situation.
- **Goal:** Abstract the instantiation process, making a system independent of how its objects are created, composed, and represented.
- **Examples:** Singleton, Factory Method, Abstract Factory, Builder, Prototype.

### 2. Structural Patterns
- **Purpose:** Concerned with how classes and objects are composed to form larger structures.
- **Goal:** Help ensure that if one part of a system changes, the entire structure of the system does not need to do the same.
- **Examples:** Adapter, Decorator, Facade, Proxy, Composite, Bridge, Flyweight.

### 3. Behavioral Patterns
- **Purpose:** Focus on communication between objects, what goes on between objects and how they operate together.
- **Goal:** Assign responsibilities between objects and encapsulate behavior in objects.
- **Examples:** Observer, Strategy, Command, Template Method, Iterator, State, Chain of Responsibility, Mediator, Memento, Visitor.

---

**Summary Table:**
| Category      | Focus Area                | Example Patterns                  |
|--------------|---------------------------|-----------------------------------|
| Creational   | Object creation           | Singleton, Factory, Builder       |
| Structural   | Object/class composition  | Adapter, Decorator, Facade        |
| Behavioral   | Object interaction        | Observer, Strategy, Iterator      |

> This segregation helps developers quickly identify the type of problem a pattern addresses and choose the right pattern for their design needs.

</details>
<hr>



<details>
<summary>What is the Strategy Design Pattern? Explain with an example.</summary>

**Strategy Pattern** is a behavioral design pattern that enables selecting an algorithm's behavior at runtime. It defines a family of algorithms, encapsulates each one, and makes them interchangeable. The client can choose which strategy to use without altering the code that uses it.

**Key Points:**
- Promotes the Open/Closed Principle by allowing new strategies to be added without modifying existing code.
- Useful when you have multiple related algorithms for a specific task and want to switch between them dynamically.

**Structure:**
- Strategy Interface: Declares a method that all concrete strategies must implement.
- Concrete Strategies: Implement the strategy interface with different algorithms.
- Context: Maintains a reference to a strategy object and delegates it to the strategy method.

**Example:**
```java
// Strategy interface
enum PaymentType { CREDIT_CARD, PAYPAL }

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}

// Usage
ShoppingCart cart = new ShoppingCart();
cart.setPaymentStrategy(new CreditCardPayment());
cart.checkout(100.0); // Paid 100.0 using Credit Card
cart.setPaymentStrategy(new PayPalPayment());
cart.checkout(50.0); // Paid 50.0 using PayPal
```

**Benefits:**
- Easily add new payment methods without changing ShoppingCart code.
- Switch strategies at runtime.

</details>
<hr>

<details>
<summary>What is the Iterator Design Pattern? Explain with an example.</summary>

**Iterator Pattern** is a behavioral design pattern that provides a way to access the elements of a collection sequentially without exposing its underlying representation.

**Key Points:**
- Decouples collection classes from traversal logic.
- Supports multiple traversals and custom iteration algorithms.
- Widely used in Java Collections Framework (e.g., `Iterator`, `ListIterator`).

**Structure:**
- Iterator Interface: Declares methods for traversing elements (e.g., `hasNext()`, `next()`).
- Concrete Iterator: Implements the iterator interface for a specific collection.
- Aggregate Interface: Declares a method to create an iterator.
- Concrete Aggregate: Implements the aggregate interface and returns an iterator instance.

**Example:**
```java
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

class NameRepository implements Iterable<String> {
    private List<String> names = new ArrayList<>();
    public void addName(String name) { names.add(name); }
    public Iterator<String> iterator() { return names.iterator(); }
}

// Usage
NameRepository repo = new NameRepository();
repo.addName("Alice");
repo.addName("Bob");
for (String name : repo) {
    System.out.println(name);
}
```

**Benefits:**
- Hides the internal structure of the collection.
- Supports multiple and custom iteration strategies.
- Makes code more readable and maintainable.

</details>
<hr>

<details>
<summary>What is the Adapter Design Pattern? Explain with an example.</summary>

**Adapter Pattern** is a structural design pattern that allows objects with incompatible interfaces to work together. It acts as a bridge between two incompatible interfaces by converting the interface of a class into another interface that a client expects.

**Key Points:**
- Useful when you want to use an existing class, but its interface does not match the one you need.
- Promotes reusability of existing code without modifying it.
- Also known as "Wrapper" pattern.

**Structure:**
- **Target:** The interface expected by the client.
- **Adaptee:** The existing class with an incompatible interface.
- **Adapter:** Implements the target interface and translates calls to the adaptee.

**Example:**
Suppose you have a legacy audio player that plays only MP3 files, but you want to play other formats (e.g., MP4, VLC) using the same interface.

```java
// Target interface
interface MediaPlayer {
    void play(String audioType, String fileName);
}

// Adaptee (incompatible interface)
class AdvancedMediaPlayer {
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file: " + fileName);
    }
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file: " + fileName);
    }
}

// Adapter
class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedMediaPlayer = new AdvancedMediaPlayer();
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMediaPlayer.playVlc(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMediaPlayer.playMp4(fileName);
        } else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}

// Client
class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter = new MediaAdapter();
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file: " + fileName);
        } else {
            mediaAdapter.play(audioType, fileName);
        }
    }
}

// Usage
AudioPlayer player = new AudioPlayer();
player.play("mp3", "song.mp3"); // Playing mp3 file: song.mp3
player.play("mp4", "movie.mp4"); // Playing mp4 file: movie.mp4
player.play("vlc", "video.vlc"); // Playing vlc file: video.vlc
player.play("avi", "clip.avi"); // Invalid media. avi format not supported
```

**Benefits:**
- Allows integration of legacy or third-party code without modifying their source.
- Promotes code reusability and flexibility.

</details>
<hr>

<details>
<summary>How do you implement the Factory Pattern in Java? When should you use it?</summary>

**Factory Pattern** is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created. It helps in decoupling object creation from its usage.

---

### When to Use Factory Pattern?
- When you have a superclass with multiple subclasses and you want to return one of the subclasses based on input or logic.
- When the exact type of object to create is determined at runtime.
- When you want to encapsulate object creation logic and avoid exposing instantiation details to the client.
- When you want to follow the Open/Closed Principle (add new types without modifying existing code).

---

### Example: Simple Factory Pattern
Suppose you have a `Shape` interface and multiple implementations:

```java
// Product interface
interface Shape {
    void draw();
}

// Concrete products
class Circle implements Shape {
    public void draw() { System.out.println("Drawing Circle"); }
}

class Rectangle implements Shape {
    public void draw() { System.out.println("Drawing Rectangle"); }
}

// Factory class
class ShapeFactory {
    public static Shape getShape(String type) {
        if (type.equalsIgnoreCase("circle")) {
            return new Circle();
        } else if (type.equalsIgnoreCase("rectangle")) {
            return new Rectangle();
        }
        throw new IllegalArgumentException("Unknown shape type");
    }
}

// Usage
Shape shape1 = ShapeFactory.getShape("circle");
shape1.draw(); // Drawing Circle
Shape shape2 = ShapeFactory.getShape("rectangle");
shape2.draw(); // Drawing Rectangle
```

---

### Example: Factory Method Pattern (with Inheritance)

```java
// Product interface
interface Animal {
    void speak();
}

// Concrete products
class Dog implements Animal {
    public void speak() { System.out.println("Woof"); }
}
class Cat implements Animal {
    public void speak() { System.out.println("Meow"); }
}

// Creator (abstract)
abstract class AnimalFactory {
    public abstract Animal createAnimal();
}

// Concrete factories
class DogFactory extends AnimalFactory {
    public Animal createAnimal() { return new Dog(); }
}
class CatFactory extends AnimalFactory {
    public Animal createAnimal() { return new Cat(); }
}

// Usage
AnimalFactory factory = new DogFactory();
Animal animal = factory.createAnimal();
animal.speak(); // Woof
```

---

### Benefits
- Encapsulates object creation logic.
- Promotes loose coupling and adherence to SOLID principles.
- Makes code more maintainable and extensible.

</details>
<hr>

<details>
<summary>What is the Abstract Factory Pattern? Explain with examples. What is the difference between Factory and Abstract Factory patterns, and when should you use each?</summary>

**Abstract Factory Pattern** is a creational design pattern that provides an interface for creating families of related or dependent objects without specifying their concrete classes. It is useful when your system needs to be independent of how its objects are created, composed, and represented.

---

### Example: Abstract Factory Pattern
Suppose you want to create UI components for different operating systems (Windows, Mac). Each OS has its own style of buttons and checkboxes.

```java
// Abstract product interfaces
interface Button { void paint(); }
interface Checkbox { void paint(); }

// Concrete products for Windows
class WindowsButton implements Button {
    public void paint() { System.out.println("Windows Button"); }
}
class WindowsCheckbox implements Checkbox {
    public void paint() { System.out.println("Windows Checkbox"); }
}

// Concrete products for Mac
class MacButton implements Button {
    public void paint() { System.out.println("Mac Button"); }
}
class MacCheckbox implements Checkbox {
    public void paint() { System.out.println("Mac Checkbox"); }
}

// Abstract factory
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete factories
class WindowsFactory implements GUIFactory {
    public Button createButton() { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}
class MacFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

// Client code
class Application {
    private Button button;
    private Checkbox checkbox;
    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }
    public void paint() {
        button.paint();
        checkbox.paint();
    }
}

// Usage
GUIFactory factory = new WindowsFactory();
Application app = new Application(factory);
app.paint(); // Windows Button, Windows Checkbox
```

---

### Factory vs Abstract Factory Pattern

| Aspect                | Factory Pattern                        | Abstract Factory Pattern                      |
|-----------------------|----------------------------------------|-----------------------------------------------|
| Purpose               | Create one product                     | Create families of related products           |
| Number of Factories   | One factory for each product           | One factory for each product family           |
| Product Types         | Single                                 | Multiple, related                            |
| Example               | ShapeFactory (Circle, Rectangle)       | GUIFactory (Button, Checkbox, etc.)           |
| Complexity            | Simple                                 | More complex (multiple factories/interfaces)  |

---

### When to Use Which?
- **Factory Pattern:**
  - When you need to create a single product type, and the exact class isn't known until runtime.
  - When you want to encapsulate object creation logic for one kind of object.
- **Abstract Factory Pattern:**
  - When you need to create families of related or dependent objects (e.g., UI widgets for different platforms).
  - When you want to ensure that products from the same family are used together.

---

**Summary:**
- Use Factory Pattern for one product type, Abstract Factory for families of products.
- Abstract Factory promotes consistency among products and makes it easy to switch between product families.

</details>
<hr>

<details>
<summary>How do you implement the Singleton Design Pattern in Java? When should you use it?</summary>

**Singleton Pattern** is a creational design pattern that ensures a class has only one instance and provides a global point of access to it. It is commonly used for shared resources like configuration, logging, or connection pools.

---

### When to Use Singleton Pattern?
- When exactly one instance of a class is needed to coordinate actions across the system.
- When you want to control access to shared resources (e.g., configuration, logging, caches).
- When you need a global point of access to an object.

---

### Example 1: Basic Singleton (Eager Initialization)
```java
class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();
    private EagerSingleton() {}
    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
```
- The instance is created at class loading time. Thread-safe but may create the instance even if not used.

---

### Example 2: Lazy Initialization (Thread-Safe)
```java
class LazySingleton {
    private static volatile LazySingleton instance;
    private LazySingleton() {}
    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
```
- Uses double-checked locking for thread safety and lazy initialization.

---

### Example 3: Singleton with Enum (Best Practice)
```java
enum EnumSingleton {
    INSTANCE;
    public void doSomething() {
        System.out.println("Doing something...");
    }
}
// Usage
EnumSingleton.INSTANCE.doSomething();
```
- Enum-based singleton is thread-safe, handles serialization, and prevents reflection attacks.

---

### Benefits
- Controls instance creation and access.
- Saves memory by avoiding multiple instances.
- Useful for shared resources and configuration.

---

### Cautions
- Can introduce global state, making testing and maintenance harder.
- Overuse can lead to tight coupling.

---

**Summary:**
- Use Singleton when a single, shared instance is required.
- Prefer enum-based singleton for most cases in Java.

</details>
<hr>

<details>
<summary>Elaborate on Singleton with Enum in Java with a detailed example</summary>

**Enum-based Singleton** is the most robust and recommended way to implement the Singleton pattern in Java. It is simple, thread-safe, handles serialization automatically, and protects against reflection attacks.

---

### Why Use Enum for Singleton?
- **Thread-safe:** Enum instances are created only once by the JVM.
- **Serialization-safe:** Enum ensures only one instance exists, even after deserialization.
- **Reflection-safe:** Prevents creation of another instance via reflection.
- **Simplicity:** Less code and no need for synchronization.

---

### Example: Logger Singleton Using Enum
Suppose you want a global logger that is used throughout your application.

```java
// Enum-based Singleton
public enum Logger {
    INSTANCE;
    // You can add fields and methods
    private int logCount = 0;

    public void log(String message) {
        logCount++;
        System.out.println("[LOG " + logCount + "]: " + message);
    }

    public int getLogCount() {
        return logCount;
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Logger logger1 = Logger.INSTANCE;
        Logger logger2 = Logger.INSTANCE;
        logger1.log("Application started");
        logger2.log("Another log message");
        System.out.println("Total logs: " + logger1.getLogCount()); // 2
        System.out.println(logger1 == logger2); // true (same instance)
    }
}
```

---

### Key Points
- The enum constant `INSTANCE` is the singleton instance.
- You can add fields, methods, and even implement interfaces in the enum.
- All usages of `Logger.INSTANCE` refer to the same object.
- No need to worry about thread safety, serialization, or reflection issues.

---

### When to Use Enum Singleton?
- When you need a single, shared instance (e.g., logger, configuration, cache).
- When you want the simplest, safest singleton implementation in Java.

---

**Summary:**
- Enum-based singleton is the preferred approach in Java for most use cases.
- It is concise, robust, and handles all edge cases automatically.

</details>
<hr>

<details>
<summary>Can an Enum have public and static methods in Java?</summary>

**Yes, Java enums can have both public and static methods.**

---

### 1. Public Methods
- You can define public (or private/protected) instance methods in an enum just like in a regular class.
- These methods can be called on enum constants (e.g., `MyEnum.VALUE.method()`)

**Example:**
```java
enum Operation {
    ADD, SUBTRACT;
    public int apply(int a, int b) {
        switch (this) {
            case ADD: return a + b;
            case SUBTRACT: return a - b;
            default: throw new AssertionError();
        }
    }
}
// Usage: Operation.ADD.apply(2, 3); // 5
```

---

### 2. Static Methods
- You can define static methods in an enum.
- Static methods are called on the enum type itself (e.g., `MyEnum.staticMethod()`).
- Common use: utility methods, custom value lookup, etc.

**Example:**
```java
enum Color {
    RED, GREEN, BLUE;
    public static Color fromString(String name) {
        for (Color c : values()) {
            if (c.name().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown color: " + name);
    }
}
// Usage: Color.fromString("red"); // Color.RED
```

---

### Summary
- Enums in Java can have fields, constructors, instance methods (public, private, etc.), and static methods.
- This makes enums powerful and flexible for modeling fixed sets of constants with behavior.

</details>
<hr>

