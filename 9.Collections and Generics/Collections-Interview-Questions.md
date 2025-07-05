# Java Collections Interview Questions

<details>
<summary>What is the difference between ArrayList, LinkedList, Vector, and Stack?</summary>

- <strong>ArrayList:</strong> Resizable array, fast random access (O(1)), slow insert/remove in the middle (O(n)), not synchronized.
- <strong>LinkedList:</strong> Doubly-linked list, fast insert/remove at ends (O(1)), slow random access (O(n)), not synchronized.
- <strong>Vector:</strong> Legacy resizable array, synchronized (thread-safe), slower than ArrayList due to synchronization.
- <strong>Stack:</strong> Legacy class, extends Vector, LIFO (last-in, first-out) operations (<code>push</code>, <code>pop</code>), synchronized.

</details>

---

<details>
<summary>What is the time complexity of operations in HashMap, TreeMap, and LinkedHashMap?</summary>

- <strong>HashMap:</strong> O(1) for get/put/remove (amortized), O(n) worst-case (hash collisions).
- <strong>TreeMap:</strong> O(log n) for get/put/remove (uses Red-Black tree).
- <strong>LinkedHashMap:</strong> Same as HashMap, but maintains insertion order (O(1) for get/put/remove).

</details>

---

<details>
<summary>How does a HashSet ensure uniqueness of elements?</summary>

HashSet uses a HashMap internally. When you add an element, it uses the element's <code>hashCode()</code> and <code>equals()</code> methods to check for duplicates. Only unique elements (as per <code>equals()</code>) are stored.

</details>

---

<details>
<summary>What is the difference between fail-fast and fail-safe iterators?</summary>

- <strong>Fail-fast:</strong> Throw <code>ConcurrentModificationException</code> if the collection is modified during iteration (e.g., ArrayList, HashMap iterators).
- <strong>Fail-safe:</strong> Do not throw exceptions; work on a copy of the collection (e.g., <code>CopyOnWriteArrayList</code>, <code>ConcurrentHashMap</code> iterators).

</details>

---

<details>
<summary>What are the differences between ConcurrentHashMap and Hashtable?</summary>

- <strong>ConcurrentHashMap:</strong> Allows concurrent read/write, uses segment locking (Java 7) or finer-grained locking (Java 8+), better performance, does not allow null keys/values.
- <strong>Hashtable:</strong> Legacy, synchronized on every method, lower performance, allows only one thread at a time, does not allow null keys/values.

</details>

---

<details>
<summary>How does fine-grained locking work in ConcurrentHashMap?</summary>

Fine-grained locking is a concurrency control technique that minimizes lock contention by locking only small portions of a data structure, rather than the entire structure.

<strong>In ConcurrentHashMap (Java 7):</strong>
- The map is divided into multiple segments (default 16).
- Each segment is an independent hash table with its own lock.
- When a thread wants to perform an operation (put, get, remove), it first determines the segment for the key (using hash).
- Only the segment containing the key is locked, so multiple threads can operate on different segments at the same time.
- This increases throughput and reduces contention compared to locking the whole map.

<strong>In Java 8 and later:</strong>
- Segments are removed; locking is done at the bucket (bin) level.
- Each bucket (linked list or tree) can be locked independently.
- For most operations (like put, remove), only the specific bucket is locked, allowing even more threads to operate concurrently.
- Reads are mostly lock-free (using volatile and CAS operations), further improving performance.

<strong>Visualization:</strong>
- Imagine a map with 16 segments (Java 7):
  - Thread 1 locks segment 0 for key "A"
  - Thread 2 locks segment 5 for key "Z"
  - Both threads can proceed without waiting for each other
- In Java 8+, Thread 1 locks only the bucket for "A", Thread 2 locks only the bucket for "Z"

<strong>Summary:</strong>
- Fine-grained locking allows high concurrency by reducing the scope of locks.
- In Java 7, locking is per segment; in Java 8+, it's per bucket/bin.
- This design enables many threads to update the map simultaneously, as long as they work on different keys.

</details>

---

<details>
<summary>How HashMap works Internally?</summary>

HashMap uses an array of buckets. Each bucket is a linked list (or a tree in Java 8+). The <code>hashCode()</code> of the key determines the bucket. On collision, elements are stored in the same bucket (as a list or tree). <code>equals()</code> is used to check for key equality.

https://howtodoinjava.com/java/collections/hashmap/how-hashmap-works-in-java/



</details>

---

<details>
<summary>What are the internal changes in HashMap in Java 8?</summary>

- Buckets with many collisions (more than 8 elements) are converted from linked lists to balanced trees (Red-Black trees) for faster lookup (O(log n)).
- Improved hash function for better distribution.

</details>

---

<details>
<summary>What are the significant internal changes in HashMap after Java 8?</summary>

After Java 8, <code>HashMap</code> introduced several important changes to improve performance, especially in cases with many hash collisions:

- <strong>Tree Bins for High Collision Buckets:</strong> If a bucket contains more than 8 entries (and the map has at least 64 buckets), the linked list in that bucket is converted to a balanced red-black tree. This improves lookup time from O(n) to O(log n) for that bucket. If the number of entries drops below 6, the tree reverts to a linked list.
- <strong>Improved Hash Function:</strong> The hash function was enhanced to better distribute keys and reduce collisions, making the map more efficient.
- <strong>Resizing and Transfer Optimization:</strong> The resizing process (when the map grows) was optimized for better performance and less contention.
- <strong>ConcurrentHashMap Similarity:</strong> Java 8's <code>ConcurrentHashMap</code> also adopted tree bins for high-collision buckets, further improving concurrent performance.

<strong>Code Example: Treeification in Java 8+</strong>

```java
// Internally, when a bucket's size exceeds 8:
if (bucketSize > 8) {
    // Convert linked list to red-black tree
    treeifyBin();
}
```

<strong>Summary Table</strong>

| Feature                | Before Java 8         | Java 8+                        |
|------------------------|-----------------------|--------------------------------|
| Bucket Structure       | Linked List           | Linked List or Tree (RB Tree)  |
| Lookup in Collisions   | O(n)                  | O(log n) (if treeified)        |
| Hash Function          | Simpler               | Improved for better spread     |
| Resizing               | Less optimized        | More efficient                 |

<strong>In summary:</strong> Java 8’s <code>HashMap</code> is much more resilient to hash collisions, with better worst-case performance and improved internal algorithms.

</details>

---

<details>
<summary>What does resizing a HashMap mean? When does it resize?</summary>

<strong>Resizing</strong> a <code>HashMap</code> means increasing the number of buckets (the internal array size) to reduce the load factor and maintain efficient performance.

- <strong>Why resize?</strong> As more entries are added, the number of collisions increases, which can degrade performance. Resizing spreads the entries across more buckets, reducing collisions.
- <strong>When does it happen?</strong> HashMap resizes automatically when the number of entries exceeds the product of the current capacity and the load factor (default load factor is 0.75).
  - For example, with a default capacity of 16 and load factor 0.75, resizing happens after the 13th entry is added (16 * 0.75 = 12, so after 12 entries).
- <strong>What happens during resizing?</strong>
  - A new, larger array (usually double the previous size) is created.
  - All existing entries are rehashed and moved to the new array (because the bucket index depends on the array size).
  - This is a costly operation, so frequent resizing should be avoided by choosing an appropriate initial capacity if the number of entries is known in advance.

<strong>Summary:</strong> Resizing is the process of expanding the internal storage of a HashMap to maintain performance as it grows. It happens automatically when the map exceeds its threshold (capacity × load factor).

</details>

---

<details>
<summary>How does PriorityQueue work internally?</summary>

PriorityQueue uses a binary heap (min-heap by default) stored in an array. The smallest element is always at the head. Insertion and removal are O(log n).

</details>

---

<details>
<summary>Explain the contract between hashCode() and equals() methods.</summary>

- If two objects are equal according to <code>equals()</code>, they must have the same <code>hashCode()</code>.
- If two objects have the same <code>hashCode()</code>, they may or may not be equal.
- Violating this contract can break collections like HashMap and HashSet.

</details>

---

<details>
<summary>How do you make a collection unmodifiable or thread-safe?</summary>

- <strong>Unmodifiable:</strong> Use <code>Collections.unmodifiableList()</code>, <code>List.of()</code> (Java 9+), etc.
- <strong>Thread-safe:</strong> Use <code>Collections.synchronizedList()</code>, concurrent collections (e.g., <code>CopyOnWriteArrayList</code>, <code>ConcurrentHashMap</code>).

</details>

---

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

---

<details>
<summary>How does HashMap store data internally? What are buckets and the internal array?</summary>

A <code>HashMap</code> uses an internal array to store data. Each element of this array is called a <strong>bucket</strong>. Here's how it works:

- <strong>Internal Array:</strong> When you create a HashMap, it allocates an array (e.g., <code>Node[] table</code>), where each slot is called a bucket.
- <strong>Bucket:</strong> Each bucket can hold one or more entries (key-value pairs). If multiple keys hash to the same bucket, they are stored together (as a linked list or tree in Java 8+).
- <strong>Storing Data:</strong>
  1. When you put a key-value pair, HashMap computes the <code>hashCode()</code> of the key.
  2. It uses this hash to determine the index in the array: <code>index = hash % array.length</code> (with some bit manipulation for better distribution).
  3. The entry is placed in the corresponding bucket (array slot).
  4. If the bucket already contains entries (collision), the new entry is added to the linked list (or tree) at that bucket.

<strong>Example:</strong>

Suppose you have a HashMap with 4 buckets (array size = 4):

| Index | Bucket (Linked List)         |
|-------|-----------------------------|
| 0     | ("dog", 1)                  |
| 1     | ("cat", 2) → ("bat", 3)     |
| 2     |                             |
| 3     | ("rat", 4)                  |

- "cat" and "bat" have the same hash index, so they are stored in the same bucket (index 1) as a linked list.
- When you call <code>get("bat")</code>, HashMap computes the hash, finds the bucket, and traverses the list to find the key.

<strong>Visualization:</strong>

```mermaid
graph TD
    A[HashMap Table (Array)]
    A --> B0[Bucket 0: ("dog", 1)]
    A --> B1[Bucket 1: ("cat", 2) → ("bat", 3)]
    A --> B2[Bucket 2: empty]
    A --> B3[Bucket 3: ("rat", 4)]
```

<strong>Summary:</strong> The internal array is the main storage, and each slot (bucket) can hold one or more entries. Collisions are handled by storing multiple entries in the same bucket as a linked list (or tree in Java 8+).

</details>

---

<details>
<summary>What is a Red-Black Tree? What do "red" and "black" mean, and why is search O(log n)?</summary>

A <strong>Red-Black Tree</strong> is a self-balancing binary search tree where each node is colored either red or black. The colors are used to enforce rules that keep the tree balanced, ensuring efficient operations.

<strong>What do "red" and "black" mean?</strong>
- Each node in the tree has a color attribute: red or black.
- The color is not related to the data, but is used to maintain balance through these rules:
  1. Every node is either red or black.
  2. The root is always black.
  3. All leaves (null children) are considered black.
  4. Red nodes cannot have red children (no two reds in a row).
  5. Every path from a node to its descendant leaves has the same number of black nodes (black-height).

<strong>How does this keep the tree balanced?</strong>
- These rules prevent the tree from becoming too unbalanced (like a linked list).
- When nodes are inserted or deleted, the tree may temporarily violate a rule, but it is quickly fixed by "recoloring" nodes and/or "rotating" subtrees.

<strong>Why is search O(log n)?</strong>
- Because the tree is always approximately balanced, the longest path from the root to a leaf is no more than twice as long as the shortest path.
- This means the height of the tree is always O(log n), where n is the number of nodes.
- Searching for a key involves traversing from the root down to a leaf, which takes at most O(log n) steps.

<strong>Visualization:</strong>

```mermaid
graph TD
    R1((10) Black)
    R1 --> R2((5) Red)
    R1 --> R3((15) Red)
    R2 --> R4((2) Black)
    R2 --> R5((7) Black)
    R3 --> R6((12) Black)
    R3 --> R7((18) Black)
```

<strong>Summary:</strong> In a red-black tree, the red and black colors are used to enforce balancing rules. This guarantees that the tree's height is always O(log n), so search, insert, and delete operations are efficient.

</details>

