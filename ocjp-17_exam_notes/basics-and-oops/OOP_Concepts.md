# OOP Concepts:

        1. Encapsulation
        2. Inheritance
        3. Polymorphism

## 1. Encapsulation:
- Encapsulation means that the internal representation of an object is generally hidden from view outside of the object's definition. Typically, only the object's own methods can directly inspect or manipulate its fields.
- Hiding the internals of the object protects its integrity by preventing users from setting the internal data of the component into an invalid or inconsistent state. A benefit of encapsulation is that it can reduce system complexity, and thus increases robustness, by allowing the developer to limit the interdependencies between software components.

#### Example Points:
- package access level is More restrictive than protected, but less restrictive than private.
- private is most restrictive.
````text
public < protected < package (or default) < private (here, public is least restrictive and private is most restrictive)
````



___

## 2. Inheritance:


### Exam Points:

- All non-static/instance methods in a class are implicitly passed a 'this' parameter when called.
- 'this' is assigned a reference to the current object automatically by the JVM. Thus, within an instance method foo, calling this.foo(); is same as calling foo();
- 