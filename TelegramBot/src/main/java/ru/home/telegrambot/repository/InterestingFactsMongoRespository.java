package ru.home.telegrambot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.home.telegrambot.model.InterestingFactsData;

import java.util.List;

@Repository
public interface InterestingFactsMongoRespository extends MongoRepository<InterestingFactsData, String> {
    public List<InterestingFactsData> findAll();
}
