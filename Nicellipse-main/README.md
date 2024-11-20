# Documentation des commandes du terminal

## Commandes disponibles

### Création d'objets

Pour créer un nouveau satellite :
```
bal := new Satellite(x,y)
```
Où `x` et `y` sont les coordonnées de position initiales du satellite.

Pour créer une nouvelle balise :
```
sat := new Balise(x,y)
```
Où `x` et `y` sont les coordonnées de position initiales de la balise.

### Contrôle des objets

Pour démarrer un objet (satellite ou balise) :
```
[nom_objet].start()
```
Exemple : `bal.start()` ou `sat.start()`

Pour arrêter un objet (satellite ou balise) :
```
[nom_objet].stop()
```
Exemple : `bal.stop()` ou `sat.stop()`

### Changement type déplacement
```
bal.changeDeplacement("hor")
bal.changeDeplacement("vert")
bal.changeDeplacement("sin")
```


## Notes
- Les commandes doivent être entrées une à la fois
- Chaque commande est validée par la touche Entrée
- Le système affiche "En attente d'une commande : " avant chaque nouvelle saisie
- Les objets créés persistent jusqu'à la fin de la session
