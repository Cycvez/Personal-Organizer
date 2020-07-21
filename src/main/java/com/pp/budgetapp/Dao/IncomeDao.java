/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pp.budgetapp.Dao;

import com.pp.budgetapp.Entity.Income;
import com.pp.budgetapp.Entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author carlo
 */
@Repository
public interface IncomeDao extends JpaRepository <Income, Integer> {
    List<Income> findAllByUser(User user);
}
