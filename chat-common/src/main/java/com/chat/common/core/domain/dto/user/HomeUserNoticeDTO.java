package com.chat.common.core.domain.dto.user;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 主页脚本客服聊天
 *
 * @author ：xingpeiyue
 * @date ：2023/9/12 18:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class HomeUserNoticeDTO implements Serializable {
    private static final long serialVersionUID = -5764449574578034855L;
    //是否都不在线 1 是  0 否
    private String allOffline;
    private String avatar;
    private String userName;
    private String company;
    private int autoOpenDialogTime;
    private String description;

}
