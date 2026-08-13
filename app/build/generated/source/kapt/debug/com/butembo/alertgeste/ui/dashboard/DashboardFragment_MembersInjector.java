package com.butembo.alertgeste.ui.dashboard;

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
public final class DashboardFragment_MembersInjector implements MembersInjector<DashboardFragment> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public DashboardFragment_MembersInjector(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<DashboardFragment> create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new DashboardFragment_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(DashboardFragment instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.butembo.alertgeste.ui.dashboard.DashboardFragment.repository")
  public static void injectRepository(DashboardFragment instance, AlertGesteRepository repository) {
    instance.repository = repository;
  }
}
