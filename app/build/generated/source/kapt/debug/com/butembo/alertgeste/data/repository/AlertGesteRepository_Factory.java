package com.butembo.alertgeste.data.repository;

import com.butembo.alertgeste.data.local.AlertGesteDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AlertGesteRepository_Factory implements Factory<AlertGesteRepository> {
  private final Provider<AlertGesteDatabase> dbProvider;

  public AlertGesteRepository_Factory(Provider<AlertGesteDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AlertGesteRepository get() {
    return newInstance(dbProvider.get());
  }

  public static AlertGesteRepository_Factory create(Provider<AlertGesteDatabase> dbProvider) {
    return new AlertGesteRepository_Factory(dbProvider);
  }

  public static AlertGesteRepository newInstance(AlertGesteDatabase db) {
    return new AlertGesteRepository(db);
  }
}
