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
import com.porashona.studymaster.data.model.Quote;
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
public final class QuoteDao_Impl implements QuoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Quote> __insertionAdapterOfQuote;

  private final EntityDeletionOrUpdateAdapter<Quote> __deletionAdapterOfQuote;

  private final EntityDeletionOrUpdateAdapter<Quote> __updateAdapterOfQuote;

  private final SharedSQLiteStatement __preparedStmtOfSetFavorite;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsShown;

  public QuoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuote = new EntityInsertionAdapter<Quote>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quotes` (`id`,`textEn`,`textBn`,`author`,`authorBn`,`category`,`isFavorite`,`isCustom`,`shownCount`,`lastShownAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Quote entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTextEn() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTextEn());
        }
        if (entity.getTextBn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTextBn());
        }
        if (entity.getAuthor() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAuthor());
        }
        if (entity.getAuthorBn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAuthorBn());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCategory());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(7, _tmp);
        final int _tmp_1 = entity.isCustom() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getShownCount());
        if (entity.getLastShownAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getLastShownAt());
        }
      }
    };
    this.__deletionAdapterOfQuote = new EntityDeletionOrUpdateAdapter<Quote>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `quotes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Quote entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfQuote = new EntityDeletionOrUpdateAdapter<Quote>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `quotes` SET `id` = ?,`textEn` = ?,`textBn` = ?,`author` = ?,`authorBn` = ?,`category` = ?,`isFavorite` = ?,`isCustom` = ?,`shownCount` = ?,`lastShownAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Quote entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTextEn() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTextEn());
        }
        if (entity.getTextBn() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTextBn());
        }
        if (entity.getAuthor() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAuthor());
        }
        if (entity.getAuthorBn() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAuthorBn());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCategory());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(7, _tmp);
        final int _tmp_1 = entity.isCustom() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getShownCount());
        if (entity.getLastShownAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getLastShownAt());
        }
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfSetFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE quotes SET isFavorite = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsShown = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE quotes SET shownCount = shownCount + 1, lastShownAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Quote quote, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuote.insertAndReturnId(quote);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Quote> quotes, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuote.insert(quotes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Quote quote, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfQuote.handle(quote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Quote quote, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfQuote.handle(quote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
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
  public Object markAsShown(final long id, final long time,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsShown.acquire();
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
          __preparedStmtOfMarkAsShown.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getRandomQuote(final Continuation<? super Quote> $completion) {
    final String _sql = "SELECT * FROM quotes ORDER BY shownCount ASC, RANDOM() LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Quote>() {
      @Override
      @Nullable
      public Quote call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTextEn = CursorUtil.getColumnIndexOrThrow(_cursor, "textEn");
          final int _cursorIndexOfTextBn = CursorUtil.getColumnIndexOrThrow(_cursor, "textBn");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorBn = CursorUtil.getColumnIndexOrThrow(_cursor, "authorBn");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfShownCount = CursorUtil.getColumnIndexOrThrow(_cursor, "shownCount");
          final int _cursorIndexOfLastShownAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastShownAt");
          final Quote _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTextEn;
            if (_cursor.isNull(_cursorIndexOfTextEn)) {
              _tmpTextEn = null;
            } else {
              _tmpTextEn = _cursor.getString(_cursorIndexOfTextEn);
            }
            final String _tmpTextBn;
            if (_cursor.isNull(_cursorIndexOfTextBn)) {
              _tmpTextBn = null;
            } else {
              _tmpTextBn = _cursor.getString(_cursorIndexOfTextBn);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorBn;
            if (_cursor.isNull(_cursorIndexOfAuthorBn)) {
              _tmpAuthorBn = null;
            } else {
              _tmpAuthorBn = _cursor.getString(_cursorIndexOfAuthorBn);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final int _tmpShownCount;
            _tmpShownCount = _cursor.getInt(_cursorIndexOfShownCount);
            final Long _tmpLastShownAt;
            if (_cursor.isNull(_cursorIndexOfLastShownAt)) {
              _tmpLastShownAt = null;
            } else {
              _tmpLastShownAt = _cursor.getLong(_cursorIndexOfLastShownAt);
            }
            _result = new Quote(_tmpId,_tmpTextEn,_tmpTextBn,_tmpAuthor,_tmpAuthorBn,_tmpCategory,_tmpIsFavorite,_tmpIsCustom,_tmpShownCount,_tmpLastShownAt);
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
  public Flow<List<Quote>> getFavoriteQuotes() {
    final String _sql = "SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quotes"}, new Callable<List<Quote>>() {
      @Override
      @NonNull
      public List<Quote> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTextEn = CursorUtil.getColumnIndexOrThrow(_cursor, "textEn");
          final int _cursorIndexOfTextBn = CursorUtil.getColumnIndexOrThrow(_cursor, "textBn");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorBn = CursorUtil.getColumnIndexOrThrow(_cursor, "authorBn");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfShownCount = CursorUtil.getColumnIndexOrThrow(_cursor, "shownCount");
          final int _cursorIndexOfLastShownAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastShownAt");
          final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Quote _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTextEn;
            if (_cursor.isNull(_cursorIndexOfTextEn)) {
              _tmpTextEn = null;
            } else {
              _tmpTextEn = _cursor.getString(_cursorIndexOfTextEn);
            }
            final String _tmpTextBn;
            if (_cursor.isNull(_cursorIndexOfTextBn)) {
              _tmpTextBn = null;
            } else {
              _tmpTextBn = _cursor.getString(_cursorIndexOfTextBn);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorBn;
            if (_cursor.isNull(_cursorIndexOfAuthorBn)) {
              _tmpAuthorBn = null;
            } else {
              _tmpAuthorBn = _cursor.getString(_cursorIndexOfAuthorBn);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final int _tmpShownCount;
            _tmpShownCount = _cursor.getInt(_cursorIndexOfShownCount);
            final Long _tmpLastShownAt;
            if (_cursor.isNull(_cursorIndexOfLastShownAt)) {
              _tmpLastShownAt = null;
            } else {
              _tmpLastShownAt = _cursor.getLong(_cursorIndexOfLastShownAt);
            }
            _item = new Quote(_tmpId,_tmpTextEn,_tmpTextBn,_tmpAuthor,_tmpAuthorBn,_tmpCategory,_tmpIsFavorite,_tmpIsCustom,_tmpShownCount,_tmpLastShownAt);
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
  public Flow<List<Quote>> getCustomQuotes() {
    final String _sql = "SELECT * FROM quotes WHERE isCustom = 1 ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quotes"}, new Callable<List<Quote>>() {
      @Override
      @NonNull
      public List<Quote> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTextEn = CursorUtil.getColumnIndexOrThrow(_cursor, "textEn");
          final int _cursorIndexOfTextBn = CursorUtil.getColumnIndexOrThrow(_cursor, "textBn");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorBn = CursorUtil.getColumnIndexOrThrow(_cursor, "authorBn");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfShownCount = CursorUtil.getColumnIndexOrThrow(_cursor, "shownCount");
          final int _cursorIndexOfLastShownAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastShownAt");
          final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Quote _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTextEn;
            if (_cursor.isNull(_cursorIndexOfTextEn)) {
              _tmpTextEn = null;
            } else {
              _tmpTextEn = _cursor.getString(_cursorIndexOfTextEn);
            }
            final String _tmpTextBn;
            if (_cursor.isNull(_cursorIndexOfTextBn)) {
              _tmpTextBn = null;
            } else {
              _tmpTextBn = _cursor.getString(_cursorIndexOfTextBn);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorBn;
            if (_cursor.isNull(_cursorIndexOfAuthorBn)) {
              _tmpAuthorBn = null;
            } else {
              _tmpAuthorBn = _cursor.getString(_cursorIndexOfAuthorBn);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final int _tmpShownCount;
            _tmpShownCount = _cursor.getInt(_cursorIndexOfShownCount);
            final Long _tmpLastShownAt;
            if (_cursor.isNull(_cursorIndexOfLastShownAt)) {
              _tmpLastShownAt = null;
            } else {
              _tmpLastShownAt = _cursor.getLong(_cursorIndexOfLastShownAt);
            }
            _item = new Quote(_tmpId,_tmpTextEn,_tmpTextBn,_tmpAuthor,_tmpAuthorBn,_tmpCategory,_tmpIsFavorite,_tmpIsCustom,_tmpShownCount,_tmpLastShownAt);
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
  public Flow<List<Quote>> getAllQuotes() {
    final String _sql = "SELECT * FROM quotes ORDER BY id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quotes"}, new Callable<List<Quote>>() {
      @Override
      @NonNull
      public List<Quote> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTextEn = CursorUtil.getColumnIndexOrThrow(_cursor, "textEn");
          final int _cursorIndexOfTextBn = CursorUtil.getColumnIndexOrThrow(_cursor, "textBn");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfAuthorBn = CursorUtil.getColumnIndexOrThrow(_cursor, "authorBn");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfShownCount = CursorUtil.getColumnIndexOrThrow(_cursor, "shownCount");
          final int _cursorIndexOfLastShownAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastShownAt");
          final List<Quote> _result = new ArrayList<Quote>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Quote _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTextEn;
            if (_cursor.isNull(_cursorIndexOfTextEn)) {
              _tmpTextEn = null;
            } else {
              _tmpTextEn = _cursor.getString(_cursorIndexOfTextEn);
            }
            final String _tmpTextBn;
            if (_cursor.isNull(_cursorIndexOfTextBn)) {
              _tmpTextBn = null;
            } else {
              _tmpTextBn = _cursor.getString(_cursorIndexOfTextBn);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpAuthorBn;
            if (_cursor.isNull(_cursorIndexOfAuthorBn)) {
              _tmpAuthorBn = null;
            } else {
              _tmpAuthorBn = _cursor.getString(_cursorIndexOfAuthorBn);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsCustom;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp_1 != 0;
            final int _tmpShownCount;
            _tmpShownCount = _cursor.getInt(_cursorIndexOfShownCount);
            final Long _tmpLastShownAt;
            if (_cursor.isNull(_cursorIndexOfLastShownAt)) {
              _tmpLastShownAt = null;
            } else {
              _tmpLastShownAt = _cursor.getLong(_cursorIndexOfLastShownAt);
            }
            _item = new Quote(_tmpId,_tmpTextEn,_tmpTextBn,_tmpAuthor,_tmpAuthorBn,_tmpCategory,_tmpIsFavorite,_tmpIsCustom,_tmpShownCount,_tmpLastShownAt);
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
  public Object getQuotesCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM quotes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
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
