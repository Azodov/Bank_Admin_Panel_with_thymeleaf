package com.login.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyAccountController {


    @GetMapping("/my-account")
    public ModelAndView myAccount() {
        ModelAndView mav = new ModelAndView("my-account");
        mav.addObject("cardHolder", "Doniyor Azodov");
        mav.addObject("billingCity", "Xorazm, Bagat, Tafakkur-56 uy");
        return mav;
    }
}
