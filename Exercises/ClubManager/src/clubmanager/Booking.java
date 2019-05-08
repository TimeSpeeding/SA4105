package clubmanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
	
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("d-MMM-yyyy H:mm");
	
	private Member member;
	private Facility facility;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	
	public Booking (Member member, Facility facility, LocalDateTime startDateTime, LocalDateTime endDateTime) throws BadBookingException {
		String error = null;
		if (member == null)	error = "No member specified";
		else if (facility == null) error = "No facility specified";
		else if ((startDateTime == null) || (endDateTime == null)) error = "Missing date";
		else if (startDateTime.isAfter(endDateTime)) error = "Start date is later than end date";
		if (error != null) throw new BadBookingException (error);
		
		this.member = member;
		this.facility = facility;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public Member getMember() {
		return member;
	}
	public Facility getFacility() {
		return facility;
	}
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
	
	public boolean overlaps(Booking newbooking) {
		if(newbooking.facility == this.facility) {
			if (newbooking.endDateTime.isBefore(this.startDateTime) || newbooking.startDateTime.isAfter(this.endDateTime)) return false;
			else return true;
		}
		else return false;
	}
	
	public String toString() {
		return "Member: " + member.toString() + "\tFacility: " + facility.toString() + "\t" + startDateTime.format(df) + " to " + endDateTime.format(df);
	}
	
	public void show() {
		System.out.println(this.toString());
	}
	
}
