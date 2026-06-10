package br.com.ai_budgeting.application;

import br.com.ai_budgeting.application.input.PersistTransactionInput;
import br.com.ai_budgeting.application.output.TransactionOutput;
import br.com.ai_budgeting.domain.Transaction;
import br.com.ai_budgeting.domain.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersistTransactionUseCase {

    private final TransactionRepository transactionRepository;

    @Tool(name = "persistTransaction", description = "Persiste uma nova transação financeira")
    public TransactionOutput execute(PersistTransactionInput input){
        var transaction = transactionRepository.save(
                new Transaction(input.description(), input.amount(), input.category()));

        return TransactionOutput.from(transaction);
    }

}
