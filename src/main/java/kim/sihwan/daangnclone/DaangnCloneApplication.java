package kim.sihwan.daangnclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DaangnCloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(DaangnCloneApplication.class, args);
    }

}
