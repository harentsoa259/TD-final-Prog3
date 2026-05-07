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
public class ActivityMemberAttendance {
    private String id;
    private MemberDescription memberDescription;
    private AttendanceStatus attendanceStatus;
}