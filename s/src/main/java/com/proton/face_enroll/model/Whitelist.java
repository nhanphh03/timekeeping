package com.proton.face_enroll.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Whitelist implements Serializable {
    private static final long serialVersionUID = -1872370911301473770L;
    private String id;
    private String userId;
    private String peopleId;
    private Timestamp createdTime;
    private String status;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Whitelist other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label71;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label71;
                    }

                    return false;
                }

                Object this$userId = this.getUserId();
                Object other$userId = other.getUserId();
                if (this$userId == null) {
                    if (other$userId != null) {
                        return false;
                    }
                } else if (!this$userId.equals(other$userId)) {
                    return false;
                }

                label57: {
                    Object this$peopleId = this.getPeopleId();
                    Object other$peopleId = other.getPeopleId();
                    if (this$peopleId == null) {
                        if (other$peopleId == null) {
                            break label57;
                        }
                    } else if (this$peopleId.equals(other$peopleId)) {
                        break label57;
                    }

                    return false;
                }

                Object this$createdTime = this.getCreatedTime();
                Object other$createdTime = other.getCreatedTime();
                if (this$createdTime == null) {
                    if (other$createdTime != null) {
                        return false;
                    }
                } else if (!this$createdTime.equals(other$createdTime)) {
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
        return other instanceof Whitelist;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $userId = this.getUserId();
        result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
        Object $peopleId = this.getPeopleId();
        result = result * 59 + ($peopleId == null ? 43 : $peopleId.hashCode());
        Object $createdTime = this.getCreatedTime();
        result = result * 59 + ($createdTime == null ? 43 : $createdTime.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "Whitelist(id=" + this.getId() + ", userId=" + this.getUserId() + ", peopleId=" + this.getPeopleId() + ", createdTime=" + this.getCreatedTime() + ", status=" + this.getStatus() + ")";
    }
}
