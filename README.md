# 💰 LedgerLite (2026 Edition)

**LedgerLite** is a modern, lightweight personal expense tracker built with **Java Swing** and **SQLite**. Designed with a "focus on the now" philosophy, it helps users track their daily spending with a clean, dark-themed interface and real-time visual analytics.

## ✨ Features

* **📊 Interactive Dashboard:** Real-time visual representation of expenses by category (bar charts).
* **🌑 Modern Dark UI:** Powered by **FlatLaf** for a sleek, professional look.
* **💾 Persistent Storage:** Uses **SQLite** to store data locally (no internet required).
* **⚡ The "7-Day Rule":** Automatic cleanup logic that keeps the database fresh by removing transactions older than 7 days upon startup.
* **🔢 Smart Table:** Auto-numbered transaction list that hides database IDs for a cleaner user experience.
* **🗑️ Easy Management:** Context menus and buttons to delete specific transactions safely.

## 🛠️ Tech Stack

* **Language:** Java (JDK 17+)
* **GUI Framework:** Swing
* **Database:** SQLite (JDBC)
* **Theme Engine:** FlatLaf (Dark Theme)
* **Architecture:** DAO Pattern (Data Access Object) for clean separation of concerns.
* **Build Tool:** Maven

## 🚀 How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Dalasenko/LedgerLite.git](https://github.com/Dalasenko/LedgerLite.git)
    ```
2.  **Open in IntelliJ IDEA** (or any Java IDE).
3.  **Reload Maven Projects** to download dependencies (SQLite, FlatLaf).
4.  Run the `App.java` file located in `src/main/java/com/ledgerlite/App.java`.

*Note: The application will automatically create the `ledgerlite.db` database file on the first run.*

## 📂 Project Structure

```text
src/main/java/com/ledgerlite
├── App.java                 # Entry point (Main)
├── dao/
│   ├── DatabaseHelper.java  # DB Connection & Creation
│   └── ExpenseDAO.java      # CRUD Operations & 7-Day Logic
├── model/
│   └── Expense.java         # Data Model
└── ui/
    ├── MainFrame.java       # Main Window Layout
    ├── DashboardPanel.java  # Stats & Charts
    ├── ExpenseTablePanel.java # Data Table
    └── FormPanel.java       # Input Form
