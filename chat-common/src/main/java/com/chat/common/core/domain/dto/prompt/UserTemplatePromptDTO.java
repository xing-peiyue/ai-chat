package com.chat.common.core.domain.dto.prompt;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ：xingpeiyue
 * @date ：2023/12/28 20:10
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class UserTemplatePromptDTO implements Serializable {
    private static final long serialVersionUID = 280387165917749950L;
    private String channel;
    private String name;
    private String shortName;
    private Map<String, String> data;
}
