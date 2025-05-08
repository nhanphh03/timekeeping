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
public class Detection implements Serializable {
    private static final long serialVersionUID = -699084574841795126L;
    private String imagePath;
    private String cameraId;
    private String peopleId;
    private Long responseTime;
    private String recognizationStatus;
    private String responseRaw;
    private String createdTime;
    private String capturedTime;
    private String firstTimeCheckIn;
    private String firstTimeCheckInNoon;
    private String livenessStatus;
    private String keyId;
    private String avatarPath;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Detection other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else {
                label167: {
                    Object this$responseTime = this.getResponseTime();
                    Object other$responseTime = other.getResponseTime();
                    if (this$responseTime == null) {
                        if (other$responseTime == null) {
                            break label167;
                        }
                    } else if (this$responseTime.equals(other$responseTime)) {
                        break label167;
                    }

                    return false;
                }

                Object this$imagePath = this.getImagePath();
                Object other$imagePath = other.getImagePath();
                if (this$imagePath == null) {
                    if (other$imagePath != null) {
                        return false;
                    }
                } else if (!this$imagePath.equals(other$imagePath)) {
                    return false;
                }

                label153: {
                    Object this$cameraId = this.getCameraId();
                    Object other$cameraId = other.getCameraId();
                    if (this$cameraId == null) {
                        if (other$cameraId == null) {
                            break label153;
                        }
                    } else if (this$cameraId.equals(other$cameraId)) {
                        break label153;
                    }

                    return false;
                }

                Object this$peopleId = this.getPeopleId();
                Object other$peopleId = other.getPeopleId();
                if (this$peopleId == null) {
                    if (other$peopleId != null) {
                        return false;
                    }
                } else if (!this$peopleId.equals(other$peopleId)) {
                    return false;
                }

                label139: {
                    Object this$recognizationStatus = this.getRecognizationStatus();
                    Object other$recognizationStatus = other.getRecognizationStatus();
                    if (this$recognizationStatus == null) {
                        if (other$recognizationStatus == null) {
                            break label139;
                        }
                    } else if (this$recognizationStatus.equals(other$recognizationStatus)) {
                        break label139;
                    }

                    return false;
                }

                Object this$responseRaw = this.getResponseRaw();
                Object other$responseRaw = other.getResponseRaw();
                if (this$responseRaw == null) {
                    if (other$responseRaw != null) {
                        return false;
                    }
                } else if (!this$responseRaw.equals(other$responseRaw)) {
                    return false;
                }

                label125: {
                    Object this$createdTime = this.getCreatedTime();
                    Object other$createdTime = other.getCreatedTime();
                    if (this$createdTime == null) {
                        if (other$createdTime == null) {
                            break label125;
                        }
                    } else if (this$createdTime.equals(other$createdTime)) {
                        break label125;
                    }

                    return false;
                }

                label118: {
                    Object this$capturedTime = this.getCapturedTime();
                    Object other$capturedTime = other.getCapturedTime();
                    if (this$capturedTime == null) {
                        if (other$capturedTime == null) {
                            break label118;
                        }
                    } else if (this$capturedTime.equals(other$capturedTime)) {
                        break label118;
                    }

                    return false;
                }

                Object this$firstTimeCheckIn = this.getFirstTimeCheckIn();
                Object other$firstTimeCheckIn = other.getFirstTimeCheckIn();
                if (this$firstTimeCheckIn == null) {
                    if (other$firstTimeCheckIn != null) {
                        return false;
                    }
                } else if (!this$firstTimeCheckIn.equals(other$firstTimeCheckIn)) {
                    return false;
                }

                label104: {
                    Object this$firstTimeCheckInNoon = this.getFirstTimeCheckInNoon();
                    Object other$firstTimeCheckInNoon = other.getFirstTimeCheckInNoon();
                    if (this$firstTimeCheckInNoon == null) {
                        if (other$firstTimeCheckInNoon == null) {
                            break label104;
                        }
                    } else if (this$firstTimeCheckInNoon.equals(other$firstTimeCheckInNoon)) {
                        break label104;
                    }

                    return false;
                }

                label97: {
                    Object this$livenessStatus = this.getLivenessStatus();
                    Object other$livenessStatus = other.getLivenessStatus();
                    if (this$livenessStatus == null) {
                        if (other$livenessStatus == null) {
                            break label97;
                        }
                    } else if (this$livenessStatus.equals(other$livenessStatus)) {
                        break label97;
                    }

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

                Object this$avatarPath = this.getAvatarPath();
                Object other$avatarPath = other.getAvatarPath();
                if (this$avatarPath == null) {
                    return other$avatarPath == null;
                } else return this$avatarPath.equals(other$avatarPath);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Detection;
    }

    public int hashCode() {
        int result = 1;
        Object $responseTime = this.getResponseTime();
        result = result * 59 + ($responseTime == null ? 43 : $responseTime.hashCode());
        Object $imagePath = this.getImagePath();
        result = result * 59 + ($imagePath == null ? 43 : $imagePath.hashCode());
        Object $cameraId = this.getCameraId();
        result = result * 59 + ($cameraId == null ? 43 : $cameraId.hashCode());
        Object $peopleId = this.getPeopleId();
        result = result * 59 + ($peopleId == null ? 43 : $peopleId.hashCode());
        Object $recognizationStatus = this.getRecognizationStatus();
        result = result * 59 + ($recognizationStatus == null ? 43 : $recognizationStatus.hashCode());
        Object $responseRaw = this.getResponseRaw();
        result = result * 59 + ($responseRaw == null ? 43 : $responseRaw.hashCode());
        Object $createdTime = this.getCreatedTime();
        result = result * 59 + ($createdTime == null ? 43 : $createdTime.hashCode());
        Object $capturedTime = this.getCapturedTime();
        result = result * 59 + ($capturedTime == null ? 43 : $capturedTime.hashCode());
        Object $firstTimeCheckIn = this.getFirstTimeCheckIn();
        result = result * 59 + ($firstTimeCheckIn == null ? 43 : $firstTimeCheckIn.hashCode());
        Object $firstTimeCheckInNoon = this.getFirstTimeCheckInNoon();
        result = result * 59 + ($firstTimeCheckInNoon == null ? 43 : $firstTimeCheckInNoon.hashCode());
        Object $livenessStatus = this.getLivenessStatus();
        result = result * 59 + ($livenessStatus == null ? 43 : $livenessStatus.hashCode());
        Object $keyId = this.getKeyId();
        result = result * 59 + ($keyId == null ? 43 : $keyId.hashCode());
        Object $avatarPath = this.getAvatarPath();
        result = result * 59 + ($avatarPath == null ? 43 : $avatarPath.hashCode());
        return result;
    }

    public String toString() {
        return "Detection(imagePath=" + this.getImagePath() + ", cameraId=" + this.getCameraId()
                + ", peopleId=" + this.getPeopleId() + ", responseTime=" + this.getResponseTime()
                + ", recognizationStatus=" + this.getRecognizationStatus() + ", responseRaw=" + this.getResponseRaw()
                + ", createdTime=" + this.getCreatedTime() + ", capturedTime=" + this.getCapturedTime()
                + ", firstTimeCheckIn=" + this.getFirstTimeCheckIn() + ", firstTimeCheckInNoon="
                + this.getFirstTimeCheckInNoon() + ", livenessStatus=" + this.getLivenessStatus()
                + ", keyId=" + this.getKeyId() + ", avatarPath=" + this.getAvatarPath() + ")";
    }
}
