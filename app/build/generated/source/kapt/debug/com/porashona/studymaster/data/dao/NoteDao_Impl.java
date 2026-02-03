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
import com.porashona.studymaster.data.model.Note;
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
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Note> __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter<Note> __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter<Note> __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfSetFavorite;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTimestamp;

  public NoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `notes` (`id`,`title`,`content`,`htmlContent`,`subjectId`,`subjectName`,`sessionId`,`isFavorite`,`color`,`imagesPaths`,`voiceNotePath`,`tags`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Note entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getContent() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContent());
        }
        if (entity.getHtmlContent() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getHtmlContent());
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
        if (entity.getSessionId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getSessionId());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getColor() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getColor());
        }
        if (entity.getImagesPaths() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getImagesPaths());
        }
        if (entity.getVoiceNotePath() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getVoiceNotePath());
        }
        if (entity.getTags() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTags());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `notes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Note entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`title` = ?,`content` = ?,`htmlContent` = ?,`subjectId` = ?,`subjectName` = ?,`sessionId` = ?,`isFavorite` = ?,`color` = ?,`imagesPaths` = ?,`voiceNotePath` = ?,`tags` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Note entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getContent() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContent());
        }
        if (entity.getHtmlContent() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getHtmlContent());
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
        if (entity.getSessionId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getSessionId());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getColor() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getColor());
        }
        if (entity.getImagesPaths() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getImagesPaths());
        }
        if (entity.getVoiceNotePath() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getVoiceNotePath());
        }
        if (entity.getTags() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTags());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfSetFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notes SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTimestamp = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notes SET updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Note note, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfNote.insertAndReturnId(note);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Note note, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNote.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Note note, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNote.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setFavorite(final long noteId, final boolean isFavorite,
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
        _stmt.bindLong(_argIndex, noteId);
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
  public Object updateTimestamp(final long noteId, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTimestamp.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, noteId);
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
          __preparedStmtOfUpdateTimestamp.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Note>> getAllNotes() {
    final String _sql = "SELECT * FROM notes ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<Note>>() {
      @Override
      @NonNull
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfHtmlContent = CursorUtil.getColumnIndexOrThrow(_cursor, "htmlContent");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfImagesPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "imagesPaths");
          final int _cursorIndexOfVoiceNotePath = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNotePath");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Note _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpHtmlContent;
            if (_cursor.isNull(_cursorIndexOfHtmlContent)) {
              _tmpHtmlContent = null;
            } else {
              _tmpHtmlContent = _cursor.getString(_cursorIndexOfHtmlContent);
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
            final Long _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final String _tmpImagesPaths;
            if (_cursor.isNull(_cursorIndexOfImagesPaths)) {
              _tmpImagesPaths = null;
            } else {
              _tmpImagesPaths = _cursor.getString(_cursorIndexOfImagesPaths);
            }
            final String _tmpVoiceNotePath;
            if (_cursor.isNull(_cursorIndexOfVoiceNotePath)) {
              _tmpVoiceNotePath = null;
            } else {
              _tmpVoiceNotePath = _cursor.getString(_cursorIndexOfVoiceNotePath);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Note(_tmpId,_tmpTitle,_tmpContent,_tmpHtmlContent,_tmpSubjectId,_tmpSubjectName,_tmpSessionId,_tmpIsFavorite,_tmpColor,_tmpImagesPaths,_tmpVoiceNotePath,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getNoteById(final long id, final Continuation<? super Note> $completion) {
    final String _sql = "SELECT * FROM notes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Note>() {
      @Override
      @Nullable
      public Note call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfHtmlContent = CursorUtil.getColumnIndexOrThrow(_cursor, "htmlContent");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfImagesPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "imagesPaths");
          final int _cursorIndexOfVoiceNotePath = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNotePath");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final Note _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpHtmlContent;
            if (_cursor.isNull(_cursorIndexOfHtmlContent)) {
              _tmpHtmlContent = null;
            } else {
              _tmpHtmlContent = _cursor.getString(_cursorIndexOfHtmlContent);
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
            final Long _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final String _tmpImagesPaths;
            if (_cursor.isNull(_cursorIndexOfImagesPaths)) {
              _tmpImagesPaths = null;
            } else {
              _tmpImagesPaths = _cursor.getString(_cursorIndexOfImagesPaths);
            }
            final String _tmpVoiceNotePath;
            if (_cursor.isNull(_cursorIndexOfVoiceNotePath)) {
              _tmpVoiceNotePath = null;
            } else {
              _tmpVoiceNotePath = _cursor.getString(_cursorIndexOfVoiceNotePath);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new Note(_tmpId,_tmpTitle,_tmpContent,_tmpHtmlContent,_tmpSubjectId,_tmpSubjectName,_tmpSessionId,_tmpIsFavorite,_tmpColor,_tmpImagesPaths,_tmpVoiceNotePath,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Note>> getNotesBySubject(final long subjectId) {
    final String _sql = "SELECT * FROM notes WHERE subjectId = ? ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<Note>>() {
      @Override
      @NonNull
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfHtmlContent = CursorUtil.getColumnIndexOrThrow(_cursor, "htmlContent");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfImagesPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "imagesPaths");
          final int _cursorIndexOfVoiceNotePath = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNotePath");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Note _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpHtmlContent;
            if (_cursor.isNull(_cursorIndexOfHtmlContent)) {
              _tmpHtmlContent = null;
            } else {
              _tmpHtmlContent = _cursor.getString(_cursorIndexOfHtmlContent);
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
            final Long _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final String _tmpImagesPaths;
            if (_cursor.isNull(_cursorIndexOfImagesPaths)) {
              _tmpImagesPaths = null;
            } else {
              _tmpImagesPaths = _cursor.getString(_cursorIndexOfImagesPaths);
            }
            final String _tmpVoiceNotePath;
            if (_cursor.isNull(_cursorIndexOfVoiceNotePath)) {
              _tmpVoiceNotePath = null;
            } else {
              _tmpVoiceNotePath = _cursor.getString(_cursorIndexOfVoiceNotePath);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Note(_tmpId,_tmpTitle,_tmpContent,_tmpHtmlContent,_tmpSubjectId,_tmpSubjectName,_tmpSessionId,_tmpIsFavorite,_tmpColor,_tmpImagesPaths,_tmpVoiceNotePath,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Note>> getNotesBySession(final long sessionId) {
    final String _sql = "SELECT * FROM notes WHERE sessionId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<Note>>() {
      @Override
      @NonNull
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfHtmlContent = CursorUtil.getColumnIndexOrThrow(_cursor, "htmlContent");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfImagesPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "imagesPaths");
          final int _cursorIndexOfVoiceNotePath = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNotePath");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Note _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpHtmlContent;
            if (_cursor.isNull(_cursorIndexOfHtmlContent)) {
              _tmpHtmlContent = null;
            } else {
              _tmpHtmlContent = _cursor.getString(_cursorIndexOfHtmlContent);
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
            final Long _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final String _tmpImagesPaths;
            if (_cursor.isNull(_cursorIndexOfImagesPaths)) {
              _tmpImagesPaths = null;
            } else {
              _tmpImagesPaths = _cursor.getString(_cursorIndexOfImagesPaths);
            }
            final String _tmpVoiceNotePath;
            if (_cursor.isNull(_cursorIndexOfVoiceNotePath)) {
              _tmpVoiceNotePath = null;
            } else {
              _tmpVoiceNotePath = _cursor.getString(_cursorIndexOfVoiceNotePath);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Note(_tmpId,_tmpTitle,_tmpContent,_tmpHtmlContent,_tmpSubjectId,_tmpSubjectName,_tmpSessionId,_tmpIsFavorite,_tmpColor,_tmpImagesPaths,_tmpVoiceNotePath,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Note>> getFavoriteNotes() {
    final String _sql = "SELECT * FROM notes WHERE isFavorite = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<Note>>() {
      @Override
      @NonNull
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfHtmlContent = CursorUtil.getColumnIndexOrThrow(_cursor, "htmlContent");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfImagesPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "imagesPaths");
          final int _cursorIndexOfVoiceNotePath = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNotePath");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Note _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpHtmlContent;
            if (_cursor.isNull(_cursorIndexOfHtmlContent)) {
              _tmpHtmlContent = null;
            } else {
              _tmpHtmlContent = _cursor.getString(_cursorIndexOfHtmlContent);
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
            final Long _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final String _tmpImagesPaths;
            if (_cursor.isNull(_cursorIndexOfImagesPaths)) {
              _tmpImagesPaths = null;
            } else {
              _tmpImagesPaths = _cursor.getString(_cursorIndexOfImagesPaths);
            }
            final String _tmpVoiceNotePath;
            if (_cursor.isNull(_cursorIndexOfVoiceNotePath)) {
              _tmpVoiceNotePath = null;
            } else {
              _tmpVoiceNotePath = _cursor.getString(_cursorIndexOfVoiceNotePath);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Note(_tmpId,_tmpTitle,_tmpContent,_tmpHtmlContent,_tmpSubjectId,_tmpSubjectName,_tmpSessionId,_tmpIsFavorite,_tmpColor,_tmpImagesPaths,_tmpVoiceNotePath,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<Note>> searchNotes(final String query) {
    final String _sql = "SELECT * FROM notes WHERE title LIKE '%' || ? || '%' OR content LIKE '%' || ? || '%' ORDER BY updatedAt DESC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<Note>>() {
      @Override
      @NonNull
      public List<Note> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfHtmlContent = CursorUtil.getColumnIndexOrThrow(_cursor, "htmlContent");
          final int _cursorIndexOfSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectId");
          final int _cursorIndexOfSubjectName = CursorUtil.getColumnIndexOrThrow(_cursor, "subjectName");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfImagesPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "imagesPaths");
          final int _cursorIndexOfVoiceNotePath = CursorUtil.getColumnIndexOrThrow(_cursor, "voiceNotePath");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<Note> _result = new ArrayList<Note>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Note _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpHtmlContent;
            if (_cursor.isNull(_cursorIndexOfHtmlContent)) {
              _tmpHtmlContent = null;
            } else {
              _tmpHtmlContent = _cursor.getString(_cursorIndexOfHtmlContent);
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
            final Long _tmpSessionId;
            if (_cursor.isNull(_cursorIndexOfSessionId)) {
              _tmpSessionId = null;
            } else {
              _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final String _tmpImagesPaths;
            if (_cursor.isNull(_cursorIndexOfImagesPaths)) {
              _tmpImagesPaths = null;
            } else {
              _tmpImagesPaths = _cursor.getString(_cursorIndexOfImagesPaths);
            }
            final String _tmpVoiceNotePath;
            if (_cursor.isNull(_cursorIndexOfVoiceNotePath)) {
              _tmpVoiceNotePath = null;
            } else {
              _tmpVoiceNotePath = _cursor.getString(_cursorIndexOfVoiceNotePath);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new Note(_tmpId,_tmpTitle,_tmpContent,_tmpHtmlContent,_tmpSubjectId,_tmpSubjectName,_tmpSessionId,_tmpIsFavorite,_tmpColor,_tmpImagesPaths,_tmpVoiceNotePath,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Integer> getNotesCount() {
    final String _sql = "SELECT COUNT(*) FROM notes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<Integer>() {
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
