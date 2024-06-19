

```java
//Arrays: class
<T> T[] copyOfRange(T[] original, int from, int to);

static <T> Stream<T> stream(T[] array); //Returns a sequential Stream with the specified array as its source.

static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive); //Returns a sequential Stream with the specified range of the specified array as its source.

```





### References:


https://www.baeldung.com/java-slicing-arrays