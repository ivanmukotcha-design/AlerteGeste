package com.butembo.alertgeste.ui.contacts;

import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ContactsFragment_MembersInjector implements MembersInjector<ContactsFragment> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public ContactsFragment_MembersInjector(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<ContactsFragment> create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new ContactsFragment_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(ContactsFragment instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.butembo.alertgeste.ui.contacts.ContactsFragment.repository")
  public static void injectRepository(ContactsFragment instance, AlertGesteRepository repository) {
    instance.repository = repository;
  }
}
