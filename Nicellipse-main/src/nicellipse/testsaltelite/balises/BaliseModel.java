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
    private int moveType; // 0 = horizontal, 1 = vertical, 2 = zigzag

    BaliseModel(int x, int y, int garbage, int maxWidth, int maxHeight) {
        this.x = x;
        this.y = y;
        this.garbage = garbage;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.announcer = new Announcer();
        this.random = new Random();
        this.moveType = random.nextInt(3); // Type de mouvement fixé une fois : 0: Horizontal, 1: Vertical, 2: Zigzag
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
            if (this.x < this.maxWidth) {
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
            if (this.y < this.maxHeight) {
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

        // Mouvement sinusoïdal léger en Y
        // Utiliser un angle pour créer une oscillation verticale (sinus)
        double angle = (double) this.x / 50;  // Ajuste la vitesse de l'oscillation
        this.y = (int) (maxHeight / 2 + 30 * Math.sin(angle)); // 30 est l'amplitude du mouvement vertical

        // Inverser la direction horizontale uniquement si on atteint les bords horizontaux
        if (this.x >= this.maxWidth || this.x <= 0) {
            movingRight = !movingRight;
        }
    }



    private void moveToSurface() {
        // Mouvement vers y = 0 lorsque la mémoire est pleine
        if (this.y > 0) {
            this.y -= 1;
        }
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
        this.announcer.announce(new MoveBaliseEvent(this));
    }
}
