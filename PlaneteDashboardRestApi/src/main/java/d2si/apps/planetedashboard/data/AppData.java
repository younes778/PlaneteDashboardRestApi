package d2si.apps.planetedashboard.data;

/**
 * Class that contains static values used to request database
 *
 */
public class AppData {
	// Data base administration
	final public static int DB_SERVER_PORT = 1433;
	final public static String DB_USER = "sa";
	final public static String DB_PASSWORD = "dssi213dz";
	final public static String APP_LOGGER = "PlaneteDashboard";

	// Url fields
	final public static String FIELD_URL = "url";
	final public static String FIELD_DB_NAME = "dbName";
	final public static String FIELD_DATE_FROM = "dateFrom";
	final public static String FIELD_DATE_TO = "dateTo";
	final public static String FIELD_DB_USER = "user";
	final public static String FIELD_DB_PASSWORD = "password";

	// Data base columns

	// Document
	final public static String COLUMN_DOC_NUMERO = "doc_numero";
	final public static String COLUMN_DOC_DATE = "doc_date";

	// Personnel
	final public static String COLUMN_REP_CODE = "rep_code";
	final public static String COLUMN_REP_NOM = "rep_nom";
	final public static String COLUMN_REP_PRENOM = "rep_prenom";

	// Tiers
	final public static String COLUMN_PCF_CODE = "pcf_code";
	final public static String COLUMN_PCF_TYPE = "pcf_type";
	final public static String COLUMN_PCF_RS = "pcf_rs";
	final public static String COLUMN_PCF_RUE = "pcf_rue";
	final public static String COLUMN_PCF_COMP = "pcf_comp";
	final public static String COLUMN_PCF_CP = "pcf_cp";
	final public static String COLUMN_PCF_VILLE = "pcf_ville";
	final public static String COLUMN_PAY_CODE = "pay_code";
	final public static String COLUMN_PCF_TEL1 = "pcf_tel1";
	final public static String COLUMN_PCF_TEL2 = "pcf_tel2";
	final public static String COLUMN_PCF_FAX = "pcf_fax";
	final public static String COLUMN_PCF_EMAIL = "pcf_email";
	final public static String COLUMN_PCF_URL = "pcf_url";
	final public static String COLUMN_PCF_FAM = "fat_lib";

	// Lignes
	final public static String COLUMN_LIG_NUMERO = "lig_numero";
	final public static String COLUMN_LIG_QTE = "lig_qte";
	final public static String COLUMN_LIG_P_NET = "lig_p_net";

	// Article
	final public static String COLUMN_ART_CODE = "art_code";
	final public static String COLUMN_ART_LIB = "art_lib";
	final public static String COLUMN_ART_FAM = "far_lib";

}
