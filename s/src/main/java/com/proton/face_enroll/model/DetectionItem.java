package com.proton.face_enroll.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetectionItem implements Serializable {
    private static final long serialVersionUID = -4740704235915652517L;
    private byte[] imageData;
    private String imagePath;
    private String id;
    private String peopleId;
    private String peopleName;
    private String groupId;
    private String gender;
    private String groupName;
    private String cameraId;
    private String cameraName;
    private String createdTime;
    private String capturedTime;
    private String firstTimeDay;
    private String firstTimeNoon;
    private String avatarPath;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DetectionItem other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else if (!Arrays.equals(this.getImageData(), other.getImageData())) {
                return false;
            } else {
                Object this$imagePath = this.getImagePath();
                Object other$imagePath = other.getImagePath();
                if (this$imagePath == null) {
                    if (other$imagePath != null) {
                        return false;
                    }
                } else if (!this$imagePath.equals(other$imagePath)) {
                    return false;
                }

                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                label167: {
                    Object this$peopleId = this.getPeopleId();
                    Object other$peopleId = other.getPeopleId();
                    if (this$peopleId == null) {
                        if (other$peopleId == null) {
                            break label167;
                        }
                    } else if (this$peopleId.equals(other$peopleId)) {
                        break label167;
                    }

                    return false;
                }

                label160: {
                    Object this$peopleName = this.getPeopleName();
                    Object other$peopleName = other.getPeopleName();
                    if (this$peopleName == null) {
                        if (other$peopleName == null) {
                            break label160;
                        }
                    } else if (this$peopleName.equals(other$peopleName)) {
                        break label160;
                    }

                    return false;
                }

                Object this$groupId = this.getGroupId();
                Object other$groupId = other.getGroupId();
                if (this$groupId == null) {
                    if (other$groupId != null) {
                        return false;
                    }
                } else if (!this$groupId.equals(other$groupId)) {
                    return false;
                }

                Object this$gender = this.getGender();
                Object other$gender = other.getGender();
                if (this$gender == null) {
                    if (other$gender != null) {
                        return false;
                    }
                } else if (!this$gender.equals(other$gender)) {
                    return false;
                }

                label139: {
                    Object this$groupName = this.getGroupName();
                    Object other$groupName = other.getGroupName();
                    if (this$groupName == null) {
                        if (other$groupName == null) {
                            break label139;
                        }
                    } else if (this$groupName.equals(other$groupName)) {
                        break label139;
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

                Object this$cameraName = this.getCameraName();
                Object other$cameraName = other.getCameraName();
                if (this$cameraName == null) {
                    if (other$cameraName != null) {
                        return false;
                    }
                } else if (!this$cameraName.equals(other$cameraName)) {
                    return false;
                }

                label118: {
                    Object this$createdTime = this.getCreatedTime();
                    Object other$createdTime = other.getCreatedTime();
                    if (this$createdTime == null) {
                        if (other$createdTime == null) {
                            break label118;
                        }
                    } else if (this$createdTime.equals(other$createdTime)) {
                        break label118;
                    }

                    return false;
                }

                label111: {
                    Object this$capturedTime = this.getCapturedTime();
                    Object other$capturedTime = other.getCapturedTime();
                    if (this$capturedTime == null) {
                        if (other$capturedTime == null) {
                            break label111;
                        }
                    } else if (this$capturedTime.equals(other$capturedTime)) {
                        break label111;
                    }

                    return false;
                }

                label104: {
                    Object this$firstTimeDay = this.getFirstTimeDay();
                    Object other$firstTimeDay = other.getFirstTimeDay();
                    if (this$firstTimeDay == null) {
                        if (other$firstTimeDay == null) {
                            break label104;
                        }
                    } else if (this$firstTimeDay.equals(other$firstTimeDay)) {
                        break label104;
                    }

                    return false;
                }

                Object this$firstTimeNoon = this.getFirstTimeNoon();
                Object other$firstTimeNoon = other.getFirstTimeNoon();
                if (this$firstTimeNoon == null) {
                    if (other$firstTimeNoon != null) {
                        return false;
                    }
                } else if (!this$firstTimeNoon.equals(other$firstTimeNoon)) {
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
        return other instanceof DetectionItem;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + Arrays.hashCode(this.getImageData());
        Object $imagePath = this.getImagePath();
        result = result * 59 + ($imagePath == null ? 43 : $imagePath.hashCode());
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $peopleId = this.getPeopleId();
        result = result * 59 + ($peopleId == null ? 43 : $peopleId.hashCode());
        Object $peopleName = this.getPeopleName();
        result = result * 59 + ($peopleName == null ? 43 : $peopleName.hashCode());
        Object $groupId = this.getGroupId();
        result = result * 59 + ($groupId == null ? 43 : $groupId.hashCode());
        Object $gender = this.getGender();
        result = result * 59 + ($gender == null ? 43 : $gender.hashCode());
        Object $groupName = this.getGroupName();
        result = result * 59 + ($groupName == null ? 43 : $groupName.hashCode());
        Object $cameraId = this.getCameraId();
        result = result * 59 + ($cameraId == null ? 43 : $cameraId.hashCode());
        Object $cameraName = this.getCameraName();
        result = result * 59 + ($cameraName == null ? 43 : $cameraName.hashCode());
        Object $createdTime = this.getCreatedTime();
        result = result * 59 + ($createdTime == null ? 43 : $createdTime.hashCode());
        Object $capturedTime = this.getCapturedTime();
        result = result * 59 + ($capturedTime == null ? 43 : $capturedTime.hashCode());
        Object $firstTimeDay = this.getFirstTimeDay();
        result = result * 59 + ($firstTimeDay == null ? 43 : $firstTimeDay.hashCode());
        Object $firstTimeNoon = this.getFirstTimeNoon();
        result = result * 59 + ($firstTimeNoon == null ? 43 : $firstTimeNoon.hashCode());
        Object $avatarPath = this.getAvatarPath();
        result = result * 59 + ($avatarPath == null ? 43 : $avatarPath.hashCode());
        return result;
    }

    public String toString() {
        return "DetectionItem(imageData=" + Arrays.toString(this.getImageData()) + ", imagePath=" + this.getImagePath() + ", id=" + this.getId() + ", peopleId=" + this.getPeopleId() + ", peopleName=" + this.getPeopleName() + ", groupId=" + this.getGroupId() + ", gender=" + this.getGender() + ", groupName=" + this.getGroupName() + ", cameraId=" + this.getCameraId() + ", cameraName=" + this.getCameraName() + ", createdTime=" + this.getCreatedTime() + ", capturedTime=" + this.getCapturedTime() + ", firstTimeDay=" + this.getFirstTimeDay() + ", firstTimeNoon=" + this.getFirstTimeNoon() + ", avatarPath=" + this.getAvatarPath() + ")";
    }

}
