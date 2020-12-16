package com.emotion.archive;

import com.emotion.archive.model.domain.EmotionArchive;
import com.emotion.archive.model.domain.User;
import com.emotion.archive.model.repository.EmotionArchiveRepository;
import com.emotion.archive.model.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DataServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmotionArchiveRepository emotionArchiveRepository;

    @Test
    @Ignore
    public void emotionTest() {
        List<EmotionArchive> emotionArchives = emotionArchiveRepository.findAllByMonth(5l, "20201201 0000", "20201231 2359");

        for (EmotionArchive emotionArchive : emotionArchives) {
            System.out.println(emotionArchive);
        }
    }

    @Test
    @Ignore
    public void procedureCallTest() {
        String changeRole = userRepository.updateUserRole(3L);
        System.out.println("change role = " + changeRole);
    }

    @Test
    @Ignore
    public void getAllTests() {
        List<User> userList = userRepository.getAllByOrderByRegDtDesc();

        for (User user : userList) {
            System.out.println(user.toString());
        }
    }

}
