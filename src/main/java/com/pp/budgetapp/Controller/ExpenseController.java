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
import com.pp.budgetapp.Entity.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
public class ExpenseController {

    @Autowired
    UserDao userDao;

    @Autowired
    ExpensesDao expensesDao;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    TimecardDao timecardDao;

    @GetMapping("addExpense")
    public String displayExpense(Integer id, Model model) {
        User user = userDao.findById(id).orElse(null);
        Expenses expense = new Expenses();
        List<Expenses> userExpenses = new ArrayList<>();
        List<Expenses> allExpenses = expensesDao.findAll();

        for (Expenses e : allExpenses) {
            if (e.getUser() == user) {
                userExpenses.add(e);
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("expense", expense);
        model.addAttribute("expenseList", userExpenses);

        return "addExpense";
    }

    @PostMapping("addExpense")
    public String addExpense(@Valid Expenses expense, BindingResult result, HttpServletRequest request, Model model) {

        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

        if (result.hasErrors()) {
            model.addAttribute("expenseList", expensesDao.findAll());
            return "home";
        }

        expense.setName(name);
        expense.setDescription(description);
        expense.setAmount(amount);

        expense.setUser(userDao.findById(Integer.parseInt(userId)).orElse(null));

        expensesDao.save(expense);

        return "redirect:/selectedUser?id=" + userId;
    }

    @GetMapping("deleteExpense")
    public String deleteExpense(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Expenses currentExpense = expensesDao.findById(id).orElse(null);
        User user = currentExpense.getUser();
        expensesDao.deleteById(id);

        return "redirect:/selectedUser?id="+user.getId();
    }

}
