package com.ukrsib.processor.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "\"CLIENT\"")
public class Client implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "\"ID\"")
    private UUID id;

    @Column(name = "\"FIRST_NAME\"") //<firstName>Ivan</firstName>
    private String firstName;

    @Column(name = "\"LAST_NAME\"") //<lastName>Ivanoff</lastName>
    private String lastName;

    @Column(name = "\"MIDDLE_NAME\"") //<middleName>Ivanoff</middleName>
    private String middleName;

    @Column(name = "\"INN\"") //<inn>1234567890</inn>
    private Integer inn;

    public Client() {
    }

    public Client(String firstName, String lastName, String middleName, Integer inn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.inn = inn;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getInn() {
        return inn;
    }

    public void setInn(Integer inn) {
        this.inn = inn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName) &&
                Objects.equals(middleName, client.middleName) &&
                Objects.equals(inn, client.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, inn);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", inn=" + inn +
                '}';
    }
}
