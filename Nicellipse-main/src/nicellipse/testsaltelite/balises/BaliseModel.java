package nicellipse.testsaltelite.balises;

import java.util.Random;
import nicellipse.testsaltelite.announcer.Announcer;

public class BaliseModel {

    private int x;
    private int y;
    private int garbage;
    private int maxWidth;
    private int maxHeight;
    private Announcer announcer;
    private boolean movingRight = true;
    private boolean movingDown = true;
    private Random random;
    private double currentAngle = 0.0;
    private int moveType; // 0 = horizontal, 1 = vertical, 2 = zigzag
    private int amplitudeMouvement ;

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
    }

    void register(Object o) {
        this.announcer.register(o, MoveBaliseEvent.class);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void collectGarbage() {
        this.garbage += 1;
    }

    public void move() {
        if (this.garbage < 100) {
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
            // Mouvement vers la surface (y = 0) si la mémoire est pleine
            moveToSurface();
        }

        // Notifier l'annonceur de l'événement de mouvement
        this.announcer.announce(new MoveBaliseEvent(this));
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
    }

    private void moveToSurface() {
        // Mouvement vers y = 0 lorsque la mémoire est pleine
        if (this.y > 0) {
            this.y -= 1;
        }
    }
}
