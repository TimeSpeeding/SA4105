package workshop;

import java.util.*;
import java.util.stream.*;

class Staff implements Comparable<Staff>{
	String id;
	String name;
	int grade;
	float salary;
	
	Staff(String id, String name, int grade, float salary) {
		this.id = id;
		this.name = name;
		this.grade = grade;	
		this.salary = salary;
	}
	
	public String toString() {
		return "Id: " + id + "\n" 
				+ "Name: " + name + "\t" 
				+ "Grade: " + grade + "\t" 
				+ "Salary: " + salary;	
	}
	
	public int compareTo(Staff staff) {
		return Double.compare(salary, staff.salary);
	}
}

public class StreamWshop {

	public static void main(String[] args) {
		List<Staff> list;
		
		List<Staff> staffList = new ArrayList<>();
		staffList.add(new Staff("6c0231c1-b015-4ca6-8dea-0efda89b9342", "John", 5, 4293));
		staffList.add(new Staff("270d0145-c551-494f-8195-8b65da8f2322", "Mike", 6, 6530));
		staffList.add(new Staff("e75a1d7e-e649-4a10-a566-566c948e2000", "Jean", 3, 2220));
		staffList.add(new Staff("c1c0a83a-faba-4be6-a1f8-2c8a60ba4da0", "Kim", 4, 3390));
		
		// exercise 1: using streams' forEach(), print the name of every staff
		System.out.println("[Exercise 1: ForEach]");
		staffList.stream().forEach(x -> System.out.println(x.name));
		
		// exercise 2: using streams' sum(), compute the total salary of all staff
		System.out.println("\n[Exercise 2: Sum]");
		System.out.println(staffList.stream().mapToDouble(x -> x.salary).sum());
		
		// exercise 3: using streams' filter(), retrieve all salary that are more than 
		// 3500 into a List, then print each of the object within the List
		System.out.println("\n[Exercise 3: Filter]");
		staffList.stream().filter(x -> x.salary > 3500).forEach(x -> System.out.println(x));
		
		// exercise 4: using streams' map(), print the list of increments that are less 
		// than 200; given a staff's increment is such that a grade of 5 and above 
		// will yield a 8% increment (off of the staff's current salary), while any other grades
		// will only yield a 4% increment.
		System.out.println("\n[Exercise 4: Map]");
		staffList.stream().map(x -> {
			if (x.grade >= 5) return x.salary * 0.08;
			else return x.salary * 0.04;
			}).filter(x -> x < 200).forEach(System.out::println);
		
		// exercise 5: using streams' sort(), sort the staff by salary in ascending 
		// order (least salary first) and print out each staff object
		System.out.println("\n[Exercise 5: Sort]");
		staffList.stream().sorted().forEach(System.out::println);
		
		// exercise 6: using streams' max(), display the highest salary among the staff
		System.out.println("\n[Exercise 6: Max]");
		System.out.println(staffList.stream().mapToDouble(x -> x.salary).max().getAsDouble());
		
		
		// exercise 7: using streams' reduce(), implement your own "max" function 
		// to display the highest salary among the staff
		System.out.println("\n[Exercise 7: Reduce]");
		System.out.println(staffList.stream().mapToDouble(x -> x.salary).reduce(0, (a, b) -> Double.max(a, b)));
		staffList.stream().mapToDouble(x -> x.salary).reduce((a, b) -> (a > b) ? a : b).ifPresent(System.out::println);
	}
}
