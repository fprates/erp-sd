package org.erp.sd.customer.common;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 7664068763929007288L;
    private int id;
    
    /**
     * 
     * @return
     */
    public final int getId() {
        return id;
    }
    
    /**
     * 
     * @param id
     */
    public final void setId(int id) {
        this.id = id;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        Customer customer;
        
        if (object == this)
            return true;
        
        if (!(object instanceof Customer))
            return false;
        
        customer = (Customer)object;
        if (customer.getId() == id)
            return true;
        
        return false;
    }
}
