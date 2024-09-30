# OOPS Failures:

1. Does this code compile?

````java
class Point { int x, y; }
class ColoredPoint extends Point { int color; }
class Test
{
   static void test(ColoredPoint p, Point q)
   {
      System.out.println("(ColoredPoint, Point)");
   }
   static void test(Point p, ColoredPoint q)
   {
      System.out.println("(Point, ColoredPoint)");
   }
   public static void main(String[] args)
   {
      ColoredPoint cp = new ColoredPoint();
      test(cp, cp);
   }
}
//Compile Error
````

- Ans:
````text
This code produces an error at compile time for the call test(cp, cp);. The problem is that there are two declarations of test that are applicable and accessible, and neither is more specific than the other. Therefore, the method invocation is ambiguous.
If you add a third definition of test:

static void test(ColoredPoint p, ColoredPoint q)
{
    System.out.println("(ColoredPoint, ColoredPoint)");
}


then it would be more specific than the other two, and the method invocation would no longer be ambiguous.
````