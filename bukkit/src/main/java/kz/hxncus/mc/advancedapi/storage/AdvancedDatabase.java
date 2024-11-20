package kz.hxncus.mc.advancedapi.storage;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.plugin.Plugin;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.File;
import java.util.Map;

/**
 * The type Database.
 * @author Hxncus
 * @since  1.0.0
 */
@ToString
public class AdvancedDatabase {
	/**
	 * The Url.
	 */
	protected String url;
	/**
	 * The Table SQL.
	 */
	protected String tableSQL;
	/**
	 * The Settings.
	 */
	@Getter
	protected DatabaseSettings settings;
	/**
	 * The Data source.
	 */
	protected HikariDataSource dataSource;
	/**
	 * The Dsl context.
	 */
	protected DSLContext dslContext;
	
	/**
	 * Instantiates a new Database.
	 *
	 * @param plugin   the plugin
	 * @param tableSQL the table SQL
	 * @param settings the settings
	 */
	public AdvancedDatabase(@NonNull final Plugin plugin, final String tableSQL, @NonNull final DatabaseSettings settings) {
		this.tableSQL = tableSQL;
		this.settings = settings;
		if (settings.getSqlDialect() == SQLDialect.SQLITE) {
			this.url = "jdbc:sqlite:plugins/" + plugin.getDataFolder().getName() + File.separator + settings.getDatabase() + ".db";
		} else {
			this.url = "jdbc:" + settings.getSqlDialect().getNameLC() + "://" + settings.getHost() + ":" + settings.getPort() + File.separator + settings.getDatabase();
		}
		this.createConnection();
	}
	
	/**
	 * Create a connection.
	 */
	public void createConnection() {
		this.dataSource = new HikariDataSource();
		this.dataSource.setJdbcUrl(this.url);
		this.dataSource.setUsername(this.settings.getUsername());
		this.dataSource.setPassword(this.settings.getPassword());
		final Map<String, Object> properties = this.settings.getProperties();
		if (properties != null) {
			properties.forEach(this.dataSource::addDataSourceProperty);
		}
		this.dslContext = DSL.using(this.dataSource, this.settings.getSqlDialect());
		if (this.tableSQL != null && !this.tableSQL.isEmpty()) {
			this.execute(this.tableSQL);
		}
	}
	
	/**
	 * Execute int.
	 *
	 * @param sql the SQL
	 * @return the int
	 */
	public int execute(@NonNull final String sql) {
		return this.dslContext.execute(sql);
	}
	
	/**
	 * Gets new id.
	 *
	 * @param table the table
	 * @return the new id
	 */
	public int getNewId(@NonNull final String table) {
		final Record entry = this.fetchOne("SELECT MAX(id) as maxId FROM " + table);
		if (entry == null) {
			return 1;
		}
		final Object obj = entry.get("maxId");
		return obj == null ? 1 : (int) obj + 1;
	}
	
	/**
	 * Fetch one record.
	 *
	 * @param sql the SQL
	 * @return the record
	 */
	public Record fetchOne(@NonNull final String sql) {
		return this.dslContext.fetchOne(sql);
	}
	
	/**
	 * Fetch result.
	 *
	 * @param sql the SQL
	 * @return the result
	 */
	public @NonNull Result<Record> fetch(@NonNull final String sql) {
		return this.dslContext.fetch(sql);
	}
	
	/**
	 * Fetch result.
	 *
	 * @param sql      the SQL
	 * @param bindings the bindings
	 * @return the result
	 */
	public @NonNull Result<Record> fetch(@NonNull final String sql, final Object @NonNull ... bindings) {
		return this.dslContext.fetch(sql, bindings);
	}
	
	/**
	 * Fetch result.
	 *
	 * @param sql   the SQL
	 * @param parts the parts
	 * @return the result
	 */
	public @NonNull Result<Record> fetch(@NonNull final String sql, final QueryPart @NonNull ... parts) {
		return this.dslContext.fetch(sql, parts);
	}
	
	/**
	 * Fetch one record.
	 *
	 * @param sql      the SQL
	 * @param bindings the bindings
	 * @return the record
	 */
	public Record fetchOne(@NonNull final String sql, final Object @NonNull ... bindings) {
		return this.dslContext.fetchOne(sql, bindings);
	}
	
	/**
	 * Fetch one record.
	 *
	 * @param sql   the SQL
	 * @param parts the parts
	 * @return the record
	 */
	public Record fetchOne(@NonNull final String sql, final QueryPart @NonNull ... parts) {
		return this.dslContext.fetchOne(sql, parts);
	}
	
	/**
	 * Execute int.
	 *
	 * @param sql      the SQL
	 * @param bindings the bindings
	 * @return the int
	 */
	public int execute(@NonNull final String sql, final Object @NonNull ... bindings) {
		return this.dslContext.execute(sql, bindings);
	}
	
	/**
	 * Execute int.
	 *
	 * @param sql   the SQL
	 * @param parts the parts
	 * @return the int
	 */
	public int execute(@NonNull final String sql, final QueryPart @NonNull ... parts) {
		return this.dslContext.execute(sql, parts);
	}
	
	/**
	 * Reload.
	 */
	public void reload() {
		this.closeConnection();
		this.createConnection();
	}
	
	/**
	 * Close connection.
	 */
	public void closeConnection() {
		if (this.dataSource != null) {
			this.dataSource.close();
		}
	}
}

