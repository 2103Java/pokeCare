package com.revature.pokecare.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Table(name = "poketrainer")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;
    @JsonIgnore
    @Column(name = "pass_hash")
    private byte[] passwordHash;
    @JsonIgnore
    @Column(name = "pass_salt")
    private byte[] salt;

    //@OneToMany  Removed temporarily
    @Transient
    private List<Pokemon> pokeList = new ArrayList<>();

    private int currency;

    public Trainer() {
    }

    public Trainer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.passwordHash = hashPassword(password, salt = genSalt());
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public boolean correctPassword(String password) {
        return Arrays.equals(passwordHash, hashPassword(password, salt));
    }

    public static byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            digest.update(salt);

            return digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] genSalt() {
        byte[] salt = new byte[32];

        try {
            SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            ThreadLocalRandom.current().nextBytes(salt);
        }
        return salt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Pokemon> getPokeList() {
        return pokeList;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setPokeList(List<Pokemon> pokeList) {
        this.pokeList = pokeList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
}