# Опис контролера PersonalExpensesController

Цей контролер відповідає за обробку запитів, що стосуються особистих витрат.

## Огляд

Контролер має наступні методи:

- `getAllExpenses()`: Отримання всіх особистих витрат.
- `addExpense()`: Додавання нової особистої витрати.
- `updateExpense()`: Оновлення існуючої особистої витрати.
- `deleteExpense()`: Видалення особистої витрати за ідентифікатором.
- `getSumFoodExpenses()`: Отримання загальної суми витрат на їжу.
- `getSumExpensesBetweenDate(startDate, endDate)`: Отримання загальної суми витрат за вказаний період часу.

## Опис методів

### getAllExpenses()

#### GET /personal_expenses/api/expenses

Метод `getAllExpenses()` використовується для отримання всіх особистих витрат.


### addExpense(personalExpenses)

#### POST /personal_expenses/api/expenses

Метод `addExpense(personalExpenses)` приймає об'єкт `personalExpenses` і додає нову особисту витрату.


### updateExpense(id, expenses)

#### PUT /personal_expenses/api/expenses/{id}

Метод `updateExpense(id, expenses)` оновлює існуючу особисту витрату за вказаним ідентифікатором.


### deleteExpense(id)

#### DELETE /personal_expenses/api/expenses/{id}

Метод `deleteExpense(id)` видаляє особисту витрату за вказаним ідентифікатором.


### getSumFoodExpenses()

#### GET /personal_expenses/api/sum-food-expenses

Метод `getSumFoodExpenses()` використовується для отримання загальної суми витрат на їжу.


### getSumExpensesBetweenDate(startDate, endDate)

#### GET /personal_expenses/api/sum-expenses-between-date?startDate={startDate}&endDate={endDate}

Метод `getSumExpensesBetweenDate(startDate, endDate)` отримує загальну суму витрат за вказаний період часу між `startDate` і `endDate`.










