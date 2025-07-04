package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapNFlatMapExample {
    public static void main(String[] args) {
        // Example of map()
        List<String> words = Arrays.asList("hello", "world");
        List<Integer> lengths = words.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Lengths: " + lengths); // Output: [5, 5]

        // Example of flatMap()
        List<String> sentences = Arrays.asList("hello world", "java stream");
        List<String> allWords = sentences.stream()
            .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
            .collect(Collectors.toList());
        System.out.println("All words: " + allWords); // Output: ["hello", "world", "java", "stream"]
    }
}
