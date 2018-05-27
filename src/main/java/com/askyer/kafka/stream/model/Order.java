package com.askyer.kafka.stream.model;

public class Order {

	private String user_name;
	private String item_name;
	private long transaction_ts;
	private int quantity ;

	public Order() {}

	public Order(String user_name, String item_name, long transaction_ts, int quantity) {
		this.user_name = user_name;
		this.item_name = item_name;
		this.transaction_ts = transaction_ts;
		this.quantity = quantity;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public long getTransaction_ts() {
		return transaction_ts;
	}

	public void setTransaction_ts(long transaction_ts) {
		this.transaction_ts = transaction_ts;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
