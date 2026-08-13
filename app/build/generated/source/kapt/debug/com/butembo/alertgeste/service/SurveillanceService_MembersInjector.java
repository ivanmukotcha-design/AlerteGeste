package com.butembo.alertgeste.service;

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
public final class SurveillanceService_MembersInjector implements MembersInjector<SurveillanceService> {
  private final Provider<AlertGesteRepository> repositoryProvider;

  public SurveillanceService_MembersInjector(Provider<AlertGesteRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<SurveillanceService> create(
      Provider<AlertGesteRepository> repositoryProvider) {
    return new SurveillanceService_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(SurveillanceService instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.butembo.alertgeste.service.SurveillanceService.repository")
  public static void injectRepository(SurveillanceService instance,
      AlertGesteRepository repository) {
    instance.repository = repository;
  }
}
