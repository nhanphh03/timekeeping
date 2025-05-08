package com.proton.face_enroll.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Config implements Serializable {
    private static final long serialVersionUID = 3480564398315610464L;
    private String id;
    private String code;
    private String type;
    private String name;
    private String value;
    private String descriptioin;
    private String status;
    private long orderNo;
    private long timemarkCache;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Config other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getOrderNo() != other.getOrderNo()) {
                return false;
            } else if (this.getTimemarkCache() != other.getTimemarkCache()) {
                return false;
            } else {
                label100: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label100;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label100;
                    }

                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                label86: {
                    Object this$type = this.getType();
                    Object other$type = other.getType();
                    if (this$type == null) {
                        if (other$type == null) {
                            break label86;
                        }
                    } else if (this$type.equals(other$type)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label79;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$value = this.getValue();
                    Object other$value = other.getValue();
                    if (this$value == null) {
                        if (other$value == null) {
                            break label72;
                        }
                    } else if (this$value.equals(other$value)) {
                        break label72;
                    }

                    return false;
                }

                Object this$descriptioin = this.getDescriptioin();
                Object other$descriptioin = other.getDescriptioin();
                if (this$descriptioin == null) {
                    if (other$descriptioin != null) {
                        return false;
                    }
                } else if (!this$descriptioin.equals(other$descriptioin)) {
                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    return other$status == null;
                } else return this$status.equals(other$status);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Config;
    }

    public int hashCode() {
        int result = 1;
        long $orderNo = this.getOrderNo();
        result = result * 59 + Long.hashCode($orderNo);
        long $timemarkCache = this.getTimemarkCache();
        result = result * 59 + Long.hashCode($timemarkCache);
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        Object $descriptioin = this.getDescriptioin();
        result = result * 59 + ($descriptioin == null ? 43 : $descriptioin.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "Config(id=" + this.getId() + ", code=" + this.getCode() + ", type=" + this.getType() + ", name="
                + this.getName() + ", value=" + this.getValue() + ", descriptioin=" + this.getDescriptioin()
                + ", status=" + this.getStatus() + ", orderNo=" + this.getOrderNo() + ", timemarkCache=" + this.getTimemarkCache() + ")";
    }
}

