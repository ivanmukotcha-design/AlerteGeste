package com.butembo.alertgeste.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.butembo.alertgeste.data.local.dao.AlerteDao;
import com.butembo.alertgeste.data.local.dao.AlerteDao_Impl;
import com.butembo.alertgeste.data.local.dao.ContactDao;
import com.butembo.alertgeste.data.local.dao.ContactDao_Impl;
import com.butembo.alertgeste.data.local.dao.GesteProfilDao;
import com.butembo.alertgeste.data.local.dao.GesteProfilDao_Impl;
import com.butembo.alertgeste.data.local.dao.UtilisateurDao;
import com.butembo.alertgeste.data.local.dao.UtilisateurDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AlertGesteDatabase_Impl extends AlertGesteDatabase {
  private volatile UtilisateurDao _utilisateurDao;

  private volatile ContactDao _contactDao;

  private volatile GesteProfilDao _gesteProfilDao;

  private volatile AlerteDao _alerteDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `utilisateurs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom` TEXT NOT NULL, `telephone` TEXT NOT NULL, `messageAlerte` TEXT NOT NULL, `surveillanceActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `contacts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom` TEXT NOT NULL, `telephone` TEXT NOT NULL, `relation` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `geste_profils` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `seuilMin` REAL NOT NULL, `seuilMax` REAL NOT NULL, `axeDetection` TEXT NOT NULL, `fenetreTempsMs` INTEGER NOT NULL, `nbRepetitions` INTEGER NOT NULL, `estEnregistre` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `alertes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `horodatage` INTEGER NOT NULL, `latitude` REAL, `longitude` REAL, `statut` TEXT NOT NULL, `contactsNotifies` TEXT NOT NULL, `detail` TEXT NOT NULL DEFAULT '')");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sms_parts` (`id` TEXT NOT NULL, `alerteId` INTEGER NOT NULL, `contactId` INTEGER NOT NULL, `nom` TEXT NOT NULL, `telephone` TEXT NOT NULL, `partIndex` INTEGER NOT NULL, `statut` TEXT NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`alerteId`) REFERENCES `alertes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_sms_parts_alerteId` ON `sms_parts` (`alerteId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'aa18ff24d955217642b2a69c45a3cec9')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `utilisateurs`");
        db.execSQL("DROP TABLE IF EXISTS `contacts`");
        db.execSQL("DROP TABLE IF EXISTS `geste_profils`");
        db.execSQL("DROP TABLE IF EXISTS `alertes`");
        db.execSQL("DROP TABLE IF EXISTS `sms_parts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUtilisateurs = new HashMap<String, TableInfo.Column>(5);
        _columnsUtilisateurs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUtilisateurs.put("nom", new TableInfo.Column("nom", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUtilisateurs.put("telephone", new TableInfo.Column("telephone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUtilisateurs.put("messageAlerte", new TableInfo.Column("messageAlerte", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUtilisateurs.put("surveillanceActive", new TableInfo.Column("surveillanceActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUtilisateurs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUtilisateurs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUtilisateurs = new TableInfo("utilisateurs", _columnsUtilisateurs, _foreignKeysUtilisateurs, _indicesUtilisateurs);
        final TableInfo _existingUtilisateurs = TableInfo.read(db, "utilisateurs");
        if (!_infoUtilisateurs.equals(_existingUtilisateurs)) {
          return new RoomOpenHelper.ValidationResult(false, "utilisateurs(com.butembo.alertgeste.data.local.entity.Utilisateur).\n"
                  + " Expected:\n" + _infoUtilisateurs + "\n"
                  + " Found:\n" + _existingUtilisateurs);
        }
        final HashMap<String, TableInfo.Column> _columnsContacts = new HashMap<String, TableInfo.Column>(4);
        _columnsContacts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("nom", new TableInfo.Column("nom", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("telephone", new TableInfo.Column("telephone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContacts.put("relation", new TableInfo.Column("relation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContacts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesContacts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContacts = new TableInfo("contacts", _columnsContacts, _foreignKeysContacts, _indicesContacts);
        final TableInfo _existingContacts = TableInfo.read(db, "contacts");
        if (!_infoContacts.equals(_existingContacts)) {
          return new RoomOpenHelper.ValidationResult(false, "contacts(com.butembo.alertgeste.data.local.entity.Contact).\n"
                  + " Expected:\n" + _infoContacts + "\n"
                  + " Found:\n" + _existingContacts);
        }
        final HashMap<String, TableInfo.Column> _columnsGesteProfils = new HashMap<String, TableInfo.Column>(7);
        _columnsGesteProfils.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGesteProfils.put("seuilMin", new TableInfo.Column("seuilMin", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGesteProfils.put("seuilMax", new TableInfo.Column("seuilMax", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGesteProfils.put("axeDetection", new TableInfo.Column("axeDetection", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGesteProfils.put("fenetreTempsMs", new TableInfo.Column("fenetreTempsMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGesteProfils.put("nbRepetitions", new TableInfo.Column("nbRepetitions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGesteProfils.put("estEnregistre", new TableInfo.Column("estEnregistre", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGesteProfils = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGesteProfils = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGesteProfils = new TableInfo("geste_profils", _columnsGesteProfils, _foreignKeysGesteProfils, _indicesGesteProfils);
        final TableInfo _existingGesteProfils = TableInfo.read(db, "geste_profils");
        if (!_infoGesteProfils.equals(_existingGesteProfils)) {
          return new RoomOpenHelper.ValidationResult(false, "geste_profils(com.butembo.alertgeste.data.local.entity.GesteProfil).\n"
                  + " Expected:\n" + _infoGesteProfils + "\n"
                  + " Found:\n" + _existingGesteProfils);
        }
        final HashMap<String, TableInfo.Column> _columnsAlertes = new HashMap<String, TableInfo.Column>(7);
        _columnsAlertes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertes.put("horodatage", new TableInfo.Column("horodatage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertes.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertes.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertes.put("statut", new TableInfo.Column("statut", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertes.put("contactsNotifies", new TableInfo.Column("contactsNotifies", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlertes.put("detail", new TableInfo.Column("detail", "TEXT", true, 0, "''", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAlertes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAlertes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAlertes = new TableInfo("alertes", _columnsAlertes, _foreignKeysAlertes, _indicesAlertes);
        final TableInfo _existingAlertes = TableInfo.read(db, "alertes");
        if (!_infoAlertes.equals(_existingAlertes)) {
          return new RoomOpenHelper.ValidationResult(false, "alertes(com.butembo.alertgeste.data.local.entity.Alerte).\n"
                  + " Expected:\n" + _infoAlertes + "\n"
                  + " Found:\n" + _existingAlertes);
        }
        final HashMap<String, TableInfo.Column> _columnsSmsParts = new HashMap<String, TableInfo.Column>(7);
        _columnsSmsParts.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmsParts.put("alerteId", new TableInfo.Column("alerteId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmsParts.put("contactId", new TableInfo.Column("contactId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmsParts.put("nom", new TableInfo.Column("nom", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmsParts.put("telephone", new TableInfo.Column("telephone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmsParts.put("partIndex", new TableInfo.Column("partIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmsParts.put("statut", new TableInfo.Column("statut", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSmsParts = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSmsParts.add(new TableInfo.ForeignKey("alertes", "CASCADE", "NO ACTION", Arrays.asList("alerteId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesSmsParts = new HashSet<TableInfo.Index>(1);
        _indicesSmsParts.add(new TableInfo.Index("index_sms_parts_alerteId", false, Arrays.asList("alerteId"), Arrays.asList("ASC")));
        final TableInfo _infoSmsParts = new TableInfo("sms_parts", _columnsSmsParts, _foreignKeysSmsParts, _indicesSmsParts);
        final TableInfo _existingSmsParts = TableInfo.read(db, "sms_parts");
        if (!_infoSmsParts.equals(_existingSmsParts)) {
          return new RoomOpenHelper.ValidationResult(false, "sms_parts(com.butembo.alertgeste.data.local.entity.SmsPart).\n"
                  + " Expected:\n" + _infoSmsParts + "\n"
                  + " Found:\n" + _existingSmsParts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "aa18ff24d955217642b2a69c45a3cec9", "9a32d162290c828cdf1d05e3dbcac830");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "utilisateurs","contacts","geste_profils","alertes","sms_parts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `utilisateurs`");
      _db.execSQL("DELETE FROM `contacts`");
      _db.execSQL("DELETE FROM `geste_profils`");
      _db.execSQL("DELETE FROM `alertes`");
      _db.execSQL("DELETE FROM `sms_parts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UtilisateurDao.class, UtilisateurDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ContactDao.class, ContactDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GesteProfilDao.class, GesteProfilDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AlerteDao.class, AlerteDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UtilisateurDao utilisateurDao() {
    if (_utilisateurDao != null) {
      return _utilisateurDao;
    } else {
      synchronized(this) {
        if(_utilisateurDao == null) {
          _utilisateurDao = new UtilisateurDao_Impl(this);
        }
        return _utilisateurDao;
      }
    }
  }

  @Override
  public ContactDao contactDao() {
    if (_contactDao != null) {
      return _contactDao;
    } else {
      synchronized(this) {
        if(_contactDao == null) {
          _contactDao = new ContactDao_Impl(this);
        }
        return _contactDao;
      }
    }
  }

  @Override
  public GesteProfilDao gesteProfilDao() {
    if (_gesteProfilDao != null) {
      return _gesteProfilDao;
    } else {
      synchronized(this) {
        if(_gesteProfilDao == null) {
          _gesteProfilDao = new GesteProfilDao_Impl(this);
        }
        return _gesteProfilDao;
      }
    }
  }

  @Override
  public AlerteDao alerteDao() {
    if (_alerteDao != null) {
      return _alerteDao;
    } else {
      synchronized(this) {
        if(_alerteDao == null) {
          _alerteDao = new AlerteDao_Impl(this);
        }
        return _alerteDao;
      }
    }
  }
}
