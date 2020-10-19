package cn.cruder.workflow.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Dsx
 * @Date: 2020-10-18 22:08
 * @Description: description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity {
    private Integer code;
    private String msg;
    private Object data;
}
