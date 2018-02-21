package allClasses;

public class Review {
	String bookName;
	String username;
	String description;
	boolean approved;

	public Review(String bookName, String username, String description, boolean approved) {
		super();
		this.bookName = bookName;
		this.username = username;
		this.description = description;
		this.approved = approved;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "Review [bookName=" + bookName + ", username=" + username + ", description=" + description
				+ ", approved=" + approved + "]";
	}

}
