package com.smartshop.sneaker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@SpringBootApplication
@RestController
public class SneakerShopController {
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
    public List<SneakerShop> viewCart(){
        return cart;
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
        cart.clear();
    }
}
