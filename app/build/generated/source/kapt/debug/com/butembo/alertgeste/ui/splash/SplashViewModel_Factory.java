package com.butembo.alertgeste.ui.splash;

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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public SplashViewModel_Factory(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<AlertGesteRepository> repositoryProvider) {
    return new SplashViewModel_Factory(repositoryProvider);
  }

  public static SplashViewModel newInstance(AlertGesteRepository repository) {
    return new SplashViewModel(repository);
  }
}
