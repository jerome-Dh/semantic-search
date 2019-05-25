# EpiSearch

## Recherche sémantique sur les maladies et médicaments sur DBPedia et Epicam

![accueil](https://github.com/jerome-Dh/semantic-search/blob/master/capture/c1.JPG)

EpiSearch est un moteur de reherche sémantique qui utilise les ontologie d'Epicam et ceux de DBPedia pour offrir les informations à l'utilisateur concernant les maladies, les médicaments et les patients atteints de certains symtômes


## Commencer

EpiSearch exploite le parallélisme afin d'offrir une vitesse de traitement plus conviviale à son utilisateur.

Il est conçu en exploitant les outils les plus performants et des technologies hautement à jour pour améléorer les résulats des recherches
Avant d'exploiter EpiSearch, il est nécessaire d'avoir un environnement prêt à l'acceuillir


### Conditions préalables

Il faut disposer d'une machine serveur capable d'exploiter le parallélisme offert par l'application afin d'en bénéficier d'une bonne performance.
Les outils logiciels peuvent être installés séparement mais il est conseillé d'utiliser ceux qui sont fournis avec l'application car celà constitue déjà un environnement préconfiguré et gère les dépendances nécessaires.

Les logiciels et outils suivants sont nécessaires pour son déploiment
- Windows version >= 8 ou Linux version >= 16
- RAM 4Go ou plus
- La JRE >= 11
- Apache Maven >= 3
- Spring core et compagnies (Automatiquement installés lors du déploiment)
- PostgreSQL >= 11
- Git >= 2


### Installation

L'installation fait suite à l'installation des outils nécessaires, s'ils sont déjà complets, alors vous pouvez commencer

- Installé le SGBD PostgreSQL et créez une base de données vide en vous servant des paramètres fournis dans le fichier ``application.properties``

Avant de continuer, rassurer vous que la connexion à la base de données fonctionne

Placer vous dans un repertoire de votre choix (de préference créez un repertoire avec le chemin ``C:\serveur``) et ensuite clonez le projet

``` git clone https://github.com/jerome-Dh/semantic-search.git```

- Déplacez-vous dans le dossier nommé ``semantic-search``, c'est le dossier créé juste après le clonage à partir de GitHub et contenant les ressources nécessaire pour l'installation du moteur de recherche sur votre environnement
- Dans ce dossier nommé ``semantic-search``, vous verrez plusieurs autres dossiers et fichiers tels que ``README.md``, ``src``, ...
- Maintenant connectez-vous à internet pour la suite
- Si tout fonctionne jusqu'ici, rendez-vous dans le dossier ``semantic-search`` précédent et lancer l'installation des dépendances en double-cliquant sur le fichier "launch.cmd", il s'agit d'un fichier d'automatisation de l'installation des dépendances.
- Attendez que l'installation des dépendances termine, celà prendra plus de 5 minutes et dépendra de votre débit de connexion internet
- Une fois le téléchargement des dépendances terminé, le serveur d'application démarrera automatiquement et ensuite vous verrez le message ci-dessous apparaitre dans votre console

![success-build](https://github.com/jerome-Dh/semantic-search/blob/master/capture/c3.JPG)

- C'est la preuve que l'installation et le démarrage ont bien fonctionné, Vous pouvez passer à la phase des tests


## Lancer les tests

A ce stade, vous dévriez avoir un serveur Java qui tourne sur votre machine et exécute l'application EpiSearch, si tel n'est pas le cas rendez-vous dans la section précédente pour l'installer
- Ouvrez votre navigateur et lancez l'url d'adresse http://localhost:8080/
Vous dévriez normalement voir la page s'afficher comme la prémière capture en haut de cette page

- Alors tout fonctionne parfaitement, utiliser les liens situés dans le pied de page pour vous rendre dans les pages d'aide, de confidentialités et autres.

### Naviguez dans l'application

- EpiSearch contient des pages qui permettent une navigation fluide
* Taper un ou plusieurs mot-clés  de recherche
* Affichage des résulats avec lien pour Wikipedia
* Le temps de réponse est fourni pour donner à l'utilisateur une estimation de l'efficacité de la vitesse fournie par le parallélisme
* Il dispose d'un menu permettant à son utilisateur de changer la langue d'affichage des résulats
* Des pages de connexion et d'inscription sont fournies pour permettre aux utilisateurs de sauvegarder leurs préférences
En navigant sur EpiSearch, vous apprendrez plein d'autres gadgets qui vous faciliterons les récherches et fournirons des résulats plus pertinents

### EpiSearch est simple d'utilisation
- Effectuer des recherche sur DBPedia ou Epicam en utilisant simplement le menu déroulant pour changer de direction
- Changer la langue d'affichage des réponses sur le pied de page
- La création de compte et la connexion sont simples et prenenent maxi 2 minutes
- La page d'aide fournie de plus amples informations à tout utilisateur désirant obtenir des réponses bréves
- En fin, l'application dispose d'une page de contact permettant de joindre les développeurs pour leurs soumettre toute question de nature technique ou autres.

### Organisation des dossiers

Après la création du dossier  "semantic-search", vous verrez l' organisation définie comme suite

![organisation-folders](https://github.com/jerome-Dh/semantic-search/blob/master/capture/c4.JPG)

- Le dossier ``sr`` contient le code sources et des tests
- les dossiers ``target`` et ``.mvn`` sont utilisés par l'application pour gérer ses dépendances
- le dossier ``capture`` contient des images de présentation
- Les fichiers situés à la racine sont nécessaires pour configurer et démarrer l'application

## Construite avec
- Linux / Windows
- Le framework Spring
- Notepad++
- JDK
- PostgreSQL
- Apache Maven
- Git


## Contribuants


### Auteurs

*** Travail initial : Jerome Dh
- [GitHub](https://github.com/jerome-Dh)
- [LinkedIn](https://www.linkedin.com/in/jerome-dh)

### Contributeurs
Les contributeurs de ce projet sont des étudiants de l'université de Yaoundé I.
Il s'agit principalement de

- Jerome Dh
- Mekofet Cyrille
- Serges Mutlen
- Mbouna Patrick

Ils ont mis sur pied la prémière architecture et la version de base exploitable du moteur de recherche en forunissant du code source et les ontologies utilisées.


## Licence

Ce projet est sous licence MIT - voir le fichier [MIT license](https://opensource.org/licenses/MIT).

## Remerciements

* A toute personne dont le code a été utilisé
* A tous les contributeurs
