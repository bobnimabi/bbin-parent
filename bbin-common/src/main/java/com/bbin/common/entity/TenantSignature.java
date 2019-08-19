package com.bbin.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2019-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TenantSignature对象", description="")
public class TenantSignature implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long tenantId;

    private Long channelId;

    @ApiModelProperty(value = "RSA私钥")
    private String rsaPrivateKey;

    @ApiModelProperty(value = "RSA公钥")
    private String rsaPublicKey;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreatedTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModifiedTime;


}
