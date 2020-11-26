package com.ukrsib.processor.fileexchange;

import com.ukrsib.processor.model.repo.TransactionsRepository;
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
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@SqlGroup({
        @Sql(value = "classpath:/com/ukrsib/start.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/com/ukrsib/end.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
class TransactionsSAXParserTest {
    private Logger logger = LoggerFactory.getLogger(TransactionsSAXParserTest.class);

    @Autowired
    private TransactionsSAXParser transactionsSAXParser;

    @Test
    public void shouldParseInputFile() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(new File("src/test/resources/transactions.xml"), transactionsSAXParser);
        logger.info("transaction: " + transactionsSAXParser.getTransactions());
        assertNotNull(transactionsSAXParser.getTransactions());
    }
}