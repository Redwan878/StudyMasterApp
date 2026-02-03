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
import com.porashona.studymaster.data.database.Converters;
import com.porashona.studymaster.data.model.RepeatType;
import com.porashona.studymaster.data.model.Routine;
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
public final class RoutineDao_Impl implements RoutineDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Routine> __insertionAdapterOfRoutine;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Routine> __deletionAdapterOfRoutine;

  private final EntityDeletionOrUpdateAdapter<Routine> __updateAdapterOfRoutine;

  private final SharedSQLiteStatement __preparedStmtOfSetEnabled;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public RoutineDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRoutine = new EntityInsertionAdapter<Routine>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `routines` (`id`,`subjectId`,`subjectName`,`title`,`hour`,`minute`,`durationMinutes`,`repeatType`,`repeatDays`,`isEnabled`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Routine entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSubjectId());
        if (entity.getSubjectName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        statement.bindLong(5, entity.getHour());
        statement.bindLong(6, entity.getMinute());
        statement.bindLong(7, entity.getDurationMinutes());
        final String _tmp = __converters.fromRepeatType(entity.getRepeatType());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final String _tmp_1 = __converters.fromIntList(entity.getRepeatDays());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final int _tmp_2 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        statement.bindLong(11, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfRoutine = new EntityDeletionOrUpdateAdapter<Routine>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `routines` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Routine entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRoutine = new EntityDeletionOrUpdateAdapter<Routine>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `routines` SET `id` = ?,`subjectId` = ?,`subjectName` = ?,`title` = ?,`hour` = ?,`minute` = ?,`durationMinutes` = ?,`repeatType` = ?,`repeatDays` = ?,`isEnabled` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Routine entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSubjectId());
        if (entity.getSubjectName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectName());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        statement.bindLong(5, entity.getHour());
        statement.bindLong(6, entity.getMinute());
        statement.bindLong(7, entity.getDurationMinutes());
        final String _tmp = __converters.fromRepeatType(entity.getRepeatType());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final String _tmp_1 = __converters.fromIntList(entity.getRepeatDays());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final int _tmp_2 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfSetEnabled = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE routines SET isEnabled = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM routines";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Routine routine, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRoutine.insertAndReturnId(routine);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Routine routine, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRoutine.handle(routine);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Routine routine, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfRoutine.handle(routine);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setEnabled(final long id, final boolean enabled,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetEnabled.acquire();
        int _argIndex = 1;
        final int _tmp = enabled ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfSetEnabled.release(_stmt);
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
  public Flow<List<Routine>> getAllRoutines() {
    final String _sql = "SELECT * FROM routines ORDER BY hour ASC, minute ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"routines"}, new Callable<List<Routine>>() {
      @Override
      @NonNull
      public List<Routine> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfRepeatType = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatType");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Routine> _result = new ArrayList<Routine>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Routine _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final RepeatType _tmpRepeatType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRepeatType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRepeatType);
            }
            _tmpRepeatType = __converters.toRepeatType(_tmp);
            final List<Integer> _tmpRepeatDays;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRepeatDays)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRepeatDays);
            }
            _tmpRepeatDays = __converters.toIntList(_tmp_1);
            final boolean _tmpIsEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Routine(_tmpId,_tmpSubjectId,_tmpSubjectName,_tmpTitle,_tmpHour,_tmpMinute,_tmpDurationMinutes,_tmpRepeatType,_tmpRepeatDays,_tmpIsEnabled,_tmpCreatedAt);
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
  public Object getRoutineById(final long id, final Continuation<? super Routine> $completion) {
    final String _sql = "SELECT * FROM routines WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Routine>() {
      @Override
      @Nullable
      public Routine call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfRepeatType = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatType");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Routine _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final RepeatType _tmpRepeatType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRepeatType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRepeatType);
            }
            _tmpRepeatType = __converters.toRepeatType(_tmp);
            final List<Integer> _tmpRepeatDays;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRepeatDays)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRepeatDays);
            }
            _tmpRepeatDays = __converters.toIntList(_tmp_1);
            final boolean _tmpIsEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Routine(_tmpId,_tmpSubjectId,_tmpSubjectName,_tmpTitle,_tmpHour,_tmpMinute,_tmpDurationMinutes,_tmpRepeatType,_tmpRepeatDays,_tmpIsEnabled,_tmpCreatedAt);
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
  public Flow<List<Routine>> getEnabledRoutines() {
    final String _sql = "SELECT * FROM routines WHERE isEnabled = 1 ORDER BY hour ASC, minute ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"routines"}, new Callable<List<Routine>>() {
      @Override
      @NonNull
      public List<Routine> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfHour = CursorUtil.getColumnIndexOrThrow(_cursor, "hour");
          final int _cursorIndexOfMinute = CursorUtil.getColumnIndexOrThrow(_cursor, "minute");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfRepeatType = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatType");
          final int _cursorIndexOfRepeatDays = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatDays");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Routine> _result = new ArrayList<Routine>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Routine _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSubjectId;
            _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpHour;
            _tmpHour = _cursor.getInt(_cursorIndexOfHour);
            final int _tmpMinute;
            _tmpMinute = _cursor.getInt(_cursorIndexOfMinute);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final RepeatType _tmpRepeatType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRepeatType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRepeatType);
            }
            _tmpRepeatType = __converters.toRepeatType(_tmp);
            final List<Integer> _tmpRepeatDays;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfRepeatDays)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfRepeatDays);
            }
            _tmpRepeatDays = __converters.toIntList(_tmp_1);
            final boolean _tmpIsEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Routine(_tmpId,_tmpSubjectId,_tmpSubjectName,_tmpTitle,_tmpHour,_tmpMinute,_tmpDurationMinutes,_tmpRepeatType,_tmpRepeatDays,_tmpIsEnabled,_tmpCreatedAt);
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
