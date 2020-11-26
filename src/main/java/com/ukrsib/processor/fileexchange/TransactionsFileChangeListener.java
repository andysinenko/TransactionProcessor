package com.ukrsib.processor.fileexchange;

import com.ukrsib.processor.config.AccountsProperties;
import com.ukrsib.processor.model.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

@Component
public class TransactionsFileChangeListener {
    private Logger logger = LoggerFactory.getLogger(TransactionsFileChangeListener.class);

    @Autowired
    AccountsProperties accountsProperties;

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    private TransactionsSAXParser transactionsSaxParser;

    @Scheduled(fixedDelayString = "${console.fixedDelay}", initialDelayString = "${console.initialDelay}")
    public void task() throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get(accountsProperties.getFolder()))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(Files::isWritable)
                    .forEach(e -> {
                        File cfile = e.toFile();
                        if ((!isLocked(cfile.toPath()))) {
                            try {
                                String extention = getFileExtention(cfile);
                                if (extention.equalsIgnoreCase("xml")) {
                                    logger.info("Found file: {}, with length: {}", cfile.getAbsolutePath(), cfile.length());

                                    SAXParserFactory factory = SAXParserFactory.newInstance();
                                    SAXParser parser = factory.newSAXParser();
                                    parser.parse(new File(cfile.getPath()), transactionsSaxParser);
                                    Files.deleteIfExists(Paths.get(cfile.getPath()));
                                } else {
                                    logger.error("Error reading file {}: file must have csv or xml extention", cfile.getAbsolutePath());
                                    Files.deleteIfExists(Paths.get(cfile.getPath()));
                                }
                            } catch (Throwable ex) {
                                logger.error("Error reading file: " + cfile.getAbsolutePath() + ex);
                            }
                        }
                    });
        }
    }

    private boolean isLocked(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.WRITE);
             FileLock lock = ch.tryLock()) {
            return lock == null;
        } catch (IOException e) {
            return true;
        }
    }

    private String getFileExtention(File file) {
        String absolutePath = file.getAbsolutePath();
        String extention = absolutePath.substring(absolutePath.lastIndexOf(".") + 1);
        return extention;
    }
}
