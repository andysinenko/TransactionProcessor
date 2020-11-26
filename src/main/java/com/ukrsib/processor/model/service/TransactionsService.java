package com.ukrsib.processor.model.service;

import com.ukrsib.processor.model.entity.Client;
import com.ukrsib.processor.model.entity.Transaction;
import com.ukrsib.processor.model.repo.ClientRepository;
import com.ukrsib.processor.model.repo.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionsService {
    private Logger logger = LoggerFactory.getLogger(TransactionsService.class);

    private ClientRepository clientRepository;
    private TransactionsRepository transactionsRepository;

    public TransactionsService(ClientRepository clientRepository, TransactionsRepository transactionsRepository) {
        this.clientRepository = clientRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Transactional
    public Transaction save(Transaction entity) {
        Client client = clientRepository.findClientByInn(entity.getClient().getInn());
        if (client == null) {
            client = clientRepository.saveAndFlush(entity.getClient());
        }
        entity.setClient(client);
        Transaction transaction = transactionsRepository.saveAndFlush(entity);
        return transaction;
    }

    public List<Transaction> findAll() {
        List<Transaction> transactionList = transactionsRepository.findAll();
        return transactionList;
    }
}
