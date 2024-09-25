package nicellipse.component;

import java.awt.Dimension;
import java.util.ArrayList;


public class NiBalise extends NiEllipse {
    private static final long serialVersionUID = 128422045550852289L;

    public int tailleMax ;
    public ArrayList<String> memoire = null;
    public boolean memoireFull = false;
    public String name ;

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
    public void addToMemoire(String donnees){
        this.memoire.add(donnees);
        if(this.memoire.size() == tailleMax){
            this.memoireFull = true;
        }
    }
    public void transfertMemoire(){
        this.memoire = new ArrayList<>();
        this.memoireFull = false;
    }
}
