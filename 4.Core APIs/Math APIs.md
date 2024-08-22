# Math APIs

- Java comes with a powerful Math class with many methods to make your life easier.
- Pay special attention to return types in math questions. They are an excellent opportunity for trickery!

#### Finding the Minimum and Maximum
- The min() and max() methods compare two values and return one of them.

````java
public static double min(double a, double b); // int/long/float/double
public static int max(int a, int b); // int/long/float/double
````
- If the arguments have the same value, the result is that same value. 
- If either value is NaN, then the result is NaN.
- Unlike the numerical comparison operators, this method considers negative zero to be strictly smaller than positive zero. 
- If one argument is positive zero and the other is negative zero, the result is negative zero.

#### Rounding Numbers:
- The round() method gets rid of the decimal portion of the value, choosing the next higher number if appropriate. 
- If the fractional part is .5 or higher, we round up.

````java
public static long round(double num);
public static int round(float num);
````

- Example:
````java
long low = Math.round(123.45); // 123
long high = Math.round(123.50); // 124
int fromFloat = Math.round(123.45f); // 123
````

#### Determining the Ceiling and Floor:

- The ceil() method takes a double value. If it is a whole number, it returns the same value.
- If it has any fractional value, it rounds up to the next whole number. By contrast, the floor() method discards any values after the decimal.

````java
public static double ceil(double num);
public static double floor(double num);
````

- Example:
````java
double c = Math.ceil(3.14); // 4.0
double f = Math.floor(3.14); // 3.0
````

#### Calculating Exponents:

- The pow() method handles exponents.
````java
public static double pow(double a, double b);//Returns the value of the first argument raised to the power of the second argument.

double squared = Math.pow(5, 2); // 25.0
````

#### Generating Random Numbers:
- The random() method returns a value greater than or equal to 0 and less than 1.
````java
static double random();
````

- Returns a double value with a positive sign, greater than or equal to 0.0 and less than 1.0.
- Returned values are chosen pseudorandomly with (approximately) uniform distribution from that range.
- When this method is first called, it creates a single new pseudorandom-number generator, exactly as if by the expression
``new java.util.Random()``
- This new pseudorandom-number generator is used thereafter for all calls to this method and is used nowhere else.
- it is common to use the Random class for generating pseudo-random numbers. It allows generating numbers of different types.

- TODO: java.util.Random 
- 