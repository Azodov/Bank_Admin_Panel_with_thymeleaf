package com.login.page.controller;

import com.login.page.entity.Transfer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransferController {

    @GetMapping("/transfer")
    public ModelAndView transfer() {
        ModelAndView mav = new ModelAndView("transfer");
        Transfer transfer = new Transfer();
        mav.addObject("transfer", transfer);
        return mav;
    }

}
