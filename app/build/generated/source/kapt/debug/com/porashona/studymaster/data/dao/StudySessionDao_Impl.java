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
import com.porashona.studymaster.data.model.SessionType;
import com.porashona.studymaster.data.model.StudySession;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudySessionDao_Impl implements StudySessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StudySession> __insertionAdapterOfStudySession;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<StudySession> __deletionAdapterOfStudySession;

  private final EntityDeletionOrUpdateAdapter<StudySession> __updateAdapterOfStudySession;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public StudySessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudySession = new EntityInsertionAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `study_sessions` (`id`,`subjectId`,`subjectName`,`durationInSeconds`,`startTime`,`endTime`,`sessionType`,`completed`,`xpEarned`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSubjectId());
        if (entity.getSubjectName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectName());
        }
        statement.bindLong(4, entity.getDurationInSeconds());
        final Long _tmp = __converters.dateToTimestamp(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final String _tmp_2 = __converters.fromSessionType(entity.getSessionType());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        final int _tmp_3 = entity.getCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_3);
        statement.bindLong(9, entity.getXpEarned());
      }
    };
    this.__deletionAdapterOfStudySession = new EntityDeletionOrUpdateAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `study_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudySession = new EntityDeletionOrUpdateAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `study_sessions` SET `id` = ?,`subjectId` = ?,`subjectName` = ?,`durationInSeconds` = ?,`startTime` = ?,`endTime` = ?,`sessionType` = ?,`completed` = ?,`xpEarned` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSubjectId());
        if (entity.getSubjectName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubjectName());
        }
        statement.bindLong(4, entity.getDurationInSeconds());
        final Long _tmp = __converters.dateToTimestamp(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final String _tmp_2 = __converters.fromSessionType(entity.getSessionType());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_2);
        }
        final int _tmp_3 = entity.getCompleted() ? 1 : 0;
        statement.bindLong(8, _tmp_3);
        statement.bindLong(9, entity.getXpEarned());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM study_sessions";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final StudySession session, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStudySession.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final StudySession session, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStudySession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final StudySession session, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStudySession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
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
  public Flow<List<StudySession>> getAllSessions() {
    final String _sql = "SELECT * FROM study_sessions ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySession>>() {
      @Override
      @NonNull
      public List<StudySession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDurationInSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationInSeconds");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfSessionType = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionType");
          final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
          final int _cursorIndexOfXpEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "xpEarned");
          final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySession _item;
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
            final long _tmpDurationInSeconds;
            _tmpDurationInSeconds = _cursor.getLong(_cursorIndexOfDurationInSeconds);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.fromTimestamp(_tmp);
            final Date _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_1);
            final SessionType _tmpSessionType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSessionType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSessionType);
            }
            _tmpSessionType = __converters.toSessionType(_tmp_2);
            final boolean _tmpCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfCompleted);
            _tmpCompleted = _tmp_3 != 0;
            final int _tmpXpEarned;
            _tmpXpEarned = _cursor.getInt(_cursorIndexOfXpEarned);
            _item = new StudySession(_tmpId,_tmpSubjectId,_tmpSubjectName,_tmpDurationInSeconds,_tmpStartTime,_tmpEndTime,_tmpSessionType,_tmpCompleted,_tmpXpEarned);
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
  public Object getSessionById(final long id,
      final Continuation<? super StudySession> $completion) {
    final String _sql = "SELECT * FROM study_sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StudySession>() {
      @Override
      @Nullable
      public StudySession call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDurationInSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationInSeconds");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfSessionType = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionType");
          final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
          final int _cursorIndexOfXpEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "xpEarned");
          final StudySession _result;
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
            final long _tmpDurationInSeconds;
            _tmpDurationInSeconds = _cursor.getLong(_cursorIndexOfDurationInSeconds);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.fromTimestamp(_tmp);
            final Date _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_1);
            final SessionType _tmpSessionType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSessionType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSessionType);
            }
            _tmpSessionType = __converters.toSessionType(_tmp_2);
            final boolean _tmpCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfCompleted);
            _tmpCompleted = _tmp_3 != 0;
            final int _tmpXpEarned;
            _tmpXpEarned = _cursor.getInt(_cursorIndexOfXpEarned);
            _result = new StudySession(_tmpId,_tmpSubjectId,_tmpSubjectName,_tmpDurationInSeconds,_tmpStartTime,_tmpEndTime,_tmpSessionType,_tmpCompleted,_tmpXpEarned);
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
  public Flow<List<StudySession>> getSessionsBetween(final long startTime, final long endTime) {
    final String _sql = "SELECT * FROM study_sessions WHERE startTime >= ? AND startTime <= ? ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySession>>() {
      @Override
      @NonNull
      public List<StudySession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDurationInSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationInSeconds");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfSessionType = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionType");
          final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
          final int _cursorIndexOfXpEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "xpEarned");
          final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySession _item;
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
            final long _tmpDurationInSeconds;
            _tmpDurationInSeconds = _cursor.getLong(_cursorIndexOfDurationInSeconds);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.fromTimestamp(_tmp);
            final Date _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_1);
            final SessionType _tmpSessionType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSessionType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSessionType);
            }
            _tmpSessionType = __converters.toSessionType(_tmp_2);
            final boolean _tmpCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfCompleted);
            _tmpCompleted = _tmp_3 != 0;
            final int _tmpXpEarned;
            _tmpXpEarned = _cursor.getInt(_cursorIndexOfXpEarned);
            _item = new StudySession(_tmpId,_tmpSubjectId,_tmpSubjectName,_tmpDurationInSeconds,_tmpStartTime,_tmpEndTime,_tmpSessionType,_tmpCompleted,_tmpXpEarned);
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
  public Flow<List<StudySession>> getSessionsForDate(final long date) {
    final String _sql = "SELECT * FROM study_sessions WHERE date(startTime/1000, 'unixepoch', 'localtime') = date(?/1000, 'unixepoch', 'localtime')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<StudySession>>() {
      @Override
      @NonNull
      public List<StudySession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDurationInSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationInSeconds");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfSessionType = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionType");
          final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
          final int _cursorIndexOfXpEarned = CursorUtil.getColumnIndexOrThrow(_cursor, "xpEarned");
          final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySession _item;
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
            final long _tmpDurationInSeconds;
            _tmpDurationInSeconds = _cursor.getLong(_cursorIndexOfDurationInSeconds);
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.fromTimestamp(_tmp);
            final Date _tmpEndTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_1);
            final SessionType _tmpSessionType;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSessionType)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSessionType);
            }
            _tmpSessionType = __converters.toSessionType(_tmp_2);
            final boolean _tmpCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfCompleted);
            _tmpCompleted = _tmp_3 != 0;
            final int _tmpXpEarned;
            _tmpXpEarned = _cursor.getInt(_cursorIndexOfXpEarned);
            _item = new StudySession(_tmpId,_tmpSubjectId,_tmpSubjectName,_tmpDurationInSeconds,_tmpStartTime,_tmpEndTime,_tmpSessionType,_tmpCompleted,_tmpXpEarned);
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
  public Flow<Long> getTotalStudyTime() {
    final String _sql = "SELECT SUM(durationInSeconds) FROM study_sessions WHERE sessionType = 'WORK'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
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
  public Flow<Long> getTotalStudyTimeSince(final long startTime) {
    final String _sql = "SELECT SUM(durationInSeconds) FROM study_sessions WHERE sessionType = 'WORK' AND startTime >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
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
  public Flow<Integer> getTotalSessionCount() {
    final String _sql = "SELECT COUNT(*) FROM study_sessions WHERE sessionType = 'WORK'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<Integer>() {
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
  public Flow<Long> getTotalTimeForSubject(final long subjectId) {
    final String _sql = "SELECT SUM(durationInSeconds) FROM study_sessions WHERE subjectId = ? AND sessionType = 'WORK'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
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
  public Flow<List<SubjectTime>> getTimeBySubject() {
    final String _sql = "SELECT subjectName, SUM(durationInSeconds) as totalTime FROM study_sessions WHERE sessionType = 'WORK' GROUP BY subjectId ORDER BY totalTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<SubjectTime>>() {
      @Override
      @NonNull
      public List<SubjectTime> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSubjectName = 0;
          final int _cursorIndexOfTotalTime = 1;
          final List<SubjectTime> _result = new ArrayList<SubjectTime>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SubjectTime _item;
            final String _tmpSubjectName;
            if (_cursor.isNull(_cursorIndexOfSubjectName)) {
              _tmpSubjectName = null;
            } else {
              _tmpSubjectName = _cursor.getString(_cursorIndexOfSubjectName);
            }
            final long _tmpTotalTime;
            _tmpTotalTime = _cursor.getLong(_cursorIndexOfTotalTime);
            _item = new SubjectTime(_tmpSubjectName,_tmpTotalTime);
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
  public Flow<List<String>> getStudyDates() {
    final String _sql = "SELECT DISTINCT date(startTime/1000, 'unixepoch', 'localtime') as studyDate FROM study_sessions WHERE sessionType = 'WORK' ORDER BY studyDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_sessions"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            final String _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(0);
            }
            _item = _tmp;
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
