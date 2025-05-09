package jamol.socialmedia.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder; // Correct encoder class
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.classic.LoggerContext;
import de.siegmar.logbackgelf.GelfUdpAppender;
import de.siegmar.logbackgelf.GelfEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LoggingConfig {

    private static final Logger logger = LoggerFactory.getLogger(LoggingConfig.class);

    public static void configureLogging() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Console Appender configuration
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setContext(context);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder(); // Correct encoder
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n");
        encoder.setContext(context);
        encoder.start();
        consoleAppender.setEncoder(encoder);
        consoleAppender.start();

        // GELF Appender configuration
        GelfUdpAppender gelfAppender = new GelfUdpAppender();
        gelfAppender.setGraylogHost("127.0.0.1");
        gelfAppender.setGraylogPort(12201);
        gelfAppender.setMaxChunkSize(508);
        gelfAppender.setUseCompression(true);

        GelfEncoder gelfEncoder = new GelfEncoder();
        gelfEncoder.setIncludeRawMessage(false);
        gelfEncoder.setIncludeMarker(true);
        gelfEncoder.setIncludeMdcData(true);
        gelfEncoder.setIncludeCallerData(false);
        gelfEncoder.setIncludeRootCauseData(false);
        gelfEncoder.setIncludeLevelName(true);
        gelfEncoder.addStaticField("app_name");
        gelfEncoder.addStaticField("environment");
        gelfAppender.setEncoder(gelfEncoder);
        gelfAppender.setContext(context);
        gelfAppender.start();

        // Root logger configuration
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(ch.qos.logback.classic.Level.INFO);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(gelfAppender);
    }

    public static void main(String[] args) {
        configureLogging();
        MDC.put("username", "admin"); // Optional: Add contextual information to logs
        logger.info("Application has started.");
    }
}

