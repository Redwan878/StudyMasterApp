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
import com.porashona.studymaster.data.model.UserProfile;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfile> __insertionAdapterOfUserProfile;

  private final EntityDeletionOrUpdateAdapter<UserProfile> __updateAdapterOfUserProfile;

  private final SharedSQLiteStatement __preparedStmtOfAddXp;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStreak;

  private final SharedSQLiteStatement __preparedStmtOfAddStudyTime;

  private final SharedSQLiteStatement __preparedStmtOfUpdateName;

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfile = new EntityInsertionAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profile` (`id`,`name`,`totalXp`,`level`,`currentStreak`,`longestStreak`,`totalStudyTimeSeconds`,`totalSessions`,`lastStudyDate`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfile entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getTotalXp());
        statement.bindLong(4, entity.getLevel());
        statement.bindLong(5, entity.getCurrentStreak());
        statement.bindLong(6, entity.getLongestStreak());
        statement.bindLong(7, entity.getTotalStudyTimeSeconds());
        statement.bindLong(8, entity.getTotalSessions());
        if (entity.getLastStudyDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastStudyDate());
        }
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__updateAdapterOfUserProfile = new EntityDeletionOrUpdateAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `user_profile` SET `id` = ?,`name` = ?,`totalXp` = ?,`level` = ?,`currentStreak` = ?,`longestStreak` = ?,`totalStudyTimeSeconds` = ?,`totalSessions` = ?,`lastStudyDate` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfile entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getTotalXp());
        statement.bindLong(4, entity.getLevel());
        statement.bindLong(5, entity.getCurrentStreak());
        statement.bindLong(6, entity.getLongestStreak());
        statement.bindLong(7, entity.getTotalStudyTimeSeconds());
        statement.bindLong(8, entity.getTotalSessions());
        if (entity.getLastStudyDate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getLastStudyDate());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfAddXp = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET totalXp = totalXp + ?, level = (totalXp + ?) / 1000 + 1 WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStreak = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET currentStreak = ?, longestStreak = CASE WHEN ? > longestStreak THEN ? ELSE longestStreak END WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfAddStudyTime = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET totalStudyTimeSeconds = totalStudyTimeSeconds + ?, totalSessions = totalSessions + 1, lastStudyDate = ? WHERE id = 1";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateName = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE user_profile SET name = ? WHERE id = 1";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final UserProfile profile, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProfile.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final UserProfile profile, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUserProfile.handle(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addXp(final int xp, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddXp.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, xp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, xp);
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
          __preparedStmtOfAddXp.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStreak(final int streak, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStreak.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, streak);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, streak);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, streak);
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
          __preparedStmtOfUpdateStreak.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object addStudyTime(final long seconds, final long date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddStudyTime.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, seconds);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, date);
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
          __preparedStmtOfAddStudyTime.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateName(final String name, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateName.acquire();
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, name);
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
          __preparedStmtOfUpdateName.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<UserProfile> getProfile() {
    final String _sql = "SELECT * FROM user_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_profile"}, new Callable<UserProfile>() {
      @Override
      @Nullable
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfTotalStudyTimeSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStudyTimeSeconds");
          final int _cursorIndexOfTotalSessions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSessions");
          final int _cursorIndexOfLastStudyDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudyDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UserProfile _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final long _tmpTotalStudyTimeSeconds;
            _tmpTotalStudyTimeSeconds = _cursor.getLong(_cursorIndexOfTotalStudyTimeSeconds);
            final int _tmpTotalSessions;
            _tmpTotalSessions = _cursor.getInt(_cursorIndexOfTotalSessions);
            final Long _tmpLastStudyDate;
            if (_cursor.isNull(_cursorIndexOfLastStudyDate)) {
              _tmpLastStudyDate = null;
            } else {
              _tmpLastStudyDate = _cursor.getLong(_cursorIndexOfLastStudyDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new UserProfile(_tmpId,_tmpName,_tmpTotalXp,_tmpLevel,_tmpCurrentStreak,_tmpLongestStreak,_tmpTotalStudyTimeSeconds,_tmpTotalSessions,_tmpLastStudyDate,_tmpCreatedAt);
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
  public Object getProfileSync(final Continuation<? super UserProfile> $completion) {
    final String _sql = "SELECT * FROM user_profile WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfile>() {
      @Override
      @Nullable
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfTotalStudyTimeSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "totalStudyTimeSeconds");
          final int _cursorIndexOfTotalSessions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSessions");
          final int _cursorIndexOfLastStudyDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudyDate");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final UserProfile _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final long _tmpTotalStudyTimeSeconds;
            _tmpTotalStudyTimeSeconds = _cursor.getLong(_cursorIndexOfTotalStudyTimeSeconds);
            final int _tmpTotalSessions;
            _tmpTotalSessions = _cursor.getInt(_cursorIndexOfTotalSessions);
            final Long _tmpLastStudyDate;
            if (_cursor.isNull(_cursorIndexOfLastStudyDate)) {
              _tmpLastStudyDate = null;
            } else {
              _tmpLastStudyDate = _cursor.getLong(_cursorIndexOfLastStudyDate);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new UserProfile(_tmpId,_tmpName,_tmpTotalXp,_tmpLevel,_tmpCurrentStreak,_tmpLongestStreak,_tmpTotalStudyTimeSeconds,_tmpTotalSessions,_tmpLastStudyDate,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
