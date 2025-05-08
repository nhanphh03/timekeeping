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
public class Timesheet implements Serializable {
    private static final long serialVersionUID = 6605679462630182084L;
    private String people_id;
    private String first_time;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Timesheet other)) {
            return false;
        } else {
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$people_id = this.getPeople_id();
                Object other$people_id = other.getPeople_id();
                if (this$people_id == null) {
                    if (other$people_id != null) {
                        return false;
                    }
                } else if (!this$people_id.equals(other$people_id)) {
                    return false;
                }

                Object this$first_time = this.getFirst_time();
                Object other$first_time = other.getFirst_time();
                if (this$first_time == null) {
                    return other$first_time == null;
                } else return this$first_time.equals(other$first_time);
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Timesheet;
    }

    public int hashCode() {
        int result = 1;
        Object $people_id = this.getPeople_id();
        result = result * 59 + ($people_id == null ? 43 : $people_id.hashCode());
        Object $first_time = this.getFirst_time();
        result = result * 59 + ($first_time == null ? 43 : $first_time.hashCode());
        return result;
    }

    public String toString() {
        return "Timesheet(people_id=" + this.getPeople_id() + ", first_time=" + this.getFirst_time() + ")";
    }

}
