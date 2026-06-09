package br.com.ai_budgeting.application;

import br.com.ai_budgeting.application.input.PersistTransactionInput;
import br.com.ai_budgeting.application.output.TransactionOutput;
import br.com.ai_budgeting.domain.Category;
import br.com.ai_budgeting.domain.Transaction;
import br.com.ai_budgeting.domain.TransactionRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersistTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public TransactionOutput execute(PersistTransactionInput input){
        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category()));

        return TransactionOutput.from(transaction);
    }

}
