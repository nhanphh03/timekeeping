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
public class ImageData implements Serializable {
    private static final long serialVersionUID = -4682848182845723516L;
    private String fileName;
    private byte[] imageData;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ImageData other)) {
            return false;
        } else {
            if (other.canEqual(this)) {
                Object this$fileName = this.getFileName();
                Object other$fileName = other.getFileName();
                if (this$fileName == null) {
                    if (other$fileName == null) {
                        return Arrays.equals(this.getImageData(), other.getImageData());
                    }
                } else if (this$fileName.equals(other$fileName)) {
                    return Arrays.equals(this.getImageData(), other.getImageData());
                }

            }
            return false;
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ImageData;
    }

    public int hashCode() {
        int result = 1;
        Object $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        result = result * 59 + Arrays.hashCode(this.getImageData());
        return result;
    }

    public String toString() {
        return "ImageData(fileName=" + this.getFileName() + ", imageData=" + Arrays.toString(this.getImageData()) + ")";
    }

}
