package kr.co.kiyu.designpatterns.builderpattern.sample.util;

import java.io.*;

import org.springframework.stereotype.Component;

@Component
public class HTMLBuilder extends Builder {

    private String filename;   // 작성할 파일명
    private PrintWriter writer; // 파일에 쓸 PrintWriter
    private StringBuilder htmlContent; // 생성된 HTML

    public HTMLBuilder() {
        this.htmlContent = new StringBuilder();
    }

    public void buildTitle(String title) {
        filename = title + ".html";

        try {
            writer = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String titleTag = "<html><head><title>" + title + "</title></head><body>";
        
        writer.println(titleTag);
        writer.println("<h1>" + title + "</h1>");

        htmlContent.append(titleTag).append("\n");
        htmlContent.append("<h1>").append(title).append("</h1>\n");
    }

    public void buildString(String str) {
        String paragraph = "<p>" + str + "</p>";
        writer.println(paragraph);
        htmlContent.append(paragraph).append("\n");
    }

    public void buildItems(String[] items) {
        writer.println("<ul>");
        htmlContent.append("<ul>\n");

        for (String item : items) {
            String listItem = "<li>" + item + "</li>";
            writer.println(listItem);
            htmlContent.append(listItem).append("\n");
        }

        writer.println("</ul>");
        htmlContent.append("</ul>\n");
    }

    public void buildDone() {
        writer.println("</body></html>");
        writer.close();

        htmlContent.append("</body></html>\n");
    }

    public String getResult() {
        return filename; // 기존과 동일하게 파일명 반환
    }

    public String getHtmlContent() {
        return htmlContent.toString(); // HTML 문자열 반환
    }
}
