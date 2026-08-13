package com.butembo.alertgeste.di;

import com.butembo.alertgeste.data.local.AlertGesteDatabase;
import com.butembo.alertgeste.data.local.dao.AlerteDao;
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
public final class AppModule_ProvideAlerteDaoFactory implements Factory<AlerteDao> {
  private final Provider<AlertGesteDatabase> dbProvider;

  public AppModule_ProvideAlerteDaoFactory(Provider<AlertGesteDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AlerteDao get() {
    return provideAlerteDao(dbProvider.get());
  }

  public static AppModule_ProvideAlerteDaoFactory create(Provider<AlertGesteDatabase> dbProvider) {
    return new AppModule_ProvideAlerteDaoFactory(dbProvider);
  }

  public static AlerteDao provideAlerteDao(AlertGesteDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAlerteDao(db));
  }
}
