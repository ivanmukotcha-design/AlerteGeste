package com.butembo.alertgeste.ui.register;

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
public final class RegisterFragment_MembersInjector implements MembersInjector<RegisterFragment> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public RegisterFragment_MembersInjector(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<RegisterFragment> create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new RegisterFragment_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(RegisterFragment instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.butembo.alertgeste.ui.register.RegisterFragment.repository")
  public static void injectRepository(RegisterFragment instance, AlertGesteRepository repository) {
    instance.repository = repository;
  }
}
