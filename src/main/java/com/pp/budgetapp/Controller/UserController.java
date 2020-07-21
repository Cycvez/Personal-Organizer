/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pp.budgetapp.Controller;

import com.pp.budgetapp.Dao.ExpensesDao;
import com.pp.budgetapp.Dao.IncomeDao;
import com.pp.budgetapp.Dao.TimecardDao;
import com.pp.budgetapp.Dao.UserDao;
import com.pp.budgetapp.Entity.Expenses;
import com.pp.budgetapp.Entity.Income;
import com.pp.budgetapp.Entity.Timecard;
import com.pp.budgetapp.Entity.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author carlo
 */
@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    ExpensesDao expensesDao;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    TimecardDao timecardDao;

    Set<ConstraintViolation<User>> violations = new HashSet<>();

    @GetMapping("users")
    public String displaySupers(Model model) {
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("usersList", userDao.findAll());

        return "users";
    }

    @PostMapping("addUser")
    public String addUser(@Valid User user, BindingResult result, HttpServletRequest request, Model model) {
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");

        if (result.hasErrors()) {
            model.addAttribute("userList", userDao.findAll());
            return "users";
        }

        user.setFirstname(firstName);
        user.setLastname(lastName);

        userDao.save(user);

        return "redirect:/users";
    }

    @GetMapping("deleteUser")
    public String deleteSuper(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        userDao.deleteById(id);

        return "redirect:/users";
    }

    @GetMapping("selectedUser")
    public String selectedUser(Integer id, Model model) {
        User user = userDao.findById(id).orElse(null);
        List<Income> userIncomes = new ArrayList<>();
        List<Income> allIncomes = incomeDao.findAll();

        for (Income i : allIncomes) {
            if (i.getUser() == user) {
                userIncomes.add(i);
            }
        }
        List<Expenses> userExpenses = new ArrayList<>();
        List<Expenses> allExpenses = expensesDao.findAll();
        for (Expenses e : allExpenses) {
            if (e.getUser() == user) {
                userExpenses.add(e);
            }
        }
        BigDecimal budget = new BigDecimal("0.00");

        for (Income eachIncome : userIncomes) {
            BigDecimal currentIncome = eachIncome.getAmount();
            budget = budget.add(currentIncome);
        }

        for (Expenses eachExpense : userExpenses) {
            BigDecimal currentExpense = eachExpense.getAmount();
            budget = budget.subtract(currentExpense);
        }

        List<Timecard> userTimecards = new ArrayList<>();
        List<Timecard> allTimecards = timecardDao.findAll();
        for (Timecard t : allTimecards) {
            if (t.getUser() == user) {
                userTimecards.add(t);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("budget", budget);
        model.addAttribute("incomeList", userIncomes);
        model.addAttribute("expenseList", userExpenses);
        model.addAttribute("userTimecards", userTimecards);

        return "selectedUser";
    }

}
