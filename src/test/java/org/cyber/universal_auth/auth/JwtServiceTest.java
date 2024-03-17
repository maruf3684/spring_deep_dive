package org.cyber.universal_auth.auth;

import io.jsonwebtoken.Claims;
import org.cyber.universal_auth.services.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import javax.crypto.SecretKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    @Test
    void testGetSignatureKey(){
        SecretKey secretKey = jwtService.getSignatureKey();
        assertNotNull(secretKey);
        System.out.println("Generated Secret Key: " + bytesToHex(secretKey.getEncoded()));
    }

    @Test
    void testCreateToken(){
        String token = jwtService.createToken("Munna");
        assertNotNull(token);
        System.out.println("Generated Token: " + token);
    }

    @Test
    void testExtractAllClaims(){
        String token = jwtService.createToken("Munna");
        Claims claims = jwtService.extractAllClaims(token);
        System.out.println("Claims: " + claims);
    }

    @Test
    void testExtractExpiration(){
        String token = jwtService.createToken("Munna");
        Date tokenExpireDate = jwtService.extractExpiration(token);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss a zzz yyyy");
        var date = sdf.format(tokenExpireDate);
        System.out.println("Token Expire Time: " + date);
    }

    @Test
    void testIsTokenExpired(){
        String token = jwtService.createToken("Munna");
        Boolean tokenExpire = jwtService.isTokenExpired(token);
        System.out.println("Token Expired : " + tokenExpire);
    }
}

//https://github.com/hello-iftekhar/springJwt/blob/main/src/main/java/com/helloIftekhar/springJwt/service/JwtService.java