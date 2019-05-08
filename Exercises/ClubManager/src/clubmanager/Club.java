package clubmanager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Club {
	
	private int currentNumber = 0;
	private List<Member> members = new ArrayList<Member>();
	private Map<String, Facility> facilities = new HashMap<String, Facility>();
	private BookingRegister bookingRegister = new BookingRegister();
	
	public int getCurrentNumber() {
		return currentNumber;
	}
	public List<Member> getMembers() {
		return members;
	}
	public Map<String, Facility> getFacilities() {
		return facilities;
	}
	
	public Member addMember (Person p) {
		currentNumber++;
		Member m = new Member(p, currentNumber);
		members.add(m);
		return m;
	}	
	public void showMembers() {
		for (Member m : members) {
				m.show();
		}
	}
	public Member getMember (int n) {
		for (Member m : members) {
			if(m.getMemberNumber() == n) return m;
		}
		return null;
	}
	public void removeMember (int n) {
		Member m = getMember(n);
		if(m != null) members.remove(m);
	}
	
	public void addFacility (Facility f) {
		facilities.put(f.getName(), f);
	}
	public Facility getFacility(String name) {
		return facilities.get(name);
	}
	public void removeFacility(String name) {
		facilities.remove(name);
	}
	public void showFacilities() {
		for (String key : facilities.keySet()) {
			facilities.get(key).show();
		}
	}
	
	public void addBooking (int n, String name, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		try {
			this.bookingRegister.addBooking(this.getMember(n), this.getFacility(name), startDateTime, endDateTime);
		}
		catch (BadBookingException e) {
			System.out.println("Sorry, " + e.getWrong());
		}
	}
	public void addBooking (Booking b) {
		try {
			this.bookingRegister.addBooking(b);
		}
		catch (BadBookingException e) {
			System.out.println("Sorry, " + e.getWrong());
		}
	}
	public ArrayList<Booking> getBookings (Facility facility, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return bookingRegister.getBookings(facility, startDateTime, endDateTime);
	}
	public void showBookings (Facility facility, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		ArrayList<Booking> bookinglist = getBookings(facility, startDateTime, endDateTime);
		for (Booking b :bookinglist) {
			b.show();
		}
	}
	
	public void show() {
		System.out.println("Members:");
		showMembers();
		System.out.println("Facilities:");
		showFacilities();
	}
	
}
