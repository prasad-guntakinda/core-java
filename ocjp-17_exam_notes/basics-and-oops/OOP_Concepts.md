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

````java
//You cannot override a non-static method with a static method and vice versa. (A default method is a non-static method.) You can, however, redeclare a static method of a super interface as a default method in the sub interface.
//For example:
class Base{
  static void m(){ }
  void n(){ }
  static void x(){ }
  void y(){ }
}
class Sub extends Base{
  void m(){ } //WILL NOT COMPILE
  static void n(){ } //WILL NOT COMPILE
  static void x(){ } //VALID, x() of base is hidden
  void y(){ } //VALID, y() of base is overridden
}
````
````java
interface Book{
  public default String getId(){
     return "ISBN123456";
  }
}

interface Encyclopedia extends Book{
   //INSERT CODE HERE
}


````
- Which of the following options can be inserted in Encyclopedia independent of each other?
- Ans:
````text
String getId(); //VALID
- An interface can redeclare a default method and also make it abstract.
- Note that if a class then implements the subinterface, it will not inherit the default method from the super interface (because it has been redeclared as abstract by the subinterface).

default String getId(){
   return "AIN8888";
};
An interface can redeclare a default method and provide a different implementation.

````

---

<details>
<summary>3. Polymorphism:</summary>

#### Exam Points:

````java

class A{ 
   public void mA(){ };
}

class B extends A { 
   public void mA(){ }
   public void mB() { }
}

class C extends B { 
   public void mC(){ }
}
and the following declarations:
A x = new B(); B y = new B(); B z = new C();
//Which of the following calls are virtual calls?
//x.mA();, y.mA();, z.mB();

````
- A virtual call means that the call is bound to a method at run time and not at compile time.

In Java, all non-private and non-final instance method calls are virtual. This is important because, at run time, a reference variable may point to an instance of a subclass of the class of the reference.  The compiler doesn't have the knowledge of the class of the actual object being referred to by the reference variable. If the subclass overrides the method, the call becomes polymorphic because now there are two versions of the method that can be invoked (the base class version and the subclass version). Therefore, the compiler is unable to bind the call to the method of a specific class. Only the JVM has the necessary information to bind the call.  The JVM knows the class of the actual object and it binds the call to the method of that class. This behavior is called polymorphism.

Thus, in Java, all non-private and non-final instance method calls are potentially polymorphic because there could be multiple versions of the method eligible to be invoked.

In this case, x.mB() is invalid call. It will not even compile because the class of x is A, which does not contain method mB(). Even though the object referred to by x is of class B which does contain mB(). z.mC() is invalid for the same reason.

</details>