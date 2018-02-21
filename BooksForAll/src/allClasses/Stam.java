package allClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class Stam {

	static String str = "yaraaa aA A Aaaaaaaaaaaaaa A ";
	static String email = "yara.rohana@gmail.com";
	static String amex = "325987615432101";
	static String visa = "1916105741133218";
	static String master = "1217799253940116";

	public boolean validateCreditCardInfo(String creditCardNum, String creditCardCompany, String cvv,
			String expirationDate) {
		String visaRegex = "\"^4[0-9]{12}(?:[0-9]{3}){0,2}$\")";
		String masterCardRegex = "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$";
		String amexRegex = "^3[47][0-9]{13}$";
		switch (creditCardCompany) {
		case "amex":
			return creditCardNum.matches(amexRegex);
		case "visa":
			return creditCardNum.matches(visaRegex);
		case "masterCard":
			return creditCardNum.matches(masterCardRegex);
		}
		return false;
	}

	public static void main(String[] args) throws ParseException {
		String regex = "^[a-zA-Z\\s]+$";
		String emailRegex = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
	//	final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				//Pattern.CASE_INSENSITIVE);
		String amexRegex = "^3[47][0-9]{13}$";
		String visaRegex = "^4[0-9]{12}(?:[0-9]{3}){0,2}$";
		String masterCardRegex = "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$";
		String cvv = "1233";

		System.out.println(str.matches(regex));
		System.out.println("email v " + email.matches(emailRegex));
		System.out.println("amex is " + amex.matches(amexRegex));
		System.out.println("visa is" + visa.matches(visaRegex));
		System.out.println("master card is " + master.matches(masterCardRegex));
		boolean res = cvv.matches("[0-9]+") && cvv.length() == 4;
		System.out.println("cvv " + res);
		System.out.println("******************");
		String input = "11/18"; // for example
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
		simpleDateFormat.setLenient(false);
		Date expiry = simpleDateFormat.parse(input);
		boolean expired = expiry.before(new Date());
		System.out.println(expired);
	}

}
