package news;

public class PersonJava {
    public static void main(String[] args) {
        MusicPlayer.INSTANCE.getState();//调用kotlin中的单例类

        Latitude latitude = Latitude.ofDouble(3.0);//调用kotlin 的半生对象 相当于静态方法
        String tag = Latitude.TAG;

    }
}
