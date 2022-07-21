package com.example.assessment.datasource;

import com.example.assessment.model.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Helper class to manipulate users array
 */
@Configuration
@Slf4j
public class UserListManipulation {

    /**
     * Sorts user array in ascending order of Name
     *
     * @param users : array of user that needs sorting
     */
    public void sortUsersInAscendingOrderOfName(@NonNull final User[] users) {
        Arrays.asList(users).sort(Comparator.comparing(User::getName));
    }

    /**
     * Gets last user from user array
     *
     * @param users : array of users
     * @return last user in given array and logs it before returning
     */
    public User getLastUser(@NonNull final User[] users) {
        User lastUserInfo = Arrays.asList(users).get(users.length - 1);
        log.info("Last User Info {} ", lastUserInfo);
        return lastUserInfo;
    }
}
