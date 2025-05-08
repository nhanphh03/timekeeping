package com.proton.face_enroll.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CapturedImage implements Serializable {
    private static final long serialVersionUID = 4136066799979876105L;
    private String pathImage;
    private String keyId;
    private String detectStatus;
    private String capturedTime;
    private String peopleId;
    private String cameraId;
    private String responseRaw;
    private Long responseTime;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CapturedImage other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else {
                label107: {
                    Object this$responseTime = this.getResponseTime();
                    Object other$responseTime = other.getResponseTime();
                    if (this$responseTime == null) {
                        if (other$responseTime == null) {
                            break label107;
                        }
                    } else if (this$responseTime.equals(other$responseTime)) {
                        break label107;
                    }

                    return false;
                }

                Object this$pathImage = this.getPathImage();
                Object other$pathImage = other.getPathImage();
                if (this$pathImage == null) {
                    if (other$pathImage != null) {
                        return false;
                    }
                } else if (!this$pathImage.equals(other$pathImage)) {
                    return false;
                }

                Object this$keyId = this.getKeyId();
                Object other$keyId = other.getKeyId();
                if (this$keyId == null) {
                    if (other$keyId != null) {
                        return false;
                    }
                } else if (!this$keyId.equals(other$keyId)) {
                    return false;
                }

                label86: {
                    Object this$detectStatus = this.getDetectStatus();
                    Object other$detectStatus = other.getDetectStatus();
                    if (this$detectStatus == null) {
                        if (other$detectStatus == null) {
                            break label86;
                        }
                    } else if (this$detectStatus.equals(other$detectStatus)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$capturedTime = this.getCapturedTime();
                    Object other$capturedTime = other.getCapturedTime();
                    if (this$capturedTime == null) {
                        if (other$capturedTime == null) {
                            break label79;
                        }
                    } else if (this$capturedTime.equals(other$capturedTime)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$peopleId = this.getPeopleId();
                    Object other$peopleId = other.getPeopleId();
                    if (this$peopleId == null) {
                        if (other$peopleId == null) {
                            break label72;
                        }
                    } else if (this$peopleId.equals(other$peopleId)) {
                        break label72;
                    }

                    return false;
                }

                Object this$cameraId = this.getCameraId();
                Object other$cameraId = other.getCameraId();
                if (this$cameraId == null) {
                    if (other$cameraId != null) {
                        return false;
                    }
                } else if (!this$cameraId.equals(other$cameraId)) {
                    return false;
                }

                Object this$responseRaw = this.getResponseRaw();
                Object other$responseRaw = other.getResponseRaw();
                if (this$responseRaw == null) {
                    return other$responseRaw == null;
                } else return this$responseRaw.equals(other$responseRaw);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CapturedImage;
    }

    public int hashCode() {
        int result = 1;
        Object $responseTime = this.getResponseTime();
        result = result * 59 + ($responseTime == null ? 43 : $responseTime.hashCode());
        Object $pathImage = this.getPathImage();
        result = result * 59 + ($pathImage == null ? 43 : $pathImage.hashCode());
        Object $keyId = this.getKeyId();
        result = result * 59 + ($keyId == null ? 43 : $keyId.hashCode());
        Object $detectStatus = this.getDetectStatus();
        result = result * 59 + ($detectStatus == null ? 43 : $detectStatus.hashCode());
        Object $capturedTime = this.getCapturedTime();
        result = result * 59 + ($capturedTime == null ? 43 : $capturedTime.hashCode());
        Object $peopleId = this.getPeopleId();
        result = result * 59 + ($peopleId == null ? 43 : $peopleId.hashCode());
        Object $cameraId = this.getCameraId();
        result = result * 59 + ($cameraId == null ? 43 : $cameraId.hashCode());
        Object $responseRaw = this.getResponseRaw();
        result = result * 59 + ($responseRaw == null ? 43 : $responseRaw.hashCode());
        return result;
    }

    public String toString() {
        return "CapturedImage(pathImage=" + this.getPathImage() + ", keyId=" + this.getKeyId() + ", detectStatus=" + this.getDetectStatus() + ", capturedTime=" + this.getCapturedTime() + ", peopleId=" + this.getPeopleId() + ", cameraId=" + this.getCameraId() + ", responseRaw=" + this.getResponseRaw() + ", responseTime=" + this.getResponseTime() + ")";
    }
}
