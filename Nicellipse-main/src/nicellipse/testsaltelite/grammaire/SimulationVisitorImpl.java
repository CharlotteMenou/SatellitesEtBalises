package nicellipse.testsaltelite.grammaire;


import java.util.HashMap;

import nicellipse.testsaltelite.grammaire.SimulationBaseVisitor;
import nicellipse.testsaltelite.sattelite.SatteliteModel;
import nicellipse.testsaltelite.balises.BaliseModel;
import java.util.Map;

public class SimulationVisitorImpl extends SimulationBaseVisitor<Void> {
    private Map<String, Object> variables = new HashMap<>();

    @Override
    public Void visitAssignment(SimulationParser.AssignmentContext ctx) {
        String variableName = ctx.IDENTIFIER().getText();
        String className = ctx.className().getText();

        // Vérification des paramètres
        if (ctx.params() != null && ctx.params().param(0) != null) {
            String paramName = ctx.params().param(0).IDENTIFIER().getText();
            String paramValue = ctx.params().param(0).expr().getText();
            System.out.println("Création de " + variableName + " de type " + className + " avec le paramètre " + paramName + " = " + paramValue);
        } else {
            System.out.println("Création de " + variableName + " de type " + className + " sans paramètres.");
        }
        return null;
    }


    @Override
    public Void visitMethodCall(SimulationParser.MethodCallContext ctx) {
        String varName = ctx.IDENTIFIER(0).getText();
        String methodName = ctx.IDENTIFIER(1).getText();
        Object obj = variables.get(varName);

        if (obj instanceof SatteliteModel) {
            SatteliteModel sat = (SatteliteModel) obj;
            if (methodName.equals("configureToMoveToRight")) {
                sat.configureToMoveToRight();
            } else if (methodName.equals("start")) {
                // Logique pour démarrer le satellite
            }
        } else if (obj instanceof BaliseModel) {
            BaliseModel balise = (BaliseModel) obj;
            if (methodName.equals("start")) {
                // Logique pour démarrer la balise
            }
        }
        return null;
    }
}