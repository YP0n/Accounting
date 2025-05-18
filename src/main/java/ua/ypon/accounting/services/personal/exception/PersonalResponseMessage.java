package ua.ypon.accounting.services.personal.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class PersonalResponseMessage {
    
    public static final String ERROR_SAVE_PERSONAL_EXPENSES = "Не вдалося зберегти особисті витрати.";
    public static final String ERROR_DELETE_PERSONAL_EXPENSES = "Не вдалося видалити особисті витрати.";
    public static final String ERROR_GET_PERSONAL_EXPENSES_BY_ID = "Не вдалося отримати особисті витрати за ID.";
}
