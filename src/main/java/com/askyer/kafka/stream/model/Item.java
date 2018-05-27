package com.askyer.kafka.stream.model;

public class Item {
	private String item_name;
	private String item_address;
	private String category;
	private double price;

	public Item() {}

	public Item(String item_name, String item_address, String category, double price) {
		this.item_name = item_name;
		this.item_address = item_address;
		this.category = category;
		this.price = price;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_address() {
		return item_address;
	}

	public void setItem_address(String item_address) {
		this.item_address = item_address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
