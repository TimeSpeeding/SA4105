package clubmanager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookingRegister {
	
	private Map<Facility, ArrayList<Booking>> bookingMap = new HashMap<Facility, ArrayList<Booking>> ();
	
	public void addBooking (Member member, Facility facility, LocalDateTime startDateTime, LocalDateTime endDateTime) throws BadBookingException{
		Booking newBooking = new Booking(member, facility, startDateTime, endDateTime);
		ArrayList<Booking> bookings = bookingMap.get(facility);
		if(bookings == null) {
			bookings = new ArrayList<Booking> ();
			bookingMap.put(facility, bookings);
		}
		else {
			for (Booking b : bookings) {
				if (newBooking.overlaps(b)) throw new BadBookingException("The time has been taken up.");
			}
		}
		bookings.add(newBooking);
	}
	public void addBooking (Booking bb) throws BadBookingException{
		ArrayList<Booking> bookings = bookingMap.get(bb.getFacility());
		if(bookings == null) {
			bookings = new ArrayList<Booking> ();
			bookingMap.put(bb.getFacility(), bookings);
		}
		else {
			for (Booking b : bookings) {
				if (b.overlaps(bb)) throw new BadBookingException("The time has been taken up.");
			}
		}
		bookings.add(bb);
	}
	
	public void removeBooking (Booking booking) {
		ArrayList<Booking> bookings = bookingMap.get(booking.getFacility());
		if (bookings != null) bookings.remove(booking);
	}
	
	public ArrayList<Booking> getBookings (Facility facility, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		ArrayList<Booking> bookinglist = new ArrayList<Booking> ();
		ArrayList<Booking> bookings = bookingMap.get(facility);
		if (bookings != null) {
			for (Booking b : bookings) {
				if (b.getStartDateTime().isAfter(startDateTime) && b.getEndDateTime().isBefore(endDateTime))
					bookinglist.add(b);
			}
		}
		return bookinglist;
	}
}
