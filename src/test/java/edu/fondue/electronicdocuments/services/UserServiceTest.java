package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.utils.annotations.ApplicationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;


@ApplicationTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeAll
    void prepare() {

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/user-service-exists-by-username.csv")
    void existsByUsername(final String username, final Boolean result) {
        System.out.println(userService.existsByUsername(username));
        System.out.println(result);
    }
}
