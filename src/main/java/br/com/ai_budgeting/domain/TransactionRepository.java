package br.com.ai_budgeting.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    List<Transaction> findAllByCategory(Category category);

    Transaction save(Transaction transaction);
}
