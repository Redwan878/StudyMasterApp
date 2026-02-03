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
import com.porashona.studymaster.data.model.RecurringType;
import com.porashona.studymaster.data.model.Task;
import com.porashona.studymaster.data.model.TaskPriority;
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
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsCompleted;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsIncomplete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldCompletedTasks;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tasks` (`id`,`title`,`description`,`subjectId`,`subjectName`,`priority`,`dueDate`,`dueTime`,`isCompleted`,`isRecurring`,`recurringType`,`parentTaskId`,`xpReward`,`createdAt`,`completedAt`,`reminderEnabled`,`reminderTime`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getSubjectId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSubjectName());
        }
        final String _tmp = __converters.fromTaskPriority(entity.getPriority());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        if (entity.getDueDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getDueDate());
        }
        if (entity.getDueTime() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDueTime());
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        final int _tmp_2 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        final String _tmp_3 = __converters.fromRecurringType(entity.getRecurringType());
        if (_tmp_3 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_3);
        }
        if (entity.getParentTaskId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getParentTaskId());
        }
        statement.bindLong(13, entity.getXpReward());
        statement.bindLong(14, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getCompletedAt());
        }
        final int _tmp_4 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(16, _tmp_4);
        if (entity.getReminderTime() == null) {
          statement.bindNull(17);
        } else {
          statement.bindLong(17, entity.getReminderTime());
        }
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`description` = ?,`subjectId` = ?,`subjectName` = ?,`priority` = ?,`dueDate` = ?,`dueTime` = ?,`isCompleted` = ?,`isRecurring` = ?,`recurringType` = ?,`parentTaskId` = ?,`xpReward` = ?,`createdAt` = ?,`completedAt` = ?,`reminderEnabled` = ?,`reminderTime` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getSubjectId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSubjectName());
        }
        final String _tmp = __converters.fromTaskPriority(entity.getPriority());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        if (entity.getDueDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getDueDate());
        }
        if (entity.getDueTime() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDueTime());
        }
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        final int _tmp_2 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        final String _tmp_3 = __converters.fromRecurringType(entity.getRecurringType());
        if (_tmp_3 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_3);
        }
        if (entity.getParentTaskId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getParentTaskId());
        }
        statement.bindLong(13, entity.getXpReward());
        statement.bindLong(14, entity.getCreatedAt());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, entity.getCompletedAt());
        }
        final int _tmp_4 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(16, _tmp_4);
        if (entity.getReminderTime() == null) {
          statement.bindNull(17);
        } else {
          statement.bindLong(17, entity.getReminderTime());
        }
        statement.bindLong(18, entity.getId());
      }
    };
    this.__preparedStmtOfMarkAsCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tasks SET isCompleted = 1, completedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsIncomplete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tasks SET isCompleted = 0, completedAt = NULL WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldCompletedTasks = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tasks WHERE isCompleted = 1 AND completedAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Task task, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTask.insertAndReturnId(task);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsCompleted(final long taskId, final long completedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsCompleted.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, completedAt);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, taskId);
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
  public Object markAsIncomplete(final long taskId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsIncomplete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, taskId);
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
          __preparedStmtOfMarkAsIncomplete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldCompletedTasks(final long before,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldCompletedTasks.acquire();
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
          __preparedStmtOfDeleteOldCompletedTasks.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Task>> getAllTasks() {
    final String _sql = "SELECT * FROM tasks WHERE parentTaskId IS NULL ORDER BY isCompleted ASC, priority DESC, dueDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Object getTaskById(final long id, final Continuation<? super Task> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Task>() {
      @Override
      @Nullable
      public Task call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final Task _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _result = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Flow<List<Task>> getSubtasks(final long parentId) {
    final String _sql = "SELECT * FROM tasks WHERE parentTaskId = ? ORDER BY isCompleted ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, parentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Flow<List<Task>> getPendingTasks() {
    final String _sql = "SELECT * FROM tasks WHERE isCompleted = 0 AND parentTaskId IS NULL ORDER BY priority DESC, dueDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Flow<List<Task>> getCompletedTasks() {
    final String _sql = "SELECT * FROM tasks WHERE isCompleted = 1 ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Flow<List<Task>> getOverdueTasks(final long date) {
    final String _sql = "SELECT * FROM tasks WHERE dueDate <= ? AND isCompleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Flow<List<Task>> getTasksForDateRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM tasks WHERE dueDate BETWEEN ? AND ? AND isCompleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Flow<List<Task>> getTasksBySubject(final long subjectId) {
    final String _sql = "SELECT * FROM tasks WHERE subjectId = ? AND isCompleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
          final int _cursorIndexOfDueTime = CursorUtil.getColumnIndexOrThrow(_cursor, "dueTime");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringType");
          final int _cursorIndexOfParentTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentTaskId");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderTime = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderTime");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
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
            final TaskPriority _tmpPriority;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPriority);
            }
            _tmpPriority = __converters.toTaskPriority(_tmp);
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpDueTime;
            if (_cursor.isNull(_cursorIndexOfDueTime)) {
              _tmpDueTime = null;
            } else {
              _tmpDueTime = _cursor.getString(_cursorIndexOfDueTime);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsRecurring;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_2 != 0;
            final RecurringType _tmpRecurringType;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfRecurringType)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfRecurringType);
            }
            _tmpRecurringType = __converters.toRecurringType(_tmp_3);
            final Long _tmpParentTaskId;
            if (_cursor.isNull(_cursorIndexOfParentTaskId)) {
              _tmpParentTaskId = null;
            } else {
              _tmpParentTaskId = _cursor.getLong(_cursorIndexOfParentTaskId);
            }
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final boolean _tmpReminderEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_4 != 0;
            final Long _tmpReminderTime;
            if (_cursor.isNull(_cursorIndexOfReminderTime)) {
              _tmpReminderTime = null;
            } else {
              _tmpReminderTime = _cursor.getLong(_cursorIndexOfReminderTime);
            }
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpSubjectId,_tmpSubjectName,_tmpPriority,_tmpDueDate,_tmpDueTime,_tmpIsCompleted,_tmpIsRecurring,_tmpRecurringType,_tmpParentTaskId,_tmpXpReward,_tmpCreatedAt,_tmpCompletedAt,_tmpReminderEnabled,_tmpReminderTime);
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
  public Flow<Integer> getPendingTasksCount() {
    final String _sql = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<Integer>() {
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
  public Flow<Integer> getCompletedTasksCountSince(final long since) {
    final String _sql = "SELECT COUNT(*) FROM tasks WHERE isCompleted = 1 AND completedAt >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, since);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<Integer>() {
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
