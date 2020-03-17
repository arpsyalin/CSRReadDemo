package priv.lyl.readmodel.mutiltread;

import android.util.Log;

import java.util.List;

import priv.lyl.readmodel.factory.ReadFileFactoryImpl;
import priv.lyl.readmodel.interf.IFileAnalysis;
import priv.lyl.readmodel.interf.IFileRead;
import priv.lyl.readmodel.interf.IResultDistribute;
import priv.lyl.readmodel.model.FileResource;

/**
 * 线程实现
 */
public class ReadFileTask implements Runnable {

    public FileResource mFileResource;
    IFileRead mFileRead;
    IFileAnalysis mFileAnalysis;
    IResultDistribute mResultDistribute;

    public ReadFileTask(FileResource fileResource) {
        this.mFileResource = fileResource;
        mFileRead = ReadFileFactoryImpl.getInstance().getFileRead(mFileResource);
        mFileAnalysis = ReadFileFactoryImpl.getInstance().getFileAnalysis(mFileResource);
        mResultDistribute = ReadFileFactoryImpl.getInstance().getResultDistribute(mFileResource);
    }

    @Override
    public void run() {
        if (mFileRead != null) {
            List<List<String>> arr = mFileRead.readFile();
            if (mResultDistribute != null)
                mResultDistribute.distribute(arr, mFileResource);
        }
    }

    public FileResource getFileResource() {
        return mFileResource;
    }
}