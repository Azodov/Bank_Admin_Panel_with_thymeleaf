package com.login.page.controller;

import com.login.page.model.Employee;
import com.login.page.model.History;
import com.login.page.model.Transfer;
import com.login.page.repository.EmployeeRepository;
import com.login.page.repository.HistoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
public class HistoryController {
    private final EmployeeRepository employeeRepository;

    private final HistoryRepository historyRepository;

    public HistoryController(EmployeeRepository employeeRepository, HistoryRepository historyRepository) {
        this.employeeRepository = employeeRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("/history")
    public ModelAndView getHistory() {
        ModelAndView mav = new ModelAndView("get-history");
        List<History> list = historyRepository.findAll();
        mav.addObject("histories", list);
        return mav;
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
}
