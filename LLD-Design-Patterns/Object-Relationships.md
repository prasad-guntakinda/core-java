# Relationship b/w Objects

In Object-Oriented Analysis and Design (OOAD), several types of relationships can exist between classes, interfaces, and objects. Understanding these relationships is fundamental to designing robust and maintainable systems.

<details>
<summary>1. Association ("knows-a")</summary>

- A general relationship where one class knows about or uses another.
- Example: A <code>Teacher</code> is associated with a <code>Student</code>.
- Can be one-to-one, one-to-many, or many-to-many.
- Also called a "knows-a" relationship.
</details>
<hr>

<details>
<summary>2. Aggregation ("has-a")</summary>

- A special form of association representing a "whole-part" relationship.
- The part can exist independently of the whole.
- Example: A <code>Department</code> has <code>Professors</code>, but a <code>Professor</code> can exist without a <code>Department</code>.
- Represented with a hollow diamond in UML.
- Also called a "has-a" relationship.
</details>
<hr>

<details>
<summary>3. Composition ("owns-a" or strong has-a)</summary>

- A stronger form of aggregation where the part cannot exist without the whole.
- Example: A <code>House</code> is composed of <code>Rooms</code>. If the <code>House</code> is destroyed, so are the <code>Rooms</code>.
- Represented with a filled diamond in UML.
- Also called an "owns-a" or "strong has-a" relationship.
</details>
<hr>

<details>
<summary>4. Inheritance (Generalization, "is-a")</summary>

- An "is-a" relationship where a subclass inherits from a superclass.
- Example: <code>Car</code> extends <code>Vehicle</code> (Car is a Vehicle).
- Used for code reuse and polymorphism.
</details>
<hr>

<details>
<summary>5. Realization (Interface Implementation, "is-a")</summary>

- A class implements the methods defined by an interface.
- Example: <code>ArrayList</code> implements <code>List</code> (ArrayList is a List).
- Enables polymorphism and abstraction.
- Also an "is-a" relationship.
</details>
<hr>

<details>
<summary>6. Dependency ("uses-a")</summary>

- A "uses-a" relationship where one class depends on another for some operation.
- Example: A <code>Service</code> class uses a <code>Repository</code> class.
- Usually short-lived and often seen as method parameters or local variables.
</details>
<hr>

<strong>Summary Table:</strong>
| Relationship   | Also Called      | Description                              | Example                        |
|---------------|------------------|------------------------------------------|--------------------------------|
| Association   | knows-a          | General connection                       | Teacher ↔ Student              |
| Aggregation   | has-a            | Whole-part, part can exist independently | Department ◇ Professor         |
| Composition   | owns-a/strong has-a | Whole-part, part cannot exist independently | House ◆ Room                |
| Inheritance   | is-a             | Subclassing                              | Car → Vehicle                  |
| Realization   | is-a             | Implements interface                      | ArrayList → List               |
| Dependency    | uses-a           | Uses temporarily                          | Service → Repository           |