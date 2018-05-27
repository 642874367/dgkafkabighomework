package com.askyer.kafka.stream.model;

/**
 * Created by askyer on 2018/5/26.
 */
public class OrderUserItem  implements Comparable<OrderUserItem>{
    //order
    private String user_name;
    private String item_name;
    private long transaction_ts;
    private int quantity;
    //user
    private String user_address;
    private String gender;
    private int age;

    //item item_name
    private String item_address;
    private String category;
    private double price;

    //total amount all
    private double amount;

    public double getAmount() {
        return quantity*price;
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

    public static OrderUserItem fromOrderUser(OrderUser orderUser) {
        OrderUserItem orderUserItem = new OrderUserItem();
        if(orderUser == null) {
            return orderUserItem;
        }
        orderUserItem.user_name = orderUser.getUser_name();
        orderUserItem.item_name = orderUser.getItem_name();
        orderUserItem.transaction_ts = orderUser.getTransaction_ts();
        orderUserItem.quantity = orderUser.getQuantity();
        orderUserItem.user_address = orderUser.getUser_address();
        orderUserItem.gender = orderUser.getGender();
        orderUserItem.age = orderUser.getAge();
        return orderUserItem;
    }

    public static OrderUserItem add2OrderUserItem(OrderUserItem item1, OrderUserItem item2) {
        OrderUserItem  item= new OrderUserItem();
        //item.user_name = item1.user_name;
        //item.user_address = item1.user_address;
        item.item_name = item1.item_name;
        item.category = item1.category;
        item.gender = item1.gender;
        item.quantity = item1.quantity + item2.quantity;
        item.amount = item1.getAmount() + item2.getAmount();
        item.price = item.amount/item.quantity;
        return item;
    }

    public static OrderUserItem fromOrderUser(OrderUser orderUser, Item item) {
        OrderUserItem orderUserItem = fromOrderUser(orderUser);
        if(item == null) {
            return orderUserItem;
        }
        orderUserItem.item_address = item.getItem_address();
        orderUserItem.category = item.getCategory();
        orderUserItem.price = item.getPrice();
        return orderUserItem;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderUserItem that = (OrderUserItem) o;

        if (transaction_ts != that.transaction_ts) return false;
        if (quantity != that.quantity) return false;
        if (age != that.age) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (user_name != null ? !user_name.equals(that.user_name) : that.user_name != null) return false;
        if (item_name != null ? !item_name.equals(that.item_name) : that.item_name != null) return false;
        if (user_address != null ? !user_address.equals(that.user_address) : that.user_address != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (item_address != null ? !item_address.equals(that.item_address) : that.item_address != null) return false;
        return category != null ? category.equals(that.category) : that.category == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = user_name != null ? user_name.hashCode() : 0;
        result = 31 * result + (item_name != null ? item_name.hashCode() : 0);
        result = 31 * result + (int) (transaction_ts ^ (transaction_ts >>> 32));
        result = 31 * result + quantity;
        result = 31 * result + (user_address != null ? user_address.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (item_address != null ? item_address.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OrderUserItem{" +
                "user_name='" + user_name + '\'' +
                ", item_name='" + item_name + '\'' +
                ", transaction_ts=" + transaction_ts +
                ", quantity=" + quantity +
                ", user_address='" + user_address + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", item_address='" + item_address + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", amount=" + getAmount() +
                '}';
    }

    @Override
    public int compareTo(OrderUserItem o) {
        if ((this.getAmount() - o.getAmount()) >0 ){
            return  1;
        } else if ((this.getAmount() - o.getAmount()) < 0 ) {
            return -1;
        } else {
            return 0;
        }
    }
}

