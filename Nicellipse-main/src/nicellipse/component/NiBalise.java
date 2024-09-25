package nicellipse.component;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

public class NiBalise extends JPanel implements NiBorderedComponent {
    private static final long serialVersionUID = 128422045550852289L;

    public int tailleMax ;
    public ArrayList<String> memoire = null;
    public boolean memoireFull = false;

    public NiBalise(int tailleMax) {
        this.defaultSetup();
        this.setDimension(new Dimension(20,20));
        this.setLayout(null);
        this.memoire = new ArrayList<>();
        this.tailleMax=tailleMax;
    }

    public boolean isFull(){
        return memoireFull;
    }
    public void setStatutMemoire (boolean statut){
        this.memoireFull = statut ;
    }
    public ArrayList<String> getMemoire(){
        return this.getMemoire();
    }
    public void addToMemoire(String donnees){
        this.memoire.add(donnees);
    }
}
