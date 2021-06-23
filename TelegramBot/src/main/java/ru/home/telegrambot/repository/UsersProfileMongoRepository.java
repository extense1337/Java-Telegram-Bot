package ru.home.telegrambot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.home.telegrambot.model.UserProfileData;

@Repository
public interface UsersProfileMongoRepository extends MongoRepository<UserProfileData, String> {
    UserProfileData findByChatId(long chatId);
    void deleteByChatId(long chatId);
}
