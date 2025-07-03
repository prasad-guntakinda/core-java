import java.util.function.*;
class LambdaApp {
    public static void main(String[] args) {
        //()->{};
        BooleanSupplier bs = ()-> true;
        Consumer c1 = s-> {};
        Consumer c2 = (s)-> {};
        //BiConsumer bc1 = x,y ->{}; // Invalid Missing Parenthesis
        BiConsumer bc = (x,y) ->{};

        //Predicate<String> p1 = s->return s.startsWith("test"); // illegal start of expression
        Predicate<String> p2 = s-> s.startsWith("test");
       // Predicate<String> p3 = s-> s+"hello";s.startsWith("test"); //
        //var v = s-> true;
    }
}

//@FunctionalInterface
interface FlyCheck{
    String toString();
}