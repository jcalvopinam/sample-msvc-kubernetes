package com.jcalvopinam.msvc.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsvcUserApplicationTests {

    @Test
    void contextLoads() {
        MsvcUserApplication.main(new String[]{});
        Assertions.assertTrue(true);
    }

}
