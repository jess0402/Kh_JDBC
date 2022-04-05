package product.model.vo;

import java.sql.Date;

public class Product {
	private String id;
	private String brand;
	private String name;
	private int price;
	private int monitor_size;
	private String os;
	private int storage;
	private Date reg_date;
	private int stock;
	
	public Product() {
		super();
	}

	public Product(String id, String brand, String name, int price, int monitor_size, String os, int storage,
			Date reg_date, int stock) {
		super();
		this.id = id;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.monitor_size = monitor_size;
		this.os = os;
		this.storage = storage;
		this.reg_date = reg_date;
		this.stock = stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMonitor_size() {
		return monitor_size;
	}

	public void setMonitor_size(int monitor_size) {
		this.monitor_size = monitor_size;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	
}
