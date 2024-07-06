package com.example.adminniospersonal;

public class PdfModal {
    public String standard, category, url, name;

    public PdfModal() {
    }

    public PdfModal(String standard, String category, String url, String name) {
        this.standard = standard;
        this.category = category;
        this.url = url;
        this.name = name;
    }

    public String getStandard() {
        return standard;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
