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
import com.porashona.studymaster.data.model.Exam;
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
public final class ExamDao_Impl implements ExamDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Exam> __insertionAdapterOfExam;

  private final EntityDeletionOrUpdateAdapter<Exam> __deletionAdapterOfExam;

  private final EntityDeletionOrUpdateAdapter<Exam> __updateAdapterOfExam;

  private final SharedSQLiteStatement __preparedStmtOfUpdateProgress;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsCompleted;

  public ExamDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExam = new EntityInsertionAdapter<Exam>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `exams` (`id`,`name`,`subjectId`,`subjectName`,`examDate`,`examTime`,`venue`,`notes`,`syllabus`,`preparationProgress`,`isCompleted`,`result`,`reflection`,`reminderEnabled`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Exam entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getSubjectId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSubjectName());
        }
        statement.bindLong(5, entity.getExamDate());
        if (entity.getExamTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getExamTime());
        }
        if (entity.getVenue() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getVenue());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        if (entity.getSyllabus() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSyllabus());
        }
        statement.bindLong(10, entity.getPreparationProgress());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(11, _tmp);
        if (entity.getResult() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getResult());
        }
        if (entity.getReflection() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getReflection());
        }
        final int _tmp_1 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        statement.bindLong(15, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfExam = new EntityDeletionOrUpdateAdapter<Exam>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `exams` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Exam entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfExam = new EntityDeletionOrUpdateAdapter<Exam>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `exams` SET `id` = ?,`name` = ?,`subjectId` = ?,`subjectName` = ?,`examDate` = ?,`examTime` = ?,`venue` = ?,`notes` = ?,`syllabus` = ?,`preparationProgress` = ?,`isCompleted` = ?,`result` = ?,`reflection` = ?,`reminderEnabled` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Exam entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getSubjectId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSubjectName());
        }
        statement.bindLong(5, entity.getExamDate());
        if (entity.getExamTime() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getExamTime());
        }
        if (entity.getVenue() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getVenue());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        if (entity.getSyllabus() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSyllabus());
        }
        statement.bindLong(10, entity.getPreparationProgress());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(11, _tmp);
        if (entity.getResult() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getResult());
        }
        if (entity.getReflection() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getReflection());
        }
        final int _tmp_1 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        statement.bindLong(15, entity.getCreatedAt());
        statement.bindLong(16, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateProgress = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE exams SET preparationProgress = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE exams SET isCompleted = 1, result = ?, reflection = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Exam exam, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfExam.insertAndReturnId(exam);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Exam exam, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfExam.handle(exam);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Exam exam, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfExam.handle(exam);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProgress(final long examId, final int progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateProgress.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, progress);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, examId);
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
          __preparedStmtOfUpdateProgress.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsCompleted(final long examId, final String result, final String reflection,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsCompleted.acquire();
        int _argIndex = 1;
        if (result == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, result);
        }
        _argIndex = 2;
        if (reflection == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, reflection);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, examId);
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
  public Flow<List<Exam>> getAllExams() {
    final String _sql = "SELECT * FROM exams ORDER BY examDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"exams"}, new Callable<List<Exam>>() {
      @Override
      @NonNull
      public List<Exam> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfExamDate = CursorUtil.getColumnIndexOrThrow(_cursor, "examDate");
          final int _cursorIndexOfExamTime = CursorUtil.getColumnIndexOrThrow(_cursor, "examTime");
          final int _cursorIndexOfVenue = CursorUtil.getColumnIndexOrThrow(_cursor, "venue");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfSyllabus = CursorUtil.getColumnIndexOrThrow(_cursor, "syllabus");
          final int _cursorIndexOfPreparationProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationProgress");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exam> _result = new ArrayList<Exam>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exam _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
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
            final long _tmpExamDate;
            _tmpExamDate = _cursor.getLong(_cursorIndexOfExamDate);
            final String _tmpExamTime;
            if (_cursor.isNull(_cursorIndexOfExamTime)) {
              _tmpExamTime = null;
            } else {
              _tmpExamTime = _cursor.getString(_cursorIndexOfExamTime);
            }
            final String _tmpVenue;
            if (_cursor.isNull(_cursorIndexOfVenue)) {
              _tmpVenue = null;
            } else {
              _tmpVenue = _cursor.getString(_cursorIndexOfVenue);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpSyllabus;
            if (_cursor.isNull(_cursorIndexOfSyllabus)) {
              _tmpSyllabus = null;
            } else {
              _tmpSyllabus = _cursor.getString(_cursorIndexOfSyllabus);
            }
            final int _tmpPreparationProgress;
            _tmpPreparationProgress = _cursor.getInt(_cursorIndexOfPreparationProgress);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exam(_tmpId,_tmpName,_tmpSubjectId,_tmpSubjectName,_tmpExamDate,_tmpExamTime,_tmpVenue,_tmpNotes,_tmpSyllabus,_tmpPreparationProgress,_tmpIsCompleted,_tmpResult,_tmpReflection,_tmpReminderEnabled,_tmpCreatedAt);
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
  public Object getExamById(final long id, final Continuation<? super Exam> $completion) {
    final String _sql = "SELECT * FROM exams WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Exam>() {
      @Override
      @Nullable
      public Exam call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfExamDate = CursorUtil.getColumnIndexOrThrow(_cursor, "examDate");
          final int _cursorIndexOfExamTime = CursorUtil.getColumnIndexOrThrow(_cursor, "examTime");
          final int _cursorIndexOfVenue = CursorUtil.getColumnIndexOrThrow(_cursor, "venue");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfSyllabus = CursorUtil.getColumnIndexOrThrow(_cursor, "syllabus");
          final int _cursorIndexOfPreparationProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationProgress");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Exam _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
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
            final long _tmpExamDate;
            _tmpExamDate = _cursor.getLong(_cursorIndexOfExamDate);
            final String _tmpExamTime;
            if (_cursor.isNull(_cursorIndexOfExamTime)) {
              _tmpExamTime = null;
            } else {
              _tmpExamTime = _cursor.getString(_cursorIndexOfExamTime);
            }
            final String _tmpVenue;
            if (_cursor.isNull(_cursorIndexOfVenue)) {
              _tmpVenue = null;
            } else {
              _tmpVenue = _cursor.getString(_cursorIndexOfVenue);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpSyllabus;
            if (_cursor.isNull(_cursorIndexOfSyllabus)) {
              _tmpSyllabus = null;
            } else {
              _tmpSyllabus = _cursor.getString(_cursorIndexOfSyllabus);
            }
            final int _tmpPreparationProgress;
            _tmpPreparationProgress = _cursor.getInt(_cursorIndexOfPreparationProgress);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Exam(_tmpId,_tmpName,_tmpSubjectId,_tmpSubjectName,_tmpExamDate,_tmpExamTime,_tmpVenue,_tmpNotes,_tmpSyllabus,_tmpPreparationProgress,_tmpIsCompleted,_tmpResult,_tmpReflection,_tmpReminderEnabled,_tmpCreatedAt);
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
  public Flow<List<Exam>> getUpcomingExams(final long today) {
    final String _sql = "SELECT * FROM exams WHERE examDate >= ? AND isCompleted = 0 ORDER BY examDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, today);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"exams"}, new Callable<List<Exam>>() {
      @Override
      @NonNull
      public List<Exam> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfExamDate = CursorUtil.getColumnIndexOrThrow(_cursor, "examDate");
          final int _cursorIndexOfExamTime = CursorUtil.getColumnIndexOrThrow(_cursor, "examTime");
          final int _cursorIndexOfVenue = CursorUtil.getColumnIndexOrThrow(_cursor, "venue");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfSyllabus = CursorUtil.getColumnIndexOrThrow(_cursor, "syllabus");
          final int _cursorIndexOfPreparationProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationProgress");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exam> _result = new ArrayList<Exam>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exam _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
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
            final long _tmpExamDate;
            _tmpExamDate = _cursor.getLong(_cursorIndexOfExamDate);
            final String _tmpExamTime;
            if (_cursor.isNull(_cursorIndexOfExamTime)) {
              _tmpExamTime = null;
            } else {
              _tmpExamTime = _cursor.getString(_cursorIndexOfExamTime);
            }
            final String _tmpVenue;
            if (_cursor.isNull(_cursorIndexOfVenue)) {
              _tmpVenue = null;
            } else {
              _tmpVenue = _cursor.getString(_cursorIndexOfVenue);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpSyllabus;
            if (_cursor.isNull(_cursorIndexOfSyllabus)) {
              _tmpSyllabus = null;
            } else {
              _tmpSyllabus = _cursor.getString(_cursorIndexOfSyllabus);
            }
            final int _tmpPreparationProgress;
            _tmpPreparationProgress = _cursor.getInt(_cursorIndexOfPreparationProgress);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exam(_tmpId,_tmpName,_tmpSubjectId,_tmpSubjectName,_tmpExamDate,_tmpExamTime,_tmpVenue,_tmpNotes,_tmpSyllabus,_tmpPreparationProgress,_tmpIsCompleted,_tmpResult,_tmpReflection,_tmpReminderEnabled,_tmpCreatedAt);
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
  public Flow<List<Exam>> getCompletedExams() {
    final String _sql = "SELECT * FROM exams WHERE isCompleted = 1 ORDER BY examDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"exams"}, new Callable<List<Exam>>() {
      @Override
      @NonNull
      public List<Exam> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfExamDate = CursorUtil.getColumnIndexOrThrow(_cursor, "examDate");
          final int _cursorIndexOfExamTime = CursorUtil.getColumnIndexOrThrow(_cursor, "examTime");
          final int _cursorIndexOfVenue = CursorUtil.getColumnIndexOrThrow(_cursor, "venue");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfSyllabus = CursorUtil.getColumnIndexOrThrow(_cursor, "syllabus");
          final int _cursorIndexOfPreparationProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationProgress");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exam> _result = new ArrayList<Exam>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exam _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
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
            final long _tmpExamDate;
            _tmpExamDate = _cursor.getLong(_cursorIndexOfExamDate);
            final String _tmpExamTime;
            if (_cursor.isNull(_cursorIndexOfExamTime)) {
              _tmpExamTime = null;
            } else {
              _tmpExamTime = _cursor.getString(_cursorIndexOfExamTime);
            }
            final String _tmpVenue;
            if (_cursor.isNull(_cursorIndexOfVenue)) {
              _tmpVenue = null;
            } else {
              _tmpVenue = _cursor.getString(_cursorIndexOfVenue);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpSyllabus;
            if (_cursor.isNull(_cursorIndexOfSyllabus)) {
              _tmpSyllabus = null;
            } else {
              _tmpSyllabus = _cursor.getString(_cursorIndexOfSyllabus);
            }
            final int _tmpPreparationProgress;
            _tmpPreparationProgress = _cursor.getInt(_cursorIndexOfPreparationProgress);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exam(_tmpId,_tmpName,_tmpSubjectId,_tmpSubjectName,_tmpExamDate,_tmpExamTime,_tmpVenue,_tmpNotes,_tmpSyllabus,_tmpPreparationProgress,_tmpIsCompleted,_tmpResult,_tmpReflection,_tmpReminderEnabled,_tmpCreatedAt);
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
  public Flow<List<Exam>> getExamsBySubject(final long subjectId) {
    final String _sql = "SELECT * FROM exams WHERE subjectId = ? ORDER BY examDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"exams"}, new Callable<List<Exam>>() {
      @Override
      @NonNull
      public List<Exam> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfExamDate = CursorUtil.getColumnIndexOrThrow(_cursor, "examDate");
          final int _cursorIndexOfExamTime = CursorUtil.getColumnIndexOrThrow(_cursor, "examTime");
          final int _cursorIndexOfVenue = CursorUtil.getColumnIndexOrThrow(_cursor, "venue");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfSyllabus = CursorUtil.getColumnIndexOrThrow(_cursor, "syllabus");
          final int _cursorIndexOfPreparationProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationProgress");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exam> _result = new ArrayList<Exam>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exam _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
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
            final long _tmpExamDate;
            _tmpExamDate = _cursor.getLong(_cursorIndexOfExamDate);
            final String _tmpExamTime;
            if (_cursor.isNull(_cursorIndexOfExamTime)) {
              _tmpExamTime = null;
            } else {
              _tmpExamTime = _cursor.getString(_cursorIndexOfExamTime);
            }
            final String _tmpVenue;
            if (_cursor.isNull(_cursorIndexOfVenue)) {
              _tmpVenue = null;
            } else {
              _tmpVenue = _cursor.getString(_cursorIndexOfVenue);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpSyllabus;
            if (_cursor.isNull(_cursorIndexOfSyllabus)) {
              _tmpSyllabus = null;
            } else {
              _tmpSyllabus = _cursor.getString(_cursorIndexOfSyllabus);
            }
            final int _tmpPreparationProgress;
            _tmpPreparationProgress = _cursor.getInt(_cursorIndexOfPreparationProgress);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exam(_tmpId,_tmpName,_tmpSubjectId,_tmpSubjectName,_tmpExamDate,_tmpExamTime,_tmpVenue,_tmpNotes,_tmpSyllabus,_tmpPreparationProgress,_tmpIsCompleted,_tmpResult,_tmpReflection,_tmpReminderEnabled,_tmpCreatedAt);
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
  public Flow<List<Exam>> getExamsInRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM exams WHERE examDate BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"exams"}, new Callable<List<Exam>>() {
      @Override
      @NonNull
      public List<Exam> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfExamDate = CursorUtil.getColumnIndexOrThrow(_cursor, "examDate");
          final int _cursorIndexOfExamTime = CursorUtil.getColumnIndexOrThrow(_cursor, "examTime");
          final int _cursorIndexOfVenue = CursorUtil.getColumnIndexOrThrow(_cursor, "venue");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfSyllabus = CursorUtil.getColumnIndexOrThrow(_cursor, "syllabus");
          final int _cursorIndexOfPreparationProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationProgress");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfReflection = CursorUtil.getColumnIndexOrThrow(_cursor, "reflection");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Exam> _result = new ArrayList<Exam>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Exam _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
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
            final long _tmpExamDate;
            _tmpExamDate = _cursor.getLong(_cursorIndexOfExamDate);
            final String _tmpExamTime;
            if (_cursor.isNull(_cursorIndexOfExamTime)) {
              _tmpExamTime = null;
            } else {
              _tmpExamTime = _cursor.getString(_cursorIndexOfExamTime);
            }
            final String _tmpVenue;
            if (_cursor.isNull(_cursorIndexOfVenue)) {
              _tmpVenue = null;
            } else {
              _tmpVenue = _cursor.getString(_cursorIndexOfVenue);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpSyllabus;
            if (_cursor.isNull(_cursorIndexOfSyllabus)) {
              _tmpSyllabus = null;
            } else {
              _tmpSyllabus = _cursor.getString(_cursorIndexOfSyllabus);
            }
            final int _tmpPreparationProgress;
            _tmpPreparationProgress = _cursor.getInt(_cursorIndexOfPreparationProgress);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final String _tmpResult;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmpResult = null;
            } else {
              _tmpResult = _cursor.getString(_cursorIndexOfResult);
            }
            final String _tmpReflection;
            if (_cursor.isNull(_cursorIndexOfReflection)) {
              _tmpReflection = null;
            } else {
              _tmpReflection = _cursor.getString(_cursorIndexOfReflection);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Exam(_tmpId,_tmpName,_tmpSubjectId,_tmpSubjectName,_tmpExamDate,_tmpExamTime,_tmpVenue,_tmpNotes,_tmpSyllabus,_tmpPreparationProgress,_tmpIsCompleted,_tmpResult,_tmpReflection,_tmpReminderEnabled,_tmpCreatedAt);
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
  public Flow<Integer> getUpcomingExamsCount(final long today) {
    final String _sql = "SELECT COUNT(*) FROM exams WHERE examDate >= ? AND isCompleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, today);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"exams"}, new Callable<Integer>() {
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
