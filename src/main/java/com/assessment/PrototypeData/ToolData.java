package com.assessment.PrototypeData;


import java.util.Map;

/***
 * Defines an in memory data store for the limited data in the assessment.  A real database would normally be used.
 */
public class ToolData {
    private final Map<String, ToolInfo> toolInfo;

    public ToolData(Map<String, ToolInfo> toolInfo){
        this.toolInfo = toolInfo;
    }

    public String getToolType(String toolCode){
        return toolInfo.get(toolCode).getToolType();
    }

    public String getBrand(String toolCode){
        return toolInfo.get(toolCode).getBrand();
    }

    public double getDailyCharge(String toolCode){
        return toolInfo.get(toolCode).getDailyCharge();
    }

    public boolean hasWeekdayCharge(String toolCode){
        return toolInfo.get(toolCode).hasWeekdayCharge();
    }

    public boolean hasWeekendCharge(String toolCode){
        return toolInfo.get(toolCode).hasWeekendCharge();
    }

    public boolean hasHolidayCharge(String toolCode){
        return toolInfo.get(toolCode).hasHolidayCharge();
    }

    public boolean hasTool(String toolCode){
        return toolInfo.containsKey(toolCode);
    }
}
