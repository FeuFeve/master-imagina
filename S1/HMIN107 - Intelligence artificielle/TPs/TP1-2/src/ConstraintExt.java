import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Pour manipuler des contraintes en extension
 *
 */
public class ConstraintExt extends Constraint{
	
	private ArrayList<ArrayList<Object>> tuples;	// ensemble des tuples de la contrainte
	
	/**
	 * Construit une contrainte d'extension vide à partir
	 * d'une liste de variables
	 * 
	 * @param var la liste de variables
	 */
	public ConstraintExt(ArrayList<String> var) {
		super(var);
		tuples = new ArrayList<ArrayList<Object>>();
	}
	
	/**
	 * Construit une contrainte d'extension vide à partir
	 * d'une liste de variables et dun nom
	 * 
	 * @param var la liste de variables
	 * @param name son nom
	 */
	public ConstraintExt(ArrayList<String> var, String name) {
		super(var,name);
		tuples = new ArrayList<ArrayList<Object>>();
	}
	
	/**
	 * Construit une contrainte en extension à partir d'une représentation
	 * textuelle de la contrainte. La liste de variables est donnée sous la forme : Var1;Var2;...
	 * Puis le nombre de tuples est indiqué et enfin chaque tupe est donné sous la forme d'une
	 * suite de valeurs "String" : Val1;Val2;...
	 * Aucune vérification n'est prévue si la syntaxe n'est pas respectée !!
	 * 
	 * @param in le buffer de lecture de la représentation textuelle de la contrainte
	 * @throws Exception en cas d'erreur de format
	 */
	public ConstraintExt(BufferedReader in) throws Exception{
		super(in);
		tuples = new ArrayList<ArrayList<Object>>();
		int nbTuples = Integer.parseInt(in.readLine());		// nombre de tuples de valeurs
		for(int j=1;j<=nbTuples;j++) {
			ArrayList<Object> tuple = new ArrayList<Object>();
			for (String v : in.readLine().split(";")) tuple.add(v);	// Val1;Val2;...;Val(arity)
			if(tuple.size() != varList.size()) System.err.println("Le tuple " + tuple + " n'a pas l'arité " + varList.size() + " de la contrainte " + name);
			else if(!tuples.add(tuple)) System.err.println("Le tuple " + tuple + " est déjà présent dans la contrainte "+ name);
		}
	}
	
	/**
	 * Ajoute un tuple de valeur à la contrainte
	 * 
	 * @param valTuple le tuple à ajouter
	 */
	public void addTuple(ArrayList<Object> valTuple) {
		if(valTuple.size() != varList.size()) System.err.println("Le tuple " + valTuple + " n'a pas l'arité " + varList.size() + " de la contrainte " + name);
		else if(!tuples.add(valTuple)) System.err.println("Le tuple " + valTuple + " est déjà présent dans la contrainte "+ name);
	}
	

	/**
	 * Checks whether the given Assignment violates this constraint
	 * @param a the current assignment
	 * @return true if there is a violation, false if there isn't
	 */
	public boolean violation(Assignation a) {
		ArrayList<String> aVars = a.getVars(); //store the assignment's and the constraint's variables in separate arrays
		ArrayList<String> cVars = this.getVars();
		for(ArrayList<Object> tuple : this.tuples){ //cycle through all the constraint's tuples, which are a list of values in order
			boolean validTuple = true;
			for(String variable : aVars){ //cycles through all the assignment's variables
				if(cVars.contains(variable)){ //if the constraint includes this variable then check that the assignment for this variable corresponds to the value for this variable in the current tuple
					if(!((a.get(variable)).equals(tuple.get(cVars.indexOf(variable))))){ //if it doesn't, then move on to the next tuple and start again
						validTuple = false;
					}
				}
				if(!validTuple) break;
			}
			if(validTuple) return false; //if we went through the current tuple without finding discrepancies then the assignment doesn't violate the constraint
		}
		//System.out.println(a + " violates " + this);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see Constraint#toString()
	 */
	public String toString() {
		return "\n\t Ext "+ super.toString() + " : " + tuples; 
	}


}
