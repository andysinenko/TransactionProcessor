package com.ukrsib.processor.fileexchange;

import com.ukrsib.processor.model.entity.Client;
import com.ukrsib.processor.model.entity.Transaction;
import com.ukrsib.processor.model.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsSAXParser extends DefaultHandler  {
    private Logger logger = LoggerFactory.getLogger(TransactionsSAXParser.class);

    private TransactionsService transactionsService;
    private Transaction transaction = null;
    private Client client = null;
    private StringBuilder data = null;

    public TransactionsSAXParser(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public Transaction getTransactions() {
        return transaction;
    }

    /*
    <transaction>
        <place>A PLACE 1</place>
        <amount>10.01</amount>
        <currency>UAH</currency>
        <card>123456****1234</card>
        <client>
            <firstName>Ivan</firstName>
            <lastName>Ivanoff</lastName>
            <middleName>Ivanoff</middleName>
            <inn>1234567890</inn>
        </client>
    </transaction>
     */

    boolean bTransaction = false;
    boolean bPlace = false;
    boolean bAmount = false;
    boolean bCurrency = false;
    boolean bCard = false;

    boolean bClient = false;
    boolean bFirstName = false;
    boolean bLastName = false;
    boolean bMiddleName = false;
    boolean bInn = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("transaction")) {
            transaction = new Transaction();
            bTransaction = true;
        } else if (qName.equalsIgnoreCase("place")) {
            bPlace = true;
        } else if (qName.equalsIgnoreCase("amount")) {
            bAmount = true;
        } else if (qName.equalsIgnoreCase("currency")) {
            bCurrency = true;
        } else if (qName.equalsIgnoreCase("card")) {
            bCard = true;
        } else if (qName.equalsIgnoreCase("client")) {
            if (client == null)
                client = new Client();
            bClient = true;
        } else if (qName.equalsIgnoreCase("firstName")) {
            bFirstName = true;
        } else if (qName.equalsIgnoreCase("lastName")) {
            bLastName = true;
        } else if (qName.equalsIgnoreCase("middleName")) {
            bMiddleName = true;
        } else if (qName.equalsIgnoreCase("inn")) {
            bInn = true;
        }
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bPlace) {
            transaction.setPlace(data.toString());
            bPlace = false;
        } else if (bAmount) {
            transaction.setAmount(Double.valueOf(data.toString()));
            bAmount = false;
        } else if (bCurrency) {
            transaction.setCurrency(data.toString());
            bCurrency = false;
        } else if (bCard) {
            transaction.setCard(data.toString());
            bCard = false;
        } else if (bFirstName) {
            client.setFirstName(data.toString());
            bFirstName = false;
        } else if (bLastName) {
            client.setLastName(data.toString());
            bLastName = false;
        } else if (bMiddleName) {
            client.setMiddleName(data.toString());
            bMiddleName = false;
        } else if (bInn) {
            client.setInn(Integer.valueOf(data.toString()));
            bInn = false;
        }

        if (qName.equalsIgnoreCase("transaction")) {
            transaction.setClient(client);
            logger.debug("endElement finished: " + transaction);
            Transaction result = transactionsService.save(transaction);
            logger.debug("result: " + result);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
