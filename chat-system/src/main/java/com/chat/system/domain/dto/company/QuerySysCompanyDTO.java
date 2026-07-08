package com.chat.system.domain.dto.company;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zk
 * @date ：2024/1/24 19:00
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class QuerySysCompanyDTO implements Serializable {
    private static final long serialVersionUID = -3324033745432114540L;

    private String name;
    private String nickName;
    private String phone;
    private String status;

}
