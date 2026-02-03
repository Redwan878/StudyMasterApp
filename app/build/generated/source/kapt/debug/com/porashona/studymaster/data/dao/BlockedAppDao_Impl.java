package com.porashona.studymaster.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.porashona.studymaster.data.model.BlockStatistic;
import com.porashona.studymaster.data.model.BlockedApp;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BlockedAppDao_Impl implements BlockedAppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BlockedApp> __insertionAdapterOfBlockedApp;

  private final EntityInsertionAdapter<BlockStatistic> __insertionAdapterOfBlockStatistic;

  private final EntityDeletionOrUpdateAdapter<BlockedApp> __deletionAdapterOfBlockedApp;

  private final EntityDeletionOrUpdateAdapter<BlockedApp> __updateAdapterOfBlockedApp;

  private final SharedSQLiteStatement __preparedStmtOfSetBlocked;

  private final SharedSQLiteStatement __preparedStmtOfSetWhitelisted;

  private final SharedSQLiteStatement __preparedStmtOfIncrementBlockAttempt;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldStats;

  public BlockedAppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBlockedApp = new EntityInsertionAdapter<BlockedApp>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `blocked_apps` (`packageName`,`appName`,`isBlocked`,`isWhitelisted`,`blockAttempts`,`lastBlockedAt`,`addedAt`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BlockedApp entity) {
        if (entity.getPackageName() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getPackageName());
        }
        if (entity.getAppName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getAppName());
        }
        final int _tmp = entity.isBlocked() ? 1 : 0;
        statement.bindLong(3, _tmp);
        final int _tmp_1 = entity.isWhitelisted() ? 1 : 0;
        statement.bindLong(4, _tmp_1);
        statement.bindLong(5, entity.getBlockAttempts());
        if (entity.getLastBlockedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLastBlockedAt());
        }
        statement.bindLong(7, entity.getAddedAt());
      }
    };
    this.__insertionAdapterOfBlockStatistic = new EntityInsertionAdapter<BlockStatistic>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `block_statistics` (`id`,`packageName`,`appName`,`blockedAt`,`sessionId`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BlockStatistic entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getPackageName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPackageName());
        }
        if (entity.getAppName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAppName());
        }
        statement.bindLong(4, entity.getBlockedAt());
        if (entity.getSessionId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getSessionId());
        }
      }
    };
    this.__deletionAdapterOfBlockedApp = new EntityDeletionOrUpdateAdapter<BlockedApp>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `blocked_apps` WHERE `packageName` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BlockedApp entity) {
        if (entity.getPackageName() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getPackageName());
        }
      }
    };
    this.__updateAdapterOfBlockedApp = new EntityDeletionOrUpdateAdapter<BlockedApp>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `blocked_apps` SET `packageName` = ?,`appName` = ?,`isBlocked` = ?,`isWhitelisted` = ?,`blockAttempts` = ?,`lastBlockedAt` = ?,`addedAt` = ? WHERE `packageName` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BlockedApp entity) {
        if (entity.getPackageName() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getPackageName());
        }
        if (entity.getAppName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getAppName());
        }
        final int _tmp = entity.isBlocked() ? 1 : 0;
        statement.bindLong(3, _tmp);
        final int _tmp_1 = entity.isWhitelisted() ? 1 : 0;
        statement.bindLong(4, _tmp_1);
        statement.bindLong(5, entity.getBlockAttempts());
        if (entity.getLastBlockedAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLastBlockedAt());
        }
        statement.bindLong(7, entity.getAddedAt());
        if (entity.getPackageName() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPackageName());
        }
      }
    };
    this.__preparedStmtOfSetBlocked = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE blocked_apps SET isBlocked = ? WHERE packageName = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetWhitelisted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE blocked_apps SET isWhitelisted = ? WHERE packageName = ?";
        return _query;
      }
    };
    this.__preparedStmtOfIncrementBlockAttempt = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE blocked_apps SET blockAttempts = blockAttempts + 1, lastBlockedAt = ? WHERE packageName = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldStats = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM block_statistics WHERE blockedAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BlockedApp app, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBlockedApp.insert(app);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<BlockedApp> apps,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBlockedApp.insert(apps);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertBlockStat(final BlockStatistic stat,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBlockStatistic.insert(stat);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BlockedApp app, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBlockedApp.handle(app);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final BlockedApp app, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBlockedApp.handle(app);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setBlocked(final String packageName, final boolean isBlocked,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetBlocked.acquire();
        int _argIndex = 1;
        final int _tmp = isBlocked ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (packageName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, packageName);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetBlocked.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setWhitelisted(final String packageName, final boolean isWhitelisted,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetWhitelisted.acquire();
        int _argIndex = 1;
        final int _tmp = isWhitelisted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (packageName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, packageName);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetWhitelisted.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementBlockAttempt(final String packageName, final long time,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementBlockAttempt.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, time);
        _argIndex = 2;
        if (packageName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, packageName);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfIncrementBlockAttempt.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldStats(final long before, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldStats.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, before);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldStats.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BlockedApp>> getAllBlockedApps() {
    final String _sql = "SELECT * FROM blocked_apps ORDER BY appName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"blocked_apps"}, new Callable<List<BlockedApp>>() {
      @Override
      @NonNull
      public List<BlockedApp> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfIsWhitelisted = CursorUtil.getColumnIndexOrThrow(_cursor, "isWhitelisted");
          final int _cursorIndexOfBlockAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "blockAttempts");
          final int _cursorIndexOfLastBlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastBlockedAt");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<BlockedApp> _result = new ArrayList<BlockedApp>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BlockedApp _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpIsWhitelisted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsWhitelisted);
            _tmpIsWhitelisted = _tmp_1 != 0;
            final int _tmpBlockAttempts;
            _tmpBlockAttempts = _cursor.getInt(_cursorIndexOfBlockAttempts);
            final Long _tmpLastBlockedAt;
            if (_cursor.isNull(_cursorIndexOfLastBlockedAt)) {
              _tmpLastBlockedAt = null;
            } else {
              _tmpLastBlockedAt = _cursor.getLong(_cursorIndexOfLastBlockedAt);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new BlockedApp(_tmpPackageName,_tmpAppName,_tmpIsBlocked,_tmpIsWhitelisted,_tmpBlockAttempts,_tmpLastBlockedAt,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BlockedApp>> getActiveBlockedApps() {
    final String _sql = "SELECT * FROM blocked_apps WHERE isBlocked = 1 AND isWhitelisted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"blocked_apps"}, new Callable<List<BlockedApp>>() {
      @Override
      @NonNull
      public List<BlockedApp> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfIsWhitelisted = CursorUtil.getColumnIndexOrThrow(_cursor, "isWhitelisted");
          final int _cursorIndexOfBlockAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "blockAttempts");
          final int _cursorIndexOfLastBlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastBlockedAt");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<BlockedApp> _result = new ArrayList<BlockedApp>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BlockedApp _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpIsWhitelisted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsWhitelisted);
            _tmpIsWhitelisted = _tmp_1 != 0;
            final int _tmpBlockAttempts;
            _tmpBlockAttempts = _cursor.getInt(_cursorIndexOfBlockAttempts);
            final Long _tmpLastBlockedAt;
            if (_cursor.isNull(_cursorIndexOfLastBlockedAt)) {
              _tmpLastBlockedAt = null;
            } else {
              _tmpLastBlockedAt = _cursor.getLong(_cursorIndexOfLastBlockedAt);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new BlockedApp(_tmpPackageName,_tmpAppName,_tmpIsBlocked,_tmpIsWhitelisted,_tmpBlockAttempts,_tmpLastBlockedAt,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getByPackageName(final String packageName,
      final Continuation<? super BlockedApp> $completion) {
    final String _sql = "SELECT * FROM blocked_apps WHERE packageName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (packageName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, packageName);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BlockedApp>() {
      @Override
      @Nullable
      public BlockedApp call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfIsWhitelisted = CursorUtil.getColumnIndexOrThrow(_cursor, "isWhitelisted");
          final int _cursorIndexOfBlockAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "blockAttempts");
          final int _cursorIndexOfLastBlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastBlockedAt");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final BlockedApp _result;
          if (_cursor.moveToFirst()) {
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpIsWhitelisted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsWhitelisted);
            _tmpIsWhitelisted = _tmp_1 != 0;
            final int _tmpBlockAttempts;
            _tmpBlockAttempts = _cursor.getInt(_cursorIndexOfBlockAttempts);
            final Long _tmpLastBlockedAt;
            if (_cursor.isNull(_cursorIndexOfLastBlockedAt)) {
              _tmpLastBlockedAt = null;
            } else {
              _tmpLastBlockedAt = _cursor.getLong(_cursorIndexOfLastBlockedAt);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _result = new BlockedApp(_tmpPackageName,_tmpAppName,_tmpIsBlocked,_tmpIsWhitelisted,_tmpBlockAttempts,_tmpLastBlockedAt,_tmpAddedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BlockedApp>> getWhitelistedApps() {
    final String _sql = "SELECT * FROM blocked_apps WHERE isWhitelisted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"blocked_apps"}, new Callable<List<BlockedApp>>() {
      @Override
      @NonNull
      public List<BlockedApp> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfIsBlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBlocked");
          final int _cursorIndexOfIsWhitelisted = CursorUtil.getColumnIndexOrThrow(_cursor, "isWhitelisted");
          final int _cursorIndexOfBlockAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "blockAttempts");
          final int _cursorIndexOfLastBlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastBlockedAt");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final List<BlockedApp> _result = new ArrayList<BlockedApp>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BlockedApp _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final boolean _tmpIsBlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBlocked);
            _tmpIsBlocked = _tmp != 0;
            final boolean _tmpIsWhitelisted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsWhitelisted);
            _tmpIsWhitelisted = _tmp_1 != 0;
            final int _tmpBlockAttempts;
            _tmpBlockAttempts = _cursor.getInt(_cursorIndexOfBlockAttempts);
            final Long _tmpLastBlockedAt;
            if (_cursor.isNull(_cursorIndexOfLastBlockedAt)) {
              _tmpLastBlockedAt = null;
            } else {
              _tmpLastBlockedAt = _cursor.getLong(_cursorIndexOfLastBlockedAt);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            _item = new BlockedApp(_tmpPackageName,_tmpAppName,_tmpIsBlocked,_tmpIsWhitelisted,_tmpBlockAttempts,_tmpLastBlockedAt,_tmpAddedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getTotalBlockAttempts() {
    final String _sql = "SELECT SUM(blockAttempts) FROM blocked_apps";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"blocked_apps"}, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BlockStatistic>> getRecentBlockStats(final int limit) {
    final String _sql = "SELECT * FROM block_statistics ORDER BY blockedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"block_statistics"}, new Callable<List<BlockStatistic>>() {
      @Override
      @NonNull
      public List<BlockStatistic> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfBlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "blockedAt");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final List<BlockStatistic> _result = new ArrayList<BlockStatistic>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BlockStatistic _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final long _tmpBlockedAt;
            _tmpBlockedAt = _cursor.getLong(_cursorIndexOfBlockedAt);
            final Long _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            }
            _item = new BlockStatistic(_tmpId,_tmpPackageName,_tmpAppName,_tmpBlockedAt,_tmpSessionId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getBlockCountSince(final long since) {
    final String _sql = "SELECT COUNT(*) FROM block_statistics WHERE blockedAt >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"block_statistics"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<AppBlockCount>> getMostBlockedApps(final long since) {
    final String _sql = "SELECT packageName, COUNT(*) as count FROM block_statistics WHERE blockedAt >= ? GROUP BY packageName ORDER BY count DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"block_statistics"}, new Callable<List<AppBlockCount>>() {
      @Override
      @NonNull
      public List<AppBlockCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = 0;
          final int _cursorIndexOfCount = 1;
          final List<AppBlockCount> _result = new ArrayList<AppBlockCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppBlockCount _item;
            final String _tmpPackageName;
            if (_cursor.isNull(_cursorIndexOfPackageName)) {
              _tmpPackageName = null;
            } else {
              _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            }
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item = new AppBlockCount(_tmpPackageName,_tmpCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
