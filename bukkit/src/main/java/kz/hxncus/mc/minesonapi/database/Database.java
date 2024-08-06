package kz.hxncus.mc.minesonapi.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.File;
import java.util.Objects;

public class Database {
	protected String url;
	protected String tableSQL;
	@Getter
	protected DatabaseSettings settings;
	protected HikariDataSource dataSource;
	protected DSLContext dslContext;
	
	public Database(@NonNull final Plugin plugin, final String tableSQL, @NonNull final DatabaseSettings settings) {
		this.tableSQL = tableSQL;
		this.settings = settings;
		if (Objects.requireNonNull(settings.sqlDialect) == SQLDialect.SQLITE) {
			this.url = "jdbc:sqlite:plugins/" + plugin.getDataFolder()
			                                          .getName() + File.separator + settings.database + ".db";
		} else {
			this.url = "jdbc:" + settings.sqlDialect.getNameLC() + "://" + settings.host + ":" + settings.port + "/" + settings.database;
		}
		this.createConnection();
	}
	
	public void createConnection() {
		this.dataSource = new HikariDataSource();
		this.dataSource.setJdbcUrl(this.url);
		this.dataSource.setUsername(this.settings.username);
		this.dataSource.setPassword(this.settings.password);
		if (this.settings.properties != null) {
			this.settings.properties.forEach(this.dataSource::addDataSourceProperty);
		}
		this.dslContext = DSL.using(this.dataSource, this.settings.getSqlDialect());
		if (this.tableSQL != null && !this.tableSQL.isEmpty()) {
			this.execute(this.tableSQL);
		}
	}
	
	public int execute(@NonNull final String sql) {
		return this.dslContext.execute(sql);
	}
	
	public int getNewId(@NonNull final String table) {
		final Record entry = this.fetchOne("SELECT MAX(id) as maxId FROM " + table);
		if (entry == null) {
			return 1;
		}
		final Object obj = entry.get("maxId");
		return obj == null ? 1 : (int) obj + 1;
	}
	
	public Record fetchOne(@NonNull final String sql) {
		return this.dslContext.fetchOne(sql);
	}
	
	public @NonNull Result<Record> fetch(@NonNull final String sql) {
		return this.dslContext.fetch(sql);
	}
	
	public @NonNull Result<Record> fetch(@NonNull final String sql, final Object @NonNull ... bindings) {
		return this.dslContext.fetch(sql, bindings);
	}
	
	public @NonNull Result<Record> fetch(@NonNull final String sql, final QueryPart @NonNull ... parts) {
		return this.dslContext.fetch(sql, parts);
	}
	
	public Record fetchOne(@NonNull final String sql, final Object @NonNull ... bindings) {
		return this.dslContext.fetchOne(sql, bindings);
	}
	
	public Record fetchOne(@NonNull final String sql, final QueryPart @NonNull ... parts) {
		return this.dslContext.fetchOne(sql, parts);
	}
	
	public int execute(@NonNull final String sql, final Object @NonNull ... bindings) {
		return this.dslContext.execute(sql, bindings);
	}
	
	public int execute(@NonNull final String sql, final QueryPart @NonNull ... parts) {
		return this.dslContext.execute(sql, parts);
	}
	
	public void reload() {
		this.closeConnection();
		this.createConnection();
	}
	
	public void closeConnection() {
		if (this.dataSource != null) {
			this.dataSource.close();
		}
	}
}

