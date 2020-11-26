package com.ukrsib.processor.model;

import com.ukrsib.processor.model.entity.Client;
import com.ukrsib.processor.model.entity.Transaction;
import com.ukrsib.processor.model.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@SqlGroup({
        @Sql(value = "classpath:/com/ukrsib/start.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/com/ukrsib/end.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
class JpaIntegrationTest {
    private Logger logger = LoggerFactory.getLogger(JpaIntegrationTest.class);

    @Autowired
    private TransactionsService transactionsService;

    @Test
    //@Ignore
    public void generateDataFullOperationTest() throws Throwable {
        Client client = new Client("Ivan", "Ivanoff", "Ivanoff", 1234567890);
        Transaction transaction1 = new Transaction("A PLACE 1", 10.01, "UAH", "123456****1234", client);
        Transaction transaction2 = new Transaction("A PLACE 2", 100.50, "USD", "123456****4321", client);
        transaction1 = transactionsService.save(transaction1);
        transaction2 = transactionsService.save(transaction2);
        logger.info("saved transaction1: " + transaction1);
        logger.info("saved transaction2: " + transaction2);
        assertNotNull(transaction1.getId());
        assertNotNull(transaction2.getId());
        assertEquals(transaction1.getClient().getId(), transaction2.getClient().getId());
    }


}

