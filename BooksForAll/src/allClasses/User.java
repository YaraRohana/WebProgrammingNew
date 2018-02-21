package allClasses;

public class User {
	String username;
	String email;
	String cityName;
	String streetName;
	int apartmentNumber;
	String postalCode;
	String phoneNumber;
	String password;
	String nickname;
	String description;
	String imageURL;
	boolean isConnected;
	


	public User(String username, String email, String cityName, String streetName, int apartmentNumber,
			String postalCode, String phoneNumber, String password, String nickname, String description,
			String imageURL, boolean isConnected) {
		super();
		this.username = username;
		this.email = email;
		this.cityName = cityName;
		this.streetName = streetName;
		this.apartmentNumber = apartmentNumber;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.nickname = nickname;
		this.description = description;
		this.imageURL = imageURL;
		this.isConnected = isConnected;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public int getApartmentNumber() {
		return apartmentNumber;
	}
	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", cityName=" + cityName + ", streetName="
				+ streetName + ", apartmentNumber=" + apartmentNumber + ", postalCode=" + postalCode + ", phoneNumber="
				+ phoneNumber + ", password=" + password + ", nickname=" + nickname + ", description=" + description
				+ ", imageURL=" + imageURL + ", isConnected=" + isConnected + "]";
	}
	

	
	
}
