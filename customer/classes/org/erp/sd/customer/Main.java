package org.erp.sd.customer;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final int CREATE = 0;
    private static final int SHOW = 1;
    private static final String[] TITLES = {
        "create-customer",
        "show-customer"
    };
    
    public final void baseform(ViewData vdata) {
        int mode = (Integer)vdata.getParameter("mode");
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "form");
        
        new DataItem(form, Const.TEXT_FIELD, "ident");
        new DataItem(form, Const.TEXT_FIELD, "name");
        
        form.addAction("save");
        
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
        mainform.addAction("show");
        mainform.addAction("create");
        mainform.addAction("update");
        mainform.addAction("delete");
        
        vdata.setNavbarActionEnabled("back", true);
        vdata.addContainer(container);
        vdata.setFocus("ident");
        vdata.setTitle("customer-master");
    }
    
    public final void save(ViewData vdata) {
        
    }
    
    public final void show(ViewData vdata) {
        DataItem ident = (DataItem)vdata.getElement("ident");
        
        vdata.export("ident", ident.getValue());
        vdata.export("mode", SHOW);
        vdata.redirect(null, "baseform");
    }
    
    public final void update(ViewData vdata) {
        
    }
}
