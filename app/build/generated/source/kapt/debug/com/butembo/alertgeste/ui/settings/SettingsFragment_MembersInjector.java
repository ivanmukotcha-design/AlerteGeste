package com.butembo.alertgeste.ui.settings;

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
public final class SettingsFragment_MembersInjector implements MembersInjector<SettingsFragment> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public SettingsFragment_MembersInjector(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<SettingsFragment> create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new SettingsFragment_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(SettingsFragment instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.butembo.alertgeste.ui.settings.SettingsFragment.repository")
  public static void injectRepository(SettingsFragment instance, AlertGesteRepository repository) {
    instance.repository = repository;
  }
}
