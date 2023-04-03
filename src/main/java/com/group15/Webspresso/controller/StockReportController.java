package com.group15.Webspresso.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group15.Webspresso.repository.ProductRepository;
import com.group15.Webspresso.service.impl.ReportServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
public class StockReportController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReportServiceImpl reportService;

    @GetMapping("/productReport")
    public void generatProductReport(HttpServletResponse response) throws JRException, IOException{
        // Generate the report
        JasperPrint jasperPrint = reportService.exportProductReport();

        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=orderReport.pdf");

        // Export the report to the response output stream
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @GetMapping("/orderReport")
    public void generateOrderReport(HttpServletResponse response) throws JRException, IOException {
        // Generate the report
        JasperPrint jasperPrint = reportService.exportOrdersReport();

        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=orderReport.pdf");

        // Export the report to the response output stream
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

}