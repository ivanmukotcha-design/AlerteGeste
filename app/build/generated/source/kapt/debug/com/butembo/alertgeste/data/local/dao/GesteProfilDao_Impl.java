package com.butembo.alertgeste.data.local.dao;

import android.database.Cursor;
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
import com.butembo.alertgeste.data.local.entity.GesteProfil;
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
public final class GesteProfilDao_Impl implements GesteProfilDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GesteProfil> __insertionAdapterOfGesteProfil;

  private final SharedSQLiteStatement __preparedStmtOfClearProfils;

  public GesteProfilDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGesteProfil = new EntityInsertionAdapter<GesteProfil>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `geste_profils` (`id`,`seuilMin`,`seuilMax`,`axeDetection`,`fenetreTempsMs`,`nbRepetitions`,`estEnregistre`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GesteProfil entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getSeuilMin());
        statement.bindDouble(3, entity.getSeuilMax());
        if (entity.getAxeDetection() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAxeDetection());
        }
        statement.bindLong(5, entity.getFenetreTempsMs());
        statement.bindLong(6, entity.getNbRepetitions());
        final int _tmp = entity.getEstEnregistre() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
    this.__preparedStmtOfClearProfils = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM geste_profils";
        return _query;
      }
    };
  }

  @Override
  public Object insertProfil(final GesteProfil profil, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGesteProfil.insert(profil);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object clearProfils(final Continuation<? super Unit> arg0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearProfils.acquire();
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
          __preparedStmtOfClearProfils.release(_stmt);
        }
      }
    }, arg0);
  }

  @Override
  public Flow<GesteProfil> getProfilActif() {
    final String _sql = "SELECT * FROM geste_profils ORDER BY id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"geste_profils"}, new Callable<GesteProfil>() {
      @Override
      @Nullable
      public GesteProfil call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSeuilMin = CursorUtil.getColumnIndexOrThrow(_cursor, "seuilMin");
          final int _cursorIndexOfSeuilMax = CursorUtil.getColumnIndexOrThrow(_cursor, "seuilMax");
          final int _cursorIndexOfAxeDetection = CursorUtil.getColumnIndexOrThrow(_cursor, "axeDetection");
          final int _cursorIndexOfFenetreTempsMs = CursorUtil.getColumnIndexOrThrow(_cursor, "fenetreTempsMs");
          final int _cursorIndexOfNbRepetitions = CursorUtil.getColumnIndexOrThrow(_cursor, "nbRepetitions");
          final int _cursorIndexOfEstEnregistre = CursorUtil.getColumnIndexOrThrow(_cursor, "estEnregistre");
          final GesteProfil _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final float _tmpSeuilMin;
            _tmpSeuilMin = _cursor.getFloat(_cursorIndexOfSeuilMin);
            final float _tmpSeuilMax;
            _tmpSeuilMax = _cursor.getFloat(_cursorIndexOfSeuilMax);
            final String _tmpAxeDetection;
            if (_cursor.isNull(_cursorIndexOfAxeDetection)) {
              _tmpAxeDetection = null;
            } else {
              _tmpAxeDetection = _cursor.getString(_cursorIndexOfAxeDetection);
            }
            final long _tmpFenetreTempsMs;
            _tmpFenetreTempsMs = _cursor.getLong(_cursorIndexOfFenetreTempsMs);
            final int _tmpNbRepetitions;
            _tmpNbRepetitions = _cursor.getInt(_cursorIndexOfNbRepetitions);
            final boolean _tmpEstEnregistre;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEstEnregistre);
            _tmpEstEnregistre = _tmp != 0;
            _result = new GesteProfil(_tmpId,_tmpSeuilMin,_tmpSeuilMax,_tmpAxeDetection,_tmpFenetreTempsMs,_tmpNbRepetitions,_tmpEstEnregistre);
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
