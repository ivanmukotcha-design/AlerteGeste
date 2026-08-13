package com.butembo.alertgeste.ui.gesture;

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
public final class GestureViewModel_Factory implements Factory<GestureViewModel> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public GestureViewModel_Factory(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GestureViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static GestureViewModel_Factory create(Provider<AlertGesteRepository> repositoryProvider) {
    return new GestureViewModel_Factory(repositoryProvider);
  }

  public static GestureViewModel newInstance(AlertGesteRepository repository) {
    return new GestureViewModel(repository);
  }
}
