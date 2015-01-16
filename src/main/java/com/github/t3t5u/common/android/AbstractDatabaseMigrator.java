package com.github.t3t5u.common.android;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.t3t5u.common.database.Entity;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public abstract class AbstractDatabaseMigrator implements DatabaseMigrator {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatabaseMigrator.class);
	private final Context context;

	protected AbstractDatabaseMigrator(final Context context) {
		this.context = context;
	}

	protected Context getContext() {
		return context;
	}

	@Override
	public void migrate(final SQLiteDatabase database, final ConnectionSource connectionSource, final boolean upgraded) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("migrate: " + this + ", " + upgraded);
		}
		if (upgraded) {
			upgrade(database, connectionSource);
		}
	}

	protected void upgrade(final SQLiteDatabase database, final ConnectionSource connectionSource) {
	}

	protected static <E extends Entity> int createTable(final ConnectionSource connectionSource, final Class<E> dataClass) {
		try {
			return TableUtils.createTable(connectionSource, dataClass);
		} catch (final SQLException e) {
			LOGGER.error("createTable", e);
			throw new RuntimeException(e);
		}
	}

	protected static <E extends Entity> int dropTable(final ConnectionSource connectionSource, final Class<E> dataClass, final boolean ignoreErrors) {
		try {
			return TableUtils.dropTable(connectionSource, dataClass, ignoreErrors);
		} catch (final SQLException e) {
			LOGGER.error("dropTable", e);
			throw new RuntimeException(e);
		}
	}

	protected static void dropTable(final SQLiteDatabase database, final String tableName) {
		if ((database == null) || (tableName == null)) {
			return;
		}
		final String sql = String.format("DROP TABLE IF EXISTS `%s`", tableName);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("dropTable: " + sql);
		}
		database.execSQL(sql);
	}

	protected static <T> boolean addColumn(final SQLiteDatabase database, final String tableName, final String columnName, final ColumnType<T> columnType) {
		return addColumn(database, tableName, columnName, columnType, new ColumnConstraints<T>(columnType));
	}

	protected static <T> boolean addColumn(final SQLiteDatabase database, final String tableName, final String columnName, final ColumnType<T> columnType, final ColumnConstraints<T> columnConstraints) {
		if (AndroidUtils.existsColumn(database, tableName, columnName)) {
			return false;
		}
		final String sql = String.format("ALTER TABLE `%s` ADD COLUMN `%s` %s", tableName, columnName, columnType) + (columnConstraints.isEmpty() ? "" : " " + columnConstraints);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addColumn: " + sql);
		}
		database.execSQL(sql);
		return AndroidUtils.existsColumn(database, tableName, columnName);
	}

	protected static boolean renameTo(final SQLiteDatabase database, final String oldTableName, final String tableName) {
		if (AndroidUtils.existsTable(database, tableName)) {
			return false;
		}
		final String sql = String.format("ALTER TABLE `%s` RENAME TO `%s`", oldTableName, tableName);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("renameTo: " + sql);
		}
		database.execSQL(sql);
		return AndroidUtils.existsTable(database, tableName);
	}
}
