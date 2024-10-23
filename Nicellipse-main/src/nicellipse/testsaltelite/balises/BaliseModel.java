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
    private int moveDuration; // Duration for which a movement type is maintained
    private int currentMoveTime; // Counter for the current move duration

    BaliseModel(int x, int y, int garbage, int maxWidth, int maxHeight) {
        this.x = x;
        this.y = y;
        this.garbage = garbage;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.announcer = new Announcer();
        this.random = new Random();
        this.moveType = random.nextInt(3); // Initial random movement type
        this.moveDuration = random.nextInt(50) + 500; // Random duration between 50 and 100 cycles
        this.currentMoveTime = 0; // Initial cycle count
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
            // Vérifier si la durée actuelle de mouvement est terminée
            if (currentMoveTime >= moveDuration) {
                // Changer de type de mouvement aléatoirement après la période
                this.moveType = random.nextInt(3); // 0: Horizontal, 1: Vertical, 2: Zigzag
                this.moveDuration = random.nextInt(50) + 50; // Nouvelle durée aléatoire entre 50 et 100
                currentMoveTime = 0; // Réinitialiser le compteur de temps
            } else {
                currentMoveTime++; // Incrémenter le compteur de temps
            }

            // Exécuter le type de mouvement courant
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
        if (movingRight) {
            this.x += 1;
        } else {
            this.x -= 1;
        }

        if (movingDown) {
            this.y += 1;
        } else {
            this.y -= 1;
        }

        // Inverser la direction quand on atteint les limites
        if (this.x >= this.maxWidth || this.x <= 0) {
            movingRight = !movingRight;
        }
        if (this.y >= this.maxHeight || this.y <= 0) {
            movingDown = !movingDown;
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