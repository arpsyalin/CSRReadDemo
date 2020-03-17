package priv.lyl.readmodel.interf;

import java.util.List;

import priv.lyl.readmodel.model.FileResource;

/**
 * 分发结果 方式有多种 需要哪种拓展就可以
 * 这里需要注意文件长度解析出来的arr太大可分段返回
 */
public interface IResultDistribute {
    void distribute(List<List<String>> arr, FileResource mFileResource);

    /**
     * 如需要准确对应参数可以解析后返回Object[]
     *
     * @param arr
     * @param mFileResource
     */
    void distributeObject(List<Object[]> arr, FileResource mFileResource);
}
