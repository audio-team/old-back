package org.soundwhere.backend.audio.dto;

public class TagDTO {
    public String[] tags;

    private String[] small_class=new String[]{
            "狗","公鸡","猪","牛","青蛙","猫","母鸡","昆虫","羊","乌鸦",
            "雨","海浪","噼啪作响的火焰","蟋蟀","叽叽喳喳的鸟儿","水滴","风","倒水","马桶冲水","雷暴",
            "哭泣的宝宝","打喷嚏","鼓掌","呼吸","咳嗽","脚步","笑哈哈","刷牙","打鼾","喝酒、啜饮",
            "敲门声","鼠标点击","键盘打字","门，木头吱吱作响","罐头打开","洗衣机","吸尘器","时钟闹钟","时钟滴答作响","玻璃破碎",
            "直升机","电锯","警笛","汽车喇叭","发动机","火车","教堂钟声","飞机","烟火","手锯"};

    private String[] big_class=new String[]{
            "动物","自然声","人声","室内声","室外声"
    };

    public TagDTO(){
        this.tags=new String[2];
    }

    public String getBig_class(int index){
        return big_class[index];
    }

    public String getSmall_class(int index){
        return small_class[index];
    }

    public void setBig_class(int index){
        this.tags[0]=getBig_class(index);
    }

    public void setSmall_class(int index){
        this.tags[1]=getSmall_class(index);
    }



}
