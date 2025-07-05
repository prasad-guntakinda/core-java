package streams;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupByAndPartition {
    public static void main(String[] args) {
       //groupByExample();
       partitionExample();

    }

    private static void partitionExample() {
        var employees = createEmployees();
        // Example of partitioning employees based on salary
        employees.stream()
            .collect(Collectors.partitioningBy(emp -> emp.salary() > 50000))
            .forEach((isHighSalary, empList) -> {
                System.out.println("High Salary: " + isHighSalary + " Employees: " + empList);
            });

        System.out.println("--------------------------------------------------");
        // Example of partitioning employees based on salary and collecting names
        employees.stream()
        .collect(Collectors.partitioningBy(emp -> emp.salary() > 50000, 
            Collectors.mapping(emp -> emp.name(), Collectors.toList())))
            .forEach((isHighSalary, empNames) -> {
                System.out.println("High Salary: " + isHighSalary + " Employee Names: " + empNames);
            });
    }

    private static void groupByExample() {
        // Example of grouping by length of names
         var  names = Stream.of("ram", "shyam", "hari", "sita", "gita", "ram", "shyam");
        names.collect(Collectors.groupingBy(name-> name.length()))
        .forEach((len, list)->{
            System.out.println("Length: " + len + " Names: " + list);
        });
        System.out.println("--------------------------------------------------");

        // Example of grouping by department and collecting employee names
        // using a Set to avoid duplicates
        var emps = createEmployees();
        emps.stream().collect(Collectors.groupingBy(emp-> emp.department(), Collectors.mapping(emp-> emp.name(), Collectors.toSet())))
        .forEach((dept, name) -> {
            System.out.println("Department: " + dept + " Employee Name: " + name);
        });

         System.out.println("--------------------------------------------------");

        // Example of grouping by department and collecting employee names
        // using a TreeMap to maintain order
        emps.stream().collect(Collectors.groupingBy(emp-> emp.department(), TreeMap::new, Collectors.mapping(emp-> emp.name(), Collectors.toList())))
        .forEach((dept, name) -> {
            System.out.println("Department: " + dept + " Employee Names: " + name);
        });
         System.out.println("--------------------------------------------------");

         // Example of grouping by department and collecting employee names
        // using a TreeMap to maintain order and counting employees
        emps.stream().collect(Collectors.groupingBy(emp-> emp.department(), TreeMap::new, Collectors.counting()))
        .forEach((dept, count) -> { 
            System.out.println("Department: " + dept + " Employee Count: " + count);
        });
         System.out.println("--------------------------------------------------");

         HashMap
    }

    private static List<Employee> createEmployees() {
        return List.of(
            new Employee("Zenny", 15, 30000, "HR"),
            new Employee("Alice", 1, 50000, "HR"),
            new Employee("Bob", 2, 60000, "IT"),
            new Employee("Charlie", 3, 70000, "IT"),
            new Employee("Bob", 23, 20000, "IT"),
            new Employee("David", 4, 80000, "Finance")
        );
    }
}

record Employee(String name, int id, double salary, String department) {
}
