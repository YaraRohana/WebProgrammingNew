package allClasses;

public class Like {
	String bookName;
	String username;
	String nickname;
	public Like(String bookName, String username, String nickname) {
		super();
		this.bookName = bookName;
		this.username = username;
		this.nickname = nickname;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "Like [bookName=" + bookName + ", username=" + username + ", nickname=" + nickname + "]";
	}
	
	
}
