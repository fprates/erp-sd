package org.erp.sd.customer;

import org.erp.sd.customer.common.Customer;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final byte CREATE = 0;
    private static final byte SHOW = 1;
    private static final byte UPDATE = 2;
    private static final String[] TITLES = {
        "create-customer",
        "show-customer",
        "update-customer"
    };
    
    public final void baseform(ViewData vdata) {
        byte mode = (Byte)vdata.getParameter("mode");
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "form");
        
        new DataItem(form, Const.TEXT_FIELD, "ident");
        new DataItem(form, Const.TEXT_FIELD, "name");
        
        new Button(container, "save");
        
        vdata.addContainer(container);
        vdata.setTitle(TITLES[mode]);
        vdata.setNavbarActionEnabled("back", true);
    }
    
    public final void create(ViewData vdata) {
        vdata.export("mode", CREATE);
        vdata.redirect(null, "baseform");
    }
    
    public final void delete(ViewData vdata) {
        
    }
    
    public void main(ViewData vdata) {
        Container container = new Form(null, "main");
        DataForm mainform = new DataForm(container, "mainform");
        
        new DataItem(mainform, Const.TEXT_FIELD, "ident");
        new Button(container, "show");
        new Button(container, "create");
        new Button(container, "update");
        new Button(container, "delete");
        
        vdata.setNavbarActionEnabled("back", true);
        vdata.addContainer(container);
        vdata.setFocus("ident");
        vdata.setTitle("customer-master");
    }
    
    public final void save(ViewData vdata) {
        SDCustomer sdcust = new SDCustomer();
        byte modo = (Byte)vdata.getParameter("mode");
        Customer customer;
        DataItem dataitem;
        
        customer = new Customer();
        dataitem = (DataItem)vdata.getElement("ident");
        customer.setId(toInteger(dataitem.getValue()));
        
        switch (modo) {
        case CREATE:
            sdcust.save(customer);
            vdata.setReloadableView(true);
            vdata.export("mode", UPDATE);
            
            break;
        case UPDATE:
            sdcust.update(customer);
            
            break;
        }
    }
    
    public final void show(ViewData vdata) {
        DataItem dataitem = (DataItem)vdata.getElement("ident");
        SDCustomer sdcust = new SDCustomer();
        int ident = toInteger(dataitem.getValue());
        Customer customer;
        
        if (ident == 0) {
            vdata.message(Const.ERROR, "customer.is.required");
            return;
        }
        
        customer = sdcust.get(ident);
        
        if (customer == null) {
            vdata.message(Const.ERROR, "invalid.customer");
            return;
        }
        
        vdata.export("customer", customer);
        vdata.export("mode", SHOW);
        vdata.setReloadableView(true);
        vdata.redirect(null, "baseform");
    }
    
    private final int toInteger(String value) {
        return (value.equals(""))? 0 : Integer.parseInt(value);
    }
    
    public final void update(ViewData vdata) {
        DataItem dataitem = (DataItem)vdata.getElement("ident");
        SDCustomer sdcust = new SDCustomer();
        int ident = toInteger(dataitem.getValue());
        Customer customer;
        
        if (ident == 0) {
            vdata.message(Const.ERROR, "customer.is.required");
            return;
        }
        
        customer = sdcust.get(ident);
        
        if (customer == null) {
            vdata.message(Const.ERROR, "invalid.customer");
            return;
        }
        
        vdata.export("customer", customer);
        vdata.export("mode", UPDATE);
        vdata.setReloadableView(true);
        vdata.redirect(null, "baseform");
    }
}
