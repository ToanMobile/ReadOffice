package com.app.office.fc.poifs.eventfilesystem;

import com.app.office.fc.poifs.filesystem.DocumentDescriptor;
import com.app.office.fc.poifs.filesystem.POIFSDocumentPath;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class POIFSReaderRegistry {
    private Map chosenDocumentDescriptors = new HashMap();
    private Set omnivorousListeners = new HashSet();
    private Map selectiveListeners = new HashMap();

    POIFSReaderRegistry() {
    }

    /* access modifiers changed from: package-private */
    public void registerListener(POIFSReaderListener pOIFSReaderListener, POIFSDocumentPath pOIFSDocumentPath, String str) {
        if (!this.omnivorousListeners.contains(pOIFSReaderListener)) {
            Set set = (Set) this.selectiveListeners.get(pOIFSReaderListener);
            if (set == null) {
                set = new HashSet();
                this.selectiveListeners.put(pOIFSReaderListener, set);
            }
            DocumentDescriptor documentDescriptor = new DocumentDescriptor(pOIFSDocumentPath, str);
            if (set.add(documentDescriptor)) {
                Set set2 = (Set) this.chosenDocumentDescriptors.get(documentDescriptor);
                if (set2 == null) {
                    set2 = new HashSet();
                    this.chosenDocumentDescriptors.put(documentDescriptor, set2);
                }
                set2.add(pOIFSReaderListener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void registerListener(POIFSReaderListener pOIFSReaderListener) {
        if (!this.omnivorousListeners.contains(pOIFSReaderListener)) {
            removeSelectiveListener(pOIFSReaderListener);
            this.omnivorousListeners.add(pOIFSReaderListener);
        }
    }

    /* access modifiers changed from: package-private */
    public Iterator getListeners(POIFSDocumentPath pOIFSDocumentPath, String str) {
        HashSet hashSet = new HashSet(this.omnivorousListeners);
        Set set = (Set) this.chosenDocumentDescriptors.get(new DocumentDescriptor(pOIFSDocumentPath, str));
        if (set != null) {
            hashSet.addAll(set);
        }
        return hashSet.iterator();
    }

    private void removeSelectiveListener(POIFSReaderListener pOIFSReaderListener) {
        Set<DocumentDescriptor> set = (Set) this.selectiveListeners.remove(pOIFSReaderListener);
        if (set != null) {
            for (DocumentDescriptor dropDocument : set) {
                dropDocument(pOIFSReaderListener, dropDocument);
            }
        }
    }

    private void dropDocument(POIFSReaderListener pOIFSReaderListener, DocumentDescriptor documentDescriptor) {
        Set set = (Set) this.chosenDocumentDescriptors.get(documentDescriptor);
        set.remove(pOIFSReaderListener);
        if (set.size() == 0) {
            this.chosenDocumentDescriptors.remove(documentDescriptor);
        }
    }
}
