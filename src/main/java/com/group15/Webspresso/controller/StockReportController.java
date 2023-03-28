// package com.group15.Webspresso.controller;

// import javax.sql.DataSource;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import jakarta.servlet.http.HttpServletResponse;

// @RestController
// public class StockReportController {

//     @Autowired
//     private DataSource dataSource;

//     @GetMapping("/stockReport")
//     public void generateStockReport(HttpServletResponse response) throws JRException, IOException {
//         // Load the JasperReports template from the classpath
//         InputStream inputStream = this.getClass().getResourceAsStream("/jasper/stockReport.jrxml");

//         // Compile the JasperReports template into a JasperReport object
//         JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

//         // Fetch the data from

//     }

// }