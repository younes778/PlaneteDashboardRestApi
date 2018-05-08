package d2si.apps.planetedashboard.data;

/**
 * Class that represents the representant
 *
 */
public class Representant {
	private String rep_code;
	private String rep_nom;
	private String rep_prenom;

	/**
	 * Representant constructor
	 *
	 * @param rep_code
	 *            representant Id
	 * @param rep_nom
	 *            representant name
	 * @param rep_prenom
	 *            representant surname
	 */
	public Representant(String rep_code, String rep_nom, String rep_prenom) {
		super();
		this.rep_code = rep_code;
		this.rep_nom = rep_nom;
		this.rep_prenom = rep_prenom;
	}

	// getters and setters
	public String getRep_code() {
		return rep_code;
	}

	public void setRep_code(String rep_code) {
		this.rep_code = rep_code;
	}

	public String getRep_nom() {
		return rep_nom;
	}

	public void setRep_nom(String rep_nom) {
		this.rep_nom = rep_nom;
	}

	public String getRep_prenom() {
		return rep_prenom;
	}

	public void setRep_prenom(String rep_prenom) {
		this.rep_prenom = rep_prenom;
	}

}
