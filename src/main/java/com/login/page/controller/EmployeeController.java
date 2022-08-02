package com.login.page.controller;

import com.login.page.config.CardNumberGenerator;
import com.login.page.config.GetCalendar;
import com.login.page.entity.Employee;
import com.login.page.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @GetMapping("")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "sign-up";
    }

    @GetMapping({"/showEmployess" ,"/list"})
    public ModelAndView getEmployeeList() {
        ModelAndView mav = new ModelAndView("list-employee");
        List<Employee>list = employeeRepository.findAll();
        mav.addObject("employees", list);
        return mav;
    }
    @GetMapping("/addEmployeeForm")
    public ModelAndView addEmployeeForm() {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Employee employee = new Employee();
        mav.addObject("employee", employee);
        return mav;
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        Calendar calendar = new GregorianCalendar();
        CardNumberGenerator cardNumberGenerator = new CardNumberGenerator();
        GetCalendar getCalendar = new GetCalendar();
        switch (employee.getCard_type()) {
            case "UZCARD":
                try {
                    employee.setCard_currency("UZS");
                    calendar.add(Calendar.YEAR, 5);
                    employee.setCard_number(cardNumberGenerator.generate("8600", 16));
                    employee.setCard_expiration_date(calendar.getTime().toString());
                } catch (Exception e) {
                    ResponseEntity.badRequest().body("Error");
                }
                break;
            case "VISA":
                try {
                    employee.setCard_currency("USD");
                    employee.setCard_number(cardNumberGenerator.generate("4231", 16));
                    calendar.add(Calendar.YEAR, 3);
                    employee.setCard_expiration_date(calendar.getTime().toString());
                } catch (Exception e) {
                    ResponseEntity.badRequest().body("Error");
                }
                break;
            case "HUMO":
                try {
                    employee.setCard_currency("UZS");
                    employee.setCard_number(cardNumberGenerator.generate("9860", 16));
                    calendar.add(Calendar.YEAR, 5);
                    employee.setCard_expiration_date(calendar.getTime().toString());
                } catch (Exception e){
                    ResponseEntity.badRequest().body("Error");
                }
                break;
        }
        employee.setCard_status(true);
        employee.setCard_billing_time_zone(calendar.getTimeZone().toZoneId().getId());
        employee.setCard_created_date(getCalendar.getCurrentDate());
        employee.setCard_balance("0");
        employeeRepository.save(employee);
        return "redirect:/showEmployess";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long employeeId) {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Employee employee = employeeRepository.findById(employeeId).get();
        mav.addObject("employee", employee);
        return mav;
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Long employeeId) {
        employeeRepository.deleteById(employeeId);
        return "redirect:/showEmployess";
    }
}
