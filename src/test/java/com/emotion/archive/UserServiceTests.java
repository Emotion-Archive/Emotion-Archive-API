package com.emotion.archive;

import com.emotion.archive.model.domain.User;
import com.emotion.archive.model.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

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
