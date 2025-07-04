# Java Collections Interview Questions

<details>
<summary>What is the difference between ArrayList, LinkedList, Vector, and Stack?</summary>

- <strong>ArrayList:</strong> Resizable array, fast random access (O(1)), slow insert/remove in the middle (O(n)), not synchronized.
- <strong>LinkedList:</strong> Doubly-linked list, fast insert/remove at ends (O(1)), slow random access (O(n)), not synchronized.
- <strong>Vector:</strong> Legacy resizable array, synchronized (thread-safe), slower than ArrayList due to synchronization.
- <strong>Stack:</strong> Legacy class, extends Vector, LIFO (last-in, first-out) operations (<code>push</code>, <code>pop</code>), synchronized.

</details>

<details>
<summary>What is the time complexity of operations in HashMap, TreeMap, and LinkedHashMap?</summary>

- <strong>HashMap:</strong> O(1) for get/put/remove (amortized), O(n) worst-case (hash collisions).
- <strong>TreeMap:</strong> O(log n) for get/put/remove (uses Red-Black tree).
- <strong>LinkedHashMap:</strong> Same as HashMap, but maintains insertion order (O(1) for get/put/remove).

</details>

<details>
<summary>How does a HashSet ensure uniqueness of elements?</summary>

HashSet uses a HashMap internally. When you add an element, it uses the element's <code>hashCode()</code> and <code>equals()</code> methods to check for duplicates. Only unique elements (as per <code>equals()</code>) are stored.

</details>

<details>
<summary>What is the difference between fail-fast and fail-safe iterators?</summary>

- <strong>Fail-fast:</strong> Throw <code>ConcurrentModificationException</code> if the collection is modified during iteration (e.g., ArrayList, HashMap iterators).
- <strong>Fail-safe:</strong> Do not throw exceptions; work on a copy of the collection (e.g., <code>CopyOnWriteArrayList</code>, <code>ConcurrentHashMap</code> iterators).

</details>

<details>
<summary>What are the differences between ConcurrentHashMap and Hashtable?</summary>

- <strong>ConcurrentHashMap:</strong> Allows concurrent read/write, uses segment locking (Java 7) or finer-grained locking (Java 8+), better performance, does not allow null keys/values.
- <strong>Hashtable:</strong> Legacy, synchronized on every method, lower performance, allows only one thread at a time, does not allow null keys/values.

</details>

<details>
<summary>How HashMap works Internally?</summary>

HashMap uses an array of buckets. Each bucket is a linked list (or a tree in Java 8+). The <code>hashCode()</code> of the key determines the bucket. On collision, elements are stored in the same bucket (as a list or tree). <code>equals()</code> is used to check for key equality.

</details>

<details>
<summary>What are the internal changes in HashMap in Java 8?</summary>

- Buckets with many collisions (more than 8 elements) are converted from linked lists to balanced trees (Red-Black trees) for faster lookup (O(log n)).
- Improved hash function for better distribution.

</details>

<details>
<summary>How does PriorityQueue work internally?</summary>

PriorityQueue uses a binary heap (min-heap by default) stored in an array. The smallest element is always at the head. Insertion and removal are O(log n).

</details>

<details>
<summary>Explain the contract between hashCode() and equals() methods.</summary>

- If two objects are equal according to <code>equals()</code>, they must have the same <code>hashCode()</code>.
- If two objects have the same <code>hashCode()</code>, they may or may not be equal.
- Violating this contract can break collections like HashMap and HashSet.

</details>

<details>
<summary>How do you make a collection unmodifiable or thread-safe?</summary>

- <strong>Unmodifiable:</strong> Use <code>Collections.unmodifiableList()</code>, <code>List.of()</code> (Java 9+), etc.
- <strong>Thread-safe:</strong> Use <code>Collections.synchronizedList()</code>, concurrent collections (e.g., <code>CopyOnWriteArrayList</code>, <code>ConcurrentHashMap</code>).

</details>

<details>
<summary>What is the difference between Comparable and Comparator?</summary>

- <strong>Comparable:</strong> Interface for natural ordering, implemented by the class itself (<code>compareTo()</code> method).
- <strong>Comparator:</strong> Separate object to define custom ordering (<code>compare()</code> method), can be passed to sort methods.

<strong>Example:</strong>
```java
Collections.sort(list); // uses Comparable
Collections.sort(list, comparator); // uses Comparator
```

</details>