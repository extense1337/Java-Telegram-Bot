package ru.home.telegrambot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "interestingFacts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterestingFactsData implements Serializable {
    @Id
    public String id;
    public String fact;

    @Override
    public String toString() {
        return String.format("Факт: %s%n", getFact());
    }
}
