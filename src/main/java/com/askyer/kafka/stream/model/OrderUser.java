package com.askyer.kafka.stream.model;

/**
 * Created by askyer on 2018/5/26.
 */
public class OrderUser {
    //from order
    private String user_name;
    private String item_name;
    private long transaction_ts;
    private int quantity;

    //from user
    private String user_address;
    private String gender;
    private int age;

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

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static OrderUser fromOrder(Order order) {
        OrderUser orderUser = new OrderUser();
        if(order == null) {
            return orderUser;
        }
        orderUser.user_name = order.getUser_name();
        orderUser.item_name = order.getItem_name();
        orderUser.transaction_ts = order.getTransaction_ts();
        orderUser.quantity = order.getQuantity();
        return orderUser;
    }

    public static OrderUser fromOrderUser(Order order, User user) {
        OrderUser orderUser = fromOrder(order);
        if(user == null) {
            return orderUser;
        }
        orderUser.gender = user.getGender();
        orderUser.age = user.getAge();
        orderUser.user_address = user.getUser_address();
        return orderUser;
    }
}