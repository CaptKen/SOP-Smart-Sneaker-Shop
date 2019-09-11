package com.smartshop.sneaker;

public class TestSingleton {

    private int itemInCart;
    private double totalPrice;


    private static TestSingleton instance;

    private TestSingleton(){

    }

    public static TestSingleton getInstance(){
        if (instance == null)
            instance = new TestSingleton();
        return instance;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemInCart() {
        return itemInCart;
    }

    public void setItemInCart(int itemInCart) {
        this.itemInCart = itemInCart;
    }
}
