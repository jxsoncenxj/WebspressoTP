package com.group15.Webspresso.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.group15.Webspresso.entity.Order;
import com.group15.Webspresso.entity.Product;
import com.group15.Webspresso.repository.OrderRepository;
import com.group15.Webspresso.repository.ProductRepository;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;

@Service
public class ReportServiceImpl {
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    public JasperPrint exportProductReport() throws FileNotFoundException, JRException {

        List<Product> products = productRepository.findAll();

        //Load the jasper file and compile it
        File file = ResourceUtils.getFile("classpath:productReports.jrxml");
        JasperDesign jasperDesign;
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);
        
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("createdBy", "SOLO");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return jasperPrint;
    }

    public JasperPrint exportOrdersReport() throws JRException, IOException {

        List<Order> orders = orderRepository.findAll();

        // Load the jasper file and compile it
        File file = ResourceUtils.getFile("classpath:orderReports.jrxml");
        JasperDesign jasperDesign;
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orders);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("createdBy", "SOLO");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return jasperPrint;
    }
}
