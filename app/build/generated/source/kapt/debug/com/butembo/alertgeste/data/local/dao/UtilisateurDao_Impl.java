package com.butembo.alertgeste.data.local.dao;

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
import com.butembo.alertgeste.data.local.entity.Utilisateur;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UtilisateurDao_Impl implements UtilisateurDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Utilisateur> __insertionAdapterOfUtilisateur;

  private final EntityDeletionOrUpdateAdapter<Utilisateur> __updateAdapterOfUtilisateur;

  private final SharedSQLiteStatement __preparedStmtOfClearUtilisateurs;

  private final SharedSQLiteStatement __preparedStmtOfSetSurveillance;

  public UtilisateurDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUtilisateur = new EntityInsertionAdapter<Utilisateur>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `utilisateurs` (`id`,`nom`,`telephone`,`messageAlerte`,`surveillanceActive`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Utilisateur entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNom() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNom());
        }
        if (entity.getTelephone() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTelephone());
        }
        if (entity.getMessageAlerte() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getMessageAlerte());
        }
        final int _tmp = entity.getSurveillanceActive() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__updateAdapterOfUtilisateur = new EntityDeletionOrUpdateAdapter<Utilisateur>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `utilisateurs` SET `id` = ?,`nom` = ?,`telephone` = ?,`messageAlerte` = ?,`surveillanceActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Utilisateur entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getNom() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getNom());
        }
        if (entity.getTelephone() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTelephone());
        }
        if (entity.getMessageAlerte() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getMessageAlerte());
        }
        final int _tmp = entity.getSurveillanceActive() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfClearUtilisateurs = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM utilisateurs";
        return _query;
      }
    };
    this.__preparedStmtOfSetSurveillance = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE utilisateurs SET surveillanceActive = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertUtilisateur(final Utilisateur utilisateur,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUtilisateur.insert(utilisateur);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateUtilisateur(final Utilisateur utilisateur,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUtilisateur.handle(utilisateur);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object clearUtilisateurs(final Continuation<? super Unit> arg0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearUtilisateurs.acquire();
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
          __preparedStmtOfClearUtilisateurs.release(_stmt);
        }
      }
    }, arg0);
  }

  @Override
  public Object setSurveillance(final boolean active, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetSurveillance.acquire();
        int _argIndex = 1;
        final int _tmp = active ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
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
          __preparedStmtOfSetSurveillance.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Flow<Utilisateur> getUtilisateur() {
    final String _sql = "SELECT * FROM utilisateurs ORDER BY id ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"utilisateurs"}, new Callable<Utilisateur>() {
      @Override
      @Nullable
      public Utilisateur call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfTelephone = CursorUtil.getColumnIndexOrThrow(_cursor, "telephone");
          final int _cursorIndexOfMessageAlerte = CursorUtil.getColumnIndexOrThrow(_cursor, "messageAlerte");
          final int _cursorIndexOfSurveillanceActive = CursorUtil.getColumnIndexOrThrow(_cursor, "surveillanceActive");
          final Utilisateur _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
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
            final String _tmpMessageAlerte;
            if (_cursor.isNull(_cursorIndexOfMessageAlerte)) {
              _tmpMessageAlerte = null;
            } else {
              _tmpMessageAlerte = _cursor.getString(_cursorIndexOfMessageAlerte);
            }
            final boolean _tmpSurveillanceActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSurveillanceActive);
            _tmpSurveillanceActive = _tmp != 0;
            _result = new Utilisateur(_tmpId,_tmpNom,_tmpTelephone,_tmpMessageAlerte,_tmpSurveillanceActive);
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
  public Object getUtilisateurOnce(final Continuation<? super Utilisateur> arg0) {
    final String _sql = "SELECT * FROM utilisateurs ORDER BY id ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Utilisateur>() {
      @Override
      @Nullable
      public Utilisateur call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNom = CursorUtil.getColumnIndexOrThrow(_cursor, "nom");
          final int _cursorIndexOfTelephone = CursorUtil.getColumnIndexOrThrow(_cursor, "telephone");
          final int _cursorIndexOfMessageAlerte = CursorUtil.getColumnIndexOrThrow(_cursor, "messageAlerte");
          final int _cursorIndexOfSurveillanceActive = CursorUtil.getColumnIndexOrThrow(_cursor, "surveillanceActive");
          final Utilisateur _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
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
            final String _tmpMessageAlerte;
            if (_cursor.isNull(_cursorIndexOfMessageAlerte)) {
              _tmpMessageAlerte = null;
            } else {
              _tmpMessageAlerte = _cursor.getString(_cursorIndexOfMessageAlerte);
            }
            final boolean _tmpSurveillanceActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSurveillanceActive);
            _tmpSurveillanceActive = _tmp != 0;
            _result = new Utilisateur(_tmpId,_tmpNom,_tmpTelephone,_tmpMessageAlerte,_tmpSurveillanceActive);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
