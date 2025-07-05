<details>
<summary>What is the difference between checked and unchecked exceptions?</summary>

- <strong>Checked exceptions:</strong> Must be declared in method signature or handled with try-catch. Subclasses of <code>Exception</code> (except <code>RuntimeException</code>).
- <strong>Unchecked exceptions:</strong> Subclasses of <code>RuntimeException</code>. Not required to be declared or caught.

</details>

---


<details>
<summary>What is the difference between throw and throws?</summary>

- <code>throw</code>: Used to explicitly throw an exception in code.
- <code>throws</code>: Used in method signature to declare possible exceptions.

</details>

---
<details>
<summary>What is finally block? When is it executed?</summary>

The <code>finally</code> block always executes after try-catch, regardless of whether an exception was thrown or caught, except when JVM exits or thread is killed.

</details>

---
<details>
<summary>Can you catch multiple exceptions in a single catch block?</summary>

Yes, using multi-catch: <code>catch (IOException | SQLException e)</code>

</details>

---
<details>
<summary>What happens if an exception is thrown in a finally block?</summary>

If an exception is thrown in <code>finally</code>, it can suppress any exception thrown in try or catch. Only the exception from finally will propagate.

</details>

---
<details>
<summary>What is the difference between final, finally, and finalize?</summary>

- <code>final</code>: Keyword to mark a variable as constant, a method as non-overridable, or a class as non-inheritable.
- <code>finally</code>: Block that always executes after try-catch.
- <code>finalize()</code>: Method called by garbage collector before object is destroyed (deprecated in Java 9+).

</details>

---

<details>
<summary>Can you rethrow an exception? How?</summary>

Yes, you can rethrow an exception using <code>throw</code> in a catch block. You can also throw a new exception.

</details>

---
<details>
<summary>What is exception chaining?</summary>

Exception chaining is associating one exception with another using the constructor <code>new Exception(String, Throwable)</code> or <code>initCause()</code>.

</details>

---
<details>
<summary>What is the base class for all exceptions?</summary>

<code>Throwable</code> is the superclass for all errors and exceptions in Java.

</details>

---


## try-with-resources:


<details>
<summary>What is try-with-resources? How does it work?</summary>

A try-with-resources statement automatically closes resources (like files, streams) that implement <code>AutoCloseable</code> after the try block, even if an exception occurs.

<strong>Example:</strong>
```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line = br.readLine();
}
```

</details>

---
<details>
<summary>Elaborate on try-with-resources in Java</summary>

<strong>try-with-resources</strong> is a feature introduced in Java 7 to simplify resource management and avoid resource leaks (such as open files, sockets, or database connections).

<strong>Key Points:</strong>
- It is a special form of the try statement that declares one or more resources within parentheses.
- A resource is any object that implements the <code>AutoCloseable</code> interface (e.g., <code>BufferedReader</code>, <code>FileInputStream</code>, <code>Connection</code>).
- Resources declared in the try-with-resources statement are automatically closed at the end of the statement, even if an exception occurs.
- This eliminates the need for a finally block to close resources manually.

<strong>Syntax Example:</strong>
```java
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) {
    String line = br.readLine();
    // process the line
} // br.close() is called automatically here
```

<strong>How it works:</strong>
- When the try block finishes (normally or due to an exception), the <code>close()</code> method of each resource is called in reverse order of their creation.
- If both the try block and the <code>close()</code> method throw exceptions, the exception from <code>close()</code> is suppressed and attached to the main exception (accessible via <code>getSuppressed()</code>).

<strong>Multiple Resources Example:</strong>
```java
try (
    FileInputStream fis = new FileInputStream("input.txt");
    FileOutputStream fos = new FileOutputStream("output.txt")
) {
    // Use fis and fos
} // Both fis and fos are closed automatically
```

<strong>Custom Resource Example:</strong>

```java
class MyResource implements AutoCloseable {
    public void close() {
        System.out.println("Resource closed");
    }
}

try (MyResource res = new MyResource()) {
    // use resource
}
```

<strong>Benefits:</strong>
- Reduces boilerplate code for closing resources
- Prevents resource leaks
- Handles exceptions during resource closing

<strong>Summary:</strong> try-with-resources is the recommended way to manage resources in modern Java, ensuring they are always closed properly and safely.

</details>

---
<details>
<summary>What is suppressed exception in try-with-resources?</summary>

If both the try block and resource's <code>close()</code> throw exceptions, the exception from <code>close()</code> is suppressed and attached to the primary exception (accessible via <code>getSuppressed()</code>).

</details>

---


<details>
<summary>What is the difference between AutoCloseable and Closeable in try-with-resources? What is the order of closing resources?</summary>

<strong>AutoCloseable vs Closeable:</strong>
- <code>AutoCloseable</code> is the parent interface introduced in Java 7. It defines a single method: <code>void close() throws Exception</code>.
- <code>Closeable</code> is a sub-interface (from Java 5, in <code>java.io</code>) that extends <code>AutoCloseable</code> and narrows the <code>close()</code> method to <code>void close() throws IOException</code>.
- All <code>Closeable</code> resources (like streams, readers, writers) can be used in try-with-resources. <code>AutoCloseable</code> is more general and can be implemented by any class.
- In practice, use <code>Closeable</code> for I/O resources, and <code>AutoCloseable</code> for other types of resources.

<strong>Order of Closing Resources:</strong>
- Resources declared in the try-with-resources statement are closed in <strong>reverse order</strong> of their creation.
- The last resource declared is closed first, then the previous one, and so on.

<strong>Example:</strong>

```java
class ResourceA implements AutoCloseable {
    public void close() {
        System.out.println("Closing ResourceA");
    }
}
class ResourceB implements Closeable {
    public void close() {
        System.out.println("Closing ResourceB");
    }
}

try (ResourceA a = new ResourceA(); ResourceB b = new ResourceB()) {
    System.out.println("Inside try block");
}
// Output:
// Inside try block
// Closing ResourceB
// Closing ResourceA
```

<strong>Summary:</strong>
- <code>Closeable</code> is for I/O resources and throws IOException; <code>AutoCloseable</code> is more general and throws Exception.
- Resources are closed in reverse order of their declaration in the try-with-resources statement.

</details>