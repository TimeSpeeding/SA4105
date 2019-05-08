package clubmanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClubApplication {

	public static void main(String[] args) {
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("d-MMM-yyyy H:mm");
		
		Person p1 = new Person("Tan", "Ah", "Beng");
		Person p2 = new Person("Stan", "Laurel");
		Person p3 = new Person("Yeoh", "Khei", "Hong");
		Facility f1 = new Facility("ISS", "one of the schools in NUS");
		Facility f2 = new Facility("COM", "another school in NUS");
		Facility f3 = new Facility("NUS");
		Club c = new Club();
		
		Member m1 = c.addMember(p1);
		Member m2 = c.addMember(p2);
		Member m3 = c.addMember(p3);
		c.addFacility(f1);
		c.addFacility(f2);
		c.addFacility(f3);
		c.show();
		System.out.println();
		
		c.removeMember(2);
		c.removeFacility("COM");
		c.show();
		System.out.println();
		
		try {
			Booking b1 = new Booking(m1, f1, LocalDateTime.parse("1-Mar-2019 15:00", df), LocalDateTime.parse("1-Mar-2019 16:00", df));
			Booking b2 = new Booking(m2, f1, LocalDateTime.parse("1-Mar-2019 16:01", df), LocalDateTime.parse("1-Mar-2019 17:00", df));
			c.addBooking(b1);
			c.addBooking(b2);
			c.showBookings(f1, LocalDateTime.parse("1-Mar-2019 00:00", df), LocalDateTime.parse("1-Mar-2019 23:59", df));
			
		} catch (BadBookingException e) {
			System.out.println("Sorry, " + e.getWrong());
		}
		
		
		
	}

}
