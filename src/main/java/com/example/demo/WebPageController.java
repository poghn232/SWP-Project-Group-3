package com.example.demo;


import com.example.demo.api.jpaDemo.Products;
import com.example.demo.api.jpaDemo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebPageController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String toHomePage(Model model){
        List<Products> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "trangchu";
    }

    @GetMapping("/features")
    public String toFeature() {return "tinhnang";}


}