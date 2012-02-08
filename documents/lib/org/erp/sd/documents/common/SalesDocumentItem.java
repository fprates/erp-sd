package org.erp.sd.documents.common;

public class SalesDocumentItem implements Comparable<SalesDocumentItem> {
    private SalesDocument sd;
    private int id;

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(SalesDocumentItem sdi) {
        int dif = sd.getId() - sdi.getSalesDocument().getId();
        
        if (dif != 0)
            return dif;

        return id - sdi.getId();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        SalesDocumentItem sdi;
        
        if (object == this)
            return true;
        
        if (!(object instanceof SalesDocumentItem))
            return false;
        
        sdi = (SalesDocumentItem)object;
        if (!sd.equals(sdi.getSalesDocument()) || (id != sdi.getId()))
            return false;
        
        return true;
    }
    
    public final int getId() {
        return id;
    }
    
    public final SalesDocument getSalesDocument() {
        return sd;
    }
    
    public final void setId(int id) {
        this.id = id;
    }
    
    public final void setSalesDocument(SalesDocument sd) {
        this.sd = sd;
    }
}
