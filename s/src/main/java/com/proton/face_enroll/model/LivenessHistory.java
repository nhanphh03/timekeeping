package com.proton.face_enroll.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LivenessHistory implements Serializable {
    private static final long serialVersionUID = -7471854545903215016L;
    private String name;
    private String status;
    private Date actionTime;
    private String cameraId;
    private String rawResponse;
    private String colorBase64;
    private String depthBase64;
    private String node;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof LivenessHistory other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else {
                label107: {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label107;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label107;
                    }

                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                Object this$actionTime = this.getActionTime();
                Object other$actionTime = other.getActionTime();
                if (this$actionTime == null) {
                    if (other$actionTime != null) {
                        return false;
                    }
                } else if (!this$actionTime.equals(other$actionTime)) {
                    return false;
                }

                label86: {
                    Object this$cameraId = this.getCameraId();
                    Object other$cameraId = other.getCameraId();
                    if (this$cameraId == null) {
                        if (other$cameraId == null) {
                            break label86;
                        }
                    } else if (this$cameraId.equals(other$cameraId)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$rawResponse = this.getRawResponse();
                    Object other$rawResponse = other.getRawResponse();
                    if (this$rawResponse == null) {
                        if (other$rawResponse == null) {
                            break label79;
                        }
                    } else if (this$rawResponse.equals(other$rawResponse)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$colorBase64 = this.getColorBase64();
                    Object other$colorBase64 = other.getColorBase64();
                    if (this$colorBase64 == null) {
                        if (other$colorBase64 == null) {
                            break label72;
                        }
                    } else if (this$colorBase64.equals(other$colorBase64)) {
                        break label72;
                    }

                    return false;
                }

                Object this$depthBase64 = this.getDepthBase64();
                Object other$depthBase64 = other.getDepthBase64();
                if (this$depthBase64 == null) {
                    if (other$depthBase64 != null) {
                        return false;
                    }
                } else if (!this$depthBase64.equals(other$depthBase64)) {
                    return false;
                }

                Object this$node = this.getNode();
                Object other$node = other.getNode();
                if (this$node == null) {
                    return other$node == null;
                } else return this$node.equals(other$node);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LivenessHistory;
    }

    public int hashCode() {
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $actionTime = this.getActionTime();
        result = result * 59 + ($actionTime == null ? 43 : $actionTime.hashCode());
        Object $cameraId = this.getCameraId();
        result = result * 59 + ($cameraId == null ? 43 : $cameraId.hashCode());
        Object $rawResponse = this.getRawResponse();
        result = result * 59 + ($rawResponse == null ? 43 : $rawResponse.hashCode());
        Object $colorBase64 = this.getColorBase64();
        result = result * 59 + ($colorBase64 == null ? 43 : $colorBase64.hashCode());
        Object $depthBase64 = this.getDepthBase64();
        result = result * 59 + ($depthBase64 == null ? 43 : $depthBase64.hashCode());
        Object $node = this.getNode();
        result = result * 59 + ($node == null ? 43 : $node.hashCode());
        return result;
    }

    public String toString() {
        return "LivenessHistory(name=" + this.getName() + ", status=" + this.getStatus() + ", actionTime=" + this.getActionTime() + ", cameraId=" + this.getCameraId() + ", rawResponse=" + this.getRawResponse() + ", colorBase64=" + this.getColorBase64() + ", depthBase64=" + this.getDepthBase64() + ", node=" + this.getNode() + ")";
    }

}
