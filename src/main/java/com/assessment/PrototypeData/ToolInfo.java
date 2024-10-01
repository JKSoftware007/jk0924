package com.assessment.PrototypeData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/***
 * Defines the information used to completely describe a tool in the system
 */
@Getter
@Setter
@AllArgsConstructor
public class ToolInfo {
    private String toolType;
    private String brand;
    private double dailyCharge;

    @Getter(AccessLevel.NONE)
    private boolean weekdayCharge;

    @Getter(AccessLevel.NONE)
    private boolean weekendCharge;

    @Getter(AccessLevel.NONE)
    private boolean holidayCharge;

    public boolean hasWeekdayCharge(){
        return weekdayCharge;
    }

    public boolean hasWeekendCharge(){
        return weekendCharge;
    }

    public boolean hasHolidayCharge(){
        return holidayCharge;
    }
}
