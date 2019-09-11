package com.smartshop.sneaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@SpringBootApplication
public class SneakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SneakerApplication.class, args);
    }
    double totalprice = 0;
    TestSingleton totalInCart = TestSingleton.getInstance();

    List<SneakerShop> cart = new ArrayList<SneakerShop>();
    List<SneakerShop> view = new ArrayList<SneakerShop>();
    List<SneakerShop> sneakers = new ArrayList<>(Arrays.asList(
            new SneakerShop("S001","Nike","Air Force 1",3800.00, 30),
            new SneakerShop("S002", "Adidas", "Yeeze 350", 8900.00,10),
            new SneakerShop("S003", "Adidas", "NMD", 5500.00,15),
            new SneakerShop("S004","Vans","OldSkool", 2400.00, 45)
    ));

    @RequestMapping("/")
    String HomePage(){
        return "Sneaker smart shop";
    }
    @RequestMapping("/shop")
    public List<SneakerShop> getAllSneaker(){
        return sneakers;
    }

    @RequestMapping("/shop/{id}")
    List<SneakerShop> viewSneaker(@PathVariable String id){
        view.clear();
        for (int i = 0; i < sneakers.size(); i++){
            if (sneakers.get(i).getId().equals(id)){
                view.add(sneakers.get(i));
            }
        }
        return view;

    }

    @RequestMapping("/shop/addtocart/{id}")
    List<SneakerShop> addSneaker(@PathVariable String id){
        for (int i = 0; i < sneakers.size(); i++){
            if (sneakers.get(i).getId().equals(id)){
                cart.add(sneakers.get(i));
            }
        }
        return cart;
    }

    @RequestMapping("shop/cart")
    public Object[] viewCart(){
        double totalprice = 0;
        int count = cart.size();
        for (int i=0; i<cart.size(); i++){
            totalprice += cart.get(i).getPrice();
        }
        totalInCart.setItemInCart(count);
        totalInCart.setTotalPrice(totalprice);
        return new Object[]{cart, totalInCart};

    }

    @RequestMapping(value = "/shop/checkout")
    public void buySneaker(){
        int amountInStock;
        for (int j =0; j <cart.size(); j++){
            for (int i = 0; i< sneakers.size(); i++){
                if(cart.get(j).getId().equals(sneakers.get(i).getId())){
                    amountInStock = sneakers.get(i).getAmount();
                    if (amountInStock == 0){
                        sneakers.remove(i);
                        break;
                    }else {
                        amountInStock = amountInStock - 1;
                        sneakers.get(i).setAmount(amountInStock);
                        break;
                    }
                }
            }
        }

        for (int i=0; i<cart.size(); i++){
            totalprice += cart.get(i).getPrice();
        }
        totalInCart.setTotalPrice(totalprice);
        try {
            FileOutputStream f = new FileOutputStream(new File("Price.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeDouble(totalprice);


            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        cart.clear();
    }
}
