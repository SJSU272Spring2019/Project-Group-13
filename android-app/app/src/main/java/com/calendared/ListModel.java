package com.calendared;

public class ListModel {
    
    private  String Title="";
    private  String Desc="";
    private  String Date="";
    private  String StartTime="";
    private  String EndTime="";
//    private  String Seater="";    
//    private  String Titleation="";    
//     
    /*********** Set Methods ******************/
     
    public void setTitle(String Title)
    {
        this.Title = Title;
    }
     
    public void setDesc(String Desc)
    {
        this.Desc = Desc;
    }
     
    public void setDate(String Date)
    {
        this.Date = Date;
    }
    
    public void setStartTime(String StartTime)
    {
        this.StartTime = StartTime;
    }
    public void setEndTime(String EndTime)
    {
        this.EndTime = EndTime;
    }

//    public void setTitleation(String Titleation)
//    {
//        this.Titleation = Titleation;
//    }
     
    /*********** Get Methods ****************/
     
    public String getTitle()
    {
        return this.Title;
    }
     
    public String getDesc()
    {
        return this.Desc;
    }
 
    public String getDate()
    {
        return this.Date;
    }    
    
    public String getStartTime()
    {
        return this.StartTime;
    }    
    public String getEndTime()
    {
        return this.EndTime;
    }  

//    public String getTitleation()
//    {
//        return this.Titleation;
//    }  
}