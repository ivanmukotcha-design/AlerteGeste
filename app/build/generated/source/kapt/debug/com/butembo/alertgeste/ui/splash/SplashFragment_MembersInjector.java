package com.butembo.alertgeste.ui.splash;

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
public final class SplashFragment_MembersInjector implements MembersInjector<SplashFragment> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public SplashFragment_MembersInjector(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<SplashFragment> create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new SplashFragment_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(SplashFragment instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.butembo.alertgeste.ui.splash.SplashFragment.repository")
  public static void injectRepository(SplashFragment instance, AlertGesteRepository repository) {
    instance.repository = repository;
  }
}
