/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pp.finalcasestudy.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pp.finalcasestudy.Dao.ExpensesDao;
import com.pp.finalcasestudy.Dao.IncomeDao;
import com.pp.finalcasestudy.Dao.TimecardDao;
import com.pp.finalcasestudy.Dao.UserDao;
import com.pp.finalcasestudy.Entity.User;

/**
 *
 * @author carlo
 */
@Controller
public class HomeController {

    @Autowired
    UserDao userDao;

    @Autowired
    ExpensesDao expensesDao;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    TimecardDao timecardDao;

    @GetMapping("/")
    public String displayHome(Model model) {
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("usersList", userDao.findAll());

        return "home";
    }
}
