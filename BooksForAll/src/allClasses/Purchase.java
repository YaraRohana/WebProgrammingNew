package allClasses;

public class Purchase {
	String username;
	String bookName;
	String creditCardNumber;
	String creditCardCompany;
	String creditCardExpiry;
	String CVV;
	String fullName;
	
	public Purchase(String username, String bookName, String creditCardNumber, String creditCardCompany,
			String creditCardExpiry, String cVV, String fullName) {
		super();
		this.username = username;
		this.bookName = bookName;
		this.creditCardNumber = creditCardNumber;
		this.creditCardCompany = creditCardCompany;
		this.creditCardExpiry = creditCardExpiry;
		CVV = cVV;
		this.fullName = fullName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCreditCardCompany() {
		return creditCardCompany;
	}
	public void setCreditCardCompany(String creditCardCompany) {
		this.creditCardCompany = creditCardCompany;
	}
	public String getCreditCardExpiry() {
		return creditCardExpiry;
	}
	public void setCreditCardExpiry(String creditCardExpiry) {
		this.creditCardExpiry = creditCardExpiry;
	}
	public String getCVV() {
		return CVV;
	}
	public void setCVV(String cVV) {
		CVV = cVV;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Override
	public String toString() {
		return "Purchase [username=" + username + ", bookName=" + bookName + ", creditCardNumber=" + creditCardNumber
				+ ", creditCardCompany=" + creditCardCompany + ", creditCardExpiry=" + creditCardExpiry + ", CVV=" + CVV
				+ ", fullName=" + fullName + "]";
	}
	
	
	
}
