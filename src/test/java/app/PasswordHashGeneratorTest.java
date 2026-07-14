package app;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordHashGeneratorTest {

    @Test
    void printHashes() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("admin123=" + encoder.encode("admin123"));
        System.out.println("reporter123=" + encoder.encode("reporter123"));
        System.out.println("subscriber123=" + encoder.encode("subscriber123"));
    }
}
