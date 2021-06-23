package ru.home.telegrambot.service;

import org.springframework.stereotype.Service;
import ru.home.telegrambot.model.InterestingFactsData;
import ru.home.telegrambot.repository.InterestingFactsMongoRespository;

import java.util.List;

@Service
public class InterestingFactsDataService {
    private InterestingFactsMongoRespository factsMongoRepository;

    public InterestingFactsDataService(InterestingFactsMongoRespository factsMongoRespository) {
        this.factsMongoRepository = factsMongoRespository;
    }

    public List<InterestingFactsData> getAllFacts() {
        return factsMongoRepository.findAll();
    }

    public void saveInterestingFactsData(InterestingFactsData factsData) {
        factsMongoRepository.save(factsData);
    }
}
