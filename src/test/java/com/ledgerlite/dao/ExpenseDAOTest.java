package com.ledgerlite.dao;

import com.ledgerlite.model.Expense;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseDAOTest {

    private ExpenseDAO expenseDAO;
    // Ορίζουμε ένα όνομα για την προσωρινή βάση των τεστ
    private final String TEST_DB = "test_ledger.db";

    @BeforeEach
    void setUp() {
        // 1. Ρυθμίζουμε τον DatabaseHelper να κοιτάει σε άλλο αρχείο (όχι στο κανονικό σου)
        DatabaseHelper.setDatabase(TEST_DB);

        // 2. Δημιουργούμε τον πίνακα από την αρχή
        DatabaseHelper.createNewDatabase();

        // 3. Αρχικοποιούμε τον DAO
        expenseDAO = new ExpenseDAO();
    }

    @AfterEach
    void tearDown() {
        // Καθαρισμός: Διαγράφουμε το αρχείο της test βάσης μόλις τελειώσει το τεστ
        File dbFile = new File(TEST_DB);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    @Test
    void testSevenDayRule() {
        System.out.println("Running 7-Day Rule Test...");

        // Δημιουργία ενός ΠΑΛΙΟΥ εξόδου (πριν από 10 μέρες)
        // Το 0 στο ID δεν παίζει ρόλο γιατί η βάση βάζει αυτόματα ID
        Expense oldExpense = new Expense(0, "Old Coffee", 3.0, "Food", LocalDate.now().minusDays(10));

        // Δημιουργία ενός ΝΕΟΥ εξόδου (σημερινό)
        Expense newExpense = new Expense(0, "Fresh Coffee", 4.0, "Food", LocalDate.now());

        // Τα προσθέτουμε στη βάση
        expenseDAO.addExpense(oldExpense);
        expenseDAO.addExpense(newExpense);

        // Επιβεβαιώνουμε ότι μπήκαν και τα 2 αρχικά
        List<Expense> initialList = expenseDAO.getAllExpenses();
        assertEquals(2, initialList.size(), "Should verify 2 expenses exist initially");

        // --- Η ΩΡΑ ΤΗΣ ΚΡΙΣΗΣ ---
        // Τρέχουμε τη μέθοδο που σβήνει τα παλιά (αυτή που καλείς στο App.java)
        expenseDAO.deleteOldExpenses();

        // Ελέγχουμε τι έμεινε
        List<Expense> remaining = expenseDAO.getAllExpenses();

        // Πρέπει να έχει μείνει ΜΟΝΟ 1 (το φρέσκο)
        assertEquals(1, remaining.size(), "Only 1 expense should remain after cleanup");

        // Επιβεβαιώνουμε ότι αυτό που έμεινε είναι το φρέσκο
        assertEquals("Fresh Coffee", remaining.get(0).getTitle(), "The remaining expense should be the new one");

        System.out.println("Test Passed! Old expenses were successfully deleted.");
    }
}