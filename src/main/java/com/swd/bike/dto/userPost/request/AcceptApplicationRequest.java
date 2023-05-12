package com.swd.bike.dto.userPost.request;

import com.swd.bike.core.BaseRequestData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptApplicationRequest extends BaseRequestData {
    private Long postId;
    private String applierId;
}
