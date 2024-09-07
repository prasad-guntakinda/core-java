# instanceof pattern:

---
Q1.

For any non-null reference o1, the expression (o1 instanceof Object) will always yield true.
Because all object derive from Object class.


Ans:
````text
You should understand here that instanceof operator returns true even if you specify a super type on the right hand side.
For example:
class A {}
class B extends A { }
B b = new B();

Now, b instanceof A will be true because b is actually an instance of B and since B is a subclass of A, therefore b IS-A A.


However, when using a pattern variable, you must use a subtype on the right hand side. So, (b instanceof A a) will not compile because the compiler knows that b will always point to an instance of A and this check is pointless. This is a new feature of instanceof that is applicable only when used with pattern matching.
````