package clubmanager;

public class Club {
	
	private int currentNumber = 0;
	private Member[] members = new Member[1000];
	
	public Member addMember (Person p) {
		currentNumber++;
		Member m = new Member(p, currentNumber);
		members[currentNumber - 1] = m;
		return m;
	}
	
	public void showMembers() {
		for (Member m : members) {
			if (m != null) m.show();
		}
	}
	
	public void removeMember (int n) {
		members[n - 1] = null;
	}
	
}
