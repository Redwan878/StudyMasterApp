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
import com.porashona.studymaster.data.model.Subject;
import java.lang.Class;
import java.lang.Exception;
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
public final class SubjectDao_Impl implements SubjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Subject> __insertionAdapterOfSubject;

  private final EntityDeletionOrUpdateAdapter<Subject> __deletionAdapterOfSubject;

  private final EntityDeletionOrUpdateAdapter<Subject> __updateAdapterOfSubject;

  private final SharedSQLiteStatement __preparedStmtOfAddTimeToSubject;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SubjectDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubject = new EntityInsertionAdapter<Subject>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `subjects` (`id`,`name`,`shortName`,`colorHex`,`icon`,`totalTimeInSeconds`,`totalSessions`,`difficultyLevel`,`targetHoursPerWeek`,`chaptersTotal`,`chaptersCompleted`,`lastStudiedAt`,`createdAt`,`isArchived`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Subject entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getShortName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getShortName());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getColorHex());
        }
        if (entity.getIcon() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getIcon());
        }
        statement.bindLong(6, entity.getTotalTimeInSeconds());
        statement.bindLong(7, entity.getTotalSessions());
        statement.bindLong(8, entity.getDifficultyLevel());
        statement.bindLong(9, entity.getTargetHoursPerWeek());
        statement.bindLong(10, entity.getChaptersTotal());
        statement.bindLong(11, entity.getChaptersCompleted());
        if (entity.getLastStudiedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getLastStudiedAt());
        }
        statement.bindLong(13, entity.getCreatedAt());
        final int _tmp = entity.isArchived() ? 1 : 0;
        statement.bindLong(14, _tmp);
      }
    };
    this.__deletionAdapterOfSubject = new EntityDeletionOrUpdateAdapter<Subject>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `subjects` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Subject entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSubject = new EntityDeletionOrUpdateAdapter<Subject>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `subjects` SET `id` = ?,`name` = ?,`shortName` = ?,`colorHex` = ?,`icon` = ?,`totalTimeInSeconds` = ?,`totalSessions` = ?,`difficultyLevel` = ?,`targetHoursPerWeek` = ?,`chaptersTotal` = ?,`chaptersCompleted` = ?,`lastStudiedAt` = ?,`createdAt` = ?,`isArchived` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Subject entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getShortName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getShortName());
        }
        if (entity.getColorHex() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getColorHex());
        }
        if (entity.getIcon() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getIcon());
        }
        statement.bindLong(6, entity.getTotalTimeInSeconds());
        statement.bindLong(7, entity.getTotalSessions());
        statement.bindLong(8, entity.getDifficultyLevel());
        statement.bindLong(9, entity.getTargetHoursPerWeek());
        statement.bindLong(10, entity.getChaptersTotal());
        statement.bindLong(11, entity.getChaptersCompleted());
        if (entity.getLastStudiedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getLastStudiedAt());
        }
        statement.bindLong(13, entity.getCreatedAt());
        final int _tmp = entity.isArchived() ? 1 : 0;
        statement.bindLong(14, _tmp);
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfAddTimeToSubject = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE subjects SET totalTimeInSeconds = totalTimeInSeconds + ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM subjects";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Subject subject, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSubject.insertAndReturnId(subject);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Subject subject, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSubject.handle(subject);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Subject subject, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSubject.handle(subject);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addTimeToSubject(final long subjectId, final long seconds,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddTimeToSubject.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, seconds);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, subjectId);
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
          __preparedStmtOfAddTimeToSubject.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
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
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Subject>> getAllSubjects() {
    final String _sql = "SELECT * FROM subjects ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"subjects"}, new Callable<List<Subject>>() {
      @Override
      @NonNull
      public List<Subject> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfShortName = CursorUtil.getColumnIndexOrThrow(_cursor, "shortName");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfTotalTimeInSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimeInSeconds");
          final int _cursorIndexOfTotalSessions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSessions");
          final int _cursorIndexOfDifficultyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyLevel");
          final int _cursorIndexOfTargetHoursPerWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "targetHoursPerWeek");
          final int _cursorIndexOfChaptersTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "chaptersTotal");
          final int _cursorIndexOfChaptersCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "chaptersCompleted");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final List<Subject> _result = new ArrayList<Subject>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Subject _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpShortName;
            if (_cursor.isNull(_cursorIndexOfShortName)) {
              _tmpShortName = null;
            } else {
              _tmpShortName = _cursor.getString(_cursorIndexOfShortName);
            }
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final long _tmpTotalTimeInSeconds;
            _tmpTotalTimeInSeconds = _cursor.getLong(_cursorIndexOfTotalTimeInSeconds);
            final int _tmpTotalSessions;
            _tmpTotalSessions = _cursor.getInt(_cursorIndexOfTotalSessions);
            final int _tmpDifficultyLevel;
            _tmpDifficultyLevel = _cursor.getInt(_cursorIndexOfDifficultyLevel);
            final int _tmpTargetHoursPerWeek;
            _tmpTargetHoursPerWeek = _cursor.getInt(_cursorIndexOfTargetHoursPerWeek);
            final int _tmpChaptersTotal;
            _tmpChaptersTotal = _cursor.getInt(_cursorIndexOfChaptersTotal);
            final int _tmpChaptersCompleted;
            _tmpChaptersCompleted = _cursor.getInt(_cursorIndexOfChaptersCompleted);
            final Long _tmpLastStudiedAt;
            if (_cursor.isNull(_cursorIndexOfLastStudiedAt)) {
              _tmpLastStudiedAt = null;
            } else {
              _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            _item = new Subject(_tmpId,_tmpName,_tmpShortName,_tmpColorHex,_tmpIcon,_tmpTotalTimeInSeconds,_tmpTotalSessions,_tmpDifficultyLevel,_tmpTargetHoursPerWeek,_tmpChaptersTotal,_tmpChaptersCompleted,_tmpLastStudiedAt,_tmpCreatedAt,_tmpIsArchived);
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
  public Object getSubjectById(final long id, final Continuation<? super Subject> $completion) {
    final String _sql = "SELECT * FROM subjects WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Subject>() {
      @Override
      @Nullable
      public Subject call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfShortName = CursorUtil.getColumnIndexOrThrow(_cursor, "shortName");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfTotalTimeInSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimeInSeconds");
          final int _cursorIndexOfTotalSessions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSessions");
          final int _cursorIndexOfDifficultyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyLevel");
          final int _cursorIndexOfTargetHoursPerWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "targetHoursPerWeek");
          final int _cursorIndexOfChaptersTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "chaptersTotal");
          final int _cursorIndexOfChaptersCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "chaptersCompleted");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final Subject _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpShortName;
            if (_cursor.isNull(_cursorIndexOfShortName)) {
              _tmpShortName = null;
            } else {
              _tmpShortName = _cursor.getString(_cursorIndexOfShortName);
            }
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final long _tmpTotalTimeInSeconds;
            _tmpTotalTimeInSeconds = _cursor.getLong(_cursorIndexOfTotalTimeInSeconds);
            final int _tmpTotalSessions;
            _tmpTotalSessions = _cursor.getInt(_cursorIndexOfTotalSessions);
            final int _tmpDifficultyLevel;
            _tmpDifficultyLevel = _cursor.getInt(_cursorIndexOfDifficultyLevel);
            final int _tmpTargetHoursPerWeek;
            _tmpTargetHoursPerWeek = _cursor.getInt(_cursorIndexOfTargetHoursPerWeek);
            final int _tmpChaptersTotal;
            _tmpChaptersTotal = _cursor.getInt(_cursorIndexOfChaptersTotal);
            final int _tmpChaptersCompleted;
            _tmpChaptersCompleted = _cursor.getInt(_cursorIndexOfChaptersCompleted);
            final Long _tmpLastStudiedAt;
            if (_cursor.isNull(_cursorIndexOfLastStudiedAt)) {
              _tmpLastStudiedAt = null;
            } else {
              _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            _result = new Subject(_tmpId,_tmpName,_tmpShortName,_tmpColorHex,_tmpIcon,_tmpTotalTimeInSeconds,_tmpTotalSessions,_tmpDifficultyLevel,_tmpTargetHoursPerWeek,_tmpChaptersTotal,_tmpChaptersCompleted,_tmpLastStudiedAt,_tmpCreatedAt,_tmpIsArchived);
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
  public Object getSubjectByName(final String name,
      final Continuation<? super Subject> $completion) {
    final String _sql = "SELECT * FROM subjects WHERE name = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Subject>() {
      @Override
      @Nullable
      public Subject call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfShortName = CursorUtil.getColumnIndexOrThrow(_cursor, "shortName");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfTotalTimeInSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "totalTimeInSeconds");
          final int _cursorIndexOfTotalSessions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSessions");
          final int _cursorIndexOfDifficultyLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyLevel");
          final int _cursorIndexOfTargetHoursPerWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "targetHoursPerWeek");
          final int _cursorIndexOfChaptersTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "chaptersTotal");
          final int _cursorIndexOfChaptersCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "chaptersCompleted");
          final int _cursorIndexOfLastStudiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastStudiedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final Subject _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpShortName;
            if (_cursor.isNull(_cursorIndexOfShortName)) {
              _tmpShortName = null;
            } else {
              _tmpShortName = _cursor.getString(_cursorIndexOfShortName);
            }
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final long _tmpTotalTimeInSeconds;
            _tmpTotalTimeInSeconds = _cursor.getLong(_cursorIndexOfTotalTimeInSeconds);
            final int _tmpTotalSessions;
            _tmpTotalSessions = _cursor.getInt(_cursorIndexOfTotalSessions);
            final int _tmpDifficultyLevel;
            _tmpDifficultyLevel = _cursor.getInt(_cursorIndexOfDifficultyLevel);
            final int _tmpTargetHoursPerWeek;
            _tmpTargetHoursPerWeek = _cursor.getInt(_cursorIndexOfTargetHoursPerWeek);
            final int _tmpChaptersTotal;
            _tmpChaptersTotal = _cursor.getInt(_cursorIndexOfChaptersTotal);
            final int _tmpChaptersCompleted;
            _tmpChaptersCompleted = _cursor.getInt(_cursorIndexOfChaptersCompleted);
            final Long _tmpLastStudiedAt;
            if (_cursor.isNull(_cursorIndexOfLastStudiedAt)) {
              _tmpLastStudiedAt = null;
            } else {
              _tmpLastStudiedAt = _cursor.getLong(_cursorIndexOfLastStudiedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            _result = new Subject(_tmpId,_tmpName,_tmpShortName,_tmpColorHex,_tmpIcon,_tmpTotalTimeInSeconds,_tmpTotalSessions,_tmpDifficultyLevel,_tmpTargetHoursPerWeek,_tmpChaptersTotal,_tmpChaptersCompleted,_tmpLastStudiedAt,_tmpCreatedAt,_tmpIsArchived);
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
