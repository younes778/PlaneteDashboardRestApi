package d2si.apps.planetedashboard.data;

/**
 * Class that represents the tiers clients, prospects and providers
 *
 */
public class Tiers {
	private String pcf_code;
	private String pcf_type;
	private String pcf_rs;
	private String pcf_rue;
	private String pcf_comp;
	private String pcf_cp;
	private String pcf_ville;
	private String pay_code;
	private String pcf_tel1;
	private String pcf_tel2;
	private String pcf_fax;
	private String pcf_email;
	private String pcf_url;
	private String fat_lib;

	/**
	 * Tiers constructor
	 *
	 * @param pcf_code
	 *            tiers Id
	 * @param pcf_type
	 *            tiers type ( C: client, F: provider, P: prospects)
	 * @param fat_lib
	 *            representant surname
	 */

	public Tiers(String pcf_code, String pcf_type, String pcf_rs, String pcf_rue, String pcf_comp, String pcf_cp,
			String pcf_ville, String pay_code, String pcf_tel1, String pcf_tel2, String pcf_fax, String pcf_email,
			String pcf_url, String fat_lib) {
		super();
		this.pcf_code = pcf_code;
		this.pcf_type = pcf_type;
		this.pcf_rs = pcf_rs;
		this.pcf_rue = pcf_rue;
		this.pcf_comp = pcf_comp;
		this.pcf_cp = pcf_cp;
		this.pcf_ville = pcf_ville;
		this.pay_code = pay_code;
		this.pcf_tel1 = pcf_tel1;
		this.pcf_tel2 = pcf_tel2;
		this.pcf_fax = pcf_fax;
		this.pcf_email = pcf_email;
		this.pcf_url = pcf_url;
		this.fat_lib = fat_lib;
	}

	// getters and setters
	public String getFat_lib() {
		return fat_lib;
	}

	public void setFat_lib(String fat_lib) {
		this.fat_lib = fat_lib;
	}

	public String getPcf_code() {
		return pcf_code;
	}

	public void setPcf_code(String pcf_code) {
		this.pcf_code = pcf_code;
	}

	public String getPcf_type() {
		return pcf_type;
	}

	public void setPcf_type(String pcf_type) {
		this.pcf_type = pcf_type;
	}

	public String getPcf_rs() {
		return pcf_rs;
	}

	public void setPcf_rs(String pcf_rs) {
		this.pcf_rs = pcf_rs;
	}

	public String getPcf_rue() {
		return pcf_rue;
	}

	public void setPcf_rue(String pcf_rue) {
		this.pcf_rue = pcf_rue;
	}

	public String getPcf_comp() {
		return pcf_comp;
	}

	public void setPcf_comp(String pcf_comp) {
		this.pcf_comp = pcf_comp;
	}

	public String getPcf_cp() {
		return pcf_cp;
	}

	public void setPcf_cp(String pcf_cp) {
		this.pcf_cp = pcf_cp;
	}

	public String getPcf_ville() {
		return pcf_ville;
	}

	public void setPcf_ville(String pcf_ville) {
		this.pcf_ville = pcf_ville;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getPcf_tel1() {
		return pcf_tel1;
	}

	public void setPcf_tel1(String pcf_tel1) {
		this.pcf_tel1 = pcf_tel1;
	}

	public String getPcf_tel2() {
		return pcf_tel2;
	}

	public void setPcf_tel2(String pcf_tel2) {
		this.pcf_tel2 = pcf_tel2;
	}

	public String getPcf_fax() {
		return pcf_fax;
	}

	public void setPcf_fax(String pcf_fax) {
		this.pcf_fax = pcf_fax;
	}

	public String getPcf_email() {
		return pcf_email;
	}

	public void setPcf_email(String pcf_email) {
		this.pcf_email = pcf_email;
	}

	public String getPcf_url() {
		return pcf_url;
	}

	public void setPcf_url(String pcf_url) {
		this.pcf_url = pcf_url;
	}

}
