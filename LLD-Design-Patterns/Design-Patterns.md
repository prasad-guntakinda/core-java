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

