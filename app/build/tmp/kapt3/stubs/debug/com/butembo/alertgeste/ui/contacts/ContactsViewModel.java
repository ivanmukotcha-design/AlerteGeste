package com.butembo.alertgeste.ui.contacts;

import androidx.lifecycle.ViewModel;
import com.butembo.alertgeste.data.local.entity.Contact;
import com.butembo.alertgeste.data.repository.AlertGesteRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u0016\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/butembo/alertgeste/ui/contacts/ContactsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;", "(Lcom/butembo/alertgeste/data/repository/AlertGesteRepository;)V", "contacts", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/butembo/alertgeste/data/local/entity/Contact;", "getContacts", "()Lkotlinx/coroutines/flow/StateFlow;", "ajouterContact", "", "contact", "delete", "(Lcom/butembo/alertgeste/data/local/entity/Contact;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mettreAJourContact", "save", "supprimerContact", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ContactsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.butembo.alertgeste.data.repository.AlertGesteRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.butembo.alertgeste.data.local.entity.Contact>> contacts = null;
    
    @javax.inject.Inject()
    public ContactsViewModel(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.repository.AlertGesteRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.butembo.alertgeste.data.local.entity.Contact>> getContacts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object save(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void ajouterContact(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact) {
    }
    
    public final void mettreAJourContact(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact) {
    }
    
    public final void supprimerContact(@org.jetbrains.annotations.NotNull()
    com.butembo.alertgeste.data.local.entity.Contact contact) {
    }
}