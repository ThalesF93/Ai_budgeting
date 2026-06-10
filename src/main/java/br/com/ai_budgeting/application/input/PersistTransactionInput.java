package br.com.ai_budgeting.application.input;

import br.com.ai_budgeting.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistTransactionInput(@ToolParam(description = "descrição do gasto") String description,
                                      @ToolParam(description = "valor go gasto") long amount,
                                      @ToolParam(description = "Categoria da transação") Category category) {
}
