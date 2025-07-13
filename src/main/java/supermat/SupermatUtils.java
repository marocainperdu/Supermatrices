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
                                       int nle, int nce) {
        if (tableau == null) {
            System.err.println("Erreur : le tableau ne peut pas être null");
            return null;
        }
        
        if (nle <= 0 || nce <= 0 || nle > nld || nce > ncd) {
            System.err.println("Erreur : paramètres invalides pour la conversion: " +
                              "nle=" + nle + ", nce=" + nce + ", nld=" + nld + ", ncd=" + ncd);
            return null;
        }
        
        if (tableau.length < nld * ncd) {
            System.err.println("Erreur : tableau trop petit: " + tableau.length + 
                              " éléments pour " + nld + "x" + ncd);
            return null;
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
    public static Supermat matSupermat(double[][] tableau) {
        if (tableau == null || tableau.length == 0) {
            System.err.println("Erreur : le tableau ne peut pas être null ou vide");
            return null;
        }
        
        int nle = tableau.length;
        int nce = tableau[0].length;
        
        // Vérifier que toutes les lignes ont la même taille
        for (int i = 1; i < nle; i++) {
            if (tableau[i] == null || tableau[i].length != nce) {
                System.err.println("Erreur : ligne " + i + " de taille incorrecte");
                return null;
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
                                   int nld, int ncd) {
        if (sm == null) {
            System.err.println("Erreur : la supermatrice ne peut pas être null");
            return;
        }
        
        if (tableau == null) {
            System.err.println("Erreur : le tableau ne peut pas être null");
            return;
        }
        
        if (sm.getNombreLignes() > nld || sm.getNombreColonnes() > ncd) {
            System.err.println("Erreur : dimensions incompatibles pour la copie: " +
                              "supermat " + sm.getNombreLignes() + "x" + sm.getNombreColonnes() + 
                              " vs tableau " + nld + "x" + ncd);
            return;
        }
        
        if (tableau.length < nld * ncd) {
            System.err.println("Erreur : tableau trop petit: " + tableau.length + 
                              " éléments pour " + nld + "x" + ncd);
            return;
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
    public static void supermatMat(Supermat sm, double[][] tableau) {
        if (sm == null) {
            System.err.println("Erreur : la supermatrice ne peut pas être null");
            return;
        }
        
        if (tableau == null || tableau.length == 0) {
            System.err.println("Erreur : le tableau ne peut pas être null ou vide");
            return;
        }
        
        int nld = tableau.length;
        int ncd = tableau[0].length;
        
        if (sm.getNombreLignes() > nld || sm.getNombreColonnes() > ncd) {
            System.err.println("Erreur : dimensions incompatibles pour la copie: " +
                              "supermat " + sm.getNombreLignes() + "x" + sm.getNombreColonnes() + 
                              " vs tableau " + nld + "x" + ncd);
            return;
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
    public static Supermat identite(int n) {
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
    public static void remplir(Supermat sm, double valeur) {
        if (sm == null) {
            System.err.println("Erreur : la supermatrice ne peut pas être null");
            return;
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
    public static void remplirAleatoire(Supermat sm, double min, double max) {
        if (sm == null) {
            System.err.println("Erreur : la supermatrice ne peut pas être null");
            return;
        }
        
        if (min > max) {
            System.err.println("Erreur : min doit être inférieur ou égal à max");
            return;
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
    public static Supermat transpose(Supermat sm) {
        if (sm == null) {
            System.err.println("Erreur : la supermatrice ne peut pas être null");
            return null;
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
