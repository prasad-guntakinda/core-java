<details>
<summary>What is the difference between checked and unchecked exceptions?</summary>

- <strong>Checked exceptions:</strong> Must be declared in method signature or handled with try-catch. Subclasses of <code>Exception</code> (except <code>RuntimeException</code>).
- <strong>Unchecked exceptions:</strong> Subclasses of <code>RuntimeException</code>. Not required to be declared or caught.

</details>

<details>
<summary>What is the difference between throw and throws?</summary>

- <code>throw</code>: Used to explicitly throw an exception in code.
- <code>throws</code>: Used in method signature to declare possible exceptions.

</details>

<details>
<summary>What is finally block? When is it executed?</summary>

The <code>finally</code> block always executes after try-catch, regardless of whether an exception was thrown or caught, except when JVM exits or thread is killed.

</details>

<details>
<summary>Can you catch multiple exceptions in a single catch block?</summary>

Yes, using multi-catch: <code>catch (IOException | SQLException e)</code>

</details>

<details>
<summary>What happens if an exception is thrown in a finally block?</summary>

If an exception is thrown in <code>finally</code>, it can suppress any exception thrown in try or catch. Only the exception from finally will propagate.

</details>

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

<details>
<summary>What is suppressed exception in try-with-resources?</summary>

If both the try block and resource's <code>close()</code> throw exceptions, the exception from <code>close()</code> is suppressed and attached to the primary exception (accessible via <code>getSuppressed()</code>).

</details>

<details>
<summary>What is the difference between final, finally, and finalize?</summary>

- <code>final</code>: Keyword to mark a variable as constant, a method as non-overridable, or a class as non-inheritable.
- <code>finally</code>: Block that always executes after try-catch.
- <code>finalize()</code>: Method called by garbage collector before object is destroyed (deprecated in Java 9+).

</details>

<details>
<summary>Can you rethrow an exception? How?</summary>

Yes, you can rethrow an exception using <code>throw</code> in a catch block. You can also throw a new exception.

</details>

<details>
<summary>What is exception chaining?</summary>

Exception chaining is associating one exception with another using the constructor <code>new Exception(String, Throwable)</code> or <code>initCause()</code>.

</details>

<details>
<summary>What is the base class for all exceptions?</summary>

<code>Throwable</code> is the superclass for all errors and exceptions in Java.

</details>