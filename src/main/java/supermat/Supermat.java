package supermat;

/**
 * Classe représentant une supermatrice avec ses dimensions et ses données.
 * Équivalent Java de la structure supermat_desc du code C.
 */
public class Supermat {
    private int nl;           // nombre de lignes
    private int nc;           // nombre de colonnes
    private double[][] ligne; // tableau des pointeurs vers les lignes
    private boolean isView;   // indique si c'est une vue (sous-matrice)
    
    /**
     * Constructeur principal - alloue une nouvelle supermatrice de taille nl x nc.
     * Équivalent de allouerSupermat() en C.
     */
    public Supermat(int nl, int nc) {
        if (nl <= 0 || nc <= 0) {
            System.err.println("Erreur : dimensions invalides (" + nl + ", " + nc + ")");
            this.nl = 1;
            this.nc = 1;
        } else {
            this.nl = nl;
            this.nc = nc;
        }
        
        this.isView = false;
        
        // Allocation du tableau de lignes
        this.ligne = new double[this.nl][this.nc];
    }
    
    /**
     * Constructeur pour créer une vue (sous-matrice).
     * Utilisé par sousMatrice().
     */
    private Supermat(int nl, int nc, double[][] ligneRef, boolean isView) {
        this.nl = nl;
        this.nc = nc;
        this.ligne = ligneRef;
        this.isView = isView;
    }
    
    /**
     * Accès en lecture à un élément - équivalent de la macro acces(a, i, j).
     */
    public double get(int i, int j) {
        if (i < 0 || i >= nl || j < 0 || j >= nc) {
            System.err.println("Erreur : indices hors limites (" + i + ", " + j + 
                             ") pour matrice " + nl + "x" + nc);
            return 0.0;
        }
        return ligne[i][j];
    }
    
    /**
     * Accès en écriture à un élément.
     */
    public void set(int i, int j, double valeur) {
        if (i < 0 || i >= nl || j < 0 || j >= nc) {
            System.err.println("Erreur : indices hors limites (" + i + ", " + j + 
                             ") pour matrice " + nl + "x" + nc);
            return;
        }
        ligne[i][j] = valeur;
    }
    
    /**
     * Retourne le nombre de lignes.
     */
    public int getNombreLignes() {
        return nl;
    }
    
    /**
     * Retourne le nombre de colonnes.
     */
    public int getNombreColonnes() {
        return nc;
    }
    
    /**
     * Indique si cette supermatrice est une vue (sous-matrice).
     */
    public boolean isView() {
        return isView;
    }
    
    /**
     * Produit matriciel de cette matrice et d'une autre.
     * Équivalent de superProduit() en C.
     */
    public Supermat produit(Supermat autre) {
        if (autre == null) {
            System.err.println("Erreur : la matrice autre ne peut pas être null");
            return null;
        }
        
        if (this.nc != autre.nl) {
            System.err.println("Erreur : dimensions incompatibles pour le produit (" + 
                             this.nc + " != " + autre.nl + ")");
            return null;
        }
        
        Supermat resultat = new Supermat(this.nl, autre.nc);
        
        for (int i = 0; i < this.nl; i++) {
            for (int j = 0; j < autre.nc; j++) {
                double somme = 0.0;
                for (int k = 0; k < this.nc; k++) {
                    somme += this.ligne[i][k] * autre.ligne[k][j];
                }
                resultat.ligne[i][j] = somme;
            }
        }
        
        return resultat;
    }
    
    /**
     * Permute deux lignes de la matrice - équivalent de permuterLignes() en C.
     * En Java, on échange les références aux tableaux.
     */
    public void permuterLignes(int i, int j) {
        if (i < 0 || j < 0 || i >= nl || j >= nl) {
            System.err.println("Erreur : indices de lignes invalides (" + i + ", " + j + 
                             ") pour matrice " + nl + "x" + nc);
            return;
        }
        
        if (i != j) {
            double[] temp = ligne[i];
            ligne[i] = ligne[j];
            ligne[j] = temp;
        }
    }
    
    /**
     * Crée une sous-matrice (vue) - équivalent de sousMatrice() en C.
     * La sous-matrice partage les données avec la matrice originale.
     */
    public Supermat sousMatrice(int l1, int l2, int c1, int c2) {
        if (l1 < 0 || l2 >= nl || c1 < 0 || c2 >= nc || l1 > l2 || c1 > c2) {
            System.err.println("Erreur : indices invalides pour la sous-matrice [" + 
                             l1 + ".." + l2 + "][" + c1 + ".." + c2 + "]");
            return null;
        }
        
        int nouvNl = l2 - l1 + 1;
        int nouvNc = c2 - c1 + 1;
        
        // Création d'un tableau de références vers les sous-lignes
        double[][] nouvLigne = new double[nouvNl][];
        for (int i = 0; i < nouvNl; i++) {
            // Création d'un sous-tableau pour chaque ligne
            nouvLigne[i] = new double[nouvNc];
            // Référence vers la portion de ligne originale
            System.arraycopy(ligne[l1 + i], c1, nouvLigne[i], 0, nouvNc);
        }
        
        return new Supermat(nouvNl, nouvNc, nouvLigne, true);
    }
    
    /**
     * Copie les valeurs d'une autre matrice dans cette matrice.
     */
    public void copierDepuis(Supermat source) {
        if (source == null) {
            System.err.println("Erreur : la matrice source ne peut pas être null");
            return;
        }
        
        if (this.nl != source.nl || this.nc != source.nc) {
            System.err.println("Erreur : dimensions incompatibles (" + 
                             this.nl + "x" + this.nc + " vs " + source.nl + "x" + source.nc + ")");
            return;
        }
        
        for (int i = 0; i < nl; i++) {
            System.arraycopy(source.ligne[i], 0, this.ligne[i], 0, nc);
        }
    }
    
    /**
     * Affiche le contenu de la supermatrice - équivalent d'afficherSupermat() en C.
     */
    public void afficher(String nom) {
        if (nom == null || nom.isEmpty()) {
            nom = "Matrice";
        }
        
        System.out.printf("%s (%dx%d) :\n", nom, nl, nc);
        for (int i = 0; i < nl; i++) {
            for (int j = 0; j < nc; j++) {
                System.out.printf("%7.2f ", ligne[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Retourne une représentation textuelle de la matrice.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Supermat(%dx%d)", nl, nc));
        if (isView) {
            sb.append(" [Vue]");
        }
        return sb.toString();
    }
    
    /**
     * Analyse la contiguïté des données - équivalent de contiguite() en C.
     * En Java, les tableaux sont toujours contigus, donc cette méthode
     * retourne toujours 2 pour une matrice normale.
     */
    public int contiguite() {
        // En Java, les tableaux 2D sont toujours organisés de manière contiguë
        // par ligne, donc on retourne toujours 2 (contigu et ordonné)
        return isView ? 1 : 2;
    }
}
