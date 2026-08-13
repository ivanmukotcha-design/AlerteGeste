package com.butembo.alertgeste.di;

import com.butembo.alertgeste.data.local.AlertGesteDatabase;
import com.butembo.alertgeste.data.local.dao.GesteProfilDao;
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
public final class AppModule_ProvideGesteProfilDaoFactory implements Factory<GesteProfilDao> {
  private final Provider<AlertGesteDatabase> dbProvider;

  public AppModule_ProvideGesteProfilDaoFactory(Provider<AlertGesteDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public GesteProfilDao get() {
    return provideGesteProfilDao(dbProvider.get());
  }

  public static AppModule_ProvideGesteProfilDaoFactory create(
      Provider<AlertGesteDatabase> dbProvider) {
    return new AppModule_ProvideGesteProfilDaoFactory(dbProvider);
  }

  public static GesteProfilDao provideGesteProfilDao(AlertGesteDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGesteProfilDao(db));
  }
}
