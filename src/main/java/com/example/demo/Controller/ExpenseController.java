package com.example.demo.Controller;

import com.example.demo.Entity.Expense;
import com.example.demo.Service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ExpenseController
{
    @Autowired
    private ExpenseService expenseService;
    //Test API
    @GetMapping("/helloworld")
    public String helloWorld (){
        return "Hello World! I'm Vu ^^";
    }

    //Find all expenses
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page){
        return expenseService.getAllExpenses(page).toList();
    }

    //Find by id
    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }

    //Delete by id
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam Long id){
        expenseService.deleteExpenseById(id);
    }

    //Post new expense and save it to database
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpenseDetails(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpenseDetails(expense);
    }

    //Update a current expense
    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable Long id) {
        return expenseService.updateExpenseDetails(id, expense);
    }

    //Read by category
    @GetMapping("/expenses/category")
    public List<Expense> getExpensesByCategory(@RequestParam String category, Pageable page){
        return expenseService.readByCategory(category, page);
    }

    //Read by name
    @GetMapping("/expenses/name")
    public List<Expense> getExpensesByName(@RequestParam String name, Pageable page){
        return expenseService.readByName(name, page);
    }

    //Read by date between
    @GetMapping("/expenses/date")
    public List<Expense> getExpensesByDateBetween(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Pageable page) {
        return expenseService.readByDateBetween(startDate, endDate, page);
    }
}
