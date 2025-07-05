# Java Thread and Concurrency Interview Questions

<details>
<summary>What is the difference between process and thread?</summary>

- <strong>Process:</strong> An independent program in execution with its own memory space.
- <strong>Thread:</strong> A lightweight unit of execution within a process, sharing memory with other threads in the same process.

</details>

---
<details>
<summary>How do you create a thread in Java?</summary>

- Extend <code>Thread</code> class and override <code>run()</code> method.
- Implement <code>Runnable</code> interface and pass to <code>Thread</code> constructor.
- Implement <code>Callable&lt;T&gt;</code> and use <code>ExecutorService</code> for tasks that return a result.

</details>

---
<details>
<summary>What is the lifecycle of a thread?</summary>

New → Runnable → Running → Blocked/Waiting/Sleeping → Dead (Terminated)

</details>

---
<details>
<summary>What is the difference between start() and run() methods?</summary>

- <code>start()</code> creates a new thread and calls <code>run()</code> in that thread.
- <code>run()</code> is just a normal method call; no new thread is created.

</details>

---
<details>
<summary>What is synchronization? Why is it needed?</summary>

Synchronization ensures that only one thread can access a shared resource at a time, preventing data inconsistency and race conditions.

</details>

---
<details>
<summary>What is a deadlock? How can you prevent it?</summary>

Deadlock occurs when two or more threads are blocked forever, each waiting for a lock held by the other. Prevent by:
- Lock ordering
- Using <code>tryLock()</code>
- Avoiding nested locks

</details>

---
<details>
<summary>What is a race condition?</summary>

A race condition occurs when multiple threads access shared data and try to change it at the same time, leading to unpredictable results.

</details>

---
<details>
<summary>What is the difference between synchronized method and synchronized block?</summary>

- <strong>Synchronized method:</strong> Locks the entire method on the object (or class for static methods).
- <strong>Synchronized block:</strong> Locks only a specific block of code, can specify which object to lock.

</details>

---
<details>
<summary>Give an example of synchronized method and synchronized block in Java</summary>

<strong>Synchronized Method Example:</strong>

```java

class Counter {
    private int count = 0;

    // Synchronized instance method
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
```
- The <code>synchronized</code> keyword on the method ensures that only one thread at a time can execute <code>increment()</code> on the same object.

<strong>Synchronized Block Example:</strong>


```java

class Counter {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}
```
- The <code>synchronized</code> block allows you to lock only a specific section of code, using any object as the lock.
- This provides more fine-grained control over synchronization.

<strong>Summary:</strong>
- Use a synchronized method to lock the entire method.
- Use a synchronized block to lock only a critical section, improving concurrency when only part of the method needs to be synchronized.

</details>

---
<details>
<summary>What is volatile keyword?</summary>

<code>volatile</code> ensures visibility of changes to variables across threads. Reads/writes to a volatile variable are always from/to main memory.

</details>

---
<details>
<summary>Elaborate on the volatile keyword in Java, with example</summary>

The <code>volatile</code> keyword in Java is used to ensure <strong>visibility</strong> of changes to variables across threads. It tells the JVM that a variable's value will be modified by different threads.

<strong>Key Points:</strong>
- When a variable is declared <code>volatile</code>, every read and write goes directly to main memory, not to a thread's local cache.
- This guarantees that changes made by one thread are immediately visible to other threads.
- <code>volatile</code> does <strong>not</strong> guarantee atomicity. For compound actions (like <code>count++</code>), use synchronization or atomic classes.
- Useful for flags, state variables, or simple communication between threads.

<strong>Example: Using volatile for a stop flag</strong>


```java

class MyTask implements Runnable {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            // do work
        }
        System.out.println("Stopped");
    }

    public void stop() {
        running = false;
    }
}

// Usage:
MyTask task = new MyTask();
Thread t = new Thread(task);
t.start();
// ...
task.stop(); // Safely stops the thread
```
- Without <code>volatile</code>, the <code>running</code> flag might be cached by the thread and never updated, so the thread may never stop.
- With <code>volatile</code>, the change is visible immediately, and the thread exits the loop.

<strong>Summary:</strong>
- Use <code>volatile</code> for variables shared between threads where you need visibility but not atomicity.
- For atomic operations, use <code>AtomicInteger</code> or synchronization.

</details>

---

<details>
<summary>What is the difference between wait(), notify(), and notifyAll()?</summary>

- <code>wait()</code>: Causes the current thread to wait until another thread calls <code>notify()</code> or <code>notifyAll()</code> on the same object.
- <code>notify()</code>: Wakes up one waiting thread.
- <code>notifyAll()</code>: Wakes up all waiting threads.
- Must be called from synchronized context.

</details>

---


## ThreadLocal


<details>
<summary>What is ThreadLocal?</summary>

<code>ThreadLocal</code> provides thread-local variables. Each thread has its own independent value, useful for user sessions, database connections, etc.

</details>

---
<details>
<summary>Explain ThreadLocal in detail with a clear explanation and example</summary>

<strong>What is ThreadLocal?</strong>
ThreadLocal is a special Java class that allows you to create variables where each thread has its own, independent copy. This means:
- The value set by one thread is not visible to other threads.
- No synchronization is needed, because each thread works with its own value.

<strong>Why use ThreadLocal?</strong>
- To avoid sharing mutable state between threads (which can cause bugs and data corruption).
- To store per-thread data like user sessions, database connections, or objects that are not thread-safe (e.g., SimpleDateFormat).

<strong>How does it work?</strong>
- When a thread calls <code>set()</code> on a ThreadLocal variable, it stores the value in a special map that is private to that thread.
- When the same thread calls <code>get()</code>, it gets its own value.
- Other threads have their own independent values.

<strong>Simple Example:</strong>

```java

class ThreadLocalExample {
    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        Runnable task = () -> {
            threadId.set((int) (Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + " has threadId: " + threadId.get());
        };
        Thread t1 = new Thread(task, "Thread-A");
        Thread t2 = new Thread(task, "Thread-B");
        t1.start();
        t2.start();
    }
}
```
- Each thread sets and gets its own <code>threadId</code> value. The values are independent and not shared.

<strong>Common Use Cases:</strong>
- User/session context in web servers (each request handled by a different thread)
- Database connections per thread
- Storing objects that are not thread-safe (like SimpleDateFormat)

<strong>Important:</strong>
- Always call <code>remove()</code> when the thread is done (especially in thread pools) to avoid memory leaks.

<strong>Summary:</strong>
ThreadLocal provides a simple way to give each thread its own variable, making it easy to avoid shared state and synchronization issues in multi-threaded programs.

</details>

---
<details>
<summary>What are some advanced usages and best practices for ThreadLocal?</summary>

<strong>Advanced Usage of ThreadLocal:</strong>

1. <strong>Storing Non-Thread-Safe Objects (e.g., SimpleDateFormat):</strong>
- Many Java classes (like <code>SimpleDateFormat</code>) are not thread-safe. ThreadLocal allows each thread to have its own instance, avoiding synchronization.

```java

private static final ThreadLocal<SimpleDateFormat> formatter =
    ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

public static String formatDate(Date date) {
    return formatter.get().format(date);
}
```

2. <strong>Per-Thread Database Connections:</strong>
- In some frameworks, ThreadLocal is used to store a database connection per thread, so each thread reuses its own connection.

```java

private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

public static Connection getConnection() {
    Connection conn = connectionHolder.get();
    if (conn == null) {
        conn = // ... create new connection ...
        connectionHolder.set(conn);
    }
    return conn;
}
```

3. <strong>Implementing Context or Session Data:</strong>
- Web servers often use ThreadLocal to store user/session data for the duration of a request.

4. <strong>InheritableThreadLocal:</strong>
- <code>InheritableThreadLocal</code> allows child threads to inherit the value from the parent thread.

```java

private static final InheritableThreadLocal<String> context = new InheritableThreadLocal<>();
```

<strong>Best Practices:</strong>
- Always call <code>remove()</code> when the thread is done (especially in thread pools) to prevent memory leaks.
- Use ThreadLocal only when truly necessary; prefer passing data explicitly if possible.
- Avoid using ThreadLocal for large objects or objects with a long lifecycle.

<strong>Summary:</strong>
ThreadLocal is powerful for isolating state per thread, but should be used carefully to avoid memory leaks and design issues. It's best for non-thread-safe objects, per-thread resources, and context data in multi-threaded environments.

</details>

---

## Executor Framework:

<details>
<summary>Explain Java's Executor Service Framework in detail for interviews</summary>

<strong>What is the Executor Service Framework?</strong>
The Executor Service Framework is a high-level API in Java for managing threads and running tasks asynchronously. It simplifies thread management, improves scalability, and makes concurrent programming easier and safer.

<strong>Key Concepts:</strong>
- <strong>Executor:</strong> The root interface for launching tasks. The main method is <code>execute(Runnable)</code>.
- <strong>ExecutorService:</strong> A sub-interface that adds methods for managing and controlling task execution, such as <code>submit()</code>, <code>shutdown()</code>, <code>invokeAll()</code>, and <code>invokeAny()</code>.
- <strong>Thread Pool:</strong> A pool of worker threads that execute submitted tasks. Threads are reused, reducing the overhead of creating new threads for each task.
- <strong>Future:</strong> Represents the result of an asynchronous computation. You can check if the task is done, wait for it, or get the result.
- <strong>Callable:</strong> Like Runnable, but returns a result and can throw checked exceptions.

<strong>Why use ExecutorService?</strong>
- Manages a pool of threads for you (no need to create/manage threads manually)
- Handles task scheduling, execution, and lifecycle
- Supports task cancellation and result retrieval
- Improves performance and resource utilization

<strong>Common Implementations:</strong>
- <code>Executors.newFixedThreadPool(int n)</code>: Fixed number of threads
- <code>Executors.newCachedThreadPool()</code>: Creates new threads as needed, reuses idle threads
- <code>Executors.newSingleThreadExecutor()</code>: Single worker thread
- <code>Executors.newScheduledThreadPool(int n)</code>: For scheduled and periodic tasks

<strong>Basic Example:</strong>

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

Runnable task = () -> System.out.println("Running in " + Thread.currentThread().getName());
executor.execute(task); // Submit a Runnable

executor.shutdown(); // Always shutdown when done
```

<strong>Submitting Callable and Getting Results:</strong>

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
Callable<Integer> task = () -> 42;
Future<Integer> future = executor.submit(task);
Integer result = future.get(); // Blocks until result is available
System.out.println("Result: " + result);
executor.shutdown();
```

<strong>Key Methods:</strong>
- <code>execute(Runnable)</code>: Run a task, no result
- <code>submit(Runnable/Callable)</code>: Run a task, returns Future
- <code>shutdown()</code>: Initiates an orderly shutdown
- <code>shutdownNow()</code>: Attempts to stop all running tasks
- <code>invokeAll()</code>: Runs a collection of tasks, returns list of Futures
- <code>invokeAny()</code>: Runs a collection of tasks, returns result of one that completes first

<strong>Best Practices:</strong>
- Always call <code>shutdown()</code> to free resources
- Prefer using thread pools over manual thread creation
- Use <code>Future</code> to handle results and exceptions

<strong>Summary:</strong>
The Executor Service Framework is the standard way to manage threads and tasks in modern Java. It abstracts away thread management, provides powerful features for task execution, and is essential for writing scalable, maintainable concurrent applications.

</details>

---
<details>
<summary>What is Callable and Future?</summary>

- <code>Callable&lt;T&gt;</code>: Like Runnable, but returns a result and can throw checked exceptions.
- <code>Future&lt;T&gt;</code>: Represents the result of an asynchronous computation, provides methods to check if the computation is complete, wait for completion, and retrieve the result.

</details>

---
<details>
<summary>What is the difference between submit() and execute() in ExecutorService?</summary>

- <code>execute()</code>: Accepts Runnable, does not return a result.
- <code>submit()</code>: Accepts Runnable or Callable, returns a Future.

</details>

---

## Fork/Join Pool Framework:

---

<details>
<summary>What is the Fork/Join framework?</summary>

A framework for parallelism that recursively splits tasks into smaller subtasks (fork), then combines results (join). Used for divide-and-conquer algorithms.

</details>

---
<details>
<summary>Explain the Fork/Join framework in Java with an example</summary>

<strong>What is the Fork/Join framework?</strong>
The Fork/Join framework (introduced in Java 7) is designed for parallelism in Java. It helps you break a large task into smaller subtasks (fork), process them in parallel, and then combine the results (join). It's ideal for divide-and-conquer algorithms.

<strong>Key Points:</strong>
- Uses a special thread pool called <code>ForkJoinPool</code>.
- Tasks extend <code>RecursiveTask&lt;V&gt;</code> (returns a result) or <code>RecursiveAction</code> (no result).
- The <code>compute()</code> method defines how to split and process the task.
- <code>fork()</code> submits a subtask for parallel execution; <code>join()</code> waits for its result.

<strong>Example: Parallel Sum using Fork/Join</strong>

```java
import java.util.concurrent.*;

class SumTask extends RecursiveTask<Integer> {
    private final int[] arr;
    private final int start, end;
    private static final int THRESHOLD = 3;

    public SumTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) sum += arr[i];
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTask left = new SumTask(arr, start, mid);
            SumTask right = new SumTask(arr, mid, end);
            left.fork(); // process left in parallel
            int rightResult = right.compute(); // process right in current thread
            int leftResult = left.join(); // wait for left
            return leftResult + rightResult;
        }
    }
}

public class ForkJoinExample {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(arr, 0, arr.length);
        int result = pool.invoke(task);
        System.out.println("Sum: " + result);
    }
}
```
- The array is split recursively until small enough, then summed in parallel.
- <code>ForkJoinPool</code> manages the worker threads and task scheduling.

<strong>Summary:</strong>
The Fork/Join framework is ideal for parallelizing recursive, divide-and-conquer tasks, making it easy to leverage multi-core processors in Java.

</details>

---
<details>
<summary>Which Java APIs use the Fork/Join framework?</summary>

Yes, several core Java APIs use the Fork/Join framework internally to provide efficient parallelism:

<strong>1. Java Streams API (parallel streams):</strong>
- When you use <code>stream().parallel()</code> or <code>parallelStream()</code> on a collection, Java uses the Fork/Join framework under the hood to process stream operations in parallel.
- Example:
  
```java
List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
int sum = list.parallelStream().mapToInt(Integer::intValue).sum();
```
- The parallel stream splits the data and processes chunks in parallel using a <code>ForkJoinPool</code>.

<strong>2. Arrays.parallelSort():</strong>
- The <code>Arrays.parallelSort()</code> method (Java 8+) uses the Fork/Join framework to sort large arrays in parallel.
- Example:

```java
int[] arr = {5, 2, 8, 1, 3};
Arrays.parallelSort(arr);
```
- The array is divided and sorted in parallel using Fork/Join tasks.

<strong>3. RecursiveTask and RecursiveAction:</strong>
- Any custom code using <code>RecursiveTask</code> or <code>RecursiveAction</code> (as shown in the Fork/Join example) leverages the Fork/Join framework.

<strong>Summary:</strong>
- The Fork/Join framework powers parallel streams, parallel array sorting, and can be used directly for custom parallel algorithms in Java.

</details>

---

<details>
<summary>What is the difference between Executor Framework and Fork/Join Pool? When should you use each?</summary>

<strong>Executor Framework:</strong>
- General-purpose framework for managing and executing asynchronous tasks using a pool of threads.
- Suitable for running independent tasks (Runnable or Callable) that do not need to be split into subtasks.
- Provides various thread pool types (fixed, cached, single, scheduled).
- Tasks are typically unrelated and do not require recursive decomposition.
- Example use cases: Web server request handling, background jobs, task queues.

<strong>Fork/Join Pool:</strong>
- Specialized implementation of ExecutorService designed for parallelism and divide-and-conquer algorithms.
- Ideal for tasks that can be recursively split into smaller subtasks (using RecursiveTask or RecursiveAction).
- Uses work-stealing to efficiently balance load among worker threads.
- Example use cases: Parallel array processing, recursive algorithms (sorting, searching, computations), parallel streams.

<strong>Summary Table:</strong>

| Feature                | Executor Framework           | Fork/Join Pool                |
|------------------------|-----------------------------|-------------------------------|
| Task Type              | Independent tasks           | Recursive, split/join tasks   |
| API                    | Runnable/Callable           | RecursiveTask/RecursiveAction |
| Use Case               | Web servers, task queues    | Parallel algorithms, streams  |
| Work Stealing          | No                          | Yes                           |
| Parallel Streams       | No                          | Yes (used internally)         |

<strong>When to use which?</strong>
- Use <strong>Executor Framework</strong> for general asynchronous task execution (independent jobs, background processing).
- Use <strong>Fork/Join Pool</strong> for parallelizing recursive, divide-and-conquer tasks or when using parallel streams/parallelSort.

</details>

---

## CountDownLatch & CyclicBarrier

<details>
<summary>What is CountDownLatch? Explain with an example</summary>

<strong>What is CountDownLatch?</strong>
CountDownLatch is a synchronization aid in <code>java.util.concurrent</code> that allows one or more threads to wait until a set of operations in other threads completes. It is initialized with a count, and threads can decrement the count using <code>countDown()</code>. When the count reaches zero, all waiting threads are released.

<strong>Key Points:</strong>
- Used to make one or more threads wait for a set of tasks to finish.
- Once the count reaches zero, the latch cannot be reset.
- Commonly used for starting a task only after several threads have completed their work, or for waiting for multiple services to be ready.

<strong>Example: Waiting for Multiple Threads to Finish</strong>

```java

import java.util.concurrent.CountDownLatch;

public class LatchExample {
    public static void main(String[] args) throws InterruptedException {
        int numWorkers = 3;
        CountDownLatch latch = new CountDownLatch(numWorkers);

        for (int i = 0; i < numWorkers; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is working");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                latch.countDown(); // Decrement the count
                System.out.println(Thread.currentThread().getName() + " finished");
            }, "Worker-" + i).start();
        }

        System.out.println("Main thread waiting for workers...");
        latch.await(); // Wait until count reaches zero
        System.out.println("All workers finished. Main thread continues.");
    }
}
```
- The main thread waits until all worker threads call <code>countDown()</code>.
- When the count reaches zero, <code>await()</code> unblocks and the main thread continues.

<strong>Summary:</strong>
CountDownLatch is useful for coordinating threads, such as waiting for multiple tasks to complete before proceeding.

</details>

---
<details>
<summary>What is CyclicBarrier? Explain with an example</summary>

<strong>What is CyclicBarrier?</strong>
CyclicBarrier is a synchronization aid in <code>java.util.concurrent</code> that allows a set of threads to all wait for each other to reach a common barrier point. Once all threads have reached the barrier, they are released to continue their work. The barrier is called "cyclic" because it can be reused after the waiting threads are released.

<strong>Key Points:</strong>
- Used to coordinate a fixed number of threads to wait for each other at a certain point.
- Once the specified number of threads have called <code>await()</code>, the barrier is tripped and all threads proceed.
- Can optionally run a barrier action (a Runnable) when the barrier is tripped.
- Can be reused (unlike CountDownLatch, which cannot be reset).

<strong>Example: Waiting for Threads to Start Together</strong>

```java

import java.util.concurrent.CyclicBarrier;

public class BarrierExample {
    public static void main(String[] args) {
        int numWorkers = 3;
        CyclicBarrier barrier = new CyclicBarrier(numWorkers, () ->
            System.out.println("All workers reached the barrier. Proceeding together!")
        );

        for (int i = 0; i < numWorkers; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " is ready");
                try {
                    barrier.await(); // Wait for all threads
                } catch (Exception e) {}
                System.out.println(Thread.currentThread().getName() + " started work");
            }, "Worker-" + i).start();
        }
    }
}
```
- Each worker thread calls <code>await()</code> on the barrier.
- When all threads have called <code>await()</code>, the barrier action runs and all threads proceed.
- The barrier can be reused for another round if needed.

<strong>Summary:</strong>
CyclicBarrier is useful for scenarios where threads must wait for each other at a common point before continuing, such as in parallel computations or multi-phase tasks.

</details>

---

<details>
<summary>CountDownLatch vs CyclicBarrier: When to use and key differences</summary>

<strong>CountDownLatch</strong>
- Used when one or more threads need to wait for a set of operations in other threads to complete.
- The count is set once and cannot be reset (one-shot event).
- Once the count reaches zero, the latch cannot be reused.
- Typical use: Main thread waits for several worker threads to finish.

<strong>CyclicBarrier</strong>
- Used when a group of threads must wait for each other to reach a common barrier point.
- The barrier is reusable (cyclic) after all threads reach the barrier.
- Can run a barrier action when the barrier is tripped.
- Typical use: Multiple threads perform phases of work and must synchronize at the end of each phase.

<strong>Summary Table:</strong>

| Feature                | CountDownLatch                | CyclicBarrier                  |
|------------------------|-------------------------------|-------------------------------|
| Resettable             | No (one-shot)                 | Yes (cyclic/reusable)         |
| Waits for              | Count to reach zero           | All parties to reach barrier  |
| Barrier Action         | No                            | Yes (optional Runnable)       |
| Usage Pattern          | One or more wait, others count down | All threads wait for each other |
| Common Use Case        | Wait for N threads/tasks to finish | Synchronize threads at phase boundary |

<strong>When to use which?</strong>
- Use <strong>CountDownLatch</strong> when you want one or more threads to wait for a set of tasks to complete (e.g., main thread waits for workers).
- Use <strong>CyclicBarrier</strong> when you want a group of threads to wait for each other at a common point, and possibly repeat this process (e.g., parallel phases in computation).

</details>



---
<details>
<summary>What is the difference between synchronized collection and concurrent collection?</summary>

- <strong>Synchronized collection:</strong> Wrapper around standard collections (e.g., <code>Collections.synchronizedList()</code>), locks the entire collection.
- <strong>Concurrent collection:</strong> Designed for concurrency (e.g., <code>ConcurrentHashMap</code>), allows better scalability and performance.

</details>

---
<details>
<summary>What is the difference between Thread.sleep() and Object.wait()?</summary>

- <code>Thread.sleep()</code>: Pauses the current thread for a specified time, does not release any locks.
- <code>Object.wait()</code>: Pauses the current thread until notified, releases the lock on the object.

</details>

---
<details>
<summary>What is the difference between Runnable and Callable?</summary>

- <code>Runnable</code>: No return value, cannot throw checked exceptions.
- <code>Callable&lt;T&gt;</code>: Returns a value, can throw checked exceptions.

</details>

---
<details>
<summary>What is the difference between Thread.yield() and Thread.sleep()?</summary>

- <code>Thread.yield()</code>: Suggests to the scheduler to pause the current thread and give other threads a chance to run.
- <code>Thread.sleep()</code>: Pauses the current thread for a specified time.

</details>

---
<details>
<summary>What is the difference between user thread and daemon thread?</summary>

- <strong>User thread:</strong> Keeps the JVM running until it finishes.
- <strong>Daemon thread:</strong> Runs in the background; JVM exits when only daemon threads remain (e.g., garbage collector).

</details>


## Locks:

---
<details>
<summary>Explain Java Locks in detail for interviews, with examples</summary>

<strong>What are Locks?</strong>
Locks are advanced synchronization mechanisms in Java (from <code>java.util.concurrent.locks</code>) that provide more flexibility than the <code>synchronized</code> keyword. They allow you to control access to shared resources in multi-threaded programs.

<strong>Why use Locks?</strong>
- More control: Explicit lock/unlock, tryLock, fairness, interruptible lock acquisition
- Can implement complex locking strategies (e.g., read/write locks)
- Useful for high-performance and scalable concurrent applications

<strong>Main Types of Locks:</strong>
- <strong>Lock (interface):</strong> The base interface for all lock implementations
- <strong>ReentrantLock:</strong> The most commonly used lock; allows the same thread to acquire the lock multiple times (reentrant)
- <strong>ReadWriteLock:</strong> Allows multiple readers or one writer at a time

<strong>Basic Example: ReentrantLock</strong>

```java

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
```
- <code>lock.lock()</code> acquires the lock; <code>lock.unlock()</code> releases it (always use <code>finally</code> to avoid deadlocks).

<strong>tryLock Example:</strong>

```java

if (lock.tryLock()) {
    try {
        // critical section
    } finally {
        lock.unlock();
    }
} else {
    // Could not acquire lock, handle accordingly
}
```
- <code>tryLock()</code> attempts to acquire the lock without blocking.

<strong>ReadWriteLock Example:</strong>

```java

import java.util.concurrent.locks.ReentrantReadWriteLock;

class Data {
    private int value;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(int v) {
        rwLock.writeLock().lock();
        try {
            value = v;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int read() {
        rwLock.readLock().lock();
        try {
            return value;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
```
- Multiple threads can read at the same time, but only one can write.

<strong>Key Features of Locks:</strong>
- Explicit lock/unlock control
- <code>tryLock()</code> for non-blocking attempts
- Fairness policies (first-come, first-served)
- Interruptible lock acquisition

<strong>Best Practices:</strong>
- Always release locks in a <code>finally</code> block
- Prefer <code>Lock</code> for advanced scenarios; use <code>synchronized</code> for simple cases
- Avoid deadlocks by careful lock ordering and design

<strong>Summary:</strong>
Locks provide advanced, flexible control over synchronization in Java. They are essential for building scalable, high-performance concurrent applications and are a key topic for interviews.

</details>

---
<details>
<summary>What is the difference between lock() and tryLock() methods?</summary>

<strong>lock():</strong>
- Acquires the lock, blocking the current thread until the lock is available.
- If another thread holds the lock, the calling thread waits (potentially forever) until it can acquire the lock.
- Usage:

```java

lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
```

<strong>tryLock():</strong>
- Attempts to acquire the lock <strong>immediately</strong> without waiting.
- Returns <code>true</code> if the lock was acquired, <code>false</code> otherwise.
- Useful for avoiding deadlocks or when you want to do something else if the lock is not available.
- Usage:

```java

if (lock.tryLock()) {
    try {
        // critical section
    } finally {
        lock.unlock();
    }
} else {
    // Could not acquire lock, handle alternative logic
}
```

<strong>Summary:</strong>
- <code>lock()</code> always waits until the lock is available.
- <code>tryLock()</code> does not wait; it returns immediately with success or failure.

</details>

---

<details>
<summary>What is the difference between synchronized and Lock?</summary>

- <code>synchronized</code>: Built-in, less flexible, automatically released.
- <code>Lock</code>: From <code>java.util.concurrent.locks</code>, more flexible, explicit lock/unlock, supports tryLock, fairness, and interruptible lock acquisition.

</details>

---
<details>
<summary>What is ReentrantLock?</summary>

A Lock implementation that allows the same thread to acquire the lock multiple times (reentrant). Provides advanced features like fairness, tryLock, and condition variables.

</details>

---
<details>
<summary>What is ReadWriteLock?</summary>

Allows multiple threads to read at the same time, but only one to write. Improves performance for read-heavy scenarios.

</details>

---

<details>
<summary>What is a Semaphore? Explain with an example</summary>

<strong>What is a Semaphore?</strong>
A Semaphore is a concurrency utility in <code>java.util.concurrent</code> that controls access to a shared resource by multiple threads. It maintains a set of permits; threads must acquire a permit before accessing the resource and release it when done. If no permits are available, threads wait until one is released.

<strong>Key Points:</strong>
- Used to limit the number of threads accessing a resource at the same time.
- Can be used for resource pools, connection limits, or controlling access to sections of code.
- Two main methods: <code>acquire()</code> (waits for a permit) and <code>release()</code> (returns a permit).

<strong>Example: Limiting Concurrent Access</strong>

```java
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        int maxConcurrent = 3;
        Semaphore semaphore = new Semaphore(maxConcurrent);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire(); // Acquire a permit
                    System.out.println(Thread.currentThread().getName() + " acquired a permit");
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " releasing permit");
                    semaphore.release(); // Release the permit
                }
            }, "Worker-" + i).start();
        }
    }
}
```
- Only 3 threads can access the critical section at the same time; others wait for a permit.
- When a thread finishes, it releases its permit, allowing another thread to proceed.

<strong>Summary:</strong>
Semaphore is useful for controlling access to limited resources, such as connection pools, or for implementing throttling in concurrent applications.

</details>

---
<details>
<summary>What is the difference between Locks and Semaphore?</summary>

<strong>Locks</strong>
- Used to provide exclusive access to a shared resource (usually only one thread at a time).
- Only one thread can hold the lock at a time (unless using ReadWriteLock for multiple readers).
- Used for mutual exclusion (critical sections).
- Examples: <code>ReentrantLock</code>, <code>ReadWriteLock</code>.
- Lock must be explicitly acquired and released by the thread.

<strong>Semaphore</strong>
- Controls access to a resource by allowing a fixed number of threads to access it simultaneously (not just one).
- Maintains a set of permits; threads acquire/release permits.
- Used for limiting concurrency (e.g., connection pools, throttling).
- Can be used for signaling between threads (counting or binary semaphore).
- Examples: <code>Semaphore</code> (counting or binary).

<strong>Summary Table:</strong>

| Feature                | Lock                        | Semaphore                      |
|------------------------|-----------------------------|--------------------------------|
| Purpose                | Mutual exclusion            | Concurrency control            |
| Number of holders      | One (or many for read lock) | Configurable (1 to N)          |
| Usage                  | Critical section            | Resource pool, throttling      |
| Acquire/Release        | lock()/unlock()             | acquire()/release()            |
| Fairness/Timeouts      | Supported                   | Supported                      |
| Signaling              | No                          | Yes (can be used for signaling)|

<strong>When to use which?</strong>
- Use <strong>Lock</strong> when you need exclusive access to a resource (mutual exclusion).
- Use <strong>Semaphore</strong> when you want to allow a fixed number of threads to access a resource concurrently.

</details>

