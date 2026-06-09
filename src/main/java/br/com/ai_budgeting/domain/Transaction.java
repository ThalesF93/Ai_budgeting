package br.com.ai_budgeting.domain;

import lombok.Data;

@Data
public class Transaction {

    private TransactionId id;

    private String description;

    private Long amount;

    private Category category;

    public Transaction(String description, Long amount, Category category) {
        this.id = new TransactionId();
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}
