package br.com.ai_budgeting.application;

import br.com.ai_budgeting.application.output.TransactionOutput;
import br.com.ai_budgeting.domain.Category;
import br.com.ai_budgeting.domain.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListTransactionsByCategoryUseCase {

    private final TransactionRepository transactionRepository;

    @Tool(name = "listTransactionsByCategory", description = "Lista Transações financeiras por categoria")
    public List<TransactionOutput> execute(@ToolParam(description = "categoria de uma transação") Category category){
        return transactionRepository.findAllByCategory(category)
                .stream()
                .map(TransactionOutput::from)
                .toList();
    }
}
