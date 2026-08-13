package com.butembo.alertgeste.ui.contacts;

import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ContactsViewModel_Factory implements Factory<ContactsViewModel> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public ContactsViewModel_Factory(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ContactsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ContactsViewModel_Factory create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new ContactsViewModel_Factory(repositoryProvider);
  }

  public static ContactsViewModel newInstance(AlertGesteRepository repository) {
    return new ContactsViewModel(repository);
  }
}
