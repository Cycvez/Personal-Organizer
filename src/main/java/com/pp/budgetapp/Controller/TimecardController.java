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
import com.pp.budgetapp.Entity.Timecard;
import com.pp.budgetapp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author carlo
 */
@Controller
public class TimecardController {

    @Autowired
    UserDao userDao;

    @Autowired
    ExpensesDao expensesDao;

    @Autowired
    IncomeDao incomeDao;

    @Autowired
    TimecardDao timecardDao;
    
    @GetMapping("addTimecard")
    public String addTimecard(Integer id, Model model){
        User user = userDao.findById(id).orElse(null);
        int userId = user.getId();
        Timecard newTimecard= new Timecard();
        newTimecard.setUser(user);
        
        return "redirect:/selectedUser?id=" + userId;
    }
    
}
