package com.mycompany.app;

import java.util.Objects;

public class Book {
    private String titre;
    private String auteur;
    private boolean disponible = true;

    public Book(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur;
        this.disponible = true;
    }

    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public boolean isDisponible() { return disponible; }

    public void emprunter() { this.disponible = false; }
    public void retourner() { this.disponible = true; }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return disponible == book.disponible && Objects.equals(titre, book.titre) && Objects.equals(auteur, book.auteur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre, auteur, disponible);
    }

    @Override
    public String toString() {
        return "Book{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
