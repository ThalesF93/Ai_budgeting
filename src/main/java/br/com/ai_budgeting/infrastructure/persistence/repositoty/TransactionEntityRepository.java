package br.com.ai_budgeting.infrastructure.persistence.repositoty;

import br.com.ai_budgeting.domain.Category;
import br.com.ai_budgeting.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByCategory(Category category);
}
