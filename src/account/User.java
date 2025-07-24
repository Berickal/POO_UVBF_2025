package account;

/**
 * Represents a regular user account in the e-shop system.
 * Extends the base Account class with additional functionality for managing delivery addresses.
 * Users can browse products, manage shopping carts, and place orders.
 *
 * @author E-Shop Development Team
 * @version 1.0
 * @since 1.0
 * @see Account
 * @see Localisation
 */
public class User extends Account{

    /** User's delivery address information */
    private Localisation addresse;

    /**
     * Default constructor that creates a user account by prompting for information.
     * Does not automatically create an address - user must add one separately.
     */
    public User(){
        super();
    }

    /**
     * Creates a user account with the specified details.
     *
     * @param nom User's last name
     * @param prenom User's first name
     * @param email User's email address (must be unique)
     * @param password User's password
     */
    public User(String nom, String prenom, String email, String password){
        super(nom, prenom, email, password);
    }

    /**
     * Creates a user account with address information.
     *
     * @param nom User's last name
     * @param prenom User's first name
     * @param email User's email address (must be unique)
     * @param password User's password
     * @param addresse User's delivery address
     */
    public User(String nom, String prenom, String email, String password, Localisation addresse){
        super(nom, prenom, email, password);
        this.addresse = addresse;
    }

    /**
     * Adds a delivery address with the specified details.
     *
     * @param ville City name
     * @param secteur Sector or district
     * @param description Additional address description
     */
    public void addAddress(String ville, String secteur, String description){
        this.addresse = new Localisation(ville, secteur, description);
        System.out.println("Adresse ajoutée avec succès !");
    }

    /**
     * Adds a delivery address by prompting user for input via console.
     */
    public void addAddress(){
        this.addresse = new Localisation();
        System.out.println("Adresse ajoutée avec succès !");
    }

    /**
     * Gets the user's delivery address.
     *
     * @return The user's address, or null if no address is set
     */
    public Localisation getAddress(){
        return this.addresse;
    }

    /**
     * Updates the existing address with new information.
     * If no address exists, creates a new one.
     *
     * @param ville New city name
     * @param secteur New sector or district
     * @param description New address description
     */
    public void updateAddress(String ville, String secteur, String description){
        if(this.addresse != null){
            this.addresse.setVille(ville);
            this.addresse.setSecteur(secteur);
            this.addresse.setDescription(description);
            System.out.println("Adresse mise à jour avec succès !");
        } else {
            this.addAddress(ville, secteur, description);
        }
    }

    /**
     * Removes the user's delivery address.
     */
    public void removeAddress(){
        this.addresse = null;
        System.out.println("Adresse supprimée !");
    }

    /**
     * Checks if the user has a delivery address configured.
     *
     * @return true if user has an address, false otherwise
     */
    public boolean hasAddress(){
        return this.addresse != null;
    }

    /**
     * Gets a formatted string representation of the user's address.
     *
     * @return Formatted address string, or message if no address exists
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
     * Displays the user's complete profile information to the console.
     */
    public void displayProfile(){
        System.out.println("=== PROFIL UTILISATEUR ===");
        System.out.println("Nom: " + this.getNom());
        System.out.println("Prénom: " + this.getPrenom());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Adresse: " + this.getFormattedAddress());
    }

    /**
     * Returns a string representation of the user object.
     *
     * @return String containing user's basic information
     */
    @Override
    public String toString(){
        return String.format("User{nom='%s', prenom='%s', email='%s', hasAddress=%s}",
                getNom(), getPrenom(), getEmail(), hasAddress());
    }
}