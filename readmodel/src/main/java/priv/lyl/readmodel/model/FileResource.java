package priv.lyl.readmodel.model;

import java.io.Serializable;

/**
 * 用途：描述文件资源任务类型
 */
public class FileResource implements Serializable {
    //优先级插入到最后
    public static final int END = 0;
    //插入到最先
    public static final int FRIST = 1;

    //路径，url或者其他
    String uri;
    //文件读取的正则 暂时无用
    String regular;
    //路径，url的类型暂时无用
    int type;
    //优先级
    int index;
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
