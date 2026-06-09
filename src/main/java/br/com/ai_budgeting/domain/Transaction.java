package br.com.ai_budgeting.domain;

import br.com.ai_budgeting.infrastructure.persistence.entity.TransactionEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
