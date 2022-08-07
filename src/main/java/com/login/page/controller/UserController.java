package com.login.page.controller;

import com.login.page.model.Employee;
import com.login.page.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {


    private final EmployeeRepository employeeRepository;

    public UserController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping("/index")
    public ModelAndView addEmployeeForm() {
        ModelAndView mav = new ModelAndView("index");
        Employee employee = new Employee();
        mav.addObject("employee", employee);
        return mav;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Employee employee) {
        Employee list = employeeRepository.findByCardNumber(employee.getCardNumber());
        if (list.getCardStatus()){
            if (employee.getCardNumber().equals(list.getCardNumber())){
                if (list.getCardPin().equals(employee.getCardPin())){

                    return "redirect:/my-account";

                }else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
}
