# Compilateur Minijava

Compilateur du langage Minijava de  Andrew J. Appel, "Modern Compiler Implémentation in Java" vers l'assembleur MIPS.

Code en Java avec utilisation de JFlex et CUP.

Outillage : Make ou Ant, Eclipse (ou Intellij).

- Auteur : Pascal Hennequin
- Version : 2021
- sous-version : Start
- Site du Cours : CSC4536  
  <http://www-inf.telecom-sudparis.eu/COURS/CSC4536/web/> 

# Version "Start"
Version de départ fournie aux élèves pour le projet Minijava.

Compilateur fonctionnel mais uniquement pour "MiniMicroJava"="hello 42" (cf. Exemples/Milestone/Test101.java).

Fournit :
- Définition des structures de données : Arbre de Syntaxe Abstraite, Arbre Sémantique, Table de symboles, Forme Intermédiaire
- Instrumentation pour impression, debug, exceptions...
- Squelette des différentes phases de compilation
- Interface avec CUP et JFlex

# Quelques exemples originaux de code Minijava (cf ~/Exemples/)
- Arithmétique de Peano
- Fonction de Ackermann
- Calcul BigNum et somme de 3 cubes

# Utilisation du projet
- Créer un lien symbolique <code>./lib</code> vers $LIBCOMPIL/lib : commande <code>Compil-link-lib</code>.
- ou créer un répertoire <code>./lib</code> contenant les librairies : java-cup-runtime.jar  java-cup.jar  jflex.jar  mars.jar.
- Utiliser :
    + la génération Make en ligne de commande
    + ou la génération Ant en ligne de commande
    + ou ouvrir comme un projet java dans Eclipse
    + ou importer comme un projet Eclipse dans Intellij IDEA
- En cas d'IDE, intégrer la génération Ant dans l'IDE pour utiliser JFlex et CUP.

