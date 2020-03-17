package priv.lyl.readmodel.interf;

import java.util.List;

//读取注意文件如果很大最好是分段读取文件分段分发结果 这里返回的都是
public interface IFileRead {
    List<List<String>> readFile();
}
