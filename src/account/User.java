package account;

/**
 * Classe représentant un utilisateur du système e-commerce
 * Hérite de Account et ajoute la gestion des adresses de livraison
 */
public class User extends Account{

    // Adresse de livraison de l'utilisateur (optionnelle)
    private Localisation addresse;

    /**
     * Constructeur par défaut
     * Appelle le constructeur parent pour la saisie des informations de base
     * L'adresse n'est pas créée automatiquement - laisse le choix à l'utilisateur
     */
    public User(){
        super(); // Appel du constructeur parent pour saisir nom, prénom, email, password
        this.addresse = new Localisation();
        // L'adresse sera ajoutée ultérieurement si nécessaire
    }

    /**
     * Constructeur complet avec adresse
     * @param nom Nom de famille
     * @param prenom Prénom
     * @param email Adresse email
     * @param password Mot de passe
     * @param addresse Adresse de livraison
     */
    public User(String nom, String prenom, String email, String password, Localisation addresse){
        super(nom, prenom, email, password);
        this.addresse = addresse;
    }

    /**
     * Ajouter une adresse avec paramètres spécifiques
     * @param ville Ville de livraison
     * @param secteur Secteur/quartier
     * @param description Description détaillée de l'adresse
     */
    public void addAddress(String ville, String secteur, String description){
        this.addresse = new Localisation(ville, secteur, description);
        System.out.println("Adresse ajoutée avec succès !");
    }

    /**
     * Ajouter une adresse en demandant la saisie à l'utilisateur
     * Utilise le constructeur par défaut de Localisation
     */
    public void addAddress(){
        this.addresse = new Localisation();
        System.out.println("Adresse ajoutée avec succès !");
    }

    /**
     * Obtenir l'adresse actuelle de l'utilisateur
     * @return L'adresse de livraison ou null si aucune adresse
     */
    public Localisation getAddress(){
        return this.addresse;
    }

    /**
     * Supprimer l'adresse de livraison
     * Utile si l'utilisateur ne souhaite plus avoir d'adresse enregistrée
     */
    public void removeAddress(){
        this.addresse = null;
        System.out.println("Adresse supprimée !");
    }

    /**
     * Vérifier si l'utilisateur a une adresse enregistrée
     * @return true si une adresse est définie, false sinon
     */
    public boolean hasAddress(){
        return this.addresse != null;
    }

    /**
     * Obtenir l'adresse sous forme de chaîne formatée
     * @return Adresse formatée ou message si aucune adresse
     */
    public String getFormattedAddress(){
        if(this.addresse == null){
            return "Aucune adresse enregistrée";
        }
        return String.format("%s, %s - %s",
                addresse.getVille(),
                addresse.getSecteur(),
                addresse.getDescription());
    }

    /**
     * Représentation textuelle de l'utilisateur pour l'affichage
     * @return Description formatée de l'utilisateur
     */
    @Override
    public String toString(){
        return String.format("User{nom='%s', prenom='%s', email='%s', hasAddress=%s}",
                getNom(), getPrenom(), getEmail(), hasAddress());
    }
}