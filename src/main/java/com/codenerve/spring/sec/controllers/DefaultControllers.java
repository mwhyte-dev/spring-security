//package com.codenerve.spring.sec.controllers;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class DefaultControllers {
//
//    private static final String VIEW_HOME = "/";
//    private static final String VIEW_INDEX = "index";
//    private static final String VIEW_LOGIN = "/login";
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @GetMapping("/login.html")
//    public String login() {
//        return "login.html";
//    }
//
//    @GetMapping("/login-error.html")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login.html";
//    }
//}