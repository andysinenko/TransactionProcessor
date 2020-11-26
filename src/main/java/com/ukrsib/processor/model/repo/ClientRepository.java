package com.ukrsib.processor.model.repo;

import com.ukrsib.processor.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query("select cl from Client cl where cl.inn = :inn")
    public Client findClientByInn(Integer inn);
}
