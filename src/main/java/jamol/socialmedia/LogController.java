package jamol.socialmedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/test-log")
    public String testLog() {
        // Qo'shimcha kontekst ma'lumotlari
        MDC.put("userId", "user-12345");
        MDC.put("requestType", "API_CALL");
        MDC.put("clientIp", "192.168.1.100");

        try {
            logger.trace("Bu TRACE darajadagi log (HTTP)");
            logger.debug("Bu DEBUG darajadagi log (HTTP)");
            logger.info("Bu INFO darajadagi log - HTTP orqali yuborilgan");
            logger.warn("Bu WARN darajadagi log - HTTP ogohlantirish");

            // Ataylab xatolik yaratamiz
            int result = 10 / 0;
        } catch (Exception e) {
            logger.error("Bu ERROR darajadagi log - HTTP orqali yuborilgan xatolik", e);
        } finally {
            // MDC ni tozalash
            MDC.clear();
        }

        return "Loglar HTTP orqali yuborildi! Graylogni tekshiring.";
    }
}