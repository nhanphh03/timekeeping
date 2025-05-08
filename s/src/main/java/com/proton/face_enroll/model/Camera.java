package com.proton.face_enroll.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Camera implements Serializable {
    private String name;
    private String rtspURL;
    private String cameraId;
    private String description;
    private double positionX;
    private double positionY;
    @JsonProperty("camera_group")
    private String cameraGroup;
    private String faceSource;
    private String cam3d;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Camera other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else if (Double.compare(this
                    .getPositionX(), other.getPositionX()) != 0) {
                return false;
            } else if (Double.compare(this.getPositionY(), other.getPositionY()) != 0) {
                return false;
            } else {
                label100: {
                    Object thisname = this.getName();
                    Object othername = other.getName();
                    if (thisname == null) {
                        if (othername == null) {
                            break label100;
                        }
                    } else if (thisname.equals(othername)) {
                        break label100;
                    }

                    return false;
                }

                Object thisrtspURL = this.getRtspURL();
                Object otherrtspURL = other.getRtspURL();
                if (thisrtspURL == null) {
                    if (otherrtspURL != null) {
                        return false;
                    }
                } else if (!thisrtspURL.equals(otherrtspURL)) {
                    return false;
                }

                label86: {
                    Object thiscameraId = this.getCameraId();
                    Object othercameraId = other.getCameraId();
                    if (thiscameraId == null) {
                        if (othercameraId == null) {
                            break label86;
                        }
                    } else if (thiscameraId.equals(othercameraId)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object thisdescription = this.getDescription();
                    Object otherdescription = other.getDescription();
                    if (thisdescription == null) {
                        if (otherdescription == null) {
                            break label79;
                        }
                    } else if (thisdescription.equals(otherdescription)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object thisCameraGroup = this.getCameraGroup();
                    Object otherCameraGroup = other.getCameraGroup();
                    if (thisCameraGroup == null) {
                        if (otherCameraGroup == null) {
                            break label72;
                        }
                    } else if (thisCameraGroup.equals(otherCameraGroup)) {
                        break label72;
                    }

                    return false;
                }

                Object thisfaceSource = this.getFaceSource();
                Object otherfaceSource = other.getFaceSource();
                if (thisfaceSource == null) {
                    if (otherfaceSource != null) {
                        return false;
                    }
                } else if (!thisfaceSource.equals(otherfaceSource)) {
                    return false;
                }

                Object thisCam3d = this.getCam3d();
                Object otherCam3d = other.getCam3d();
                if (thisCam3d == null) {
                    return otherCam3d == null;
                } else return thisCam3d.equals(otherCam3d);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Camera;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + Double.hashCode(this.getPositionX());
        result = result * 59 + Double.hashCode(this.getPositionY());
        Object name = this.getName();
        result = result * 59 + (name == null ? 43 : name.hashCode());
        Object rtspURL = this.getRtspURL();
        result = result * 59 + (rtspURL == null ? 43 : rtspURL.hashCode());
        Object cameraId = this.getCameraId();
        result = result * 59 + (cameraId == null ? 43 : cameraId.hashCode());
        Object description = this.getDescription();
        result = result * 59 + (description == null ? 43 : description.hashCode());
        Object cameraGroup = this.getCameraGroup();
        result = result * 59 + (cameraGroup == null ? 43 : cameraGroup.hashCode());
        Object faceSource = this.getFaceSource();
        result = result * 59 + (faceSource == null ? 43 : faceSource.hashCode());
        Object cam3d = this.getCam3d();
        result = result * 59 + (cam3d == null ? 43 : cam3d.hashCode());
        return result;
    }

    public String toString() {
        return "Camera(name=" + this.getName() + ", rtspURL=" + this.getRtspURL()
                + ", cameraId=" + this.getCameraId() + ", description="
                + this.getDescription() + ", positionX="
                + this.getPositionX() + ", positionY="
                + this.getPositionY() + ", cameraGroup="
                + this.getCameraGroup() + ", faceSource="
                + this.getFaceSource() + ", cam3d="
                + this.getCam3d() + ")";
    }

}

