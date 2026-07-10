package com.chat.common.core.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2023/11/17 15:16
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class RegisterCustomerDTO implements Serializable {
    private static final long serialVersionUID = -2920603641406499819L;
    @NotNull(message = "账号不能为空")
    private String username;
    @NotNull(message = "昵称不能为空")
    private String nickName;
}
