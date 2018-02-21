package allClasses;


public class Ebook {

	String name;
	String imageURL;
	double price;
	String description;
	int likes;
	
	
	public Ebook(String name, String imageURL, double price, String description, int likes) {
		super();
		this.name = name;
		this.imageURL = imageURL;
		this.price = price;
		this.description = description;
		this.likes = likes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Ebook [name=" + name + ", imageURL=" + imageURL + ", price=" + price + ", description=" + description
				+ ", likes=" + likes + "]";
	}
	
}
