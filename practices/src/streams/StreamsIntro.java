package streams;

import java.util.stream.Stream;

public class StreamsIntro {
    public static void main(String[] args) {
       finiteStreams();
       //infiniteStreams();

    
    }

    private static void finiteStreams() {
        Stream.empty()
            .forEach(System.out::println);
            System.out.println("-----------------");
        Stream.of("Welcome to Streams")
            .forEach(System.out::println);
            System.out.println("-----------------");
        Stream.ofNullable(null)
            .forEach(System.out::println);
            System.out.println("-----------------");


       /*  Stream.of(null)
            .forEach(System.out::println); */ // NullPointerException
          
        Stream.of(12,20,30,null, 40)
            .forEach(System.out::print);
        System.out.println("------------------");
        Stream.Builder<Integer> mBuilder = Stream.<Integer>builder().add(20).add(40);
        mBuilder.accept(500);
        mBuilder.andThen(num -> mBuilder.add(num));
                
        mBuilder.build().forEach(System.out::print);
        System.out.println("------------------");

        Stream<String> s1 = Stream.of("a", "b", "c"); // stream of 3 elements
Stream<int[]> s2 = Stream.of(new int[]{1, 2, 3}); // stream of 1 element (the array)
s2.forEach(System.out::println); // prints the array reference
    }

    private static void infiniteStreams() {
       Stream.generate(()-> "hello").limit(5).forEach(System.out::print);
       System.out.println();
       Stream.iterate(1, n -> n + 1)
            .limit(10)
            .forEach(System.out::print);
        System.out.println();
        
        Stream.iterate(1, n -> n + 2)
            .limit(10)
            .forEach(System.out::print);
        System.out.println();
        
        Stream.iterate(0, n -> n < 16, n -> n + 2)
            .forEach(System.out::print);
        System.out.println();
    }

}
