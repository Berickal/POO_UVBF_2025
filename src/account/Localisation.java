package account;

import java.util.Scanner;

public class Localisation {

    private String ville;
    private String secteur;
    private String description;

    public Localisation(){
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Entrez votre ville : ");
            String ville = sc.nextLine().trim();
            System.out.print("Entrez votre secteur : ");
            String secteur = sc.nextLine().trim();
            System.out.print("Entrez votre description : ");
            String description = sc.nextLine().trim();

            // Added: Basic validation
            if(ville.isEmpty()){
                System.out.println("La ville ne peut pas être vide. Valeur par défaut utilisée.");
                ville = "Non spécifié";
            }
            if(secteur.isEmpty()){
                System.out.println("Le secteur ne peut pas être vide. Valeur par défaut utilisée.");
                secteur = "Non spécifié";
            }

            this.ville = ville;
            this.secteur = secteur;
            this.description = description.isEmpty() ? "Aucune description" : description;

        } catch (Exception e) {
            System.out.println("Erreur lors de la saisie de l'adresse. Valeurs par défaut utilisées.");
            this.ville = "Non spécifié";
            this.secteur = "Non spécifié";
            this.description = "Aucune description";
        }
    }

    public Localisation(String ville, String secteur, String description){
        // Added: Input validation and sanitization
        this.ville = (ville != null && !ville.trim().isEmpty()) ? ville.trim() : "Non spécifié";
        this.secteur = (secteur != null && !secteur.trim().isEmpty()) ? secteur.trim() : "Non spécifié";
        this.description = (description != null && !description.trim().isEmpty()) ? description.trim() : "Aucune description";
    }

    public String getVille() {
        return ville;
    }

    public String getSecteur() {
        return secteur;
    }

    public String getDescription() {
        return description;
    }

    // Added: Validation in setters
    public void setVille(String ville) {
        if(ville != null && !ville.trim().isEmpty()){
            this.ville = ville.trim();
        } else {
            System.out.println("La ville ne peut pas être vide !");
        }
    }

    public void setSecteur(String secteur) {
        if(secteur != null && !secteur.trim().isEmpty()){
            this.secteur = secteur.trim();
        } else {
            System.out.println("Le secteur ne peut pas être vide !");
        }
    }

    public void setDescription(String description) {
        this.description = (description != null && !description.trim().isEmpty()) ?
                description.trim() : "Aucune description";
    }

    // Added: Get formatted address
    public String getFormattedAddress(){
        return String.format("%s, %s - %s", ville, secteur, description);
    }

    // Added: Check if address is complete
    public boolean isComplete(){
        return !ville.equals("Non spécifié") &&
                !secteur.equals("Non spécifié") &&
                !description.equals("Aucune description");
    }

    // Added: Update all fields at once
    public void updateAddress(String ville, String secteur, String description){
        setVille(ville);
        setSecteur(secteur);
        setDescription(description);
    }

    // Added: Display address details
    public void displayAddress(){
        System.out.println("=== ADRESSE ===");
        System.out.println("Ville: " + this.ville);
        System.out.println("Secteur: " + this.secteur);
        System.out.println("Description: " + this.description);
    }

    // Added: Copy constructor
    public Localisation(Localisation other){
        if(other != null){
            this.ville = other.ville;
            this.secteur = other.secteur;
            this.description = other.description;
        } else {
            this.ville = "Non spécifié";
            this.secteur = "Non spécifié";
            this.description = "Aucune description";
        }
    }

    @Override
    public String toString(){
        return getFormattedAddress();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Localisation other = (Localisation) obj;
        return ville.equals(other.ville) &&
                secteur.equals(other.secteur) &&
                description.equals(other.description);
    }
}