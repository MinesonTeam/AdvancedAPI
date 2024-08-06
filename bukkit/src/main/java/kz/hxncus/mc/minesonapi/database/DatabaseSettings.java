package kz.hxncus.mc.minesonapi.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.jooq.SQLDialect;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatabaseSettings {
	protected String host;
	protected String port;
	protected String database;
	protected String username;
	protected String password;
	protected String tablePrefix;
	protected SQLDialect sqlDialect;
	protected Map<String, String> properties;
	
	public static DatabaseSettings fromConfig(final ConfigurationSection section) {
		final ConfigurationSection dbSection = section.getConfigurationSection("database.sql");
		return builder()
				.host(dbSection.getString("host", "localhost"))
				.port(dbSection.getString("port", "3306"))
				.database(dbSection.getString("database", "simplepenalty"))
				.username(dbSection.getString("username", "root"))
				.password(dbSection.getString("password", ""))
				.tablePrefix(dbSection.getString("table-prefix", "simplepenalty_"))
				.sqlDialect(SQLDialect.valueOf(section.getString("database.type", "SQLITE")
				                                      .toUpperCase(Locale.ENGLISH))
				)
				.properties(dbSection.getConfigurationSection("properties")
				                     .getValues(false)
				                     .entrySet()
				                     .stream()
				                     .collect(Collectors.toMap(
						                     Map.Entry::getKey,
						                     value -> value.getValue()
						                                   .toString()
				                     ))
				)
				.build();
	}
}
