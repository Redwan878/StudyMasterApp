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
import com.porashona.studymaster.data.model.ResourceType;
import com.porashona.studymaster.data.model.StudyResource;
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
public final class StudyResourceDao_Impl implements StudyResourceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StudyResource> __insertionAdapterOfStudyResource;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<StudyResource> __deletionAdapterOfStudyResource;

  private final EntityDeletionOrUpdateAdapter<StudyResource> __updateAdapterOfStudyResource;

  private final SharedSQLiteStatement __preparedStmtOfIncrementVisitCount;

  private final SharedSQLiteStatement __preparedStmtOfSetFavorite;

  public StudyResourceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudyResource = new EntityInsertionAdapter<StudyResource>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `study_resources` (`id`,`title`,`url`,`type`,`subjectId`,`subjectName`,`description`,`thumbnail`,`isFavorite`,`visitCount`,`lastVisitedAt`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudyResource entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUrl());
        }
        final String _tmp = __converters.fromResourceType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
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
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        if (entity.getThumbnail() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getThumbnail());
        }
        final int _tmp_1 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getVisitCount());
        if (entity.getLastVisitedAt() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getLastVisitedAt());
        }
        statement.bindLong(12, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfStudyResource = new EntityDeletionOrUpdateAdapter<StudyResource>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `study_resources` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudyResource entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudyResource = new EntityDeletionOrUpdateAdapter<StudyResource>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `study_resources` SET `id` = ?,`title` = ?,`url` = ?,`type` = ?,`subjectId` = ?,`subjectName` = ?,`description` = ?,`thumbnail` = ?,`isFavorite` = ?,`visitCount` = ?,`lastVisitedAt` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudyResource entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUrl());
        }
        final String _tmp = __converters.fromResourceType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
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
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        if (entity.getThumbnail() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getThumbnail());
        }
        final int _tmp_1 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        statement.bindLong(10, entity.getVisitCount());
        if (entity.getLastVisitedAt() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getLastVisitedAt());
        }
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfIncrementVisitCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE study_resources SET visitCount = visitCount + 1, lastVisitedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE study_resources SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final StudyResource resource, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStudyResource.insertAndReturnId(resource);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final StudyResource resource, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStudyResource.handle(resource);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final StudyResource resource, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStudyResource.handle(resource);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object incrementVisitCount(final long id, final long time,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfIncrementVisitCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, time);
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
          __preparedStmtOfIncrementVisitCount.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setFavorite(final long id, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
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
          __preparedStmtOfSetFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StudyResource>> getAllResources() {
    final String _sql = "SELECT * FROM study_resources ORDER BY lastVisitedAt DESC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_resources"}, new Callable<List<StudyResource>>() {
      @Override
      @NonNull
      public List<StudyResource> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfLastVisitedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisitedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<StudyResource> _result = new ArrayList<StudyResource>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudyResource _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final ResourceType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toResourceType(_tmp);
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
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final Long _tmpLastVisitedAt;
            if (_cursor.isNull(_cursorIndexOfLastVisitedAt)) {
              _tmpLastVisitedAt = null;
            } else {
              _tmpLastVisitedAt = _cursor.getLong(_cursorIndexOfLastVisitedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new StudyResource(_tmpId,_tmpTitle,_tmpUrl,_tmpType,_tmpSubjectId,_tmpSubjectName,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpVisitCount,_tmpLastVisitedAt,_tmpCreatedAt);
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
  public Object getResourceById(final long id,
      final Continuation<? super StudyResource> $completion) {
    final String _sql = "SELECT * FROM study_resources WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<StudyResource>() {
      @Override
      @Nullable
      public StudyResource call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfLastVisitedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisitedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final StudyResource _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final ResourceType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toResourceType(_tmp);
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
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final Long _tmpLastVisitedAt;
            if (_cursor.isNull(_cursorIndexOfLastVisitedAt)) {
              _tmpLastVisitedAt = null;
            } else {
              _tmpLastVisitedAt = _cursor.getLong(_cursorIndexOfLastVisitedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new StudyResource(_tmpId,_tmpTitle,_tmpUrl,_tmpType,_tmpSubjectId,_tmpSubjectName,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpVisitCount,_tmpLastVisitedAt,_tmpCreatedAt);
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
  public Flow<List<StudyResource>> getResourcesBySubject(final long subjectId) {
    final String _sql = "SELECT * FROM study_resources WHERE subjectId = ? ORDER BY visitCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_resources"}, new Callable<List<StudyResource>>() {
      @Override
      @NonNull
      public List<StudyResource> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfLastVisitedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisitedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<StudyResource> _result = new ArrayList<StudyResource>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudyResource _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final ResourceType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toResourceType(_tmp);
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
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final Long _tmpLastVisitedAt;
            if (_cursor.isNull(_cursorIndexOfLastVisitedAt)) {
              _tmpLastVisitedAt = null;
            } else {
              _tmpLastVisitedAt = _cursor.getLong(_cursorIndexOfLastVisitedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new StudyResource(_tmpId,_tmpTitle,_tmpUrl,_tmpType,_tmpSubjectId,_tmpSubjectName,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpVisitCount,_tmpLastVisitedAt,_tmpCreatedAt);
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
  public Flow<List<StudyResource>> getResourcesByType(final ResourceType type) {
    final String _sql = "SELECT * FROM study_resources WHERE type = ? ORDER BY visitCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromResourceType(type);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_resources"}, new Callable<List<StudyResource>>() {
      @Override
      @NonNull
      public List<StudyResource> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfLastVisitedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisitedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<StudyResource> _result = new ArrayList<StudyResource>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudyResource _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final ResourceType _tmpType;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toResourceType(_tmp_1);
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
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_2 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final Long _tmpLastVisitedAt;
            if (_cursor.isNull(_cursorIndexOfLastVisitedAt)) {
              _tmpLastVisitedAt = null;
            } else {
              _tmpLastVisitedAt = _cursor.getLong(_cursorIndexOfLastVisitedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new StudyResource(_tmpId,_tmpTitle,_tmpUrl,_tmpType,_tmpSubjectId,_tmpSubjectName,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpVisitCount,_tmpLastVisitedAt,_tmpCreatedAt);
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
  public Flow<List<StudyResource>> getFavoriteResources() {
    final String _sql = "SELECT * FROM study_resources WHERE isFavorite = 1 ORDER BY visitCount DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_resources"}, new Callable<List<StudyResource>>() {
      @Override
      @NonNull
      public List<StudyResource> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfLastVisitedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisitedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<StudyResource> _result = new ArrayList<StudyResource>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudyResource _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final ResourceType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toResourceType(_tmp);
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
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final Long _tmpLastVisitedAt;
            if (_cursor.isNull(_cursorIndexOfLastVisitedAt)) {
              _tmpLastVisitedAt = null;
            } else {
              _tmpLastVisitedAt = _cursor.getLong(_cursorIndexOfLastVisitedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new StudyResource(_tmpId,_tmpTitle,_tmpUrl,_tmpType,_tmpSubjectId,_tmpSubjectName,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpVisitCount,_tmpLastVisitedAt,_tmpCreatedAt);
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
  public Flow<List<StudyResource>> searchResources(final String query) {
    final String _sql = "SELECT * FROM study_resources WHERE title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_resources"}, new Callable<List<StudyResource>>() {
      @Override
      @NonNull
      public List<StudyResource> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfVisitCount = CursorUtil.getColumnIndexOrThrow(_cursor, "visitCount");
          final int _cursorIndexOfLastVisitedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastVisitedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<StudyResource> _result = new ArrayList<StudyResource>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudyResource _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final ResourceType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toResourceType(_tmp);
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
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            final int _tmpVisitCount;
            _tmpVisitCount = _cursor.getInt(_cursorIndexOfVisitCount);
            final Long _tmpLastVisitedAt;
            if (_cursor.isNull(_cursorIndexOfLastVisitedAt)) {
              _tmpLastVisitedAt = null;
            } else {
              _tmpLastVisitedAt = _cursor.getLong(_cursorIndexOfLastVisitedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new StudyResource(_tmpId,_tmpTitle,_tmpUrl,_tmpType,_tmpSubjectId,_tmpSubjectName,_tmpDescription,_tmpThumbnail,_tmpIsFavorite,_tmpVisitCount,_tmpLastVisitedAt,_tmpCreatedAt);
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
