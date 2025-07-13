package supermat;

/**
 * Classe utilitaire pour les fonctions de conversion et d'analyse des supermatrices.
 * Équivalent des fonctions matSupermat, supermatMat, etc. du code C.
 */
public class SupermatUtils {
    
    /**
     * Crée une supermatrice à partir d'un tableau 1D.
     * Équivalent de matSupermat() en C.
     * 
     * @param tableau Le tableau 1D source
     * @param nld Nombre de lignes dans le tableau source
     * @param ncd Nombre de colonnes dans le tableau source  
     * @param nle Nombre de lignes à extraire
     * @param nce Nombre de colonnes à extraire
     * @return Une nouvelle supermatrice
     */
    public static Supermat matSupermat(double[] tableau, int nld, int ncd, 
                                       int nle, int nce) throws SupermatException {
        if (tableau == null) {
            throw new SupermatException("Le tableau ne peut pas être null");
        }
        
        if (nle <= 0 || nce <= 0 || nle > nld || nce > ncd) {
            throw new SupermatException(
                String.format("Paramètres invalides pour la conversion: " +
                              "nle=%d, nce=%d, nld=%d, ncd=%d", nle, nce, nld, ncd));
        }
        
        if (tableau.length < nld * ncd) {
            throw new SupermatException(
                String.format("Tableau trop petit: %d éléments pour %dx%d", 
                              tableau.length, nld, ncd));
        }
        
        Supermat resultat = new Supermat(nle, nce);
        
        for (int i = 0; i < nle; i++) {
            for (int j = 0; j < nce; j++) {
                double valeur = tableau[i * ncd + j];
                resultat.set(i, j, valeur);
            }
        }
        
        return resultat;
    }
    
    /**
     * Crée une supermatrice à partir d'un tableau 2D.
     * Version surchargée plus pratique pour Java.
     */
    public static Supermat matSupermat(double[][] tableau) throws SupermatException {
        if (tableau == null || tableau.length == 0) {
            throw new SupermatException("Le tableau ne peut pas être null ou vide");
        }
        
        int nle = tableau.length;
        int nce = tableau[0].length;
        
        // Vérifier que toutes les lignes ont la même taille
        for (int i = 1; i < nle; i++) {
            if (tableau[i] == null || tableau[i].length != nce) {
                throw new SupermatException(
                    String.format("Ligne %d de taille incorrecte", i));
            }
        }
        
        Supermat resultat = new Supermat(nle, nce);
        
        for (int i = 0; i < nle; i++) {
            for (int j = 0; j < nce; j++) {
                resultat.set(i, j, tableau[i][j]);
            }
        }
        
        return resultat;
    }
    
    /**
     * Copie les éléments d'une supermatrice dans un tableau 1D.
     * Équivalent de supermatMat() en C.
     */
    public static void supermatMat(Supermat sm, double[] tableau, 
                                   int nld, int ncd) throws SupermatException {
        if (sm == null) {
            throw new SupermatException("La supermatrice ne peut pas être null");
        }
        
        if (tableau == null) {
            throw new SupermatException("Le tableau ne peut pas être null");
        }
        
        if (sm.getNombreLignes() > nld || sm.getNombreColonnes() > ncd) {
            throw new SupermatException(
                String.format("Dimensions incompatibles pour la copie: " +
                              "supermat %dx%d vs tableau %dx%d", 
                              sm.getNombreLignes(), sm.getNombreColonnes(), nld, ncd));
        }
        
        if (tableau.length < nld * ncd) {
            throw new SupermatException(
                String.format("Tableau trop petit: %d éléments pour %dx%d", 
                              tableau.length, nld, ncd));
        }
        
        for (int i = 0; i < sm.getNombreLignes(); i++) {
            for (int j = 0; j < sm.getNombreColonnes(); j++) {
                tableau[i * ncd + j] = sm.get(i, j);
            }
        }
    }
    
    /**
     * Copie les éléments d'une supermatrice dans un tableau 2D.
     * Version surchargée plus pratique pour Java.
     */
    public static void supermatMat(Supermat sm, double[][] tableau) throws SupermatException {
        if (sm == null) {
            throw new SupermatException("La supermatrice ne peut pas être null");
        }
        
        if (tableau == null || tableau.length == 0) {
            throw new SupermatException("Le tableau ne peut pas être null ou vide");
        }
        
        int nld = tableau.length;
        int ncd = tableau[0].length;
        
        if (sm.getNombreLignes() > nld || sm.getNombreColonnes() > ncd) {
            throw new SupermatException(
                String.format("Dimensions incompatibles pour la copie: " +
                              "supermat %dx%d vs tableau %dx%d", 
                              sm.getNombreLignes(), sm.getNombreColonnes(), nld, ncd));
        }
        
        for (int i = 0; i < sm.getNombreLignes(); i++) {
            for (int j = 0; j < sm.getNombreColonnes(); j++) {
                tableau[i][j] = sm.get(i, j);
            }
        }
    }
    
    /**
     * Crée une matrice identité de taille n x n.
     */
    public static Supermat identite(int n) throws SupermatException {
        Supermat resultat = new Supermat(n, n);
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                resultat.set(i, j, (i == j) ? 1.0 : 0.0);
            }
        }
        
        return resultat;
    }
    
    /**
     * Remplit une matrice avec une valeur constante.
     */
    public static void remplir(Supermat sm, double valeur) throws SupermatException {
        if (sm == null) {
            throw new SupermatException("La supermatrice ne peut pas être null");
        }
        
        for (int i = 0; i < sm.getNombreLignes(); i++) {
            for (int j = 0; j < sm.getNombreColonnes(); j++) {
                sm.set(i, j, valeur);
            }
        }
    }
    
    /**
     * Remplit une matrice avec des valeurs aléatoires entre min et max.
     */
    public static void remplirAleatoire(Supermat sm, double min, double max) throws SupermatException {
        if (sm == null) {
            throw new SupermatException("La supermatrice ne peut pas être null");
        }
        
        if (min > max) {
            throw new SupermatException("min doit être inférieur ou égal à max");
        }
        
        java.util.Random random = new java.util.Random();
        
        for (int i = 0; i < sm.getNombreLignes(); i++) {
            for (int j = 0; j < sm.getNombreColonnes(); j++) {
                double valeur = min + random.nextDouble() * (max - min);
                sm.set(i, j, valeur);
            }
        }
    }
    
    /**
     * Transpose une matrice.
     */
    public static Supermat transpose(Supermat sm) throws SupermatException {
        if (sm == null) {
            throw new SupermatException("La supermatrice ne peut pas être null");
        }
        
        Supermat resultat = new Supermat(sm.getNombreColonnes(), sm.getNombreLignes());
        
        for (int i = 0; i < sm.getNombreLignes(); i++) {
            for (int j = 0; j < sm.getNombreColonnes(); j++) {
                resultat.set(j, i, sm.get(i, j));
            }
        }
        
        return resultat;
    }
}
