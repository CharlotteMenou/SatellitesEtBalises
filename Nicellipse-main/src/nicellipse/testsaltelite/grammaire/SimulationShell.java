package nicellipse.testsaltelite.grammaire;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class SimulationShell {
    public static void main(String[] args) throws Exception {
        // Script de simulation exemple
        String script = """
            sat1 := new Satellite(hauteur = 2000);
            sat1.start();
            b1 := new Balise(deplacement = horizontal, profondeur = 200);
            b1.start();
        """;

        // Charger le script dans ANTLR
        CharStream input = CharStreams.fromString(script);
        SimulationLexer lexer = new SimulationLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimulationParser parser = new SimulationParser(tokens);

        // Commencer l'analyse de la règle de base, par exemple 'program' ou 'command'
        ParseTree tree = parser.script();  // 'program' est à remplacer par le nom de votre règle de départ
        System.out.println(tree.toStringTree(parser));  // Affiche l'arbre syntaxique

        // Utiliser un visiteur pour interpréter les commandes
        SimulationVisitor visitor = new SimulationVisitorImpl();
        visitor.visit(tree);  // Exécute les actions définies dans votre visiteur
    }
}

