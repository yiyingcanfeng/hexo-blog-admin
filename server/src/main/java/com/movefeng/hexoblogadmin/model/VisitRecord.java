package com.movefeng.hexoblogadmin.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VisitRecord {

    private Integer id;
    private java.util.Date visitTime;
    private String visitUrl;
    private String articleTitle;
    private String remoteIp;
    private Integer remotePort;

}
