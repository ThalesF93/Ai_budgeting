package br.com.ai_budgeting.infrastructure.http;

import br.com.ai_budgeting.application.ListTransactionsByCategoryUseCase;
import br.com.ai_budgeting.application.PersistTransactionUseCase;
import br.com.ai_budgeting.domain.Category;
import br.com.ai_budgeting.infrastructure.http.request.TransactionRequest;
import br.com.ai_budgeting.infrastructure.http.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionsByCategoryUseCase listTransactionsByCategoryUseCase;
    private final TranscriptionModel transcriptionModel;
    private final TextToSpeechModel textToSpeechModel;


    private final ChatClient chatClient;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request){
          var transaction =  persistTransactionUseCase.execute(request.toInput());
            return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> readTransactions(@PathVariable Category category){
        return listTransactionsByCategoryUseCase.execute(category).stream().map(TransactionResponse::from).toList();
    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    public ResponseEntity<Resource> transcribe(@RequestParam("file") MultipartFile file){
        var userMessage = transcriptionModel.transcribe(file.getResource());
        var result = chatClient.prompt().user(userMessage).call().content();

        byte[] audio = textToSpeechModel.call(result);

        var resource = new ByteArrayResource(audio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,ContentDisposition.attachment().filename("audio.mp3")
                        .build()
                        .toString())
                .body(resource);

    }
}
