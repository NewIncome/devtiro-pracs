package com.devtiro.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Component
public class DatabaseInitBanner {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private DataSource dataSource;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() throws Exception {
    String dbUrl = dataSource.getConnection().getMetaData().getURL();
    String schemaQuery = dbUrl.contains("h2") ?
        "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_TYPE = 'TABLE'" :
        "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'";
    Integer tableCount = jdbcTemplate.queryForObject(schemaQuery, Integer.class);
    String timestamp = LocalDateTime.now().toString();
    //to explicitly check the created table names. Debug: List table names
    String debugQuery = dbUrl.contains("h2") ?
        "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' AND TABLE_TYPE = 'TABLE'" :
        "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'";
    String queryList = jdbcTemplate.queryForList(debugQuery).toString();

    String banner = """
      =============================
      \u001B[33m_                                                               \s
      | \\ _ _|_ _ |_  _  _  _    |   _  _  _| _  _|   |_  _ __ __  _  __
      |_/(_| |_(_||_)(_|_> (/_   |__(_)(_|(_|(/_(_|   |_)(_|| || |(/_ |\s
      \u001B[0m=============================
        \u001B[33mDB URL: %s
      Tables Loaded: %d
      Table names: %s
        \u001B[0mLoaded At: %s
      =============================\u001B[33m
        $$$$$$$\\             $$\\               $$\\                                               \s
        $$  __$$\\            $$ |              $$ |                                              \s
        $$ |  $$ | $$$$$$\\ $$$$$$\\    $$$$$$\\  $$$$$$$\\   $$$$$$\\   $$$$$$$\\  $$$$$$\\            \s
        $$ |  $$ | \\____$$\\\\_$$  _|   \\____$$\\ $$  __$$\\  \\____$$\\ $$  _____|$$  __$$\\           \s
        $$ |  $$ | $$$$$$$ | $$ |     $$$$$$$ |$$ |  $$ | $$$$$$$ |\\$$$$$$\\  $$$$$$$$ |          \s
        $$ |  $$ |$$  __$$ | $$ |$$\\ $$  __$$ |$$ |  $$ |$$  __$$ | \\____$$\\ $$   ____|          \s
        $$$$$$$  |\\$$$$$$$ | \\$$$$  |\\$$$$$$$ |$$$$$$$  |\\$$$$$$$ |$$$$$$$  |\\$$$$$$$\\           \s
        \\_______/  \\_______|  \\____/  \\_______|\\_______/  \\_______|\\_______/  \\_______|       \s
         $$$$$$\\                      $$\\ $$\\                     $$\\     $$\\                    \s
        $$  __$$\\                     $$ |\\__|                    $$ |    \\__|                   \s
        $$ /  $$ | $$$$$$\\   $$$$$$\\  $$ |$$\\  $$$$$$$\\ $$$$$$\\ $$$$$$\\   $$\\  $$$$$$\\  $$$$$$$\\ \s
        $$$$$$$$ |$$  __$$\\ $$  __$$\\ $$ |$$ |$$  _____|\\____$$\\\\_$$  _|  $$ |$$  __$$\\ $$  __$$\\\s
        $$  __$$ |$$ /  $$ |$$ /  $$ |$$ |$$ |$$ /      $$$$$$$ | $$ |    $$ |$$ /  $$ |$$ |  $$ |
        $$ |  $$ |$$ |  $$ |$$ |  $$ |$$ |$$ |$$ |     $$  __$$ | $$ |$$\\ $$ |$$ |  $$ |$$ |  $$ |
        $$ |  $$ |$$$$$$$  |$$$$$$$  |$$ |$$ |\\$$$$$$$\\\\$$$$$$$ | \\$$$$  |$$ |\\$$$$$$  |$$ |  $$ |
        \\__|  \\__|$$  ____/ $$  ____/ \\__|\\__| \\_______|\\_______|  \\____/ \\__| \\______/ \\__|  \\__|
                  $$ |      $$ |                                                                 \s
                  $$ |      $$ |                                                                 \s
                  \\__|      \\__|                                                                 \s
                                                                                                    \s
         $$$$$$\\    $$\\                          $$\\                     $$\\                     \s
        $$  __$$\\   $$ |                         $$ |                    $$ |                    \s
        $$ /  \\__|$$$$$$\\    $$$$$$\\   $$$$$$\\ $$$$$$\\    $$$$$$\\   $$$$$$$ |                    \s
        \\$$$$$$\\  \\_$$  _|   \\____$$\\ $$  __$$\\\\_$$  _|  $$  __$$\\ $$  __$$ |                    \s
         \\____$$\\   $$ |     $$$$$$$ |$$ |  \\__| $$ |    $$$$$$$$ |$$ /  $$ |                    \s
        $$\\   $$ |  $$ |$$\\ $$  __$$ |$$ |       $$ |$$\\ $$   ____|$$ |  $$ |                    \s
        \\$$$$$$  |  \\$$$$  |\\$$$$$$$ |$$ |       \\$$$$  |\\$$$$$$$\\ \\$$$$$$$ |                    \s
         \\______/    \\____/  \\_______|\\__|        \\____/  \\_______| \\_______|\u001B[0m
      """.formatted(dbUrl, tableCount != null ? tableCount : 0, queryList, timestamp);

    System.out.println(banner);

    // Log debug List table names
    System.out.println("DEBUG: Tables: " + queryList);
  }
}
