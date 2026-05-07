package com.Prog3.agroptima.entity.dto;

import com.Prog3.agroptima.entity.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateActivityMemberAttendance {
    private String memberIdentifier;
    private AttendanceStatus attendanceStatus;

}
