package ua.ypon.accounting.services.income.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class IncomeResponseMessage {
    public static final String ERROR_SAVE_INCOME = "Не вдалося зберегти дохід.";
    public static final String ERROR_DELETE_INCOME = "Не вдалося видалити дохід.";
    public static final String ERROR_INCOME_NOT_FOUND_ID = "Дохід не знайдено за вказаним ID.";
}
