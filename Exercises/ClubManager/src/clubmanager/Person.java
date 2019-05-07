package clubmanager;

public class Person {
	
	private String surName;
	private String firstName;
	private String secondName;
	
	public Person (String surName, String firstName, String secondName) {
		this.surName = surName;
		this.firstName = firstName;
		this.secondName = secondName;
	}	
	public Person (String surName, String firstName) {
		this(surName, firstName, null);
	}
	
	
	public String getSurName() {
		return surName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	
	public String toString() {
		if (secondName == null) return surName + " " + firstName;
		else return surName + " " + firstName + " " + secondName;
	}
	
	public void show() {
		System.out.println(this.toString());
	}

	/*public void show () {
		System.out.print("Hello, " + surName + " " + firstName);
		if(secondName != null) System.out.print(" " + secondName);
		System.out.println("!");
	}*/
	
}
