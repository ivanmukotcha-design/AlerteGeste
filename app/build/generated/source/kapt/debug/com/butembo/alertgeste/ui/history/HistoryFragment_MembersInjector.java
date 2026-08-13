package com.butembo.alertgeste.ui.history;

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
public final class HistoryFragment_MembersInjector implements MembersInjector<HistoryFragment> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public HistoryFragment_MembersInjector(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<HistoryFragment> create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new HistoryFragment_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(HistoryFragment instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.butembo.alertgeste.ui.history.HistoryFragment.repository")
  public static void injectRepository(HistoryFragment instance, AlertGesteRepository repository) {
    instance.repository = repository;
  }
}
