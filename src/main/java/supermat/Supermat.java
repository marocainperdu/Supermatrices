package supermat;

/**
 * Classe représentant une supermatrice avec ses dimensions et ses données.
 * Équivalent Java de la structure supermat_desc du code C.
 */
public class Supermat {
    private int nl;           // nombre de lignes
    private int nc;           // nombre de colonnes
    private double[][] ligne; // tableau des pointeurs vers les lignes
    private boolean isSousMat;   // indique si c'est une vue (sous-matrice)
    
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
        
        this.isSousMat = false;
        
        // Allocation du tableau de lignes
        this.ligne = new double[this.nl][this.nc];
    }

    /**
     * Constructeur pour créer une vue (sous-matrice).
     * Utilisé par sousMatrice().
     */
    private Supermat(int nl, int nc, double[][] ligneRef, boolean isSousMat) {
        this.nl = nl;
        this.nc = nc;
        this.ligne = ligneRef;
        this.isSousMat = isSousMat;
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
        return isSousMat;
    }
    
    /**
     * Produit matriciel de deux supermatrices.
     * Équivalent de superProduit() en C.
     */
    public static Supermat produit(Supermat matrice1, Supermat matrice2) {
        if (matrice1 == null) {
            System.err.println("Erreur : la première matrice ne peut pas être null");
            return null;
        }
        
        if (matrice2 == null) {
            System.err.println("Erreur : la seconde matrice ne peut pas être null");
            return null;
        }
        
        if (matrice1.nc != matrice2.nl) {
            System.err.println("Erreur : dimensions incompatibles pour le produit (" + 
                             matrice1.nc + " != " + matrice2.nl + ")");
            return null;
        }
        
        Supermat resultat = new Supermat(matrice1.nl, matrice2.nc);
        
        for (int i = 0; i < matrice1.nl; i++) {
            for (int j = 0; j < matrice2.nc; j++) {
                double somme = 0.0;
                for (int k = 0; k < matrice1.nc; k++) {
                    somme += matrice1.ligne[i][k] * matrice2.ligne[k][j];
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
     * Crée une supermatrice à partir d'un tableau 1D.
     * Équivalent de matSupermat() en C.
     */
    public static Supermat matSupermat(double[] m, int nld, int ncd, int nle, int nce) {
        if (m == null) {
            System.err.println("Erreur : le tableau ne peut pas être null");
            return null;
        }
        
        if (nle <= 0 || nce <= 0 || nle > nld || nce > ncd) {
            System.err.println("Erreur : paramètres invalides pour matSupermat");
            return null;
        }
        
        if (m.length < nld * ncd) {
            System.err.println("Erreur : tableau trop petit");
            return null;
        }
        
        Supermat resultat = new Supermat(nle, nce);
        
        for (int i = 0; i < nle; i++) {
            for (int j = 0; j < nce; j++) {
                double valeur = m[i * ncd + j];
                resultat.set(i, j, valeur);
            }
        }
        
        return resultat;
    }
    
    /**
     * Copie les éléments d'une supermatrice dans un tableau 1D.
     * Équivalent de supermatMat() en C.
     */
    public void supermatMat(double[] m, int nld, int ncd) {
        if (m == null) {
            System.err.println("Erreur : le tableau ne peut pas être null");
            return;
        }
        
        if (this.nl > nld || this.nc > ncd) {
            System.err.println("Erreur : dimensions incompatibles pour supermatMat");
            return;
        }
        
        if (m.length < nld * ncd) {
            System.err.println("Erreur : tableau trop petit");
            return;
        }
        
        for (int i = 0; i < this.nl; i++) {
            for (int j = 0; j < this.nc; j++) {
                m[i * ncd + j] = this.ligne[i][j];
            }
        }
    }
    
    /**
     * Libère les ressources de la supermatrice.
     * Équivalent de recuprèreSupermat() en C.
     * En Java, cette méthode marque la matrice comme libérée pour information.
     */
    public void recupererSupermat() {
        // En Java, le garbage collector s'occupe automatiquement de la mémoire
        // On peut juste marquer la matrice comme "libérée" pour cohérence avec le C
        this.ligne = null;
        this.nl = 0;
        this.nc = 0;
        this.isSousMat = false;
        System.out.println("Supermatrice libérée (marquée pour garbage collection)");
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
        if (isSousMat) {
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
        return isSousMat ? 1 : 2;
    }
}
