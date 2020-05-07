package edu.fondue.electronicdocuments.utils.listeners;

import edu.fondue.electronicdocuments.utils.FieldDecrypter;
import edu.fondue.electronicdocuments.utils.FieldEncrypter;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptionListener implements PreInsertEventListener, PreUpdateEventListener, PostLoadEventListener {

    private final FieldEncrypter fieldEncrypter;

    private final FieldDecrypter fieldDecrypter;

    @Override
    public void onPostLoad(PostLoadEvent event) {
        fieldDecrypter.decrypt(event.getEntity());
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        Object[] state = event.getState();
        String[] propertyNames = event.getPersister().getPropertyNames();
        Object entity = event.getEntity();
        fieldEncrypter.encrypt(state, propertyNames, entity);
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        Object[] state = event.getState();
        String[] propertyNames = event.getPersister().getPropertyNames();
        Object entity = event.getEntity();
        fieldEncrypter.encrypt(state, propertyNames, entity);
        return false;
    }
}
