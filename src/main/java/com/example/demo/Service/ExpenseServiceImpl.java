package com.example.demo.Service;

import com.example.demo.Entity.Expense;
import com.example.demo.ExceptionHandling.ResourceNotFoundException;
import com.example.demo.Repository.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService
{
    @Autowired
    private UserService userService;
    @Autowired
    private ExpenseRepo expenseRepo;
    //Return all expenses
    @Override
    public Page<Expense> getAllExpenses(Pageable page){
        return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    //Return expense with its id
    @Override
    public Expense getExpenseById(Long id)
    {
       Optional<Expense> expense= expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
        if (expense.isPresent()) {
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found with id " + id);
    }

    //Delete Expense By Id
    @Override
    public void deleteExpenseById(Long id)
    {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()){
            expenseRepo.deleteById(id);
        } else {
        throw new RuntimeException("Expense is not valid with id " + id);
        }
    }

    //Save posted expense to database
    @Override
    public Expense saveExpenseDetails(Expense expense)
    {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepo.save(expense);
    }

    //Update expense
    @Override
    public Expense updateExpenseDetails(Long id, Expense expense)
    {
        Expense currentExpense = getExpenseById(id);
        currentExpense.setName(expense.getName() != null ? expense.getName() : currentExpense.getName());
        currentExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : currentExpense.getDescription());
        currentExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : currentExpense.getCategory());
        currentExpense.setDate(expense.getDate() != null ? expense.getDate() : currentExpense.getDate());
        currentExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : currentExpense.getAmount());
        return expenseRepo.save(currentExpense);
    }

    //Find by category
    @Override
    public List<Expense> readByCategory(String category, Pageable page)
    {
        return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(),category, page).toList();
    }

    //Find by name containing
    @Override
    public List<Expense> readByName(String keyword, Pageable page)
    {
        return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),keyword, page).toList();
    }

    //Find by date between
    @Override
    public List<Expense> readByDateBetween(Date startDate, Date endDate, Pageable page)
    {
        if (startDate == null) {
            startDate = new Date(0);
        };
        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        };

        return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),startDate,endDate,page).toList();
    }
}
