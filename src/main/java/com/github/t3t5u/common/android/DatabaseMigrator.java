package com.github.t3t5u.common.android;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;

public interface DatabaseMigrator {
	void migrate(SQLiteDatabase database, ConnectionSource connectionSource, boolean upgraded);
}
