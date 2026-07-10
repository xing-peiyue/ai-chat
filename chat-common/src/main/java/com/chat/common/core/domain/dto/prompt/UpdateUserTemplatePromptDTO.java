package com.chat.common.core.domain.dto.prompt;

import lombok.*;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 更新用户模板提示语
 *
 * @author ：xingpeiyue
 * @date ：2023/12/28 21:37
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class UpdateUserTemplatePromptDTO implements Serializable {
    private static final long serialVersionUID = 5203914261809073836L;


    /**
     * 模板id
     */
    private Long id;
    @NotBlank(message = "模板名称不能为空")
    private String name;
    @NotBlank(message = "参数有误")
    private String channel;
    @NotBlank(message = "参数有误")
    private String type;
    /**
     * 模板语言
     */
    @NotBlank(message = "模板语言不能为空")
    private String prompt;
}
