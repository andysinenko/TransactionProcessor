package com.ukrsib.processor.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "\"TRANSACTION\"")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "\"ID\"")
    private UUID id;

    @Column(name = "\"PLACE\"") //<place>A PLACE 1</place>
    private String place;

    @Column(name = "\"AMOUNT\"") //<amount>10.01</amount>
    private Double amount;

    @Column(name = "\"CURRENCY\"") //<currency>UAH</currency>
    private String currency;

    @Column(name = "\"CARD\"") //<card>123456****1234</card>
    private String card;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "CLIENT") //<card>123456****1234</card>
    private Client client;

    public Transaction() {
    }

    public Transaction(String place, Double amount, String currency, String card, Client client) {
        this.id = id;
        this.place = place;
        this.amount = amount;
        this.currency = currency;
        this.card = card;
        this.client = client;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(place, that.place) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(card, that.card) &&
                Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, amount, currency, card, client);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", card='" + card + '\'' +
                ", client=" + client +
                '}';
    }
}
