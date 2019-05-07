package clubmanager;

public class ClubApplication {

	public static void main(String[] args) {
		Person p1 = new Person("Tan", "Ah", "Beng");
		Person p2 = new Person("Stan", "Laurel");
		Person p3 = new Person("Yeoh", "Khei", "Hong");
		Club c = new Club();
			
		p1.show();
		p2.show();
		p3.show();
		System.out.println();
		
		c.addMember(p1);
		c.addMember(p2);
		c.addMember(p3);		
		c.showMembers();
		System.out.println();
		
		c.removeMember(2);
		c.showMembers();
		
	}

}
