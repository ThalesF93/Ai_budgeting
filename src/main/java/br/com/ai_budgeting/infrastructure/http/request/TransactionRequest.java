package br.com.ai_budgeting.infrastructure.http.request;

import br.com.ai_budgeting.application.input.PersistTransactionInput;
import br.com.ai_budgeting.domain.Category;

public record TransactionRequest(String description, Category category, long amount) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, amount, category);
    }
}
