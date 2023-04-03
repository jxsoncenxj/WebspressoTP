package com.group15.Webspresso.classes;

import java.util.ArrayList;
import java.util.List;

import com.group15.Webspresso.entity.Product;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ProductReportDataSource implements JRDataSource {

    private List<Product> productList = new ArrayList<>();
    private int index = -1;

    public ProductReportDataSource(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return (index < productList.size());
    }

    @Override
    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;
        String fieldName = field.getName();

        if ("productName".equals(fieldName)) {
            value = productList.get(index).getProductName();
        } else if ("quantity".equals(fieldName)) {
            value = productList.get(index).getProductStock();
        }

        return value;
    }
}
