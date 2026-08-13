package com.butembo.alertgeste.di;

import com.butembo.alertgeste.data.local.AlertGesteDatabase;
import com.butembo.alertgeste.data.local.dao.ContactDao;
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
public final class AppModule_ProvideContactDaoFactory implements Factory<ContactDao> {
  private final Provider<AlertGesteDatabase> dbProvider;

  public AppModule_ProvideContactDaoFactory(Provider<AlertGesteDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ContactDao get() {
    return provideContactDao(dbProvider.get());
  }

  public static AppModule_ProvideContactDaoFactory create(Provider<AlertGesteDatabase> dbProvider) {
    return new AppModule_ProvideContactDaoFactory(dbProvider);
  }

  public static ContactDao provideContactDao(AlertGesteDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideContactDao(db));
  }
}
