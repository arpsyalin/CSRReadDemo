package priv.lyl.readmodel.interf;

import priv.lyl.readmodel.model.FileResource;

public interface IReadFileFactory {
    IFileRead getFileRead(FileResource model);

    IFileAnalysis getFileAnalysis(FileResource model);

    IResultDistribute getResultDistribute(FileResource model);
}
