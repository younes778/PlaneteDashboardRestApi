package d2si.apps.planetedashboard.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import d2si.apps.planetedashboard.data.AppData;
import d2si.apps.planetedashboard.data.Tiers;

/**
 * Class that represents the tiers controller
 *
 */

@RestController
public class TiersController {
	ArrayList<Tiers> tiers;

	/**
	 * Method that get tiers from the distant server between two dates
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @param dateTo
	 *            end date
	 * @return a list of tiers from distant database between the two dates
	 */
	@RequestMapping("/tiersGet")
	public ArrayList<Tiers> get(@RequestParam(value = AppData.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppData.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppData.FIELD_DATE_FROM, defaultValue = "") String dateFrom,
			@RequestParam(value = AppData.FIELD_DATE_TO, defaultValue = "") String dateTo) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		tiers = new ArrayList<>();

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(AppData.DB_USER);
		ds.setPassword(AppData.DB_PASSWORD);
		ds.setServerName(url);
		ds.setPortNumber(AppData.DB_SERVER_PORT);
		ds.setDatabaseName(dbName);

		try {
			System.out.println("Connecting to SQL Server ... ");
			con = ds.getConnection();
			System.out.println("Connected");
			String tiers_request = " select pcf_code, pcf_type, pcf_rs, pcf_rue, pcf_comp, pcf_cp, pcf_ville, pay_code, pcf_tel1, "
					+ " pcf_tel2, pcf_fax, pcf_email, pcf_url, f.fat_lib from tiers t left join tiers_fam f on t.fat_code = f.fat_code where pcf_code in( "
					+ " select pcf_code from documents " + " where doc_type in ('V','A') and doc_date between '"
					+ dateFrom + "' and '" + dateTo + "' )";

			stmt = con.createStatement();
			rs = stmt.executeQuery(tiers_request);
			while (rs.next()) {
				String fam = rs.getString(AppData.COLUMN_PCF_FAM);
				if (fam == null)
					fam = "";
				tiers.add(new Tiers(rs.getString(AppData.COLUMN_PCF_CODE), rs.getString(AppData.COLUMN_PCF_TYPE),
						rs.getString(AppData.COLUMN_PCF_RS), rs.getString(AppData.COLUMN_PCF_RUE),
						rs.getString(AppData.COLUMN_PCF_COMP), rs.getString(AppData.COLUMN_PCF_CP),
						rs.getString(AppData.COLUMN_PCF_VILLE), rs.getString(AppData.COLUMN_PAY_CODE),
						rs.getString(AppData.COLUMN_PCF_TEL1), rs.getString(AppData.COLUMN_PCF_TEL2),
						rs.getString(AppData.COLUMN_PCF_FAX), rs.getString(AppData.COLUMN_PCF_EMAIL),
						rs.getString(AppData.COLUMN_PCF_URL), fam));
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return tiers;
	}

	/**
	 * Method that update tiers from the distant server starting from a date
	 *
	 * @param url
	 *            database server url
	 * @param dbName
	 *            database name
	 * @param dateFrom
	 *            start date
	 * @return a list of tiers from distant database starting from date
	 */

	@RequestMapping("/tiersUpdate")
	public ArrayList<Tiers> update(@RequestParam(value = AppData.FIELD_URL, defaultValue = "") String url,
			@RequestParam(value = AppData.FIELD_DB_NAME, defaultValue = "") String dbName,
			@RequestParam(value = AppData.FIELD_DATE_FROM, defaultValue = "") String dateFrom) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		tiers = new ArrayList<>();

		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(AppData.DB_USER);
		ds.setPassword(AppData.DB_PASSWORD);
		ds.setServerName(url);
		ds.setPortNumber(AppData.DB_SERVER_PORT);
		ds.setDatabaseName(dbName);

		try {
			System.out.println("Connecting to SQL Server ... ");
			con = ds.getConnection();
			System.out.println("Connected");
			String tiers_request = " select pcf_code, pcf_type, pcf_rs, pcf_rue, pcf_comp, pcf_cp, pcf_ville, pay_code, pcf_tel1, "
					+ " pcf_tel2, pcf_fax, pcf_email, pcf_url, f.fat_lib from tiers t left join tiers_fam f on t.fat_code = f.fat_code where pcf_dtmaj > ' "
					+ dateFrom + " ' ";

			stmt = con.createStatement();
			rs = stmt.executeQuery(tiers_request);
			while (rs.next()) {
				String fam = rs.getString(AppData.COLUMN_PCF_FAM);
				if (fam == null)
					fam = "";
				tiers.add(new Tiers(rs.getString(AppData.COLUMN_PCF_CODE), rs.getString(AppData.COLUMN_PCF_TYPE),
						rs.getString(AppData.COLUMN_PCF_RS), rs.getString(AppData.COLUMN_PCF_RUE),
						rs.getString(AppData.COLUMN_PCF_COMP), rs.getString(AppData.COLUMN_PCF_CP),
						rs.getString(AppData.COLUMN_PCF_VILLE), rs.getString(AppData.COLUMN_PAY_CODE),
						rs.getString(AppData.COLUMN_PCF_TEL1), rs.getString(AppData.COLUMN_PCF_TEL2),
						rs.getString(AppData.COLUMN_PCF_FAX), rs.getString(AppData.COLUMN_PCF_EMAIL),
						rs.getString(AppData.COLUMN_PCF_URL), fam));
			}
			rs.close();

		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		return tiers;
	}
}
