package priv.lyl.readmodel.interf;

import java.util.List;

import priv.lyl.readmodel.model.FileResource;

/**
 * 分发结果
 */
public interface IResultDistribute {
    void distribute(List<List<String>> arr, FileResource mFileResource);
}
