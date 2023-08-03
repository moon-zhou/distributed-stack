package org.moonzhou.onebyone.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    HANDLE_TOO_FAST(400,"您操作太快咯！！请稍后再试把！"),
    UPLOAD_FILE_FAIL(500,"上传文件失败！！"),
    FORM_TOO_MANY(400,"您评论的太快啦！！有啥慢慢说呗！！"),
    REQUEST_TOO_FAST(400,"您访问的有点频繁哦，请不要做坏事呢！！"),
    ;
    private int code;
    private String msg;

}