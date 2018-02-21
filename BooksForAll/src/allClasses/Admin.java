package allClasses;

public class Admin {
	 String name="admin";
	 String password="Passw0rd";
	 boolean isConnected;
	 
	public Admin(String name, String password, boolean isConnected) {
		super();
		this.name = name;
		this.password = password;
		this.isConnected = isConnected;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	@Override
	public String toString() {
		return "Admin [name=" + name + ", password=" + password + ", isConnected=" + isConnected + "]";
	}
	 

	 
	
}
