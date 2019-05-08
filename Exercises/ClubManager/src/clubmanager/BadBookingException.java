package clubmanager;

public class BadBookingException extends Exception{

	private String wrong;
	
	public BadBookingException (String wrong) {
		this.wrong = wrong;
	}

	public String getWrong() {
		return wrong;
	}
	
}
