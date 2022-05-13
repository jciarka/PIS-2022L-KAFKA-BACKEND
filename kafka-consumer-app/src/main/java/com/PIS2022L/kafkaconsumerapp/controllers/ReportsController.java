package com.PIS2022L.kafkaconsumerapp.controllers;

import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class ReportsController {

    @GetMapping(
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody byte[] getEmptyDocument() throws IOException {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "\t<style>\n" +
                "\t.card {\n" +
                "\t  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);\n" +
                "\t  padding: 10px;\n" +
                "\t  transition: 0.3s;\n" +
                "\t  display: flex;\n" +
                "\t  flex-wrap: wrap;\n" +
                "\t  align-content: center;\n" +
                "\t  background-color: #f5f7fa;\n" +
                "\t}\n" +
                "\t\n" +
                "\tdiv.a {\n" +
                "\t  text-align: center;\n" +
                "\t}\n" +
                "\n" +
                "\t.card:hover {\n" +
                "\t  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);\n" +
                "\t}\n" +
                "\n" +
                "\t.container {\n" +
                "\t  padding: 2px 16px;\n" +
                "\t}\n" +
                "\n" +
                "\tbody {\n" +
                "\t  font-family: Helvetica, Arial, sans-serif;\n" +
                "\t}\n" +
                "\n" +
                "\ta {\n" +
                "\t  background-color: #1F7F4C; font-size: 18px; font-family: Helvetica, Arial, sans-serif; font-weight: bold; text-decoration: none; padding: 5px 10px; color: #ffffff; border-radius: 5px; display: inline-block; mso-padding-alt: 0;>\n" +
                "\t}\n" +
                "\n" +
                "\t</style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  \n" +
                "  <div class=\"container m-4 jusutify-content-center\">\n" +
                "  \n" +
                "  \t<div class=\"card\" style=\"width: 40rem;\">\n" +
                "\t  <div class=\"card-body\">\n" +
                "\t\t  <h2>Project PIS</h2>\n" +
                "\t  \n" +
                "\t\t  <h4>$PROJECT_NAME - Build # $BUILD_NUMBER </br>\n" +
                "\t\t  Has been released with status: $BUILD_STATUS</h4>\n" +
                "\t\t  \n" +
                "\t\t  <h4>Available actions:</h4>\n" +
                "\t\t  \n" +
                "\t\t  <a href=\"http://localhost:8888/job/docker%20ci-cd%20pis-frontend-jenkinsfile/\"> \n" +
                "\t\t  \tJenkins project site\n" +
                "\t  \t  </a>\n" +
                "\t\t  <a href=\"https://github.com/jciarka/PIS-2022L-KAFKA-PROD-FRONT/\"> \n" +
                "\t\t  \tGithub project site\n" +
                "\t\t  </a>\n" +
                "\t\t  <a href=\"http://localhost:8888/job/docker%20deployment%20pis-frontend-jenkinsfile/build?token=frontend\" > \n" +
                "\t\t  \tDeploy appliation\n" +
                "                 </a> \n" +
                "\t  </div>\n" +
                "\t</div>\n" +
                "  </div>\n" +
                "  </body>\n" +
                "</html>  ";
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, pdf);

        byte[] content = pdf.toByteArray();
        pdf.close();
        return content;
    }
}
