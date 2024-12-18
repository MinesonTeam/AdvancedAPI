package kz.hxncus.mc.advancedapi.data.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.jooq.SQLDialect;

import java.util.Locale;
import java.util.Map;

/**
 * The type Database settings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatabaseSettings {
	private String host;
	private String port;
	private String database;
	private String username;
	private String password;
	private String tablePrefix;
	private SQLDialect sqlDialect;
	private Map<String, Object> properties;
	
	/**
	 * From config database settings.
	 *
	 * @param section the section
	 * @return the database settings
	 */
	public static DatabaseSettings fromConfig(final ConfigurationSection section) {
		final ConfigurationSection dbSection = section.getConfigurationSection("database.sql");
		if (dbSection == null) {
			return builder().build();
		}
		final ConfigurationSection sectionProperties;
		if (dbSection.contains("properties")) {
			sectionProperties = dbSection.getConfigurationSection("properties");
		} else {
			sectionProperties = dbSection.createSection("properties");
		}
		return builder()
				.host(dbSection.getString("host", "localhost"))
				.port(dbSection.getString("port", "3306"))
				.database(dbSection.getString("database", ""))
				.username(dbSection.getString("username", "root"))
				.password(dbSection.getString("password", ""))
				.tablePrefix(dbSection.getString("table-prefix", ""))
				.sqlDialect(SQLDialect.valueOf(section.getString("database.type", "SQLITE").toUpperCase(Locale.ENGLISH)))
				.properties(sectionProperties.getValues(false)).build();
	}
}
