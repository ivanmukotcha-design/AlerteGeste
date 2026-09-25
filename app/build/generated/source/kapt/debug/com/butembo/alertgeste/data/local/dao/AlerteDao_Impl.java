package com.butembo.alertgeste.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.butembo.alertgeste.data.local.entity.Alerte;
import com.butembo.alertgeste.data.local.entity.SmsPart;
import java.lang.Class;
import java.lang.Double;
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
public final class AlerteDao_Impl implements AlerteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Alerte> __insertionAdapterOfAlerte;

  private final EntityInsertionAdapter<SmsPart> __insertionAdapterOfSmsPart;

  private final SharedSQLiteStatement __preparedStmtOfSetStatus;

  private final SharedSQLiteStatement __preparedStmtOfSetLocation;

  private final SharedSQLiteStatement __preparedStmtOfSetContacts;

  private final SharedSQLiteStatement __preparedStmtOfSetPartStatus;

  private final SharedSQLiteStatement __preparedStmtOfExpireParts;

  private final SharedSQLiteStatement __preparedStmtOfInterruptPreparations;

  private final SharedSQLiteStatement __preparedStmtOfPruneHistory;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCompletedAlerte;

  private final SharedSQLiteStatement __preparedStmtOfClearHistorique;

  public AlerteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlerte = new EntityInsertionAdapter<Alerte>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `alertes` (`id`,`horodatage`,`latitude`,`longitude`,`statut`,`contactsNotifies`,`detail`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
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
        if (entity.getDetail() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDetail());
        }
      }
    };
    this.__insertionAdapterOfSmsPart = new EntityInsertionAdapter<SmsPart>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `sms_parts` (`id`,`alerteId`,`contactId`,`nom`,`telephone`,`partIndex`,`statut`,`failureReason`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SmsPart entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        statement.bindLong(2, entity.getAlerteId());
        statement.bindLong(3, entity.getContactId());
        if (entity.getNom() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNom());
        }
        if (entity.getTelephone() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTelephone());
        }
        statement.bindLong(6, entity.getPartIndex());
        if (entity.getStatut() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getStatut());
        }
        if (entity.getFailureReason() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFailureReason());
        }
      }
    };
    this.__preparedStmtOfSetStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alertes SET statut = ?, detail = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetLocation = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alertes SET latitude = ?, longitude = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetContacts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alertes SET contactsNotifies = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetPartStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sms_parts SET statut = ?, failureReason = ? WHERE id = ? AND statut IN ('EN_ATTENTE', 'INCONNU')";
        return _query;
      }
    };
    this.__preparedStmtOfExpireParts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sms_parts SET statut = 'INCONNU' WHERE alerteId = ? AND statut = 'EN_ATTENTE'";
        return _query;
      }
    };
    this.__preparedStmtOfInterruptPreparations = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alertes SET statut = 'INTERROMPUE', detail = 'Service interrompu avant envoi.' WHERE statut IN ('COMPTE_A_REBOURS', 'LOCALISATION')";
        return _query;
      }
    };
    this.__preparedStmtOfPruneHistory = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM alertes WHERE id NOT IN (SELECT id FROM alertes ORDER BY horodatage DESC LIMIT 200) AND statut NOT IN ('COMPTE_A_REBOURS', 'LOCALISATION', 'EN_COURS')";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCompletedAlerte = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM alertes WHERE id = ? AND statut NOT IN ('COMPTE_A_REBOURS', 'LOCALISATION', 'EN_COURS')";
        return _query;
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
  public Object insertAlerte(final Alerte alerte, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAlerte.insertAndReturnId(alerte);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertParts(final List<SmsPart> parts,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSmsPart.insert(parts);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setStatus(final long id, final String status, final String detail,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetStatus.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        if (detail == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, detail);
        }
        _argIndex = 3;
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
          __preparedStmtOfSetStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setLocation(final long id, final Double latitude, final Double longitude,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetLocation.acquire();
        int _argIndex = 1;
        if (latitude == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindDouble(_argIndex, latitude);
        }
        _argIndex = 2;
        if (longitude == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindDouble(_argIndex, longitude);
        }
        _argIndex = 3;
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
          __preparedStmtOfSetLocation.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setContacts(final long id, final String contacts,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetContacts.acquire();
        int _argIndex = 1;
        if (contacts == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, contacts);
        }
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
          __preparedStmtOfSetContacts.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setPartStatus(final String id, final String status, final String failureReason,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetPartStatus.acquire();
        int _argIndex = 1;
        if (status == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, status);
        }
        _argIndex = 2;
        if (failureReason == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, failureReason);
        }
        _argIndex = 3;
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
          __preparedStmtOfSetPartStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object expireParts(final long alertId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfExpireParts.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, alertId);
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
          __preparedStmtOfExpireParts.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object interruptPreparations(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfInterruptPreparations.acquire();
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
          __preparedStmtOfInterruptPreparations.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object pruneHistory(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfPruneHistory.acquire();
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
          __preparedStmtOfPruneHistory.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCompletedAlerte(final long id,
      final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCompletedAlerte.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteCompletedAlerte.release(_stmt);
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
    final String _sql = "SELECT * FROM alertes ORDER BY horodatage DESC LIMIT 200";
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
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detail");
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
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            _item = new Alerte(_tmpId,_tmpHorodatage,_tmpLatitude,_tmpLongitude,_tmpStatut,_tmpContactsNotifies,_tmpDetail);
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
  public Object getAlerte(final long id, final Continuation<? super Alerte> $completion) {
    final String _sql = "SELECT * FROM alertes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Alerte>() {
      @Override
      @Nullable
      public Alerte call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHorodatage = CursorUtil.getColumnIndexOrThrow(_cursor, "horodatage");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfContactsNotifies = CursorUtil.getColumnIndexOrThrow(_cursor, "contactsNotifies");
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detail");
          final Alerte _result;
          if (_cursor.moveToFirst()) {
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
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            _result = new Alerte(_tmpId,_tmpHorodatage,_tmpLatitude,_tmpLongitude,_tmpStatut,_tmpContactsNotifies,_tmpDetail);
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
  public Flow<Alerte> observeAlerte(final long id) {
    final String _sql = "SELECT * FROM alertes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"alertes"}, new Callable<Alerte>() {
      @Override
      @Nullable
      public Alerte call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfHorodatage = CursorUtil.getColumnIndexOrThrow(_cursor, "horodatage");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfContactsNotifies = CursorUtil.getColumnIndexOrThrow(_cursor, "contactsNotifies");
          final int _cursorIndexOfDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "detail");
          final Alerte _result;
          if (_cursor.moveToFirst()) {
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
            final String _tmpDetail;
            if (_cursor.isNull(_cursorIndexOfDetail)) {
              _tmpDetail = null;
            } else {
              _tmpDetail = _cursor.getString(_cursorIndexOfDetail);
            }
            _result = new Alerte(_tmpId,_tmpHorodatage,_tmpLatitude,_tmpLongitude,_tmpStatut,_tmpContactsNotifies,_tmpDetail);
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
  public Object getParts(final long alertId,
      final Continuation<? super List<SmsPart>> $completion) {
    final String _sql = "SELECT * FROM sms_parts WHERE alerteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, alertId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SmsPart>>() {
      @Override
      @NonNull
      public List<SmsPart> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAlerteId = CursorUtil.getColumnIndexOrThrow(_cursor, "alerteId");
          final int _cursorIndexOfContactId = CursorUtil.getColumnIndexOrThrow(_cursor, "contactId");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfTelephone = CursorUtil.getColumnIndexOrThrow(_cursor, "telephone");
          final int _cursorIndexOfPartIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "partIndex");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfFailureReason = CursorUtil.getColumnIndexOrThrow(_cursor, "failureReason");
          final List<SmsPart> _result = new ArrayList<SmsPart>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SmsPart _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpAlerteId;
            _tmpAlerteId = _cursor.getLong(_cursorIndexOfAlerteId);
            final long _tmpContactId;
            _tmpContactId = _cursor.getLong(_cursorIndexOfContactId);
            final String _tmpNom;
            if (_cursor.isNull(_cursorIndexOfNom)) {
              _tmpNom = null;
            } else {
              _tmpNom = _cursor.getString(_cursorIndexOfNom);
            }
            final String _tmpTelephone;
            if (_cursor.isNull(_cursorIndexOfTelephone)) {
              _tmpTelephone = null;
            } else {
              _tmpTelephone = _cursor.getString(_cursorIndexOfTelephone);
            }
            final int _tmpPartIndex;
            _tmpPartIndex = _cursor.getInt(_cursorIndexOfPartIndex);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            final String _tmpFailureReason;
            if (_cursor.isNull(_cursorIndexOfFailureReason)) {
              _tmpFailureReason = null;
            } else {
              _tmpFailureReason = _cursor.getString(_cursorIndexOfFailureReason);
            }
            _item = new SmsPart(_tmpId,_tmpAlerteId,_tmpContactId,_tmpNom,_tmpTelephone,_tmpPartIndex,_tmpStatut,_tmpFailureReason);
            _result.add(_item);
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
  public Object getPart(final String id, final Continuation<? super SmsPart> $completion) {
    final String _sql = "SELECT * FROM sms_parts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SmsPart>() {
      @Override
      @Nullable
      public SmsPart call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAlerteId = CursorUtil.getColumnIndexOrThrow(_cursor, "alerteId");
          final int _cursorIndexOfContactId = CursorUtil.getColumnIndexOrThrow(_cursor, "contactId");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfTelephone = CursorUtil.getColumnIndexOrThrow(_cursor, "telephone");
          final int _cursorIndexOfPartIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "partIndex");
          final int _cursorIndexOfStatut = CursorUtil.getColumnIndexOrThrow(_cursor, "statut");
          final int _cursorIndexOfFailureReason = CursorUtil.getColumnIndexOrThrow(_cursor, "failureReason");
          final SmsPart _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final long _tmpAlerteId;
            _tmpAlerteId = _cursor.getLong(_cursorIndexOfAlerteId);
            final long _tmpContactId;
            _tmpContactId = _cursor.getLong(_cursorIndexOfContactId);
            final String _tmpNom;
            if (_cursor.isNull(_cursorIndexOfNom)) {
              _tmpNom = null;
            } else {
              _tmpNom = _cursor.getString(_cursorIndexOfNom);
            }
            final String _tmpTelephone;
            if (_cursor.isNull(_cursorIndexOfTelephone)) {
              _tmpTelephone = null;
            } else {
              _tmpTelephone = _cursor.getString(_cursorIndexOfTelephone);
            }
            final int _tmpPartIndex;
            _tmpPartIndex = _cursor.getInt(_cursorIndexOfPartIndex);
            final String _tmpStatut;
            if (_cursor.isNull(_cursorIndexOfStatut)) {
              _tmpStatut = null;
            } else {
              _tmpStatut = _cursor.getString(_cursorIndexOfStatut);
            }
            final String _tmpFailureReason;
            if (_cursor.isNull(_cursorIndexOfFailureReason)) {
              _tmpFailureReason = null;
            } else {
              _tmpFailureReason = _cursor.getString(_cursorIndexOfFailureReason);
            }
            _result = new SmsPart(_tmpId,_tmpAlerteId,_tmpContactId,_tmpNom,_tmpTelephone,_tmpPartIndex,_tmpStatut,_tmpFailureReason);
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
  public Object pendingAlerts(final Continuation<? super List<Long>> $completion) {
    final String _sql = "SELECT id FROM alertes WHERE statut = 'EN_COURS'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<Long> _result = new ArrayList<Long>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Long _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getLong(0);
            }
            _result.add(_item);
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
