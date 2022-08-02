package com.login.page.controller;

import com.login.page.config.CardNumberGenerator;
import com.login.page.config.GetCalendar;
import com.login.page.entity.Employee;
import com.login.page.entity.History;
import com.login.page.entity.Transfer;
import com.login.page.repository.EmployeeRepository;
import com.login.page.repository.HistoryRepository;
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

    private final HistoryRepository historyRepository;

    public EmployeeController(EmployeeRepository employeeRepository, HistoryRepository historyRepository) {
        this.employeeRepository = employeeRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("")
    public String login() {
        return "login";
    }


    @GetMapping({"/showEmployess" ,"/list"})
    public ModelAndView getEmployeeList() {
        ModelAndView mav = new ModelAndView("list-employee");
        List<Employee>list = employeeRepository.findAll();
        mav.addObject("employees", list);
        return mav;
    }

    @GetMapping("/history")
    public ModelAndView getHistory() {
        ModelAndView mav = new ModelAndView("get-history");
        List<History>list = historyRepository.findAll();
        mav.addObject("histories", list);
        return mav;
    }

    @GetMapping("/transfer")
    public ModelAndView transfer() {
        ModelAndView mav = new ModelAndView("transfer");
        Transfer transfer = new Transfer();
        mav.addObject("transfer", transfer);
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

    @PostMapping("/transaction")
    public String transfer(@ModelAttribute Transfer transfer) {
        Employee fromEmployee = employeeRepository.findByCardNumber(transfer.getFromCardNumber());
        Employee toEmployee = employeeRepository.findByCardNumber(transfer.getToCardNumber());
        Calendar calendar = new GregorianCalendar();
        History history = new History();

        if (!fromEmployee.getCardStatus() || !toEmployee.getCardStatus()) {
            history.setFromCardNumber(fromEmployee.getCardNumber());
            history.setToCardNumber(toEmployee.getCardNumber());
            history.setAmount(transfer.getAmount());
            history.setDate(calendar.getTime().toString());
            history.setStatus(false);
            history.setDescription("Karta bloklangan");
            historyRepository.save(history);

        } else if (!fromEmployee.getCardCurrency().equals(toEmployee.getCardCurrency())) {
            history.setFromCardNumber(fromEmployee.getCardNumber());
            history.setToCardNumber(toEmployee.getCardNumber());
            history.setAmount(transfer.getAmount());
            history.setDate(calendar.getTime().toString());
            history.setStatus(false);
            history.setDescription(fromEmployee.getCardCurrency() + " valyuta birligidan " + toEmployee.getCardCurrency() + " valyuta birligiga o'tkazish mumkin emas ");
            historyRepository.save(history);

        } else if (Integer.parseInt(fromEmployee.getCardBalance()) < Integer.parseInt(transfer.getAmount())) {
            history.setFromCardNumber(fromEmployee.getCardNumber());
            history.setToCardNumber(toEmployee.getCardNumber());
            history.setAmount(transfer.getAmount());
            history.setDate(calendar.getTime().toString());
            history.setStatus(false);
            history.setDescription("Kartaga mablag' yetarli emas");
            historyRepository.save(history);

        } else if (fromEmployee.getCardNumber().equals(toEmployee.getCardNumber())) {
            history.setFromCardNumber(fromEmployee.getCardNumber());
            history.setToCardNumber(toEmployee.getCardNumber());
            history.setAmount(transfer.getAmount());
            history.setDate(calendar.getTime().toString());
            history.setStatus(false);
            history.setDescription("Jo'natuvchi bilan qabul qiluvchining kartalari bir xil");
            historyRepository.save(history);
        } else {
            fromEmployee.setCardBalance(String.valueOf(Integer.parseInt(fromEmployee.getCardBalance()) - Integer.parseInt(transfer.getAmount())));
            toEmployee.setCardBalance(String.valueOf(Integer.parseInt(toEmployee.getCardBalance()) + Integer.parseInt(transfer.getAmount())));
            employeeRepository.save(fromEmployee);
            employeeRepository.save(toEmployee);
            history.setFromCardNumber(fromEmployee.getCardNumber());
            history.setToCardNumber(toEmployee.getCardNumber());
            history.setAmount(transfer.getAmount());
            history.setDate(calendar.getTime().toString());
            history.setStatus(true);
            history.setDescription("Muvaffaqqiyatli");
            historyRepository.save(history);
        }
        return "redirect:/history";
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
