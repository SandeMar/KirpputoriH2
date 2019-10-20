package projekti.harjoitus.kirpputori.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String productName;
	private String color;
	private int size;
	@Min(1)
	private int price;
	//Tämä taulu on "owner"
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="categoryid")//nimetään foreing key-linkki categoryn ja productin välillä
	private Category category;
	
public Product() {}

public Product(String productName, String color, int size, int price) {
super();
this.productName = productName;
this.color = color;
this.size = size;
this.price = price;

}
public Product(String productName, String color, int size, int price, Category category) {
super();
this.productName = productName;
this.color = color;
this.size = size;
this.price = price;
this.category = category;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}

public String getColor() {
	return color;
}

public void setColor(String color) {
	this.color = color;
}

public int getSize() {
	return size;
}

public void setSize(int size) {
	this.size = size;
}

public int getPrice() {
	return price;
}

public void setPrice(int price) {
	this.price = price;
}
public Category getCategory() {
	return category;
}

public void setCategory(Category category) {
	this.category = category;
}
@Override
public String toString() {
	if(this.category != null)
	return "Product [id=" + id + ", productName=" + productName + ", color=" + color + ", size=" + size + ", price=" + price +", category=" + this.getCategory() + "]";
	else
		return "Product [id=" + id + ", productName=" + productName + ", color=" + color + ", size=" + size + ", price=" + price +"]";
}
}
