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
import com.porashona.studymaster.data.model.Challenge;
import com.porashona.studymaster.data.model.ChallengeType;
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
public final class ChallengeDao_Impl implements ChallengeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Challenge> __insertionAdapterOfChallenge;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Challenge> __deletionAdapterOfChallenge;

  private final EntityDeletionOrUpdateAdapter<Challenge> __updateAdapterOfChallenge;

  private final SharedSQLiteStatement __preparedStmtOfUpdateProgress;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsCompleted;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldChallenges;

  public ChallengeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChallenge = new EntityInsertionAdapter<Challenge>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `challenges` (`id`,`title`,`titleBn`,`description`,`descriptionBn`,`type`,`targetValue`,`currentValue`,`xpReward`,`isCompleted`,`isActive`,`date`,`completedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Challenge entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getTitleBn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitleBn());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        if (entity.getDescriptionBn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescriptionBn());
        }
        final String _tmp = __converters.fromChallengeType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        statement.bindLong(7, entity.getTargetValue());
        statement.bindLong(8, entity.getCurrentValue());
        statement.bindLong(9, entity.getXpReward());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        if (entity.getDate() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getDate());
        }
        if (entity.getCompletedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getCompletedAt());
        }
      }
    };
    this.__deletionAdapterOfChallenge = new EntityDeletionOrUpdateAdapter<Challenge>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `challenges` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Challenge entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfChallenge = new EntityDeletionOrUpdateAdapter<Challenge>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `challenges` SET `id` = ?,`title` = ?,`titleBn` = ?,`description` = ?,`descriptionBn` = ?,`type` = ?,`targetValue` = ?,`currentValue` = ?,`xpReward` = ?,`isCompleted` = ?,`isActive` = ?,`date` = ?,`completedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Challenge entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getTitleBn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitleBn());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        if (entity.getDescriptionBn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescriptionBn());
        }
        final String _tmp = __converters.fromChallengeType(entity.getType());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        statement.bindLong(7, entity.getTargetValue());
        statement.bindLong(8, entity.getCurrentValue());
        statement.bindLong(9, entity.getXpReward());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(11, _tmp_2);
        if (entity.getDate() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getDate());
        }
        if (entity.getCompletedAt() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getCompletedAt());
        }
        if (entity.getId() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getId());
        }
      }
    };
    this.__preparedStmtOfUpdateProgress = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE challenges SET currentValue = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsCompleted = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE challenges SET isCompleted = 1, completedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldChallenges = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM challenges WHERE date < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Challenge challenge, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChallenge.insert(challenge);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Challenge> challenges,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChallenge.insert(challenges);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Challenge challenge, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChallenge.handle(challenge);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Challenge challenge, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChallenge.handle(challenge);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProgress(final String id, final int value,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateProgress.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, value);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfUpdateProgress.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsCompleted(final String id, final long completedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsCompleted.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, completedAt);
        _argIndex = 2;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfMarkAsCompleted.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldChallenges(final String date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldChallenges.acquire();
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
          __preparedStmtOfDeleteOldChallenges.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Challenge>> getDailyChallenges(final String date) {
    final String _sql = "SELECT * FROM challenges WHERE date = ? AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"challenges"}, new Callable<List<Challenge>>() {
      @Override
      @NonNull
      public List<Challenge> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTitleBn = CursorUtil.getColumnIndexOrThrow(_cursor, "titleBn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDescriptionBn = CursorUtil.getColumnIndexOrThrow(_cursor, "descriptionBn");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTargetValue = CursorUtil.getColumnIndexOrThrow(_cursor, "targetValue");
          final int _cursorIndexOfCurrentValue = CursorUtil.getColumnIndexOrThrow(_cursor, "currentValue");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<Challenge> _result = new ArrayList<Challenge>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Challenge _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpTitleBn;
            if (_cursor.isNull(_cursorIndexOfTitleBn)) {
              _tmpTitleBn = null;
            } else {
              _tmpTitleBn = _cursor.getString(_cursorIndexOfTitleBn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpDescriptionBn;
            if (_cursor.isNull(_cursorIndexOfDescriptionBn)) {
              _tmpDescriptionBn = null;
            } else {
              _tmpDescriptionBn = _cursor.getString(_cursorIndexOfDescriptionBn);
            }
            final ChallengeType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toChallengeType(_tmp);
            final int _tmpTargetValue;
            _tmpTargetValue = _cursor.getInt(_cursorIndexOfTargetValue);
            final int _tmpCurrentValue;
            _tmpCurrentValue = _cursor.getInt(_cursorIndexOfCurrentValue);
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _item = new Challenge(_tmpId,_tmpTitle,_tmpTitleBn,_tmpDescription,_tmpDescriptionBn,_tmpType,_tmpTargetValue,_tmpCurrentValue,_tmpXpReward,_tmpIsCompleted,_tmpIsActive,_tmpDate,_tmpCompletedAt);
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
  public Object getChallengeById(final String id,
      final Continuation<? super Challenge> $completion) {
    final String _sql = "SELECT * FROM challenges WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Challenge>() {
      @Override
      @Nullable
      public Challenge call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTitleBn = CursorUtil.getColumnIndexOrThrow(_cursor, "titleBn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDescriptionBn = CursorUtil.getColumnIndexOrThrow(_cursor, "descriptionBn");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTargetValue = CursorUtil.getColumnIndexOrThrow(_cursor, "targetValue");
          final int _cursorIndexOfCurrentValue = CursorUtil.getColumnIndexOrThrow(_cursor, "currentValue");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final Challenge _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpTitleBn;
            if (_cursor.isNull(_cursorIndexOfTitleBn)) {
              _tmpTitleBn = null;
            } else {
              _tmpTitleBn = _cursor.getString(_cursorIndexOfTitleBn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpDescriptionBn;
            if (_cursor.isNull(_cursorIndexOfDescriptionBn)) {
              _tmpDescriptionBn = null;
            } else {
              _tmpDescriptionBn = _cursor.getString(_cursorIndexOfDescriptionBn);
            }
            final ChallengeType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toChallengeType(_tmp);
            final int _tmpTargetValue;
            _tmpTargetValue = _cursor.getInt(_cursorIndexOfTargetValue);
            final int _tmpCurrentValue;
            _tmpCurrentValue = _cursor.getInt(_cursorIndexOfCurrentValue);
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _result = new Challenge(_tmpId,_tmpTitle,_tmpTitleBn,_tmpDescription,_tmpDescriptionBn,_tmpType,_tmpTargetValue,_tmpCurrentValue,_tmpXpReward,_tmpIsCompleted,_tmpIsActive,_tmpDate,_tmpCompletedAt);
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
  public Flow<List<Challenge>> getCompletedChallenges() {
    final String _sql = "SELECT * FROM challenges WHERE isCompleted = 1 ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"challenges"}, new Callable<List<Challenge>>() {
      @Override
      @NonNull
      public List<Challenge> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTitleBn = CursorUtil.getColumnIndexOrThrow(_cursor, "titleBn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfDescriptionBn = CursorUtil.getColumnIndexOrThrow(_cursor, "descriptionBn");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTargetValue = CursorUtil.getColumnIndexOrThrow(_cursor, "targetValue");
          final int _cursorIndexOfCurrentValue = CursorUtil.getColumnIndexOrThrow(_cursor, "currentValue");
          final int _cursorIndexOfXpReward = CursorUtil.getColumnIndexOrThrow(_cursor, "xpReward");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<Challenge> _result = new ArrayList<Challenge>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Challenge _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpTitleBn;
            if (_cursor.isNull(_cursorIndexOfTitleBn)) {
              _tmpTitleBn = null;
            } else {
              _tmpTitleBn = _cursor.getString(_cursorIndexOfTitleBn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpDescriptionBn;
            if (_cursor.isNull(_cursorIndexOfDescriptionBn)) {
              _tmpDescriptionBn = null;
            } else {
              _tmpDescriptionBn = _cursor.getString(_cursorIndexOfDescriptionBn);
            }
            final ChallengeType _tmpType;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfType);
            }
            _tmpType = __converters.toChallengeType(_tmp);
            final int _tmpTargetValue;
            _tmpTargetValue = _cursor.getInt(_cursorIndexOfTargetValue);
            final int _tmpCurrentValue;
            _tmpCurrentValue = _cursor.getInt(_cursorIndexOfCurrentValue);
            final int _tmpXpReward;
            _tmpXpReward = _cursor.getInt(_cursorIndexOfXpReward);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsActive;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_2 != 0;
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            _item = new Challenge(_tmpId,_tmpTitle,_tmpTitleBn,_tmpDescription,_tmpDescriptionBn,_tmpType,_tmpTargetValue,_tmpCurrentValue,_tmpXpReward,_tmpIsCompleted,_tmpIsActive,_tmpDate,_tmpCompletedAt);
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
  public Flow<Integer> getCompletedCountForDate(final String date) {
    final String _sql = "SELECT COUNT(*) FROM challenges WHERE isCompleted = 1 AND date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"challenges"}, new Callable<Integer>() {
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
