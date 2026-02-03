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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.porashona.studymaster.data.database.Converters;
import com.porashona.studymaster.data.model.AcademicEvent;
import com.porashona.studymaster.data.model.EventType;
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
public final class AcademicEventDao_Impl implements AcademicEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AcademicEvent> __insertionAdapterOfAcademicEvent;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<AcademicEvent> __deletionAdapterOfAcademicEvent;

  private final EntityDeletionOrUpdateAdapter<AcademicEvent> __updateAdapterOfAcademicEvent;

  public AcademicEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAcademicEvent = new EntityInsertionAdapter<AcademicEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `academic_events` (`id`,`title`,`description`,`eventType`,`date`,`endDate`,`time`,`subjectId`,`subjectName`,`isHoliday`,`reminderEnabled`,`reminderMinutesBefore`,`color`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AcademicEvent entity) {
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
        final String _tmp = __converters.fromEventType(entity.getEventType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        statement.bindLong(5, entity.getDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getEndDate());
        }
        if (entity.getTime() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getTime());
        }
        if (entity.getSubjectId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSubjectName());
        }
        final int _tmp_1 = entity.isHoliday() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        statement.bindLong(12, entity.getReminderMinutesBefore());
        if (entity.getColor() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getColor());
        }
        statement.bindLong(14, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfAcademicEvent = new EntityDeletionOrUpdateAdapter<AcademicEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `academic_events` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AcademicEvent entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAcademicEvent = new EntityDeletionOrUpdateAdapter<AcademicEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `academic_events` SET `id` = ?,`title` = ?,`description` = ?,`eventType` = ?,`date` = ?,`endDate` = ?,`time` = ?,`subjectId` = ?,`subjectName` = ?,`isHoliday` = ?,`reminderEnabled` = ?,`reminderMinutesBefore` = ?,`color` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AcademicEvent entity) {
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
        final String _tmp = __converters.fromEventType(entity.getEventType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        statement.bindLong(5, entity.getDate());
        if (entity.getEndDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getEndDate());
        }
        if (entity.getTime() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getTime());
        }
        if (entity.getSubjectId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getSubjectId());
        }
        if (entity.getSubjectName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSubjectName());
        }
        final int _tmp_1 = entity.isHoliday() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.getReminderEnabled() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        statement.bindLong(12, entity.getReminderMinutesBefore());
        if (entity.getColor() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getColor());
        }
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final AcademicEvent event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAcademicEvent.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final AcademicEvent event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAcademicEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final AcademicEvent event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfAcademicEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AcademicEvent>> getAllEvents() {
    final String _sql = "SELECT * FROM academic_events ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"academic_events"}, new Callable<List<AcademicEvent>>() {
      @Override
      @NonNull
      public List<AcademicEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfIsHoliday = CursorUtil.getColumnIndexOrThrow(_cursor, "isHoliday");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AcademicEvent> _result = new ArrayList<AcademicEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AcademicEvent _item;
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
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __converters.toEventType(_tmp);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
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
            final boolean _tmpIsHoliday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsHoliday);
            _tmpIsHoliday = _tmp_1 != 0;
            final boolean _tmpReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_2 != 0;
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AcademicEvent(_tmpId,_tmpTitle,_tmpDescription,_tmpEventType,_tmpDate,_tmpEndDate,_tmpTime,_tmpSubjectId,_tmpSubjectName,_tmpIsHoliday,_tmpReminderEnabled,_tmpReminderMinutesBefore,_tmpColor,_tmpCreatedAt);
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
  public Object getEventById(final long id, final Continuation<? super AcademicEvent> $completion) {
    final String _sql = "SELECT * FROM academic_events WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AcademicEvent>() {
      @Override
      @Nullable
      public AcademicEvent call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfIsHoliday = CursorUtil.getColumnIndexOrThrow(_cursor, "isHoliday");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final AcademicEvent _result;
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
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __converters.toEventType(_tmp);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
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
            final boolean _tmpIsHoliday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsHoliday);
            _tmpIsHoliday = _tmp_1 != 0;
            final boolean _tmpReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_2 != 0;
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new AcademicEvent(_tmpId,_tmpTitle,_tmpDescription,_tmpEventType,_tmpDate,_tmpEndDate,_tmpTime,_tmpSubjectId,_tmpSubjectName,_tmpIsHoliday,_tmpReminderEnabled,_tmpReminderMinutesBefore,_tmpColor,_tmpCreatedAt);
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
  public Flow<List<AcademicEvent>> getUpcomingEvents(final long today) {
    final String _sql = "SELECT * FROM academic_events WHERE date >= ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, today);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"academic_events"}, new Callable<List<AcademicEvent>>() {
      @Override
      @NonNull
      public List<AcademicEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfIsHoliday = CursorUtil.getColumnIndexOrThrow(_cursor, "isHoliday");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AcademicEvent> _result = new ArrayList<AcademicEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AcademicEvent _item;
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
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __converters.toEventType(_tmp);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
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
            final boolean _tmpIsHoliday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsHoliday);
            _tmpIsHoliday = _tmp_1 != 0;
            final boolean _tmpReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_2 != 0;
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AcademicEvent(_tmpId,_tmpTitle,_tmpDescription,_tmpEventType,_tmpDate,_tmpEndDate,_tmpTime,_tmpSubjectId,_tmpSubjectName,_tmpIsHoliday,_tmpReminderEnabled,_tmpReminderMinutesBefore,_tmpColor,_tmpCreatedAt);
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
  public Flow<List<AcademicEvent>> getEventsInRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM academic_events WHERE date BETWEEN ? AND ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"academic_events"}, new Callable<List<AcademicEvent>>() {
      @Override
      @NonNull
      public List<AcademicEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfIsHoliday = CursorUtil.getColumnIndexOrThrow(_cursor, "isHoliday");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AcademicEvent> _result = new ArrayList<AcademicEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AcademicEvent _item;
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
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __converters.toEventType(_tmp);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
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
            final boolean _tmpIsHoliday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsHoliday);
            _tmpIsHoliday = _tmp_1 != 0;
            final boolean _tmpReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_2 != 0;
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AcademicEvent(_tmpId,_tmpTitle,_tmpDescription,_tmpEventType,_tmpDate,_tmpEndDate,_tmpTime,_tmpSubjectId,_tmpSubjectName,_tmpIsHoliday,_tmpReminderEnabled,_tmpReminderMinutesBefore,_tmpColor,_tmpCreatedAt);
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
  public Flow<List<AcademicEvent>> getEventsByType(final EventType type) {
    final String _sql = "SELECT * FROM academic_events WHERE eventType = ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromEventType(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"academic_events"}, new Callable<List<AcademicEvent>>() {
      @Override
      @NonNull
      public List<AcademicEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfIsHoliday = CursorUtil.getColumnIndexOrThrow(_cursor, "isHoliday");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AcademicEvent> _result = new ArrayList<AcademicEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AcademicEvent _item;
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
            final EventType _tmpEventType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __converters.toEventType(_tmp_1);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
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
            final boolean _tmpIsHoliday;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsHoliday);
            _tmpIsHoliday = _tmp_2 != 0;
            final boolean _tmpReminderEnabled;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_3 != 0;
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AcademicEvent(_tmpId,_tmpTitle,_tmpDescription,_tmpEventType,_tmpDate,_tmpEndDate,_tmpTime,_tmpSubjectId,_tmpSubjectName,_tmpIsHoliday,_tmpReminderEnabled,_tmpReminderMinutesBefore,_tmpColor,_tmpCreatedAt);
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
  public Flow<List<AcademicEvent>> getHolidays() {
    final String _sql = "SELECT * FROM academic_events WHERE isHoliday = 1 ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"academic_events"}, new Callable<List<AcademicEvent>>() {
      @Override
      @NonNull
      public List<AcademicEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfIsHoliday = CursorUtil.getColumnIndexOrThrow(_cursor, "isHoliday");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AcademicEvent> _result = new ArrayList<AcademicEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AcademicEvent _item;
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
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __converters.toEventType(_tmp);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
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
            final boolean _tmpIsHoliday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsHoliday);
            _tmpIsHoliday = _tmp_1 != 0;
            final boolean _tmpReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_2 != 0;
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AcademicEvent(_tmpId,_tmpTitle,_tmpDescription,_tmpEventType,_tmpDate,_tmpEndDate,_tmpTime,_tmpSubjectId,_tmpSubjectName,_tmpIsHoliday,_tmpReminderEnabled,_tmpReminderMinutesBefore,_tmpColor,_tmpCreatedAt);
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
  public Flow<List<AcademicEvent>> getEventsBySubject(final long subjectId) {
    final String _sql = "SELECT * FROM academic_events WHERE subjectId = ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"academic_events"}, new Callable<List<AcademicEvent>>() {
      @Override
      @NonNull
      public List<AcademicEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfIsHoliday = CursorUtil.getColumnIndexOrThrow(_cursor, "isHoliday");
          final int _cursorIndexOfReminderEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderEnabled");
          final int _cursorIndexOfReminderMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "reminderMinutesBefore");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<AcademicEvent> _result = new ArrayList<AcademicEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AcademicEvent _item;
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
            final EventType _tmpEventType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEventType);
            }
            _tmpEventType = __converters.toEventType(_tmp);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final Long _tmpEndDate;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmpEndDate = null;
            } else {
              _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            }
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
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
            final boolean _tmpIsHoliday;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsHoliday);
            _tmpIsHoliday = _tmp_1 != 0;
            final boolean _tmpReminderEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfReminderEnabled);
            _tmpReminderEnabled = _tmp_2 != 0;
            final int _tmpReminderMinutesBefore;
            _tmpReminderMinutesBefore = _cursor.getInt(_cursorIndexOfReminderMinutesBefore);
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new AcademicEvent(_tmpId,_tmpTitle,_tmpDescription,_tmpEventType,_tmpDate,_tmpEndDate,_tmpTime,_tmpSubjectId,_tmpSubjectName,_tmpIsHoliday,_tmpReminderEnabled,_tmpReminderMinutesBefore,_tmpColor,_tmpCreatedAt);
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
  public Flow<Integer> getUpcomingEventsCount(final long today) {
    final String _sql = "SELECT COUNT(*) FROM academic_events WHERE date >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, today);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"academic_events"}, new Callable<Integer>() {
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
