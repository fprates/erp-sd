package org.erp.sd.documents;

import org.erp.sd.documents.common.SalesDocument;
import org.erp.sd.documents.common.SalesDocumentItem;
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
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final byte CREATE = 0;
    private static final byte SHOW = 1;
    private static final byte UPDATE = 2;
    private static final String[] TITLES = {
        "sales-document-create",
        "sales-document-show",
        "sales-document-update"
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
        byte mode = (Byte)vdata.getParameter("mode");
        
        if (mode != SHOW)
            new Button(container, "newcustomer");
        
        header = new DataForm(container, "header");
        new DataItem(header, Const.TEXT_FIELD, "ident");
        new DataItem(header, Const.TEXT_FIELD, "receb");
        
        itens = new Table(container, "itens");
        new TableColumn(itens, "nrpos");
        new TableColumn(itens, "cdmat");
        new TableColumn(itens, "quant");
        new TableColumn(itens, "unqtd");
        itens.setMark(true);
        
        switch(mode) {
        case CREATE:
            addTableItem(itens);
            
            new Button(container, "add");
            new Button(container, "remove");
            new Button(container, "save");
            new Button(container, "validate");
            
            break;
            
        case UPDATE:
            new Button(container, "add");
            new Button(container, "remove");
            new Button(container, "save");
            new Button(container, "validate");
            
            break;
            
        case SHOW:
            break;
        }
        
        vdata.setNavbarActionEnabled("back", true);
        vdata.setTitle(TITLES[mode]);
        vdata.addContainer(container);
    }
    
    public final void create(ViewData vdata) {
        vdata.setReloadableView(true);
        vdata.export("mode", CREATE);
        vdata.redirect(null, "baseform");
    }
    
    public final String getTableValue(TableItem item, String name) {
        InputComponent input = (InputComponent)item.get(name);
        
        return input.getValue();
    }
    
    public final void main(ViewData vdata) {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "document");
        
        new DataItem(form, Const.TEXT_FIELD, "ident");
//        new DataItem(form, Const.RADIO_BUTTON, "dvtype");
        
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        vdata.addContainer(container);
        vdata.setTitle("sales-document-selector");
        vdata.setNavbarActionEnabled("back", true);
    }
    
    public final void newcustomer(ViewData vdata) {
        vdata.export("mode", CREATE);
        vdata.redirect("erp-sd-customer", "baseform");
    }
    
    public final void remove(ViewData vdata) {
        Table table = (Table)vdata.getElement("itens");
        
        for (TableItem item : table.getItens())
            if (item.isSelected())
                table.remove(item);
    }
    
    public final void save(ViewData vdata) throws Exception {
        SalesDocumentItem sditem;
        byte modo = (Byte)vdata.getParameter("mode");
        Table itens = (Table)vdata.getElement("itens");
        SD sd = new SD();
        SalesDocument sddoc = new SalesDocument();
        
        for (TableItem item : itens.getItens()) {
            sditem = new SalesDocumentItem();
            sditem.setId(Integer.parseInt(getTableValue(item, "nrpos")));
            sditem.setSalesDocument(sddoc);
            
            sddoc.add(sditem);
        }
        
        switch (modo) {
        case CREATE:
            sd.save(sddoc);
            vdata.setReloadableView(true);
            vdata.export("mode", UPDATE);
            
            break;
        case UPDATE:
            sd.update(sddoc);
            
            break;
        }
            
    }
    
    private final SalesDocument select(int ident) {
        SD sd;
        SalesDocument sddoc;
        
        sd = new SD();
        sddoc = sd.get(ident);
        
        return (sddoc == null)?null : sddoc;
    }
    
    public final void show(ViewData vdata) {
        SalesDocument sddoc;
        DataForm form = (DataForm)vdata.getElement("document");
        int ident = toInteger(form.getValue("ident"));
        
        
        if (ident == 0) {
            vdata.message(Const.ERROR, "document.identifier.obligatory");
            return;
        }
        
        sddoc = select(ident);
        
        if (sddoc == null) {
            vdata.message(Const.ERROR, "invalid.sales.order");
            return;
        }
        
        vdata.export("sddoc", sddoc);
        vdata.setReloadableView(true);
        vdata.export("mode", SHOW);
        vdata.redirect(null, "baseform");
    }
    
    private final int toInteger(String value) {
        return (value.equals(""))? 0 : Integer.parseInt(value);
    }
    
    public final void update(ViewData vdata) {
        SalesDocument sddoc;
        DataForm form = (DataForm)vdata.getElement("document");
        int ident = toInteger(form.getValue("ident"));
        
        if (ident == 0) {
            vdata.message(Const.ERROR, "document.identifier.obligatory");
            return;
        }
        
        sddoc = select(ident);
        
        if (sddoc == null) {
            vdata.message(Const.ERROR, "invalid.sales.order");
            return;
        }
        
        vdata.export("sddoc", sddoc);
        vdata.export("mode", UPDATE);
        vdata.setReloadableView(true);
        vdata.redirect(null, "baseform");
    }
    
    public final void validate(ViewData vdata) {
        
    }
}
