package org.erp.sd.documents.common;

import java.util.Set;
import java.util.TreeSet;

public class SalesDocument {
    private Set<SalesDocumentItem> itens;
    private int id;
    private int sender;
    private int receiver;
    
    public SalesDocument() {
        itens = new TreeSet<SalesDocumentItem>();
    }
    
    public final void add(SalesDocumentItem item) {
        itens.add(item);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        SalesDocument sd;
        
        if (object == this)
            return true;
        
        if (!(object instanceof SalesDocument))
            return false;
        
        sd = (SalesDocument)object;
        if (sd.getId() == id)
            return true;
        
        return false;
    }
    
    public final int getId() {
        return id;
    }
    
    public final SalesDocumentItem[] getItens() {
        return itens.toArray(new SalesDocumentItem[0]);
    }
    
    public final int getReceiver() {
        return receiver;
    }
    
    public final int getSender() {
        return sender;
    }
    
    public final void remove(SalesDocumentItem item) {
        itens.remove(item);
    }
    
    public final void setId(int id) {
        this.id = id;
    }
    
    public final void setReceiver(int receiver) {
        this.receiver = receiver;
    }
    
    public final void setSender(int sender) {
        this.sender = sender;
    }
}
