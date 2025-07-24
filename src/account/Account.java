package account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe mère pour les comptes utilisateurs (Client et Admin)
 *
 * @author Bérickal
 */
public class Account {

    //Base de données fictive pour le stockage des données
    private static List<Account> db = new ArrayList<>();

    private String nom;

    private String prenom;

    private String email;

    protected String password;

    /**
     * Constructeur par défaut : l'utilisateur devra renseigner manuellement les
     * information relative au compte.
     * Sauvegarde les informations du compte à la fin du process.
     * Controle préalable du mail afin d'éviter les doublons.
     */
    public Account(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez votre nom : ");
        String nom = sc.nextLine();
        System.out.print("Entrez votre prenom : ");
        String prenom = sc.nextLine();
        System.out.print("Entrez votre email : ");
        String email = sc.nextLine();
        System.out.print("Entrez votre mot de passe : ");
        String password = sc.nextLine();

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;

        if(!isEmailExist(this.getEmail())){
            saveAccount();
        } else {
            System.out.println("Email déjà utilisé !");
        }
    }

    /**
     * Parameterized constructor for creating an account with provided details.
     *
     * @param nom User's last name
     * @param prenom User's first name
     * @param email User's email address
     * @param password User's password
     */
    public Account(String nom, String prenom, String email, String password){
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;

        if(!isEmailExist(this.getEmail())){
            saveAccount();
        }
    }

    /**
     * Gets the user's last name.
     *
     * @return The user's last name
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Gets the user's first name.
     *
     * @return The user's first name
     */
    public String getPrenom(){
        return this.prenom;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email address
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Sets the user's last name.
     *
     * @param nom The new last name
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Sets the user's first name.
     *
     * @param prenom The new first name
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's password.
     *
     * @param password The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Authenticates a user with email and password, and updates current instance with user data.
     *
     * @param email The email address to authenticate
     * @param password The password to authenticate
     * @return true if authentication successful, false otherwise
     */
    public boolean connecter(String email, String password){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getEmail().equals(email) && db.get(i).password.equals(password)){
                this.setNom(db.get(i).nom);
                this.setPrenom(db.get(i).prenom);
                this.setEmail(db.get(i).getEmail());
                this.setPassword(db.get(i).password);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an email address is already registered in the system.
     *
     * @param email The email address to check
     * @return true if email exists, false otherwise
     */
    public static boolean isEmailExist(String email){
        for(int i = 0; i < db.size(); i++){
            if(db.get(i).getEmail().equals(email)) return true;
        }
        return false;
    }

    /**
     * Saves the current account to the database.
     * Private method called automatically during account creation.
     */
    private void saveAccount(){
        db.add(this);
        System.out.println("Compte enregistré avec succès !");
        System.out.println("Nombre de compte créés : " + db.size());
    }

    /**
     * Gets a copy of all registered accounts.
     *
     * @return List of all accounts in the system
     */
    public static List<Account> getAllAccounts(){
        return new ArrayList<>(db);
    }
}