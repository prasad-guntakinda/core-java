# Java Thread and Concurrency Interview Questions

<details>
<summary>What is the difference between process and thread?</summary>

- <strong>Process:</strong> An independent program in execution with its own memory space.
- <strong>Thread:</strong> A lightweight unit of execution within a process, sharing memory with other threads in the same process.

</details>

<details>
<summary>How do you create a thread in Java?</summary>

- Extend <code>Thread</code> class and override <code>run()</code> method.
- Implement <code>Runnable</code> interface and pass to <code>Thread</code> constructor.
- Implement <code>Callable&lt;T&gt;</code> and use <code>ExecutorService</code> for tasks that return a result.

</details>

<details>
<summary>What is the lifecycle of a thread?</summary>

New → Runnable → Running → Blocked/Waiting/Sleeping → Dead (Terminated)

</details>

<details>
<summary>What is the difference between start() and run() methods?</summary>

- <code>start()</code> creates a new thread and calls <code>run()</code> in that thread.
- <code>run()</code> is just a normal method call; no new thread is created.

</details>

<details>
<summary>What is synchronization? Why is it needed?</summary>

Synchronization ensures that only one thread can access a shared resource at a time, preventing data inconsistency and race conditions.

</details>

<details>
<summary>What is a deadlock? How can you prevent it?</summary>

Deadlock occurs when two or more threads are blocked forever, each waiting for a lock held by the other. Prevent by:
- Lock ordering
- Using <code>tryLock()</code>
- Avoiding nested locks

</details>

<details>
<summary>What is a race condition?</summary>

A race condition occurs when multiple threads access shared data and try to change it at the same time, leading to unpredictable results.

</details>

<details>
<summary>What is the difference between synchronized method and synchronized block?</summary>

- <strong>Synchronized method:</strong> Locks the entire method on the object (or class for static methods).
- <strong>Synchronized block:</strong> Locks only a specific block of code, can specify which object to lock.

</details>

<details>
<summary>What is volatile keyword?</summary>

<code>volatile</code> ensures visibility of changes to variables across threads. Reads/writes to a volatile variable are always from/to main memory.

</details>

<details>
<summary>What is the difference between wait(), notify(), and notifyAll()?</summary>

- <code>wait()</code>: Causes the current thread to wait until another thread calls <code>notify()</code> or <code>notifyAll()</code> on the same object.
- <code>notify()</code>: Wakes up one waiting thread.
- <code>notifyAll()</code>: Wakes up all waiting threads.
- Must be called from synchronized context.

</details>

<details>
<summary>What is ThreadLocal?</summary>

<code>ThreadLocal</code> provides thread-local variables. Each thread has its own independent value, useful for user sessions, database connections, etc.

</details>

<details>
<summary>What is the Executor framework?</summary>

A high-level API for managing threads and tasks. Includes <code>ExecutorService</code>, <code>ThreadPoolExecutor</code>, <code>ScheduledExecutorService</code>, etc. Handles thread pooling, scheduling, and task execution.

</details>

<details>
<summary>What is Callable and Future?</summary>

- <code>Callable&lt;T&gt;</code>: Like Runnable, but returns a result and can throw checked exceptions.
- <code>Future&lt;T&gt;</code>: Represents the result of an asynchronous computation, provides methods to check if the computation is complete, wait for completion, and retrieve the result.

</details>

<details>
<summary>What is the difference between submit() and execute() in ExecutorService?</summary>

- <code>execute()</code>: Accepts Runnable, does not return a result.
- <code>submit()</code>: Accepts Runnable or Callable, returns a Future.

</details>

<details>
<summary>What is the difference between synchronized and Lock?</summary>

- <code>synchronized</code>: Built-in, less flexible, automatically released.
- <code>Lock</code>: From <code>java.util.concurrent.locks</code>, more flexible, explicit lock/unlock, supports tryLock, fairness, and interruptible lock acquisition.

</details>

<details>
<summary>What is ReentrantLock?</summary>

A Lock implementation that allows the same thread to acquire the lock multiple times (reentrant). Provides advanced features like fairness, tryLock, and condition variables.

</details>

<details>
<summary>What is ReadWriteLock?</summary>

Allows multiple threads to read at the same time, but only one to write. Improves performance for read-heavy scenarios.

</details>

<details>
<summary>What is CountDownLatch?</summary>

A synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.

</details>

<details>
<summary>What is CyclicBarrier?</summary>

A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point.

</details>

<details>
<summary>What is Semaphore?</summary>

A counting semaphore controls access to a resource by multiple threads. It maintains a set of permits; threads acquire/release permits to enter/exit the critical section.

</details>

<details>
<summary>What is the difference between synchronized collection and concurrent collection?</summary>

- <strong>Synchronized collection:</strong> Wrapper around standard collections (e.g., <code>Collections.synchronizedList()</code>), locks the entire collection.
- <strong>Concurrent collection:</strong> Designed for concurrency (e.g., <code>ConcurrentHashMap</code>), allows better scalability and performance.

</details>

<details>
<summary>What is the Fork/Join framework?</summary>

A framework for parallelism that recursively splits tasks into smaller subtasks (fork), then combines results (join). Used for divide-and-conquer algorithms.

</details>

<details>
<summary>What is the difference between Thread.sleep() and Object.wait()?</summary>

- <code>Thread.sleep()</code>: Pauses the current thread for a specified time, does not release any locks.
- <code>Object.wait()</code>: Pauses the current thread until notified, releases the lock on the object.

</details>

<details>
<summary>What is the difference between Runnable and Callable?</summary>

- <code>Runnable</code>: No return value, cannot throw checked exceptions.
- <code>Callable&lt;T&gt;</code>: Returns a value, can throw checked exceptions.

</details>

<details>
<summary>What is the difference between Thread.yield() and Thread.sleep()?</summary>

- <code>Thread.yield()</code>: Suggests to the scheduler to pause the current thread and give other threads a chance to run.
- <code>Thread.sleep()</code>: Pauses the current thread for a specified time.

</details>

<details>
<summary>What is the difference between user thread and daemon thread?</summary>

- <strong>User thread:</strong> Keeps the JVM running until it finishes.
- <strong>Daemon thread:</strong> Runs in the background; JVM exits when only daemon threads remain (e.g., garbage collector).

</details>