package com.bbin.api.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class DomainQuryVO extends  BaseVO{
    private Long id;

    @ApiModelProperty(value = "1、域名 2、ip地址")
    private Integer siteType;

    @ApiModelProperty(value = "域名")
    private String siteName;

    private String memo;

    @ApiModelProperty(value = "-1 删除  1、启用 2、暂停 ")
    private Integer status;

    private LocalDateTime gmtModifiedTime;

    private LocalDateTime gmtCreatedTime;

}
