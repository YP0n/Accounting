CREATE TABLE IF NOT EXISTS BUSINESS_EXPENSES
(
    ID SERIAL PRIMARY KEY,
    DATE_EXPENSE_BUSINESS DATE,
    FUEL                  NUMERIC(38, 2),
    MAINTENANCE           NUMERIC(38, 2),
    OWNER                 NUMERIC(38, 2),
    RENT                  NUMERIC(38, 2),
    SALARY_I              NUMERIC(38, 2),
    SALARY_V              NUMERIC(38, 2),
    SUPPLIERS             NUMERIC(38, 2),
    TAX_PENSION           NUMERIC(38, 2),
    TAX_SINGLE            NUMERIC(38, 2),
    TAX_WAR               NUMERIC(38, 2),
    UTILITY_AND_WATER     NUMERIC(38, 2)
);


