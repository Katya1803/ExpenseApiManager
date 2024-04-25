package com.example.demo.Repository;

import com.example.demo.Entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long>
{
    Page<Expense> findByUserIdAndCategory(Long userId,String category, Pageable page);
    Page<Expense> findByUserIdAndNameContaining(Long userId,String keyword, Pageable page);
    Page<Expense> findByUserIdAndDateBetween(Long userId,Date startDate, Date endDate, Pageable page);
    Page<Expense> findByUserId(Long userId, Pageable pageable);
    Optional<Expense> findByUserIdAndId(Long userId, long expenseId);

}
