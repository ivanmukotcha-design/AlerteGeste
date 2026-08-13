package com.butembo.alertgeste.data.repository;

import com.butembo.alertgeste.data.local.dao.AlerteDao;
import com.butembo.alertgeste.data.local.dao.ContactDao;
import com.butembo.alertgeste.data.local.dao.GesteProfilDao;
import com.butembo.alertgeste.data.local.dao.UtilisateurDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AlertGesteRepository_Factory implements Factory<AlertGesteRepository> {
  private final Provider<UtilisateurDao> utilisateurDaoProvider;

  private final Provider<ContactDao> contactDaoProvider;

  private final Provider<GesteProfilDao> gesteProfilDaoProvider;

  private final Provider<AlerteDao> alerteDaoProvider;

  public AlertGesteRepository_Factory(Provider<UtilisateurDao> utilisateurDaoProvider,
      Provider<ContactDao> contactDaoProvider, Provider<GesteProfilDao> gesteProfilDaoProvider,
      Provider<AlerteDao> alerteDaoProvider) {
    this.utilisateurDaoProvider = utilisateurDaoProvider;
    this.contactDaoProvider = contactDaoProvider;
    this.gesteProfilDaoProvider = gesteProfilDaoProvider;
    this.alerteDaoProvider = alerteDaoProvider;
  }

  @Override
  public AlertGesteRepository get() {
    return newInstance(utilisateurDaoProvider.get(), contactDaoProvider.get(), gesteProfilDaoProvider.get(), alerteDaoProvider.get());
  }

  public static AlertGesteRepository_Factory create(Provider<UtilisateurDao> utilisateurDaoProvider,
      Provider<ContactDao> contactDaoProvider, Provider<GesteProfilDao> gesteProfilDaoProvider,
      Provider<AlerteDao> alerteDaoProvider) {
    return new AlertGesteRepository_Factory(utilisateurDaoProvider, contactDaoProvider, gesteProfilDaoProvider, alerteDaoProvider);
  }

  public static AlertGesteRepository newInstance(UtilisateurDao utilisateurDao,
      ContactDao contactDao, GesteProfilDao gesteProfilDao, AlerteDao alerteDao) {
    return new AlertGesteRepository(utilisateurDao, contactDao, gesteProfilDao, alerteDao);
  }
}
