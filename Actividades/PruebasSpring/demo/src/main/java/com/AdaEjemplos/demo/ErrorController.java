/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AdaEjemplos.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author islam
 */

@RestController
public class ErrorController {
    
    @GetMapping("/hello")
    public String getHello(@RequestParam(value = "hello", defaultValue = "World") String name){
        return String.format("Hello %s!", name);
    }
    
    @GetMapping("/error")
    public String handleError() {
        return "error";
    }
}
