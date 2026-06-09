package br.com.ai_budgeting.infrastructure.persistence.entity;

import br.com.ai_budgeting.domain.Category;
import br.com.ai_budgeting.domain.Transaction;
import br.com.ai_budgeting.domain.TransactionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionEntity {

    @Id
    private UUID id;

    private String description;

    private long amount;

    @Enumerated(EnumType.STRING)
    private Category category;


    public static TransactionEntity from(Transaction transaction){
       return new TransactionEntity(transaction.getId().uuid(), transaction.getDescription(), transaction.getAmount(), transaction.getCategory());
    }


    public Transaction toDomain() {
        return new  Transaction(
                new TransactionId(this.id), this.description, this.amount, this.category
                );
    }
}
