package com.thelittlegym.mobile.enums;

/**
 * Created by TONY on 2017/8/25.
 */
public enum menuEnum {
    ACTIVITY("activity","活动"),
    THEME("theme","课程亮点");
    private String Id;
    private String Name;

    menuEnum(String  id, String name) {
        Id = id;
        Name = name;
    }
    public static menuEnum getMenu(String Id){
        switch (Id){
            case "activity":
                return ACTIVITY;
            case "theme":
                return THEME;
            default:
                return ACTIVITY;
        }
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return "menuEnum{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
