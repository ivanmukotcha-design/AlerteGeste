package com.butembo.alertgeste.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.butembo.alertgeste.data.local.entity.Alerte;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
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
public final class AlerteDao_Impl implements AlerteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Alerte> __insertionAdapterOfAlerte;

  private final EntityDeletionOrUpdateAdapter<Alerte> __deletionAdapterOfAlerte;

  private final SharedSQLiteStatement __preparedStmtOfClearHistorique;

  public AlerteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlerte = new EntityInsertionAdapter<Alerte>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `alertes` (`id`,`horodatage`,`latitude`,`longitude`,`statut`,`contactsNotifies`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Alerte entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getHorodatage());
        if (entity.getLatitude() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getLongitude());
        }
        if (entity.getStatut() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStatut());
        }
        if (entity.getContactsNotifies() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getContactsNotifies());
        }
      }
    };
    this.__deletionAdapterOfAlerte = new EntityDeletionOrUpdateAdapter<Alerte>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `alertes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Alerte entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfClearHistorique = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM alertes";
        return _query;
      }
    };
  }

  @Override
  public Object insertAlerte(final Alerte alerte, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAlerte.insert(alerte);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAlerte(final Alerte alerte, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAlerte.handle(alerte);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearHistorique(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearHistorique.acquire();
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
          __preparedStmtOfClearHistorique.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Alerte>> getHistoriqueAlertes() {
    final String _sql = "SELECT * FROM alertes ORDER BY horodatage DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alertes"}, new Callable<List<Alerte>>() {
      @Override
      @NonNull
      public List<Alerte> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHorodatage = CursorUtil.getColumnIndexOrThrow(_cursor, "horodatage");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfContactsNotifies = CursorUtil.getColumnIndexOrThrow(_cursor, "contactsNotifies");
          final List<Alerte> _result = new ArrayList<Alerte>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Alerte _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpHorodatage;
            _tmpHorodatage = _cursor.getLong(_cursorIndexOfHorodatage);
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            final String _tmpContactsNotifies;
            if (_cursor.isNull(_cursorIndexOfContactsNotifies)) {
              _tmpContactsNotifies = null;
            } else {
              _tmpContactsNotifies = _cursor.getString(_cursorIndexOfContactsNotifies);
            }
            _item = new Alerte(_tmpId,_tmpHorodatage,_tmpLatitude,_tmpLongitude,_tmpStatut,_tmpContactsNotifies);
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
