package br.com.ai_budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
public class OpenAiTranscriptionModelIT {

    @Autowired
    OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    @ParameterizedTest
    @CsvSource({
            "gastos_financeiros.mp3, R$ 32"
    })
    void should_containExpectedKeywords_when_audioFilesAreProcessed(String filename, String expectedKeyword){
        var recording = new ClassPathResource("audio/" + filename);

        var response =  openAiAudioTranscriptionModel.call(recording);

        assertThat(response).isNotEmpty();
        assertThat(response).contains(expectedKeyword);
        System.out.println(response);
    }

}
