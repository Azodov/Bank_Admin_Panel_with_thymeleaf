package com.login.page.controller;

import com.login.page.config.CardNumberGenerator;
import com.login.page.config.GetCalendar;
import com.login.page.model.Employee;
import com.login.page.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping({"/showEmployess" ,"/list", "/"})
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
        switch (employee.getCardType()) {
            case "UZCARD":
                try {
                    employee.setCardCurrency("UZS");
                    calendar.add(Calendar.YEAR, 5);
                    employee.setCardNumber(cardNumberGenerator.generate("8600", 16));
                    employee.setCardExpirationDate(calendar.getTime().toString());
                } catch (Exception e) {
                    ResponseEntity.badRequest().body("Error");
                }
                break;
            case "VISA":
                try {
                    employee.setCardCurrency("USD");
                    employee.setCardNumber(cardNumberGenerator.generate("4231", 16));
                    calendar.add(Calendar.YEAR, 3);
                    employee.setCardExpirationDate(calendar.getTime().toString());
                } catch (Exception e) {
                    ResponseEntity.badRequest().body("Error");
                }
                break;
            case "HUMO":
                try {
                    employee.setCardCurrency("UZS");
                    employee.setCardNumber(cardNumberGenerator.generate("9860", 16));
                    calendar.add(Calendar.YEAR, 5);
                    employee.setCardExpirationDate(calendar.getTime().toString());
                } catch (Exception e){
                    ResponseEntity.badRequest().body("Error");
                }
                break;
        }
        employee.setCardStatus(true);
        employee.setCardBillingTimeZone(calendar.getTimeZone().toZoneId().getId());
        employee.setCardCreatedDate(getCalendar.getCurrentDate());
        employee.setCardBalance("0");
        employeeRepository.save(employee);
        return "redirect:/showEmployess";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long employeeId) {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        mav.addObject("employee", employee);
        return mav;
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Long employeeId) {
        employeeRepository.deleteById(employeeId);
        return "redirect:/showEmployess";
    }
}
