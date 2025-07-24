package account;

import java.util.Scanner;

/**
 * Classe représentant une adresse de livraison
 * Stocke les informations géographiques pour la livraison des commandes
 */
public class Localisation {

    // Attributs de l'adresse
    private String ville;        // Ville de livraison
    private String secteur;      // Secteur
    private String description;  // Description détaillée (rue, numéro, indications)

    /**
     * Constructeur par défaut - demande la saisie des informations à l'utilisateur
     * Inclut une validation de base et des valeurs par défaut en cas d'erreur
     */
    public Localisation(){
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Entrez votre ville : ");
            String ville = sc.nextLine();
            System.out.print("Entrez votre secteur : ");
            String secteur = sc.nextLine();
            System.out.print("Entrez votre description : ");
            String description = sc.nextLine();

            // Validation de base des champs obligatoires
            if(ville.isEmpty()){
                System.out.println("La ville ne peut pas être vide. Valeur par défaut utilisée.");
                ville = "Non spécifié";
            }
            if(secteur.isEmpty()){
                System.out.println("Le secteur ne peut pas être vide. Valeur par défaut utilisée.");
                secteur = "Non spécifié";
            }

            // Attribution des valeurs avec validation
            this.ville = ville;
            this.secteur = secteur;
            this.description = description.isEmpty() ? "Aucune description" : description;

        } catch (Exception e) {
            // Gestion d'erreur robuste avec valeurs par défaut
            System.out.println("Erreur lors de la saisie de l'adresse. Valeurs par défaut utilisées.");
            this.ville = "Non spécifié";
            this.secteur = "Non spécifié";
            this.description = "Aucune description";
        }
    }

    /**
     * Constructeur paramétré avec validation des entrées
     * @param ville Ville (ne peut pas être vide)
     * @param secteur Secteur (ne peut pas être vide)
     * @param description Description détaillée (optionnelle)
     */
    public Localisation(String ville, String secteur, String description){
        // Validation et nettoyage des entrées
        this.ville = ville;
        this.secteur = secteur;
        this.description = description;
    }

    // Getters - méthodes d'accès aux attributs
    public String getVille() {
        return ville;
    }

    public String getSecteur() {
        return secteur;
    }

    public String getDescription() {
        return description;
    }

    // Setters avec validation

    /**
     * Setter avec validation pour la ville
     * @param ville Nouvelle ville (ne peut pas être vide)
     */
    public void setVille(String ville) {
        if(ville != null && !ville.isEmpty()){
            this.ville = ville.trim();
        } else {
            System.out.println("La ville ne peut pas être vide !");
        }
    }

    /**
     * Setter avec validation pour le secteur
     * @param secteur Nouveau secteur (ne peut pas être vide)
     */
    public void setSecteur(String secteur) {
        if(secteur != null && !secteur.isEmpty()){
            this.secteur = secteur.trim();
        } else {
            System.out.println("Le secteur ne peut pas être vide !");
        }
    }

    /**
     * Setter pour la description (peut être vide)
     * @param description Nouvelle description
     */
    public void setDescription(String description) {
        this.description = (description != null && !description.isEmpty()) ?
                description.trim() : "Aucune description";
    }

    /**
     * Obtenir l'adresse sous forme formatée
     * @return Adresse complète sous forme de chaîne
     */
    public String getFormattedAddress(){
        return String.format("%s, %s - %s", ville, secteur, description);
    }

    /**
     * Mettre à jour tous les champs en une seule fois
     * @param ville Nouvelle ville
     * @param secteur Nouveau secteur
     * @param description Nouvelle description
     */
    public void updateAddress(String ville, String secteur, String description){
        setVille(ville);
        setSecteur(secteur);
        setDescription(description);
    }


    /**
     * Représentation textuelle de l'adresse
     * @return Adresse formatée
     */
    @Override
    public String toString(){
        return getFormattedAddress();
    }

    /**
     * Méthode de comparaison pour vérifier l'égalité des adresses
     * @param obj Objet à comparer
     * @return true si les adresses sont identiques
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;  // Même référence
        if(obj == null || getClass() != obj.getClass()) return false;  // Type différent

        Localisation other = (Localisation) obj;
        return ville.equals(other.ville) &&
                secteur.equals(other.secteur) &&
                description.equals(other.description);
    }
}