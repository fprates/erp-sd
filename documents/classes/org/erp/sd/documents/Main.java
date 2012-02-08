package org.erp.sd.documents;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final int CREATE = 0;
    private static final int SHOW = 1;
    private static final String[] TITLES = {
        "sales-document-create",
        "sales-document-show"
    };
    
    public void add(ViewData vdata) {
        Table table = (Table)vdata.getElement("itens");
        
        addTableItem(table);
    }
    
    private final void addTableItem(Table table) {
        int last;
        TableItem tableitem;
        InputComponent input;
        Element element;
        
        last = table.length();
        if (last > 0) {
            tableitem = table.getTableItem(last - 1);
            input = (InputComponent)tableitem.get("nrpos");
            last = Integer.parseInt(input.getValue());
        }
        
        tableitem = new TableItem(table);
        element = new TextField(table, "nrpos");
        tableitem.add(element);
        
        element.setEnabled(false);
        input = (InputComponent)element;
        input.setValue(Integer.toString(last + 1));
        
        tableitem.add(new TextField(table, "cdmat"));
        tableitem.add(new TextField(table, "quant"));
        tableitem.add(new TextField(table, "unqtd"));
    }
    
    public final void baseform(ViewData vdata) throws Exception {
        DataForm header;
        Table itens;
        Container container = new Form(null, "base");
        int mode = (Integer)vdata.getParameter("mode");
        
        if (mode != SHOW)
            new Button(container, "newcustomer");
        
        header = new DataForm(container, "header");
        new DataItem(header, Const.TEXT_FIELD, "ident");
        new DataItem(header, Const.TEXT_FIELD, "receb");
        
        itens = new Table(container, "itens");
        itens.addColumn("nrpos");
        itens.addColumn("cdmat");
        itens.addColumn("quant");
        itens.addColumn("unqtd");
        itens.setMark(true);
        
        switch(mode) {
        case CREATE:
            addTableItem(itens);
            break;
        case SHOW:
            break;
        }
        
        new Button(container, "add");
        new Button(container, "remove");
        new Button(container, "save");
        
        vdata.setNavbarActionEnabled("back", true);
        vdata.setTitle(TITLES[mode]);
        vdata.addContainer(container);
    }
    
    public final void create(ViewData vdata) {
        vdata.setReloadableView(true);
        vdata.export("mode", CREATE);
        vdata.redirect(null, "baseform");
    }
    
    public final void main(ViewData vdata) {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "document");
        
        new DataItem(form, Const.TEXT_FIELD, "ident");
//        new DataItem(form, Const.RADIO_BUTTON, "dvtype");
        
        
        form.addAction("create");
        form.addAction("show");
        form.addAction("update");
        
        vdata.addContainer(container);
        vdata.setTitle("sales-document-selector");
        vdata.setNavbarActionEnabled("back", true);
    }
    
    public final void newcustomer(ViewData vdata) {
        vdata.export("mode", CREATE);
        vdata.redirect("erp-sd-customer", "baseform");
    }
    
    public final void newmaterial(ViewData vdata) {
        vdata.export("mode", CREATE);
        vdata.redirect("erpmm", "main");
    }
    
    public final void remove(ViewData vdata) {
        Table table = (Table)vdata.getElement("itens");
        
        for (TableItem item : table.getItens())
            if (item.isSelected())
                table.remove(item);
    }
    
    public final void save(ViewData vdata) throws Exception {
        
    }
    
    public final void show(ViewData vdata) {
        DataForm form = (DataForm)vdata.getElement("document");
        String ident = form.getValue("ident");
        
        if (ident.equals("")) {
            vdata.message(Const.ERROR, "document.identifier.obligatory");
            return;
        }
        
        vdata.setReloadableView(true);
        vdata.export("mode", SHOW);
        vdata.redirect(null, "baseform");
    }
}
