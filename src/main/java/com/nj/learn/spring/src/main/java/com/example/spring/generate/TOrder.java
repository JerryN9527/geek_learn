package com.example.spring.generate;

import java.io.Serializable;
import lombok.Data;

/**
 * @author
 */
@Data
public class  TOrder implements Serializable {
    private Long orderId;

    private Integer userId;

    private String status;

    private static final long serialVersionUID = 1L;
}