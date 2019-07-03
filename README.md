# EpiSearch


## Recherche sémantique sur les maladies et médicaments sur DBPedia et Epicam

![accueil](https://github.com/jerome-Dh/semantic-search/blob/master/capture/c1.JPG)

EpiSearch est un moteur de reherche sémantique qui utilise les ontologie d'Epicam et ceux fournies par DBPedia pour offrir les informations à l'utilisateur concernant les maladies, les médicaments et les patients atteints de certains symtômes

S'il vous plait, rendez-vous ![sur Wikipedia](https://en.wikipedia.org/wiki/Semantic_search) pour plus d'informations sur la recherche sémantique


## Commencer

EpiSearch exploite le parallélisme afin d'offrir une vitesse de traitement plus conviviale à son utilisateur.

Il est conçu en exploitant les outils les plus performants et des technologies hautement à jour pour améléorer les résulats des recherches
Avant d'exploiter EpiSearch, il est nécessaire d'avoir un environnement prêt à l'acceuillir


### Conditions préalables

Il faut disposer d'une machine serveur capable d'exploiter le parallélisme offert par l'application afin de bénéficier des bonnes performances.

Les logiciels, bibiothèques et outils suivants sont nécessaires pour le déploiment de l'application ``EpiSearch`` sur votre environnement
- Windows >= 8 ou Linux >= 16
- RAM 4Go ou plus
- La JRE >= 11
- Apache Maven >= 3
- Spring core et compagnies (Automatiquement installés lors du déploiment)
- PostgreSQL >= 11
- Omp4j http://www.omp4j.org/
- Git >= 2

[Note] Après installation, ajoutez les chemins d'exécution dans la variable d'environnement path de votre système pour ``JRE``, ``Maven``, ``Git`` et ``PostgreSQL``.
Suivez ce guide pour voir comment ajouter un chemin dans le path: https://www.java.com/fr/download/help/path.xml 

[Note] La dépendance ``Omp4j`` mentionné dans le fichier ``pom.xml`` du projet est absente sur les dépôts en ligne. Il est nécessaire de l'installer sur votre dépôt local.
Pour voir comment l'ajouter à votre dépôt local, suivez l'exemple suivant

#### Ajouter la dépendance ``Omp4j`` sur votre dépôt Maven local
- Téléchargez le fichier ``omp4j-1.2_2.jar`` à l'adresse http://www.omp4j.org/download
- Assurez-vous d'être toujours connecté à internet
- Placez le jar téléchargé dans un dossier, par exemple ``C:\dependances``
- Déplacez-vous dans ce dossier et tapez la commande suivante
```
mvn install:install-file -Dfile=omp4j-1.2_2.jar -DgroupId=org.omp4j -DartifactId=abego -Dversion=1.2_2 -Dpackaging=jar
```
- Attendez que Maven télécharge les dépendances supplémentaires et finalise l'installation de ``Omp4j`` sur votre dépôt local
- Si tout se termine normalement, vous verrez un message ``Success`` dans la console 
- Vous pouvez continuer la suite de l'installation


### Installation

[Note] Ne poursuivez l'installation de l'application que si les outils mentionnés plus haut fonctionnent correctement.

- Installé le SGBD PostgreSQL et créez une base de données vide en vous servant des paramètres fournis dans le fichier ``application.properties`` situé dans ``src\main\resources``

Avant de continuer, rassurez-vous que la connexion à la base de données fonctionne correctement

Placez-vous dans un repertoire de votre choix (exemple ``C:\serveur``) et clonez le projet

``` 
git clone https://github.com/jerome-Dh/semantic-search.git
```

- Le dossier ``semantic-search`` créé juste après le clonage à partir de GitHub contient des ressources nécessaire pour l'installation du moteur de recherche dans votre environnement
- Dans ce dossier vous verrez plusieurs autres sous-dossiers et fichiers tels que ``README.md``, ``src``, ...
- rassurez-vous d'être connecté à internet
- Rendez-vous dans le dossier ``semantic-search`` lancer l'installation des dépendances en double-cliquant sur le fichier ``launch.cmd``
Ce fichier exécute simplement la commande ``mvn spring-boot:run`` qui permet d'installer toutes les dépendances dont l'application a besoin et démarrer le serveur automatiquement.
- Attendez que l'installation des dépendances termine, celà prendra plus de 5 minutes et dépendra de votre débit de connexion internet
- Si tout se termine bien vous verrez le message ci-dessous apparaitre dans votre console

![success-build](https://github.com/jerome-Dh/semantic-search/blob/master/capture/c3.JPG)

- C'est la preuve que l'installation et le démarrage ont bien fonctionné, Vous pouvez passer à la phase des tests de votre application


## Lancer les tests

A ce stade, vous dévriez avoir un serveur Java qui tourne sur votre machine et exécute l'application EpiSearch, si tel n'est pas le cas rendez-vous dans la section précédente pour l'installer
- Ouvrez votre navigateur et lancez l'url d'adresse http://localhost:8080/
Vous dévriez normalement voir la page s'afficher comme la prémière capture en haut de cette page

- Alors tout fonctionne parfaitement, utiliser les liens situés dans le pied de page pour naviguer vers d'autres pages.

### Naviguez dans l'application

- EpiSearch contient des pages qui permettent une recherche simple et une navigation fluide
* Taper un ou plusieurs mot-clés de recherche
* Affichage des résulats avec lien pour Wikipedia
* Le temps de réponse est fourni pour donner à l'utilisateur une estimation de l'efficacité de la vitesse fournie par le parallélisme
* Il dispose d'un menu permettant à son utilisateur de changer la langue d'affichage des résulats
* Des pages de connexion et d'inscription sont fournies pour permettre aux utilisateurs de conserver leurs préférences
En navigant sur EpiSearch, vous verrez plein d'autres gadgets qui vous faciliterons les récherches pour fournir des résulats plus pertinents

### EpiSearch est simple d'utilisation
- Effectuer des recherche sur DBPedia ou Epicam en utilisant le menu déroulant pour changer de direction
- Changer la langue d'affichage des réponses (Dans le pied de page)
- La création de compte et la connexion sont simples et prenenent maxi 2 minutes
- La page d'aide fournie de plus amples informations à tout utilisateur désirant obtenir des réponses bréves
- En fin, l'application dispose d'une page de contact permettant de joindre les développeurs pour leurs soumettre toute question de nature technique ou autres.

### Organisation des dossiers

Dans le dossier ``semantic-search``, vous verrez l' organisation définie comme suite

![organisation-folders](https://github.com/jerome-Dh/semantic-search/blob/master/capture/c4.JPG)

- Le dossier ``src`` contient le code sources, les tests unitaires et les ontologies
- les dossiers ``target`` et ``.mvn`` sont utilisés par l'application pour gérer ses dépendances
- le dossier ``capture`` contient des images de présentation
- Tous les autres fichiers situés à la racine sont nécessaires pour configurer et démarrer l'application

## Construite avec
- Linux / Windows
- Le framework Spring
- Notepad++
- JDK
- PostgreSQL
- Apache Maven
- Git
- Omp4j


## Contribuants


### Auteurs

*** Travail initial : Jerome Dh
- [GitHub](https://github.com/jerome-Dh)
- [LinkedIn](https://www.linkedin.com/in/jerome-dh)

### Contributeurs
Les contributeurs de ce projet sont des étudiants de l'université de Yaoundé I.
Il s'agit principalement de

- ![Jerome Dh](https://github.com/jerome-Dh)
- Mekofet Cyrille
- Serges Mutlen
- Mbouna Patrick

Ils ont mis sur pied la prémière architecture et la version de base exploitable du moteur de recherche en fournissant du code source et les ontologies utilisées.


## Licence

Ce projet est sous licence MIT - voir le fichier [MIT license](https://opensource.org/licenses/MIT).

## Remerciements

* A toute personne dont le code a été utilisé
* A tous les contributeurs
