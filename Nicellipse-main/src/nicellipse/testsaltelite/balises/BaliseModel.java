package nicellipse.testsaltelite.balises;

import java.util.Random;

import nicellipse.testsaltelite.announcer.Announcer;

public class BaliseModel {

    // propriétés du model
    private int x;
    private int y;
    private int garbage;
    private final int maxWidth;
    private final int maxHeight;
    // Announcer
    private final Announcer announcer;
    // Random
    private final Random random;
    // gestion de la mémoire
    private final int garbageLimit;
    private boolean isGivingGarbage = false;
    // Variables de mouvement
    private boolean movingRight = true;
    private boolean movingDown = true;
    private double currentAngle = 0.0;
    private int moveType; // 0 = horizontal, 1 = vertical, 2 = zigzag
    private int amplitudeMouvement ;
    private int range = 0;
    private boolean isFree = true;
    private boolean isOnTop = false ;

    public BaliseModel(int x, int y, int garbage, int maxWidth, int maxHeight) {
        this.x = x;
        this.y = y;
        this.garbage = garbage;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.announcer = new Announcer();
        this.random = new Random();
        this.moveType = random.nextInt(3); // Type de mouvement fixé une fois : 0: Horizontal, 1: Vertical, 2: Zigzag
        this.amplitudeMouvement = random.nextInt(50);

        this.garbageLimit = random.nextInt(100, 150); // Limite de mémoire aléatoire entre 450 et 500
    }

    void register(Object o) {
        this.announcer.register(o, MoveBaliseEvent.class);
    }

    // add the register method
    public void registerSattelite(Object o) {
        this.announcer.register(o, ListenToSatteliteEvent.class);
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isFree() {
        return this.isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }
    public boolean isOnTop() {
        return this.isOnTop;
    }

    public void collectGarbage() {
        // add a random number between 0 and 2 to garbage
        this.garbage += random.nextInt(3);
    }
    //
    private boolean isReturningToPosition = false;

    public void move() {
        if (this.garbage < this.garbageLimit && this.y > 0 && !this.isGivingGarbage && !isReturningToPosition) {
            // Exécuter le type de mouvement fixé
            switch (this.moveType) {
                case 0:
                    moveHorizontal();
                    break;
                case 1:
                    moveVertical();
                    break;
                case 2:
                    moveZigzag();
                    break;
            }
        } else {
            if (!this.isGivingGarbage && !isReturningToPosition) {
                moveToSurface();
            } else if (this.isGivingGarbage && !isReturningToPosition) {
                consumeGarbage();
            } else if (garbage == 0 ) {
                backToRandomPosition();
            } else {
                System.out.println("Not supposed to be here");
            }
        }

        // Notifier l'annonceur de l'événement de mouvement
        this.announcer.announce(new MoveBaliseEvent(this));
        // announce the movement to sattelites
        this.announcer.announce(new ListenToSatteliteEvent(this));
    }

    private void moveToSurface() {
        if (this.y > 0) {
            this.y -= 1;
            this.isGivingGarbage = false;
        } else {
            isReturningToPosition = false;
            this.isOnTop = true;
            // Initialiser range dès que la balise atteint la surface
            if (this.range == 0) {
                defineRange();
            }
        }
    }

    private void defineRange() {
        this.range = 600;  // Définir une plage aléatoire entre 100 et 150
        //System.out.println("New range :" + this.range);
        this.isGivingGarbage = true;
        this.isReturningToPosition = false;

    }


    private void consumeGarbage() {
        // if i am free i wait to be connected to a sattelite
        if (this.isFree) {
            return;
        }
        if (this.garbage > 0) {
            this.garbage = 0; // Consommer les déchets
            this.isReturningToPosition = false;
        } else {
            this.isGivingGarbage = false;
            this.isReturningToPosition = true; // Activer le retour à la position
        }
    }

    private void backToRandomPosition() {
        //System.out.println("backToRandomPosition");

        //System.out.println("New this.range  "+ this.range);
        if (this.range > 0 && isReturningToPosition) {
            this.range -= 10;
            //System.out.println("New this.range  "+ this.range);
            if (this.range % 2 == 0) {
                this.y += 1;
                this.isOnTop = false;
                //System.out.println("New y "+ this.y);
            }
        } else {
            // Réinitialiser les paramètres de mouvement
            isReturningToPosition = false;
            this.moveType = random.nextInt(3); // Changer le type de mouvement après le retour
            //System.out.println("New random position: " + this.y);
        }
    }


    private void moveHorizontal() {
        if (movingRight) {
            if (this.x < this.maxWidth - 20) {
                this.x += 1;
            } else {
                movingRight = false;
                this.x -= 1;
            }
        } else {
            if (this.x > 0) {
                this.x -= 1;
            } else {
                movingRight = true;
                this.x += 1;
            }
        }
        // collect garbage
        collectGarbage();
    }

    private void moveVertical() {
        if (movingDown) {
            if (this.y < this.maxHeight - 20) {
                this.y += 1;
            } else {
                movingDown = false;
                this.y -= 1;
            }
        } else {
            if (this.y > 0) {
                this.y -= 1;
            } else {
                movingDown = true;
                this.y += 1;
            }
        }
        // collect garbage
        collectGarbage();
    }

    private void moveZigzag() {
        // Mouvement horizontal dans une seule direction
        if (movingRight) {
            this.x += 1; // Avance vers la droite
        } else {
            this.x -= 1; // Avance vers la gauche
        }

        // Incrément de l'angle pour la progression continue de la sinusoïde
        currentAngle += 0.1;  // Ajustez l'incrément pour contrôler la vitesse de l'oscillation verticale


        // Calcul de la position en Y en utilisant une sinusoïde continue
        this.y = (int) (maxHeight / 2 + amplitudeMouvement * Math.sin(currentAngle)); // 10 est l'amplitude

        // Inverser la direction horizontale uniquement si on atteint les bords horizontaux
        if (this.x >= this.maxWidth - 20 || this.x <= 0) {
            movingRight = !movingRight;
        }
        // collect garbage
        collectGarbage();
    }
}
