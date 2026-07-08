package com.chat.system.domain.dto.role;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: chat
 * @description:
 * @author: peiyue.xing
 * @create: 2023-12-12 23:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class SysRoleSaveUpdateDTO implements Serializable {
    private static final long serialVersionUID = -2528328743404992875L;
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色权限
     */
    @NotBlank(message = "权限字符不能为空")
    private String roleKey;

    /**
     * 角色排序
     */
    @NotBlank(message = "角色排序不能为空")
    private String roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    private String dataScope;

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    private boolean deptCheckStrictly;

    /**
     * 角色状态（1正常 0停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private String remark;

    /**
     * 菜单组
     */
    private Long[] menuIds;

    /**
     * 部门组（数据权限）
     */
    private Long[] deptIds;
}
