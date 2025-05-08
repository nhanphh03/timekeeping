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
public class People implements Serializable {
    private static final long serialVersionUID = 5881523207835488493L;
    private String peopleId;
    private String name;
    private Department group;
    private String lastCheckInTime;
    private String mobilePhone;
    private int enableNotification;
    private String gender;
    private String greetingText;
    private String greetingAudio;
    private long timemarkCache;
    private String avatarPath;
    private String imageBase64;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof People other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getEnableNotification() != other.getEnableNotification()) {
                return false;
            } else if (this.getTimemarkCache() != other.getTimemarkCache()) {
                return false;
            } else {
                label136: {
                    Object this$peopleId = this.getPeopleId();
                    Object other$peopleId = other.getPeopleId();
                    if (this$peopleId == null) {
                        if (other$peopleId == null) {
                            break label136;
                        }
                    } else if (this$peopleId.equals(other$peopleId)) {
                        break label136;
                    }

                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                label122: {
                    Object this$group = this.getGroup();
                    Object other$group = other.getGroup();
                    if (this$group == null) {
                        if (other$group == null) {
                            break label122;
                        }
                    } else if (this$group.equals(other$group)) {
                        break label122;
                    }

                    return false;
                }

                label115: {
                    Object this$lastCheckInTime = this.getLastCheckInTime();
                    Object other$lastCheckInTime = other.getLastCheckInTime();
                    if (this$lastCheckInTime == null) {
                        if (other$lastCheckInTime == null) {
                            break label115;
                        }
                    } else if (this$lastCheckInTime.equals(other$lastCheckInTime)) {
                        break label115;
                    }

                    return false;
                }

                Object this$mobilePhone = this.getMobilePhone();
                Object other$mobilePhone = other.getMobilePhone();
                if (this$mobilePhone == null) {
                    if (other$mobilePhone != null) {
                        return false;
                    }
                } else if (!this$mobilePhone.equals(other$mobilePhone)) {
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

                label94: {
                    Object this$greetingText = this.getGreetingText();
                    Object other$greetingText = other.getGreetingText();
                    if (this$greetingText == null) {
                        if (other$greetingText == null) {
                            break label94;
                        }
                    } else if (this$greetingText.equals(other$greetingText)) {
                        break label94;
                    }

                    return false;
                }

                label87: {
                    Object this$greetingAudio = this.getGreetingAudio();
                    Object other$greetingAudio = other.getGreetingAudio();
                    if (this$greetingAudio == null) {
                        if (other$greetingAudio == null) {
                            break label87;
                        }
                    } else if (this$greetingAudio.equals(other$greetingAudio)) {
                        break label87;
                    }

                    return false;
                }

                Object this$avatarPath = this.getAvatarPath();
                Object other$avatarPath = other.getAvatarPath();
                if (this$avatarPath == null) {
                    if (other$avatarPath != null) {
                        return false;
                    }
                } else if (!this$avatarPath.equals(other$avatarPath)) {
                    return false;
                }

                Object this$imageBase64 = this.getImageBase64();
                Object other$imageBase64 = other.getImageBase64();
                if (this$imageBase64 == null) {
                    return other$imageBase64 == null;
                } else return this$imageBase64.equals(other$imageBase64);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof People;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getEnableNotification();
        long $timemarkCache = this.getTimemarkCache();
        result = result * 59 + Long.hashCode($timemarkCache);
        Object $peopleId = this.getPeopleId();
        result = result * 59 + ($peopleId == null ? 43 : $peopleId.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $group = this.getGroup();
        result = result * 59 + ($group == null ? 43 : $group.hashCode());
        Object $lastCheckInTime = this.getLastCheckInTime();
        result = result * 59 + ($lastCheckInTime == null ? 43 : $lastCheckInTime.hashCode());
        Object $mobilePhone = this.getMobilePhone();
        result = result * 59 + ($mobilePhone == null ? 43 : $mobilePhone.hashCode());
        Object $gender = this.getGender();
        result = result * 59 + ($gender == null ? 43 : $gender.hashCode());
        Object $greetingText = this.getGreetingText();
        result = result * 59 + ($greetingText == null ? 43 : $greetingText.hashCode());
        Object $greetingAudio = this.getGreetingAudio();
        result = result * 59 + ($greetingAudio == null ? 43 : $greetingAudio.hashCode());
        Object $avatarPath = this.getAvatarPath();
        result = result * 59 + ($avatarPath == null ? 43 : $avatarPath.hashCode());
        Object $imageBase64 = this.getImageBase64();
        result = result * 59 + ($imageBase64 == null ? 43 : $imageBase64.hashCode());
        return result;
    }

    public String toString() {
        return "People(peopleId=" + this.getPeopleId() + ", name=" + this.getName() + ", group=" + this.getGroup() + ", lastCheckInTime=" + this.getLastCheckInTime() + ", mobilePhone=" + this.getMobilePhone() + ", enableNotification=" + this.getEnableNotification() + ", gender=" + this.getGender() + ", greetingText=" + this.getGreetingText() + ", greetingAudio=" + this.getGreetingAudio() + ", timemarkCache=" + this.getTimemarkCache() + ", avatarPath=" + this.getAvatarPath() + ", imageBase64=" + this.getImageBase64() + ")";
    }
}
