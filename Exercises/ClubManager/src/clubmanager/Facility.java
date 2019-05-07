package clubmanager;

public class Facility {
	
	private String name;
	private String description;
	
	public Facility (String name, String description)	{
		this.name = name;
		this.description = description;
	}
	public Facility (String name)	{
		this(name, null);
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	public String toString() {
		if(description == null) return name;
		else return name + " (" + description + ")";
	}
	
	public void show() {
		System.out.print(this.toString());
	}
	
}
