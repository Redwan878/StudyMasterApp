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
import com.porashona.studymaster.data.model.Goal;
import com.porashona.studymaster.data.model.GoalType;
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
public final class GoalDao_Impl implements GoalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Goal> __insertionAdapterOfGoal;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Goal> __deletionAdapterOfGoal;

  private final EntityDeletionOrUpdateAdapter<Goal> __updateAdapterOfGoal;

  private final SharedSQLiteStatement __preparedStmtOfAddMinutesToGoal;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsCompleted;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldCompletedGoals;

  public GoalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGoal = new EntityInsertionAdapter<Goal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `goals` (`id`,`title`,`targetMinutes`,`currentMinutes`,`subjectId`,`subjectName`,`goalType`,`isCompleted`,`createdAt`,`completedAt`,`date`,`streakCount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Goal entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        statement.bindLong(3, entity.getTargetMinutes());
        statement.bindLong(4, entity.getCurrentMinutes());
        if (entity.getSubjectId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSubjectName());
        }
        final String _tmp = __converters.fromGoalType(entity.getGoalType());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getCompletedAt());
        }
        if (entity.getDate() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDate());
        }
        statement.bindLong(12, entity.getStreakCount());
      }
    };
    this.__deletionAdapterOfGoal = new EntityDeletionOrUpdateAdapter<Goal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `goals` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Goal entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfGoal = new EntityDeletionOrUpdateAdapter<Goal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `goals` SET `id` = ?,`title` = ?,`targetMinutes` = ?,`currentMinutes` = ?,`subjectId` = ?,`subjectName` = ?,`goalType` = ?,`isCompleted` = ?,`createdAt` = ?,`completedAt` = ?,`date` = ?,`streakCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Goal entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        statement.bindLong(3, entity.getTargetMinutes());
        statement.bindLong(4, entity.getCurrentMinutes());
        if (entity.getSubjectId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSubjectName());
        }
        final String _tmp = __converters.fromGoalType(entity.getGoalType());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getCompletedAt());
        }
        if (entity.getDate() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDate());
        }
        statement.bindLong(12, entity.getStreakCount());
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfAddMinutesToGoal = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE goals SET currentMinutes = currentMinutes + ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE goals SET isCompleted = 1, completedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldCompletedGoals = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM goals WHERE date < ? AND isCompleted = 1";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Goal goal, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfGoal.insertAndReturnId(goal);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Goal goal, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfGoal.handle(goal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Goal goal, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfGoal.handle(goal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object addMinutesToGoal(final long goalId, final int minutes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAddMinutesToGoal.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, minutes);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, goalId);
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
          __preparedStmtOfAddMinutesToGoal.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsCompleted(final long goalId, final long completedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsCompleted.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, completedAt);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, goalId);
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
          __preparedStmtOfMarkAsCompleted.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldCompletedGoals(final String date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldCompletedGoals.acquire();
        int _argIndex = 1;
        if (date == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, date);
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
          __preparedStmtOfDeleteOldCompletedGoals.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Goal>> getAllGoals() {
    final String _sql = "SELECT * FROM goals ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"goals"}, new Callable<List<Goal>>() {
      @Override
      @NonNull
      public List<Goal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTargetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetMinutes");
          final int _cursorIndexOfCurrentMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "currentMinutes");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfGoalType = CursorUtil.getColumnIndexOrThrow(_cursor, "goalType");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStreakCount = CursorUtil.getColumnIndexOrThrow(_cursor, "streakCount");
          final List<Goal> _result = new ArrayList<Goal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Goal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpTargetMinutes;
            _tmpTargetMinutes = _cursor.getInt(_cursorIndexOfTargetMinutes);
            final int _tmpCurrentMinutes;
            _tmpCurrentMinutes = _cursor.getInt(_cursorIndexOfCurrentMinutes);
            final Long _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final GoalType _tmpGoalType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGoalType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGoalType);
            }
            _tmpGoalType = __converters.toGoalType(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpStreakCount;
            _tmpStreakCount = _cursor.getInt(_cursorIndexOfStreakCount);
            _item = new Goal(_tmpId,_tmpTitle,_tmpTargetMinutes,_tmpCurrentMinutes,_tmpSubjectId,_tmpSubjectName,_tmpGoalType,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpDate,_tmpStreakCount);
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
  public Object getGoalById(final long id, final Continuation<? super Goal> $completion) {
    final String _sql = "SELECT * FROM goals WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Goal>() {
      @Override
      @Nullable
      public Goal call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTargetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetMinutes");
          final int _cursorIndexOfCurrentMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "currentMinutes");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfGoalType = CursorUtil.getColumnIndexOrThrow(_cursor, "goalType");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStreakCount = CursorUtil.getColumnIndexOrThrow(_cursor, "streakCount");
          final Goal _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpTargetMinutes;
            _tmpTargetMinutes = _cursor.getInt(_cursorIndexOfTargetMinutes);
            final int _tmpCurrentMinutes;
            _tmpCurrentMinutes = _cursor.getInt(_cursorIndexOfCurrentMinutes);
            final Long _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final GoalType _tmpGoalType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGoalType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGoalType);
            }
            _tmpGoalType = __converters.toGoalType(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpStreakCount;
            _tmpStreakCount = _cursor.getInt(_cursorIndexOfStreakCount);
            _result = new Goal(_tmpId,_tmpTitle,_tmpTargetMinutes,_tmpCurrentMinutes,_tmpSubjectId,_tmpSubjectName,_tmpGoalType,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpDate,_tmpStreakCount);
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
  public Flow<List<Goal>> getGoalsForDate(final String date, final GoalType type) {
    final String _sql = "SELECT * FROM goals WHERE date = ? AND goalType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    _argIndex = 2;
    final String _tmp = __converters.fromGoalType(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"goals"}, new Callable<List<Goal>>() {
      @Override
      @NonNull
      public List<Goal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTargetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetMinutes");
          final int _cursorIndexOfCurrentMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "currentMinutes");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfGoalType = CursorUtil.getColumnIndexOrThrow(_cursor, "goalType");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStreakCount = CursorUtil.getColumnIndexOrThrow(_cursor, "streakCount");
          final List<Goal> _result = new ArrayList<Goal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Goal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpTargetMinutes;
            _tmpTargetMinutes = _cursor.getInt(_cursorIndexOfTargetMinutes);
            final int _tmpCurrentMinutes;
            _tmpCurrentMinutes = _cursor.getInt(_cursorIndexOfCurrentMinutes);
            final Long _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final GoalType _tmpGoalType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfGoalType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfGoalType);
            }
            _tmpGoalType = __converters.toGoalType(_tmp_1);
            final boolean _tmpIsCompleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_2 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpStreakCount;
            _tmpStreakCount = _cursor.getInt(_cursorIndexOfStreakCount);
            _item = new Goal(_tmpId,_tmpTitle,_tmpTargetMinutes,_tmpCurrentMinutes,_tmpSubjectId,_tmpSubjectName,_tmpGoalType,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpDate,_tmpStreakCount);
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
  public Flow<List<Goal>> getDailyGoals(final String date) {
    final String _sql = "SELECT * FROM goals WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"goals"}, new Callable<List<Goal>>() {
      @Override
      @NonNull
      public List<Goal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTargetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetMinutes");
          final int _cursorIndexOfCurrentMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "currentMinutes");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfGoalType = CursorUtil.getColumnIndexOrThrow(_cursor, "goalType");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStreakCount = CursorUtil.getColumnIndexOrThrow(_cursor, "streakCount");
          final List<Goal> _result = new ArrayList<Goal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Goal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpTargetMinutes;
            _tmpTargetMinutes = _cursor.getInt(_cursorIndexOfTargetMinutes);
            final int _tmpCurrentMinutes;
            _tmpCurrentMinutes = _cursor.getInt(_cursorIndexOfCurrentMinutes);
            final Long _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final GoalType _tmpGoalType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGoalType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGoalType);
            }
            _tmpGoalType = __converters.toGoalType(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpStreakCount;
            _tmpStreakCount = _cursor.getInt(_cursorIndexOfStreakCount);
            _item = new Goal(_tmpId,_tmpTitle,_tmpTargetMinutes,_tmpCurrentMinutes,_tmpSubjectId,_tmpSubjectName,_tmpGoalType,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpDate,_tmpStreakCount);
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
  public Flow<List<Goal>> getActiveGoals() {
    final String _sql = "SELECT * FROM goals WHERE isCompleted = 0 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"goals"}, new Callable<List<Goal>>() {
      @Override
      @NonNull
      public List<Goal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTargetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "targetMinutes");
          final int _cursorIndexOfCurrentMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "currentMinutes");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfGoalType = CursorUtil.getColumnIndexOrThrow(_cursor, "goalType");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStreakCount = CursorUtil.getColumnIndexOrThrow(_cursor, "streakCount");
          final List<Goal> _result = new ArrayList<Goal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Goal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final int _tmpTargetMinutes;
            _tmpTargetMinutes = _cursor.getInt(_cursorIndexOfTargetMinutes);
            final int _tmpCurrentMinutes;
            _tmpCurrentMinutes = _cursor.getInt(_cursorIndexOfCurrentMinutes);
            final Long _tmpSubjectId;
            if (_cursor.isNull(_cursorIndexOfSubjectId)) {
              _tmpSubjectId = null;
            } else {
              _tmpSubjectId = _cursor.getLong(_cursorIndexOfSubjectId);
            }
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final GoalType _tmpGoalType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGoalType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGoalType);
            }
            _tmpGoalType = __converters.toGoalType(_tmp);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpStreakCount;
            _tmpStreakCount = _cursor.getInt(_cursorIndexOfStreakCount);
            _item = new Goal(_tmpId,_tmpTitle,_tmpTargetMinutes,_tmpCurrentMinutes,_tmpSubjectId,_tmpSubjectName,_tmpGoalType,_tmpIsCompleted,_tmpCreatedAt,_tmpCompletedAt,_tmpDate,_tmpStreakCount);
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
  public Flow<Integer> getCompletedGoalsCount(final String startDate) {
    final String _sql = "SELECT COUNT(*) FROM goals WHERE isCompleted = 1 AND date >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (startDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, startDate);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"goals"}, new Callable<Integer>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
