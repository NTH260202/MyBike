package com.swd.bike.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.servlet.http.HttpServletRequest;
public class BaseRequestData extends RequestData {
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String cifNo;
    @JsonIgnore
    private HttpServletRequest context;
    @JsonIgnore
    private String clientId;
    @JsonIgnore
    private String secretKey;
    @JsonIgnore
    private String userId;
    @JsonIgnore
    private Long timestamp;
    @JsonIgnore
    private String encryptedData;
    @JsonIgnore
    private String signature;

    protected BaseRequestData(BaseRequestDataBuilder<?, ?> b) {
        super(b);
        this.accessToken = b.accessToken;
        this.cifNo = b.cifNo;
    }

    public static BaseRequestDataBuilder<?, ?> builder() {
        return new BaseRequestDataBuilderImpl();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getCifNo() {
        return this.cifNo;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public HttpServletRequest getContext() {
        return context;
    }

    public void setContext(HttpServletRequest context) {
        this.context = context;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseRequestData)) {
            return false;
        } else {
            BaseRequestData other = (BaseRequestData)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$accessToken = this.getAccessToken();
                Object other$accessToken = other.getAccessToken();
                if (this$accessToken == null) {
                    if (other$accessToken != null) {
                        return false;
                    }
                } else if (!this$accessToken.equals(other$accessToken)) {
                    return false;
                }

                Object this$cifNo = this.getCifNo();
                Object other$cifNo = other.getCifNo();
                if (this$cifNo == null) {
                    if (other$cifNo != null) {
                        return false;
                    }
                } else if (!this$cifNo.equals(other$cifNo)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseRequestData;
    }

    public int hashCode() {
        int result = 1;
        Object $accessToken = this.getAccessToken();
        result = result * 59 + ($accessToken == null ? 43 : $accessToken.hashCode());
        Object $cifNo = this.getCifNo();
        result = result * 59 + ($cifNo == null ? 43 : $cifNo.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = this.getAccessToken();
        return "BaseRequestData(accessToken=" + var10000 + ", cifNo=" + this.getCifNo() + ")";
    }

    public BaseRequestData() {
    }

    public BaseRequestData(String accessToken, String cifNo) {
        this.accessToken = accessToken;
        this.cifNo = cifNo;
    }

    private static final class BaseRequestDataBuilderImpl extends BaseRequestDataBuilder<BaseRequestData, BaseRequestDataBuilderImpl> {
        private BaseRequestDataBuilderImpl() {
        }

        protected BaseRequestDataBuilderImpl self() {
            return this;
        }

        public BaseRequestData build() {
            return new BaseRequestData(this);
        }
    }

    public abstract static class BaseRequestDataBuilder<C extends BaseRequestData, B extends BaseRequestDataBuilder<C, B>> extends RequestDataBuilder<C, B> {
        private String accessToken;
        private String cifNo;

        public BaseRequestDataBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this.self();
        }

        public B cifNo(String cifNo) {
            this.cifNo = cifNo;
            return this.self();
        }

        public String toString() {
            String var10000 = super.toString();
            return "BaseRequestData.BaseRequestDataBuilder(super=" + var10000 + ", accessToken=" + this.accessToken + ", cifNo=" + this.cifNo + ")";
        }
    }
}
