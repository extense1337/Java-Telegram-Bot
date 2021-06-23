package ru.home.telegrambot.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "userProfileData")
public class UserProfileData implements Serializable {

    @Id
    String id;
    String name;
    int age;
    String gender;
    long chatId;

    @Override
    public String toString() {
        return String.format("Имя: %s%nВозраст: %d%nПол: %s%n", getName(), getAge(), getGender());
    }
}
