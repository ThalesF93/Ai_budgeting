package br.com.ai_budgeting.domain;


import java.util.UUID;

public record TransactionId(
        UUID uuid) {

    public TransactionId(){
        this(UUID.randomUUID());
    }
}
