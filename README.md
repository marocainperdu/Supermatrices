# [Lien du Github](https://github.com/marocainperdu/Supermatrices)

# Membre du groupe

Cheikh Ibrahima NDIAYE
Mouhamadou Rassoul NAME 
Adja Maïmouna DIOP
Ahmadou Khadim TOURE 
Amadou Tidiane KANE
Mohamed Moustapha NIANG

---
# Bibliothèque Supermatrices Java

## Structure du projet

```
src/main/java/supermat/
├── Supermat.java           # Classe principale représentant une supermatrice
├── SupermatUtils.java      # Classe utilitaire pour les conversions et opérations
├── TestSupermat.java      # Tests reproduisant le main() du code C
└── DemoSupermat.java      # Démonstration complète des fonctionnalités
```

## Classes principales

### `Supermat`
Classe principale représentant une supermatrice avec :
- **Attributs** : `nl` (nombre de lignes), `nc` (nombre de colonnes), `ligne` (données), `isSousMat` (indicateur de sous-matrice)
- **Constructeurs** : Allocation de nouvelles matrices et création de vues
- **Méthodes d'accès** : `get(i,j)`, `set(i,j,val)`, getters pour dimensions
- **Opérations** : `produit()` (statique), `permuterLignes()`, `sousMatrice()`
- **Conversions** : `matSupermat()` (statique), `supermatMat()`
- **Gestion mémoire** : `recupererSupermat()` 
- **Affichage** : `afficher()`, `toString()`

### `SupermatUtils`
Classe utilitaire contenant :
- Conversions tableau ↔ supermatrice
- Création de matrices spéciales (identité, aléatoire)
- Opérations avancées (transposée)
### Adaptations
1. **Sous-matrices** : En Java, elles sont indépendantes (copie) plutôt que des vues partagées
2. **Contiguïté** : Concept moins pertinent en Java (tableaux toujours contigus)
   - **En C** : La fonction `contiguite()` était nécessaire car les matrices pouvaient être stockées de manière non-contiguë en mémoire (pointeurs vers des blocs séparés)
   - **En Java** : Les tableaux 2D (`double[][]`) sont automatiquement organisés de manière contiguë par ligne. Le garbage collector et la JVM garantissent une gestion optimale de la mémoire
   - **Implémentation** : La méthode `contiguite()` retourne toujours `2` (contigu et ordonné) pour les matrices normales et `1` pour les sous-matrices, principalement pour la compatibilité avec l'API C
   - **Pourquoi pas vraiment utile** : Java abstrait complètement la gestion mémoire, rendant cette vérification obsolète
3. **Gestion mémoire** : Libération automatique en Java vs manuelle en C
   - **En C** : `recuprèreSupermat()` était obligatoire pour éviter les fuites mémoire (chaque `malloc()` nécessite un `free()`)
   - **En Java** : Le Garbage Collector libère automatiquement la mémoire non référencée
   - **Implémentation** : `recupererSupermat()` conservée pour la compatibilité, mais optionnelle
4. **Pointeurs** : Remplacés par des références d'objets

## Compilation et exécution

### Avec les scripts fournis (Windows)
```bash
# Compilation uniquement
compile.bat

# Tests principaux uniquement
test.bat

# Compilation + tests + démonstration
run.bat
```

### Avec javac
```bash
# Compilation
javac -d bin src/main/java/supermat/*.java

# Exécution
java -cp bin supermat.TestSupermat
java -cp bin supermat.DemoSupermat
```

## Exemples d'utilisation

### Création et manipulation de base
```java
// Création d'une matrice 3x4
Supermat a = new Supermat(3, 4);

// Remplissage
for (int i = 0; i < a.getNombreLignes(); i++) {
    for (int j = 0; j < a.getNombreColonnes(); j++) {
        a.set(i, j, i * 10 + j);
    }
}

// Affichage
a.afficher("Ma matrice");
```

### Produit matriciel
```java
Supermat a = new Supermat(3, 4);
Supermat b = new Supermat(4, 2);
// ... remplissage ...

Supermat c = Supermat.produit(a, b);  // c sera 3x2
c.afficher("Résultat");
```

### Sous-matrice
```java
Supermat sub = a.sousMatrice(0, 1, 1, 2);  // Lignes 0-1, colonnes 1-2
sub.afficher("Sous-matrice");
```

### Conversions
```java
// Depuis tableau 1D (méthode principale dans Supermat)
double[] data1D = {1, 2, 3, 4, 5, 6};
Supermat sm = Supermat.matSupermat(data1D, 3, 2, 2, 2);  // 2x2 depuis tableau 3x2

// Depuis tableau 2D (méthode utilitaire dans SupermatUtils)
double[][] data = {{1,2,3}, {4,5,6}};
Supermat sm2 = SupermatUtils.matSupermat(data);

// Vers tableau 1D (méthode principale)
double[] result1D = new double[6];
sm.supermatMat(result1D, 3, 2);

// Vers tableau 2D (méthode utilitaire)
double[][] result = new double[2][3];
SupermatUtils.supermatMat(sm2, result);

// Libération explicite (optionnelle en Java)
sm.recupererSupermat();
```

## Gestion d'erreurs

La bibliothèque utilise des messages d'erreur simples via `System.err.println()` pour signaler :
- Dimensions invalides
- Indices hors limites  
- Incompatibilités de dimensions pour les opérations
- Matrices null

En cas d'erreur, les méthodes retournent `null` ou des valeurs par défaut et affichent un message d'erreur.

```java
Supermat a = new Supermat(-1, 5);  // Affiche une erreur et crée une matrice 1x1
double val = a.get(10, 10);        // Affiche une erreur et retourne 0.0
Supermat.produit(a, null);         // Affiche une erreur et retourne null
a.recupererSupermat();             // Libère explicitement les ressources
```

## Tests

- `TestSupermat.main()` : Reproduit fidèlement les tests du code C original
- `DemoSupermat.main()` : Démonstration étendue avec exemples pratiques
- Méthode `TestSupermat.testerErreurs()` : Tests des cas d'erreur avec messages simples

## Correspondance avec l'énoncé C

Cette implémentation Java respecte fidèlement l'énoncé original :

| **Fonction C demandée** | **Méthode Java implémentée** | **Description** |
|-------------------------|------------------------------|-----------------|
| `SUPERMAT allouerSupermat(int nl, int nc)` | `new Supermat(int nl, int nc)` | Constructeur principal |
| `double acces(SUPERMAT a, int i, int j)` | `get(int i, int j)` + `set(int i, int j, double)` | Accès lecture/écriture |
| `SUPERMAT superProduit(SUPERMAT a, SUPERMAT b)` | `static Supermat produit(Supermat, Supermat)` | Produit matriciel |
| `void permuterLignes(SUPERMAT a, int i, int j)` | `permuterLignes(int i, int j)` | Permutation de lignes |
| `SUPERMAT sousMatrice(SUPERMAT a, int l1, l2, c1, c2)` | `sousMatrice(int l1, int l2, int c1, int c2)` | Extraction de sous-matrice |
| `SUPERMAT matSupermat(double *m, int nld, ncd, nle, nce)` | `static matSupermat(double[], int, int, int, int)` | Tableau → Supermatrice |
| `void supermatMat(SUPERMAT sm, double *m, int nld, ncd)` | `supermatMat(double[], int, int)` | Supermatrice → Tableau |
| `int contiguite(SUPERMAT a)` | `contiguite()` | Analyse de contiguïté |
| `void recuprèreSupermat(SUPERMAT sm)` | `recupererSupermat()` | Libération mémoire |

### Note spéciale sur la fonction `contiguite()`

**Pourquoi cette fonction n'est pas vraiment nécessaire en Java :**

1. **En C** : La contiguïté était cruciale car :
   - Les matrices pouvaient être allouées avec `malloc()` de manière fragmentée
   - Les pointeurs vers les lignes pouvaient pointer vers des zones mémoire non-adjacentes
   - Il fallait vérifier si les données étaient stockées de manière optimale pour les calculs

2. **En Java** : Cette vérification devient obsolète car :
   - Les tableaux 2D (`double[][]`) sont automatiquement contigus par ligne
   - La JVM et le garbage collector optimisent automatiquement l'organisation mémoire
   - Java abstrait complètement la gestion de la mémoire physique
   - Pas d'accès direct aux pointeurs ou à l'organisation mémoire

3. **Notre implémentation** : 
   - Retourne toujours `2` (contigu et ordonné) pour les matrices normales
   - Retourne `1` pour les sous-matrices (par cohérence avec le concept C)
   - Conservée uniquement pour la compatibilité avec l'énoncé original

**Conclusion** : En Java, tous les tableaux sont par nature contigus, rendant cette fonction purement informative.

### Note spéciale sur la fonction `recupererSupermat()`

**Pourquoi cette fonction n'est pas vraiment nécessaire en Java :**

1. **En C** : La libération manuelle était obligatoire car :
   - Allocation manuelle avec `malloc()` nécessite un `free()` correspondant
   - Risque de fuites mémoire si on oublie de libérer
   - Gestion explicite de chaque pointeur et bloc mémoire alloué
   - Le programmeur est responsable de la gestion complète du cycle de vie

2. **En Java** : Cette libération devient automatique car :
   - Le **Garbage Collector (GC)** libère automatiquement la mémoire inutilisée
   - Pas de pointeurs explicites ni d'allocation manuelle (`new` suffit)
   - La JVM détecte automatiquement les objets non référencés
   - Gestion automatique du cycle de vie des objets

3. **Notre implémentation** :
   - Met simplement les références à `null` (aide le GC)
   - Affiche un message informatif pour la cohérence avec le C
   - Conservée uniquement pour respecter l'énoncé original
   - **Optionnelle** : le GC s'en charge de toute façon

4. **Exemple de différence** :
   ```c
   // En C - OBLIGATOIRE
   SUPERMAT a = allouerSupermat(3, 4);
   // ... utilisation ...
   recuprèreSupermat(a);  // OBLIGATOIRE sinon fuite mémoire
   ```
   
   ```java
   // En Java - OPTIONNEL
   Supermat a = new Supermat(3, 4);
   // ... utilisation ...
   a.recupererSupermat();  // OPTIONNEL - le GC s'en charge automatiquement
   // ou simplement : a = null;
   ```

**Conclusion** : En Java, la gestion mémoire automatique rend cette fonction optionnelle et purement informative.
