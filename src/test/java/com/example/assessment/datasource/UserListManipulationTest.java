package com.example.assessment.datasource;

import com.example.assessment.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Slf4j
class UserListManipulationTest {

    @InjectMocks
    UserListManipulation userListManipulation;

    User[] users;

    @Test
    public void test_sortUsersInAscendingOrderOfName() {
        users = createUserArray();
        userListManipulation.sortUsersInAscendingOrderOfName(users);
        Assertions.assertEquals(users[0].getName(), "test1");
        Assertions.assertEquals(users[1].getName(), "test2");
        Assertions.assertEquals(users[2].getName(), "test3");
    }

    @Test
    public void test_getLastUser() {
        users = createUserArray();
        User user = userListManipulation.getLastUser(users);
        Assertions.assertEquals(user.getName(), "test2");
        Assertions.assertEquals(user.getId(), 3);
    }

    private User[] createUserArray() {
        users = new User[3];
        User user1 = new User();
        user1.setId(1);
        user1.setName("test3");
        User user2 = new User();
        user2.setId(1);
        user2.setName("test1");
        User user3 = new User();
        user3.setId(3);
        user3.setName("test2");
        users[0] = user1;
        users[1] = user2;
        users[2] = user3;
        return users;
    }

}