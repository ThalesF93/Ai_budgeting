package br.com.ai_budgeting.application.input;

import br.com.ai_budgeting.domain.Category;

public record PersistTransactionInput(String description, long amount, Category category) {
}
