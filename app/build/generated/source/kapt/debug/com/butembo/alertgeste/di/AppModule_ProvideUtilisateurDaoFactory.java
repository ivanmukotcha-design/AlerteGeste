package com.butembo.alertgeste.di;

import com.butembo.alertgeste.data.local.AlertGesteDatabase;
import com.butembo.alertgeste.data.local.dao.UtilisateurDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideUtilisateurDaoFactory implements Factory<UtilisateurDao> {
  private final Provider<AlertGesteDatabase> dbProvider;

  public AppModule_ProvideUtilisateurDaoFactory(Provider<AlertGesteDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public UtilisateurDao get() {
    return provideUtilisateurDao(dbProvider.get());
  }

  public static AppModule_ProvideUtilisateurDaoFactory create(
      Provider<AlertGesteDatabase> dbProvider) {
    return new AppModule_ProvideUtilisateurDaoFactory(dbProvider);
  }

  public static UtilisateurDao provideUtilisateurDao(AlertGesteDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideUtilisateurDao(db));
  }
}
