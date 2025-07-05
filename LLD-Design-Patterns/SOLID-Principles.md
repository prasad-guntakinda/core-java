# SOLID Principles:

The SOLID principles are five key object-oriented design guidelines that help create maintainable, flexible, and robust software. Each letter stands for a principle:

<details>
<summary>1. Single Responsibility Principle (SRP)</summary>

- A class should have only one reason to change, meaning it should have only one job or responsibility.
- <strong>Benefit:</strong> Easier to maintain, test, and understand.
- <strong>Example:</strong>

```java
class InvoicePrinter {
    public void print(Invoice invoice) {
        // printing logic
    }
}

class InvoicePersistence {
    public void save(Invoice invoice) {
        // save logic
    }
}
```
- <code>InvoicePrinter</code> handles printing, <code>InvoicePersistence</code> handles saving. Each class has a single responsibility.
</details>
<hr>

<details>
<summary>2. Open/Closed Principle (OCP)</summary>

- Software entities (classes, modules, functions) should be open for extension, but closed for modification.
- <strong>Benefit:</strong> You can add new functionality without changing existing code.
- <strong>Example:</strong>

```java
interface Shape {
    double area();
}

class Circle implements Shape {
    private double radius;
    public Circle(double radius) { this.radius = radius; }
    public double area() { return Math.PI * radius * radius; }
}

class Rectangle implements Shape {
    private double width, height;
    public Rectangle(double width, double height) { this.width = width; this.height = height; }
    public double area() { return width * height; }
}

// New shapes can be added without modifying existing code.
```
</details>
<hr>

<details>
<summary>3. Liskov Substitution Principle (LSP)</summary>

- Subtypes must be substitutable for their base types without altering the correctness of the program.
- <strong>Benefit:</strong> Ensures that derived classes extend the base class without changing its behavior.
- <strong>Example:</strong>

```java
class Bird {
    public void fly() { /* ... */ }
}

class Sparrow extends Bird {}
class Eagle extends Bird {}
// All subclasses of Bird can be used wherever Bird is expected.
```
- <strong>Counter-example:</strong> If you add a <code>Penguin</code> class that cannot fly, it would violate LSP.
</details>
<hr>

<details>
<summary>4. Interface Segregation Principle (ISP)</summary>

- Clients should not be forced to depend on interfaces they do not use.
- <strong>Benefit:</strong> Promotes small, specific interfaces rather than large, general-purpose ones.
- <strong>Example:</strong>

```java
interface Printer {
    void print();
}

interface Scanner {
    void scan();
}

class MultiFunctionPrinter implements Printer, Scanner {
    public void print() { /* ... */ }
    public void scan() { /* ... */ }
}

class SimplePrinter implements Printer {
    public void print() { /* ... */ }
}
```
- <code>SimplePrinter</code> does not need to implement <code>scan()</code>.
</details>
<hr>

<details>
<summary>5. Dependency Inversion Principle (DIP)</summary>

- High-level modules should not depend on low-level modules; both should depend on abstractions.
- Abstractions should not depend on details; details should depend on abstractions.
- <strong>Benefit:</strong> Reduces coupling and increases flexibility.
- <strong>Example:</strong>

```java
interface MessageService {
    void sendMessage(String message);
}

class EmailService implements MessageService {
    public void sendMessage(String message) { /* ... */ }
}

class Notification {
    private MessageService service;
    public Notification(MessageService service) {
        this.service = service;
    }
    public void notifyUser(String message) {
        service.sendMessage(message);
    }
}
```
- <code>Notification</code> depends on the <code>MessageService</code> abstraction, not a concrete implementation.
</details>
<hr>


<details>

<summary> Deep Dive: Open/Closed Principle (OCP) </summary>

- **Definition:**  
  Software entities (classes, modules, functions) should be open for extension, but closed for modification.

- **Goal:**  
  You should be able to add new functionality by adding new code, not by changing existing, tested code.

### Why is OCP Important?

- **Reduces risk:** Modifying existing code can introduce bugs. OCP encourages you to extend behavior without touching stable code.
- **Promotes maintainability:** New requirements can be met by adding new classes or methods, not by rewriting old ones.
- **Enables plug-in architectures:** Systems can be extended with new features (plugins, modules) without altering the core.

### How to Achieve OCP

- **Use Abstraction:**  
  Rely on interfaces or abstract classes. Write code that depends on abstractions, not concrete implementations.

- **Favor Composition over Inheritance:**  
  Use composition to add new behavior, rather than modifying existing classes.

- **Apply Design Patterns:**  
  Many patterns (Strategy, Decorator, Observer, Factory, etc.) help achieve OCP.

### Advanced Example: Using Strategy Pattern

Suppose you have a payment processing system:

```java
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        // Credit card payment logic
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(double amount) {
        // PayPal payment logic
    }
}

class PaymentProcessor {
    private PaymentStrategy strategy;
    public PaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    public void process(double amount) {
        strategy.pay(amount);
    }
}
```

**How is this OCP?**  
- You can add new payment methods (e.g., `CryptoPayment`) by implementing `PaymentStrategy`—no need to modify `PaymentProcessor` or existing strategies.

### Real-World Scenario

Suppose you’re building a reporting tool. Instead of a single `ReportGenerator` class with a giant `if-else` or `switch` for every report type, use OCP:

```java
interface Report {
    void generate();
}

class PDFReport implements Report {
    public void generate() { /* ... */ }
}

class ExcelReport implements Report {
    public void generate() { /* ... */ }
}

class ReportService {
    public void generateReport(Report report) {
        report.generate();
    }
}
```
- To add a new report type, just implement `Report`—no changes to `ReportService`.

### Best Practices

- **Don’t over-engineer:**  
  Use OCP where change is likely. Too much abstraction can make code harder to understand.

- **Unit test abstractions:**  
  Since you won’t modify existing code, ensure your abstractions are well-tested.

- **Document extension points:**  
  Make it clear where and how the system can be extended.
</details>
<hr>

<details>
<summary>Favor Composition over Inheritance</summary>

**Favoring composition over inheritance** is a key object-oriented design principle that encourages using object composition to achieve code reuse and flexibility, rather than relying solely on class inheritance.

---

### Why Favor Composition?
- **Flexibility:** You can change behavior at runtime by composing objects differently.
- **Avoids Fragile Base Class Problem:** Inheritance tightly couples subclasses to superclass implementation details, making changes risky.
- **Promotes Reuse:** You can mix and match behaviors by composing objects, rather than creating deep and rigid inheritance hierarchies.
- **Easier to Test and Maintain:** Smaller, focused components are easier to test and maintain.

---

### Example: Inheritance vs. Composition

#### Inheritance (Less Flexible)
```java
class Engine {
    public void start() { System.out.println("Engine started"); }
}

class Car extends Engine {
    public void drive() {
        start(); // Inherited from Engine
        System.out.println("Car is driving");
    }
}
```
- Here, `Car` inherits from `Engine`, but a car is not an engine. This is a misuse of inheritance.

#### Composition (Preferred)
```java
class Engine {
    public void start() { System.out.println("Engine started"); }
}

class Car {
    private Engine engine;
    public Car(Engine engine) {
        this.engine = engine;
    }
    public void drive() {
        engine.start();
        System.out.println("Car is driving");
    }
}
```
- Now, `Car` has an `Engine` (composition). This models the real-world relationship more accurately and allows you to swap different engine types if needed.

---

### Real-World Scenario: Adding Features
Suppose you want to add a `TurboEngine` or a `HybridEngine`. With composition, you can simply inject a different engine implementation into the `Car`:

```java
class TurboEngine extends Engine {
    @Override
    public void start() { System.out.println("Turbo engine started"); }
}

Car turboCar = new Car(new TurboEngine());
turboCar.drive();
```

---

### Best Practices
- Use inheritance only for true "is-a" relationships.
- Use composition for "has-a" or "can-do" relationships.
- Prefer interfaces or abstract types for composed components to maximize flexibility.

</details>
