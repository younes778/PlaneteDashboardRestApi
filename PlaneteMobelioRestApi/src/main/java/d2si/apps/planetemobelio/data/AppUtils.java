package d2si.apps.planetemobelio.data;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * Class that contains static values used to request database
 *
 */
public class AppUtils {
	// Data base administration
	final public static int DB_SERVER_PORT = 1433;
	final public static String APP_LOGGER = "PlaneteDashboard";

	// Url fields
	final public static String FIELD_URL = "url";
	final public static String FIELD_DB_NAME = "dbName";
	final public static String FIELD_DB_USER = "dbUser";
	final public static String FIELD_DB_PASSWORD = "dbPassword";
	final public static String FIELD_DATE_FROM = "dateFrom";
	final public static String FIELD_DATE_TO = "dateTo";
	final public static String FIELD_USER = "user";
	final public static String FIELD_PASSWORD = "password";
	
	// User type
	final public static int USER_ERROR = -1;
	final public static int USER_ADMIN = 0;
	final public static int USER_COMMERCIAL = 1;
	final public static int USER_LIVREUR = 2;
	final public static int USER_SAV = 3;
	

	// Data base columns
	
	// User
	final public static String COLUMN_USER_ROLE_ID = "rol_id";
	
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

	final private static String strKey = "RGBRGB";

	public static String decrypt(String encryptedText) throws Exception {

		byte[] message = Base64.decodeBase64(encryptedText.getBytes("utf-8"));

		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digestOfPassword = md.digest(strKey.getBytes("utf-8"));
		byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		 for (int j = 0,  k = 16; j < 8;)
         {
             keyBytes[k++] = keyBytes[j++];
         }
		IvParameterSpec param = new IvParameterSpec(new byte[8]);
		SecretKey key = new SecretKeySpec(keyBytes, "DESede");

		Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, key,param);

		byte[] plainText = decipher.doFinal(message);

		return new String(plainText, "UTF-8");

	}

}
