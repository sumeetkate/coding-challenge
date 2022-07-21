package com.example.assessment.controller;

import com.example.assessment.datasource.UserListManipulation;
import com.example.assessment.model.User;
import com.example.assessment.repository.DeleteUser;
import com.example.assessment.repository.GetUsersRepo;
import com.example.assessment.repository.UpdateUserRepo;
import com.example.assessment.util.HeadersExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Calls various API(s) and performs operations like : get list of users , print out number of pages ,
 * update last user's name , delete last user and gets user with given userId 5555
 */
@Slf4j
@RestController
public class PlayGround {

    protected static final String HEADER_KEY_FOR_NUMBER_OF_PAGES = "X-Pagination-Pages";
    protected static final String NEW_NAME_FOR_LAST_USER = "Jhon Doe";
    protected static final Integer NON_EXISTENT_USER_ID = 5555;
    protected static final Integer DEFAULT_PAGE_ID = 3;
    protected static final String SUCCESS_RESPONSE = "All Operations successful";
    protected static final String FAILURE_RESPONSE = "One or more api endpoints dint work";

    final GetUsersRepo getUsersRepo;
    final HeadersExtractor headersExtractor;
    final UpdateUserRepo updateUserRepo;
    final DeleteUser deleteUser;
    final UserListManipulation userListManipulation;

    /**
     * Entry holds /playground mapping to call multiple apis. Return success response only if all operations succeed.
     *
     * @param getUsersRepo         : makes call to list of users
     * @param headersExtractor     : extracts header given Key
     * @param updateUserRepo       : updates user specified attribute
     * @param deleteUser           : deletes specified user
     * @param userListManipulation : helper to perform manipulation on retrieved user list
     */
    @Autowired
    public PlayGround(final GetUsersRepo getUsersRepo,
                      final HeadersExtractor headersExtractor,
                      final UpdateUserRepo updateUserRepo,
                      final DeleteUser deleteUser,
                      final UserListManipulation userListManipulation) {
        this.getUsersRepo = getUsersRepo;
        this.headersExtractor = headersExtractor;
        this.updateUserRepo = updateUserRepo;
        this.deleteUser = deleteUser;
        this.userListManipulation = userListManipulation;
    }

    @GetMapping("/playGround")
    public ResponseEntity<?> playWithApis() {
        boolean allOperationsSucceed = true;
        ResponseEntity<String> response;
        try {
            ResponseEntity<User[]> getUsersResponse = getUsersRepo.getUsersForPageNumber(DEFAULT_PAGE_ID);
            if (getUsersResponse == null || getUsersResponse.getBody() == null) {
                log.warn("No data present skipping few data modification operations");
            } else {
                final HttpHeaders headers = getUsersResponse.getHeaders();
                headersExtractor.extractHeaderValue(HEADER_KEY_FOR_NUMBER_OF_PAGES, headers);

                userListManipulation.sortUsersInAscendingOrderOfName(getUsersResponse.getBody());

                User lastUserInfo = userListManipulation.getLastUser(getUsersResponse.getBody());

                updateUserRepo.updateUserName(lastUserInfo, NEW_NAME_FOR_LAST_USER);

                deleteUser.deleteUser(lastUserInfo);
            }
            getUsersRepo.getUserWithGiveId(NON_EXISTENT_USER_ID);
        } catch (Exception exception) {
            log.error("Something went wrong with playGround", exception);
            allOperationsSucceed = false;
        } finally {
            if (allOperationsSucceed) {
                response = ResponseEntity.status(HttpStatus.OK)
                        .body(SUCCESS_RESPONSE);
            } else {
                response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(FAILURE_RESPONSE);
            }
        }
        return response;
    }
}
