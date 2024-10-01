package com.assessment.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
    private String errorMsg;
    private int status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorInfo errorInfo = (ErrorInfo) o;
        return getStatus() == errorInfo.getStatus() && Objects.equals(getErrorMsg(), errorInfo.getErrorMsg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getErrorMsg(), getStatus());
    }
}
