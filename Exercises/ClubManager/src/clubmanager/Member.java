package clubmanager;

public class Member extends Person{

	private int memberNumber;
	
	public Member (String surName, String firstName, String secondName, int memberNumber) {
		super(surName, firstName, secondName);
		this.memberNumber = memberNumber;
	}	
	public Member (String surName, String firstName, int memberNumber) {
		super(surName, firstName);
		this.memberNumber = memberNumber;
	}
	public Member (Person p, int memberNumber) {
		this(p.getSurName(), p.getFirstName(), p.getSecondName(), memberNumber);
	}
	
	public int getMemberNumber() {
		return memberNumber;
	}
	
	public String toString() {
		return super.toString() + ", " + Integer.toString(memberNumber);
	}
	
}
