package com.thecode.WebSite_CHECK_XML.Model.application;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorKCBGroup {
    private String maLk;
    private List<ErrorKCBDetail> errors;

    public ErrorKCBGroup(String maLk) {
        this.maLk = maLk;
        this.errors = new ArrayList<>();
    }

    public String getMaLk() {
        return maLk;
    }

    public List<ErrorKCBDetail> getErrors() {
        return errors;
    }

    public void addError(ErrorKCBDetail error) {
       if (error != null && error.getErrorDetail() != null && !error.getErrorDetail().isBlank()) {
        this.errors.add(error);
    }
    }
}

