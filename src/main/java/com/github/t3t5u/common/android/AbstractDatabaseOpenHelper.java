package com.github.t3t5u.common.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.github.t3t5u.common.util.ExtraClassUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public abstract class AbstractDatabaseOpenHelper extends OrmLiteSqliteOpenHelper {
	private final Context context;
	private final int version;
	private final String migrator;
	private DatabaseMigrator[] migrators;

	protected AbstractDatabaseOpenHelper(final Context context, final String name, final CursorFactory factory, final int version, final Class<? extends DatabaseMigrator> migrator1) {
		super(context, name, factory, version);
		this.context = context;
		this.version = version;
		migrator = migrator1.getName().replaceFirst("1$", "");
	}

	@Override
	public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
		onCreate(database, connectionSource, version);
	}

	protected void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource, final int currentVersion) {
		final DatabaseMigrator[] migrators = getDatabaseMigrators(currentVersion);
		for (int index = 0, version = 1; (index < migrators.length) && (version <= currentVersion); index++, version++) {
			migrate(migrators[index], database, connectionSource);
		}
	}

	private void migrate(final DatabaseMigrator migrator, final SQLiteDatabase database, final ConnectionSource connectionSource) {
		migrator.migrate(database, connectionSource, false);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
		onUpgrade(database, connectionSource, version, oldVersion, newVersion);
	}

	protected void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, final int currentVersion, final int oldVersion, final int newVersion) {
		final DatabaseMigrator[] migrators = getDatabaseMigrators(currentVersion);
		for (int index = 0, version = 1; (index < migrators.length) && (version <= currentVersion); index++, version++) {
			migrate(migrators[index], database, connectionSource, version, oldVersion, newVersion);
		}
	}

	private void migrate(final DatabaseMigrator migrator, final SQLiteDatabase database, final ConnectionSource connectionSource, final int version, final int oldVersion, final int newVersion) {
		if ((oldVersion >= version) || (newVersion < version)) {
			return;
		}
		migrator.migrate(database, connectionSource, true);
	}

	private synchronized DatabaseMigrator[] getDatabaseMigrators(final int currentVersion) {
		if (migrators != null) {
			return migrators;
		}
		final List<DatabaseMigrator> list = new ArrayList<DatabaseMigrator>();
		for (int version = 1; version <= currentVersion; version++) {
			list.add(getDatabaseMigrator(version));
		}
		migrators = list.toArray(new DatabaseMigrator[list.size()]);
		return migrators;
	}

	private DatabaseMigrator getDatabaseMigrator(final int version) {
		return (DatabaseMigrator) ExtraClassUtils.newInstance(ExtraClassUtils.forName(migrator + version), new Class[] { Context.class }, context);
	}
}
