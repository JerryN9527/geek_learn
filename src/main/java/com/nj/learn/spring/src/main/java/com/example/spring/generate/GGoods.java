package com.example.spring.generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * g_goods
 * @author 
 */
@Data
@Accessors(chain = true)
public class GGoods implements Serializable {
    private Integer id;

    private String gName;

    private Double gUnitPrice;

    private String gCommit;

    private String gAbout;

    private String gImageUrl;

    private Boolean delSign;

    private Date creatTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}